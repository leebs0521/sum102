package com.example.Sum102.Domain;

public class Books {
    private Long bookId;
    private String bName;
    private Integer bPrice;
    private Integer userID;
    private String bInfo;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public Integer getbPrice() {
        return bPrice;
    }

    public void setbPrice(Integer bPrice) {
        this.bPrice = bPrice;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getbInfo() {
        return bInfo;
    }

    public void setbInfo(String bInfo) {
        this.bInfo = bInfo;
    }
}
