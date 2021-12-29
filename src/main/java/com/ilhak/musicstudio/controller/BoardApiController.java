package com.ilhak.musicstudio.controller;

import java.util.List;

import com.ilhak.musicstudio.model.Board;
import com.ilhak.musicstudio.model.BoardView;
import com.ilhak.musicstudio.model.Reply;
import com.ilhak.musicstudio.model.ReplyView;
import com.ilhak.musicstudio.model.YoutubeResponse;
import com.ilhak.musicstudio.model.YoutubeSearch;
import com.ilhak.musicstudio.repository.BoardRepository;
import com.ilhak.musicstudio.repository.ReplyRepository;
import com.ilhak.musicstudio.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BoardApiController {
    
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyRepository replyRepository;

    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title, 
            @RequestParam(required = false, defaultValue = "") String content) {
        if(ObjectUtils.isEmpty(title) && ObjectUtils.isEmpty(content)) {
            return boardRepository.findAll();
        } else {
            return boardRepository.findByTitle(title);
        }
    }

    @PostMapping("/board/write")
    Board newBoard(@RequestBody Board newBoard) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        return boardService.save(userEmail, newBoard);
    }

    @GetMapping("/boards/{id}")
    BoardView replaceBoard(@PathVariable Long id) {
        Board board = boardRepository.findById(id).orElse(null);
        return new BoardView(board);
    }

    

    @PutMapping("/boards/{id}")
    Board write(@RequestBody Board newBoard, @PathVariable Long id) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardRepository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return boardRepository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }

    @PostMapping("/search-video")
    public YoutubeResponse searchVideo(@RequestBody YoutubeSearch youtubeSearch) {
        YoutubeResponse res = boardService.searchYoutube(youtubeSearch.getSearchKeyword(), youtubeSearch.getPageToken());
        return res;
    }

    @GetMapping("/board/random")
    int getRandomBoardId() {
        return boardRepository.getRandomBoardId();
    }

    @PostMapping("/boards/{id}/comment")
    public Reply postComment(@PathVariable Long id, @RequestBody Reply reply) {
        //
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        //reply.setBoardId(id);
        return boardService.postComment(id, reply, userEmail);
    }

    @GetMapping("/board/comments")
    List<ReplyView> getComments(@RequestParam Long boardId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        return boardService.getComments(boardId, userEmail);
    }

    @DeleteMapping("/board/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        //ResponseEntity res;
        boardService.deleteComment(id);
        ResponseEntity<String> res = new ResponseEntity<String>("success", HttpStatus.OK);
        return res;
    }
}
