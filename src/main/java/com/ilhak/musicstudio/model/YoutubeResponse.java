package com.ilhak.musicstudio.model;

import java.util.Date;
import java.util.List;

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
        private String description;
        private Date publishedAt;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageInfo{
        private Long totalResults;
        private int resultsPerPage;
    }
}