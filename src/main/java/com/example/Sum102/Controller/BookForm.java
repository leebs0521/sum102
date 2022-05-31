package com.example.Sum102.Controller;

    public class BookForm {
        private String bName="";
        private Integer bPrice = 0;
        private Integer userID = 0;
        private String bInfo = "";

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
