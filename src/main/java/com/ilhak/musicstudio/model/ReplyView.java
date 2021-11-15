package com.ilhak.musicstudio.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyView {
    private Long id;
    private String userName;
    private Date entryDate;
    private String content;
    public ReplyView (Reply reply) {
        this.id = reply.getId();
        this.userName = reply.getUser().getUsername();
        this.entryDate = reply.getEntryDate();
        this.content = reply.getContent();
    }
}
