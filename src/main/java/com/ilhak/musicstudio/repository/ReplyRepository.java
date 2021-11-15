package com.ilhak.musicstudio.repository;

import java.util.List;

import com.ilhak.musicstudio.model.Reply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    public List<Reply> findByBoardId(Long boardId);
}
