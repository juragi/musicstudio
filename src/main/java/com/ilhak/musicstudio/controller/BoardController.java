package com.ilhak.musicstudio.controller;

import javax.validation.Valid;

import com.ilhak.musicstudio.helper.BoardValidator;
import com.ilhak.musicstudio.model.Board;
import com.ilhak.musicstudio.model.YoutubeResponse;
import com.ilhak.musicstudio.repository.BoardRepository;
import com.ilhak.musicstudio.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;
    
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false, defaultValue = "1") int page, 
            @RequestParam(required = false, defaultValue = "")String searchText ) {
        
        if(page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContainingOrderByIdDesc(searchText, searchText, pageable);
        
        Long perPage = 10L;
        Long start; // 1 -> 1,  9 -> 1,  10 -> 1, 11 -> 11, 20 -> 11
        Long last; // 1 -> 10, 9 -> 10, 10 -> 10, 11 -> 20, 20 -> 20
        int totalPages = boards.getTotalPages();
        start = totalPages == 0 ? 0 : (Long)(((int)Math.ceil((double)page / perPage) - 1) * perPage + 1);
        last = Math.min((Long)((int)Math.ceil((double)page / perPage) * perPage), totalPages);
        model.addAttribute("startPage", start); // page ??? ?????????
        model.addAttribute("endPage", last); // page ??? ?????????
        model.addAttribute("page", page); // ?????? ?????????
        model.addAttribute("totalPage", boards.getTotalPages()); // ?????? ????????? ??????
        model.addAttribute("boards", boards);

        return "board/list";
    }

    @ResponseBody
    @GetMapping("/youtubetest")
    public YoutubeResponse test() {
        YoutubeResponse res = boardService.searchYoutube("avicii", null);
        return res;
    }

    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable Long id) {
        model.addAttribute("boardId", id);
        return "board/view";
    }

    @GetMapping("/write")
    public String write(Model model, @RequestParam(required = false) Long id) {
        Board board;
        if(id == null) board = new Board();
        else board = boardRepository.findById(id).orElse(null);
        model.addAttribute("board", board);
        return "board/write";
    }


    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        Board board;
        if(id == null) {
            board = new Board();
        } else {
            board = boardRepository.findById(id).orElse(null);
        }
        model.addAttribute("board", board);
        return "board/form";
    }

    

    @PostMapping("/form")
    public String formSubmit(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) return "board/form";
        String userEmail = authentication.getName();
        //SecurityContextHolder.getContext().getAuthentication();
        //board.setUser(user);
        boardService.save(userEmail, board);
        return "redirect:/board/list";
    }

    @PostMapping("/write")
    public String write(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) return "board/write";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        Board savedBoard = boardService.save(userEmail, board);
        return "redirect:/board/view/" + savedBoard.getId();
    }

    
}
