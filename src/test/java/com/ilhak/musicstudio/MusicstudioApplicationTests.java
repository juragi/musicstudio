package com.ilhak.musicstudio;

import java.io.UnsupportedEncodingException;

import com.ilhak.musicstudio.service.BoardService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
class MusicstudioApplicationTests {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService boardService;

	@Test
	void contextLoads() {
		logger.info("{} haha", "test");
		try {
			
			String jsonString = boardService.searchYoutube("avicii");
			logger.info("search result: {}", jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
