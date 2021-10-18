package com.ilhak.musicstudio.repository;

import java.util.List;

import com.ilhak.musicstudio.model.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    
    @Query(value = "select * from board where title = ?1", nativeQuery = true)
    Board findByTitleNative(String title);
}
