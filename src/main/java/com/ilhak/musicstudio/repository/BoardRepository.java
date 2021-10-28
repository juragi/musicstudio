package com.ilhak.musicstudio.repository;

import java.util.List;

import com.ilhak.musicstudio.model.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);

    Page<Board> findByTitleContainingOrContentContainingOrderByIdDesc(String title, String content, Pageable pageable);
    
    @Query(value = "select * from board where title = ?1", nativeQuery = true)
    Board findByTitleNative(String title);

    @Query(value = "select id from board order by rand() limit 0, 1", nativeQuery = true)
    int getRandomBoardId();
}
