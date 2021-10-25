package com.ilhak.musicstudio.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeResponse {

    private List<YoutubeItem> items;
    private String nextPageToken;
    private String prevPageToken;
    private PageInfo pageInfo;
    
    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class YoutubeItem {
        private Id id;
        private Snippet snippet;
        private String kind;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Id {
        private String videoId;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Snippet {
        private String title;
        private String description;
        private Date publishedAt;
        private Map<String, Thumbnail> thumbnails;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Thumbnail {
        private String url;
        private int width;
        private int height;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageInfo{
        private Long totalResults;
        private int resultsPerPage;
    }
}