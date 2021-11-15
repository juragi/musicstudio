package com.ilhak.musicstudio.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilhak.musicstudio.model.Board;
import com.ilhak.musicstudio.model.Reply;
import com.ilhak.musicstudio.model.ReplyView;
import com.ilhak.musicstudio.model.User;
import com.ilhak.musicstudio.model.YoutubeResponse;
import com.ilhak.musicstudio.repository.BoardRepository;
import com.ilhak.musicstudio.repository.ReplyRepository;
import com.ilhak.musicstudio.repository.UserRepository;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${youtubeapi.key}")
    private String youtubeApiKey;

    public Board save(String userEmail, Board board){
        User user = userRepository.findByEmail(userEmail);
        board.setUser(user);
        return boardRepository.save(board);
    }

    public YoutubeResponse searchYoutube(String searchKeyword, String pageToken) {
        YoutubeResponse response = null;
        String apiUrl = "https://www.googleapis.com/youtube/v3/search";
        String jsonResult = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("key", youtubeApiKey));
            nvps.add(new BasicNameValuePair("part", "snippet"));
            nvps.add(new BasicNameValuePair("type", "video"));
            nvps.add(new BasicNameValuePair("maxResults", "20"));
            nvps.add(new BasicNameValuePair("videoEmbeddable", "true"));
            nvps.add(new BasicNameValuePair("q", URLEncoder.encode(searchKeyword,"UTF-8")));
            if(pageToken != null) nvps.add(new BasicNameValuePair("pageToken", pageToken));
            String parameters = String.join("&", nvps.stream().map(x-> x.getName() + "=" + x.getValue()).collect(Collectors.toList()));
            HttpGet httpGet = new HttpGet(apiUrl + "?" + parameters);
            CloseableHttpResponse res = httpClient.execute(httpGet);
            jsonResult = EntityUtils.toString(res.getEntity(), "UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.readValue(jsonResult, YoutubeResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public Reply postComment(Long boardId, Reply reply, String userEmail) {
        Board board = boardRepository.findById(boardId).orElse(null);
        System.out.println(reply.getBoard());
        reply.setBoard(board);
        User user = userRepository.findByEmail(userEmail);
        reply.setUser(user);
        reply.setEntryDate(new Date());
        Reply newReply;
        try {
            newReply = replyRepository.save(reply);
        } catch(Exception e) {
            newReply = new Reply();
            e.printStackTrace();
        }
        return  newReply;
    }

    public List<ReplyView> getComments(Long boardId) {
        List<Reply> boards = replyRepository.findByBoardId(boardId);
        return boards.stream().map(x-> {
            return new ReplyView(x);
        }).collect(Collectors.toList());
    }

}
