package com.ssafy.trippals.board.service;

import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardFileDto;
import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.dto.BoardResultDto;
import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardFile;
import com.ssafy.trippals.board.repository.BoardCustomRepository;
import com.ssafy.trippals.board.repository.BoardFileRepository;
import com.ssafy.trippals.board.repository.BoardRepository;
import com.ssafy.trippals.board.repository.BookmarkRepository;
import com.ssafy.trippals.common.exception.BoardNotFoundException;
import com.ssafy.trippals.common.file.FileUploadService;
import com.ssafy.trippals.common.file.UploadedFile;
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
    private final BoardCustomRepository boardCustomRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FileUploadService fileUploadService;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Override
    public BoardResultDto boardList(BoardParamDto boardParamDto) {

        BoardResultDto boardResultDto = new BoardResultDto();

        try {
            List<BoardDto> list = boardCustomRepository.findBoard(boardParamDto);
            int count = boardCustomRepository.countBoard(boardParamDto.getSearchWord());

            boardResultDto.setList(list);
            boardResultDto.setCount(count);

        }catch(Exception e) {
            e.printStackTrace();
        }

        return boardResultDto;
    }

    @Override
    public BoardResultDto boardListSearchWord(BoardParamDto boardParamDto) {

        BoardResultDto boardResultDto = new BoardResultDto();

        try {
            List<BoardDto> list = boardCustomRepository.findBoardBySearchWord(boardParamDto);
            int count = boardCustomRepository.countBySearchWord(boardParamDto.getSearchWord());
            System.out.println(boardParamDto);
            boardResultDto.setList(list);
            boardResultDto.setCount(count);

        }catch(Exception e) {
            e.printStackTrace();
        }
        return boardResultDto;
    }

    @Override
    @Transactional
    public BoardResultDto boardDetail(BoardParamDto boardParamDto) {

        BoardResultDto boardResultDto = new BoardResultDto();

        try {
            int userReadCnt = boardCustomRepository.findReadByUser(boardParamDto);
            if( userReadCnt == 0 ) {//두 메소드 한 트랜잭션
                boardCustomRepository.insertReadByUser(boardParamDto.getBoardSeq(), boardParamDto.getUserSeq());
                boardCustomRepository.boardReadCountUpdate(boardParamDto.getBoardSeq());
            }

            BoardDto dto = boardCustomRepository.findBoardBySeq(boardParamDto.getBoardSeq());
            List<BoardFileDto> fileList = boardCustomRepository.boardDetailFileList(dto.getSeq());
            boolean checkBookmark = bookmarkRepository.existsBoardBookmarkByBoardSeqAndUserSeq(boardParamDto.getBoardSeq(), boardParamDto.getUserSeq());

            dto.setFileList(fileList);
            boardResultDto.setDto(dto);
            boardResultDto.setCheckBookmark(checkBookmark);

        }catch(Exception e) {
            e.printStackTrace();

        }

        return boardResultDto;
    }

    @Override
    @Transactional
    //DB 저장 먼저 하면 안되는 이유
    //DB 트랜잭션 롤백이 catch 보다 우선
    public BoardResultDto boardInsert(BoardDto dto, MultipartHttpServletRequest request) {

        BoardResultDto boardResultDto = new BoardResultDto();

        List<File> rollbackFileList = new ArrayList<>();

        try {
            boardRepository.save(dto.convertToBoard()); // useGeneratedKeys="true" keyProperty="boardId"

            List<MultipartFile> fileList = request.getFiles("file");

            for (MultipartFile part : fileList) {

                int boardSeq = dto.getSeq();

                UploadedFile uploaded = fileUploadService.uploadFile(part);

                rollbackFileList.add(uploaded.getSaveFile());

                // Table Insert
                BoardFileDto boardFileDto = new BoardFileDto();
                boardFileDto.setBoardSeq(boardSeq);
                boardFileDto.setFileName(uploaded.getFileName());
                boardFileDto.setFileSize(part.getSize());
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
        return boardResultDto;
    }

    @Override
    @Transactional
    public BoardResultDto boardUpdate(BoardDto dto, MultipartHttpServletRequest request){

        BoardResultDto boardResultDto = new BoardResultDto();

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
                boardFileDto.setFileSize(part.getSize());
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

        return boardResultDto;
    }

    @Override
    @Transactional
    public BoardResultDto boardDelete(int boardSeq,int userSeq) {

        BoardResultDto boardResultDto = new BoardResultDto();

        try {
            boardFileRepository.findAllByBoardSeq(boardSeq).stream()
                    .map(BoardFile::getFileUuid)
                    .forEach(fileUploadService::deleteFile);

            boardRepository.deleteById(boardSeq);
        }catch(Exception e) {
            e.printStackTrace();

        }

        return boardResultDto;
    }


}
