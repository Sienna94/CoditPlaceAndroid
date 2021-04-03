package com.example.coditplace2.retrofit.responseBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGet {

    @SerializedName("pimage1")
    @Expose
    private String pimage1;
    @SerializedName("picon")
    @Expose
    private Object picon;
    @SerializedName("pcontent")
    @Expose
    private String pcontent;
    @SerializedName("pvisit")
    @Expose
    private String pvisit;
    @SerializedName("pphone")
    @Expose
    private String pphone;
    @SerializedName("plocation")
    @Expose
    private Integer plocation;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("pidx")
    @Expose
    private Integer pidx;
    @SerializedName("pcategory")
    @Expose
    private Integer pcategory;
    @SerializedName("paddress")
    @Expose
    private String paddress;

    public String getPimage1() {
        return pimage1;
    }

    public void setPimage1(String pimage1) {
        this.pimage1 = pimage1;
    }

    public Object getPicon() {
        return picon;
    }

    public void setPicon(Object picon) {
        this.picon = picon;
    }

    public String getPcontent() {
        return pcontent;
    }

    public void setPcontent(String pcontent) {
        this.pcontent = pcontent;
    }

    public String getPvisit() {
        return pvisit;
    }

    public void setPvisit(String pvisit) {
        this.pvisit = pvisit;
    }

    public String getPphone() {
        return pphone;
    }

    public void setPphone(String pphone) {
        this.pphone = pphone;
    }

    public Integer getPlocation() {
        return plocation;
    }

    public void setPlocation(Integer plocation) {
        this.plocation = plocation;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPidx() {
        return pidx;
    }

    public void setPidx(Integer pidx) {
        this.pidx = pidx;
    }

    public Integer getPcategory() {
        return pcategory;
    }

    public void setPcategory(Integer pcategory) {
        this.pcategory = pcategory;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }
}