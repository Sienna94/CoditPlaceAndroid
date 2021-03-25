package com.example.coditplace2;

public class ItemData {
    String pIdx;
    String pName;
    String pImage;
    String pVisit;
    String pIcon;
    String pCategory;
    String pPhone;
    String pContent;
    String pLike;

    public ItemData(String pIdx, String pName, String pImage, String pVisit, String pIcon, String pCategory, String pPhone, String pContent, String pLike) {
        this.pIdx = pIdx;
        this.pName = pName;
        this.pImage = pImage;
        this.pVisit = pVisit;
        this.pIcon = pIcon;
        this.pCategory = pCategory;
        this.pPhone = pPhone;
        this.pContent = pContent;
        this.pLike = pLike;
    }

    public String getpIdx() {
        return pIdx;
    }

    public String getpName() {
        return pName;
    }

    public String getpImage() {
        return pImage;
    }

    public String getpVisit() {
        return pVisit;
    }

    public String getpIcon() {
        return pIcon;
    }

    public String getpCategory() {
        return pCategory;
    }

    public String getpPhone() {
        return pPhone;
    }

    public String getpContent() {
        return pContent;
    }

    public String getpLike() {
        return pLike;
    }
}
