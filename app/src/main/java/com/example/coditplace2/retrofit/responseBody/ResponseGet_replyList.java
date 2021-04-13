package com.example.coditplace2.retrofit.responseBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGet_replyList {
    @SerializedName("pimage1")
    @Expose
    private String pimage1;
    @SerializedName("picon")
    @Expose
    private String picon;
    @SerializedName("rdate")
    @Expose
    private String rdate;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("rwriter")
    @Expose
    private String rwriter;
    @SerializedName("rcontent")
    @Expose
    private String rcontent;
    @SerializedName("rscore")
    @Expose
    private Integer rscore;
    @SerializedName("ridx")
    @Expose
    private Integer ridx;

    public String getPimage1() {
        return pimage1;
    }

    public void setPimage1(String pimage1) {
        this.pimage1 = pimage1;
    }

    public String getPicon() {
        return picon;
    }

    public void setPicon(String picon) {
        this.picon = picon;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRwriter() {
        return rwriter;
    }

    public void setRwriter(String rwriter) {
        this.rwriter = rwriter;
    }

    public String getRcontent() {
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }

    public Integer getRscore() {
        return rscore;
    }

    public void setRscore(Integer rscore) {
        this.rscore = rscore;
    }

    public Integer getRidx() {
        return ridx;
    }

    public void setRidx(Integer ridx) {
        this.ridx = ridx;
    }
}
