package com.ilhak.musicstudio;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilhak.musicstudio.model.YoutubeResponse;
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
			
			YoutubeResponse response = boardService.searchYoutube("avicii", "CAIQAA");
			
			//logger.info("search result: {}", jsonString);
			//ObjectMapper mapper = new ObjectMapper();
			//YoutubeResponse map = mapper.readValue(jsonString, YoutubeResponse.class);
			//logger.info("map: {}", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
