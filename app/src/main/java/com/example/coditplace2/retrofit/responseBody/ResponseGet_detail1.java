package com.example.coditplace2.retrofit.responseBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGet_detail1 {
    @SerializedName("pimage1")
    @Expose
    private String pimage1;
    @SerializedName("picon")
    @Expose
    private Object picon;
    @SerializedName("pimage2")
    @Expose
    private String pimage2;
    @SerializedName("pcontent")
    @Expose
    private String pcontent;
    @SerializedName("pvisit")
    @Expose
    private String pvisit;
    @SerializedName("pphone")
    @Expose
    private String pphone;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("pimage3")
    @Expose
    private String pimage3;
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

    public String getPimage2() {
        return pimage2;
    }

    public void setPimage2(String pimage2) {
        this.pimage2 = pimage2;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPimage3() {
        return pimage3;
    }

    public void setPimage3(String pimage3) {
        this.pimage3 = pimage3;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }
}
