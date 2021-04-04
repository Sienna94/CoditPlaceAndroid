package com.example.coditplace2.dto;

public class ReplyData {
    public int ridx;
    public String rwriter;
    public int rscore;
    public String rdate;
    public String rcontent;

    public ReplyData(int ridx, String rwriter, int rscore, String rdate, String rcontent) {
        this.ridx = ridx;
        this.rwriter = rwriter;
        this.rscore = rscore;
        this.rdate = rdate;
        this.rcontent = rcontent;
    }
}
