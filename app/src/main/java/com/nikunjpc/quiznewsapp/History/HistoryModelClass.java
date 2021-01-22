package com.nikunjpc.quiznewsapp.History;

public class HistoryModelClass {

    String type;
    String category;
    int id;
    int correct_answer;

    public HistoryModelClass(int id, String type, String category, int correct_answer) {
        this.type = type;
        this.category = category;
        this.id = id;
        this.correct_answer = correct_answer;
    }

    public HistoryModelClass(String type, String category, int correct_answer) {
        this.type = type;
        this.category = category;
        this.correct_answer = correct_answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }
}
