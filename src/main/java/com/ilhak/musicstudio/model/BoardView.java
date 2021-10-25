package com.ilhak.musicstudio.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardView {
    private Long id;
    private String title;
    private String content;
    private String username;

    public BoardView() {}
    public BoardView(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUser().getUsername();
    }
}
