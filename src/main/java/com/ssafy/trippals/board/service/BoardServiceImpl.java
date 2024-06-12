package com.ssafy.trippals.board.service;

import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardFileDto;
import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardFile;
import com.ssafy.trippals.board.entity.BoardUserRead;
import com.ssafy.trippals.board.entity.BoardUserReadId;
import com.ssafy.trippals.board.repository.BoardFileRepository;
import com.ssafy.trippals.board.repository.BoardRepository;
import com.ssafy.trippals.board.repository.BoardUserReadRepository;
import com.ssafy.trippals.board.repository.BookmarkRepository;
import com.ssafy.trippals.common.exception.BoardNotFoundException;
import com.ssafy.trippals.common.file.FileUploadService;
import com.ssafy.trippals.common.file.UploadedFile;
import com.ssafy.trippals.common.page.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FileUploadService fileUploadService;
    private final BoardUserReadRepository boardUserReadRepository;

    @Override
    public PageResponse<BoardDto> boardList(BoardParamDto boardParamDto) {

        List<BoardDto> contents = boardRepository.findBoard(boardParamDto).stream()
                                .map(BoardDto::new)
                                .toList();

        int count = Math.toIntExact(boardRepository.countBoard(boardParamDto.getSearchWord()));

        return new PageResponse<>(contents, boardParamDto.getOffset(), boardParamDto.getLimit(), count);
    }

    @Override
    @Transactional
    public BoardDto boardDetail(int boardSeq, int userSeq) {

        long userReadCnt = boardRepository.findReadByUser(boardSeq, userSeq);
        Board board = boardRepository.findById(boardSeq)
                .orElseThrow(BoardNotFoundException::new);

        if( userReadCnt == 0 ) {//두 메소드 한 트랜잭션
            board.setReadCount(board.getReadCount() + 1);
            boardUserReadRepository.save(new BoardUserRead(new BoardUserReadId(boardSeq, userSeq)));
        }

        List<BoardFile> fileList = boardFileRepository.findAllByBoardSeq(board.getSeq());
        boolean checkBookmark = bookmarkRepository.existsBoardBookmarkByBoardSeqAndUserSeq(boardSeq, userSeq);

        BoardDto boardDto = new BoardDto(board);
        boardDto.setBookmark(checkBookmark);
        boardDto.setFileList(fileList.stream().map(BoardFileDto::new).toList());

        return boardDto;
    }

    @Override
    @Transactional
    public void boardInsert(BoardDto dto, MultipartHttpServletRequest request) {
        List<File> rollbackFileList = new ArrayList<>();

        try {
            boardRepository.save(dto.convertToBoard());

            List<MultipartFile> fileList = request.getFiles("file");

            for (MultipartFile part : fileList) {

                int boardSeq = dto.getSeq();

                UploadedFile uploaded = fileUploadService.uploadFile(part);

                rollbackFileList.add(uploaded.getSaveFile());

                // Table Insert
                BoardFileDto boardFileDto = new BoardFileDto();
                boardFileDto.setBoardSeq(boardSeq);
                boardFileDto.setFileName(uploaded.getFileName());
                boardFileDto.setFileSize(Math.toIntExact(part.getSize()));
                boardFileDto.setFileContentType(part.getContentType());
                boardFileDto.setFileUUID(uploaded.getFileUUID());

                boardFileRepository.save(boardFileDto.convertToBoardFile());
            }
        }catch(Exception e) {
            e.printStackTrace();

            for(File file : rollbackFileList) {
                if(file.exists()) {
                    file.delete();
                }
            }
        }
    }

    @Override
    @Transactional
    public void boardUpdate(BoardDto dto, MultipartHttpServletRequest request){
        List<File> rollbackFileList = new ArrayList<>();
        try {
            Board board = boardRepository.findById(dto.getSeq()).orElseThrow(BoardNotFoundException::new);

            List<MultipartFile> fileList = request.getFiles("file");

            boardFileRepository.findAllByBoardSeq(board.getSeq()).stream()
                    .map(BoardFile::getFileUuid)
                    .forEach(fileUploadService::deleteFile);

            boardFileRepository.deleteAllByBoardSeq(board.getSeq());

            for (MultipartFile part : fileList) {
                int boardSeq = dto.getSeq();

                UploadedFile uploaded = fileUploadService.uploadFile(part);
                rollbackFileList.add(uploaded.getSaveFile());

                // Table Insert
                BoardFileDto boardFileDto = new BoardFileDto();
                boardFileDto.setBoardSeq(boardSeq);
                boardFileDto.setFileName(uploaded.getFileName());
                boardFileDto.setFileSize(Math.toIntExact(part.getSize()));
                boardFileDto.setFileContentType(part.getContentType());
                boardFileDto.setFileUUID(uploaded.getFileUUID());

                boardFileRepository.save(boardFileDto.convertToBoardFile());
            }
        } catch(Exception e) {
            e.printStackTrace();

            for(File file : rollbackFileList) {
                if(file.exists()) {
                    file.delete();
                }
            }


        }
    }

    @Override
    @Transactional
    public void boardDelete(int boardSeq,int userSeq) {
        boardFileRepository.findAllByBoardSeq(boardSeq).stream()
                .map(BoardFile::getFileUuid)
                .forEach(fileUploadService::deleteFile);

        boardRepository.deleteById(boardSeq);
    }
}
