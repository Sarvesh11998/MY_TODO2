package com.example.lenovo.my_todo;

/**
 * Created by Lenovo on 9/19/2017.
 */

class Tododetail {
    private String task;
    private long id;
    private int rank = 0;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Tododetail(String task, int rank, long id) {
        this.task = task;
        this.id = id;
        this.rank = rank;
    }


    public String gettask() {
        return task;
    }

    public void settask(String task) {
        this.task = task;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
