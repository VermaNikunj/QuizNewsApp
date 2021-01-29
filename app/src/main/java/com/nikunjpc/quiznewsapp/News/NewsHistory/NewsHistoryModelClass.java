package com.nikunjpc.quiznewsapp.News.NewsHistory;

public class NewsHistoryModelClass {
    int s_id;
    String searched;

    public NewsHistoryModelClass(int s_id, String searched) {
        this.s_id = s_id;
        this.searched = searched;
    }

    public NewsHistoryModelClass(String searched) {
        this.searched = searched;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getSearched() {
        return searched;
    }

    public void setSearched(String searched) {
        this.searched = searched;
    }
}
