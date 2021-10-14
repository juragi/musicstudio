package com.ilhak.musicstudio.repository;

import com.ilhak.musicstudio.model.Board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
}
