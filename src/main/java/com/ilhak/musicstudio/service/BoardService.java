package com.ilhak.musicstudio.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Value("${youtubeapi.key}")
    private String youtubeApiKey;

    public String searchYoutube(String searchKeyword) {
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
            String parameters = String.join("&", nvps.stream().map(x-> x.getName() + "=" + x.getValue()).collect(Collectors.toList()));
            HttpGet httpGet = new HttpGet(apiUrl + "?" + parameters);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            jsonResult = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

}
