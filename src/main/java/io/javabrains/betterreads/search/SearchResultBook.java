package io.javabrains.betterreads.search;

import java.util.List;

public class SearchResultBook {
    private String key;
    private String title;
    private List<String> author_name;
    private String cover_i;
    private int first_publish_year;
    
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<String> getAuthor_name() {
        return author_name;
    }
    public void setAuthor_name(List<String> author_name) {
        this.author_name = author_name;
    }
    public String getCover_i() {
        return cover_i;
    }
    public void setCover_i(String cover_i) {
        this.cover_i = cover_i;
    }
    public int getFirst_publish_year() {
        return first_publish_year;
    }
    public void setFirst_publish_year(int first_publish_year) {
        this.first_publish_year = first_publish_year;
    }

    
}
