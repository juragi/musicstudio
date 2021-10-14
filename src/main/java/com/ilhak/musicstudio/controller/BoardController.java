package com.ilhak.musicstudio.controller;

import java.util.List;

import javax.validation.Valid;

import com.ilhak.musicstudio.helper.BoardValidator;
import com.ilhak.musicstudio.model.Board;
import com.ilhak.musicstudio.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;
    
    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "board/list";
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
    public String formSubmit(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) return "board/form";
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
