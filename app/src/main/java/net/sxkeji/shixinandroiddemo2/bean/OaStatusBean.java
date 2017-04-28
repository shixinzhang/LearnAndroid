package net.sxkeji.shixinandroiddemo2.bean;

import java.util.List;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class OaStatusBean {

    private List<SignbtnsBean> signbtns;

    public List<SignbtnsBean> getSignbtns() {
        return signbtns;
    }

    public void setSignbtns(List<SignbtnsBean> signbtns) {
        this.signbtns = signbtns;
    }

    public static class SignbtnsBean {
        /**
         * detail : {"addr":"上海市普陀区光复西路靠近汇银铭尊6号楼","clientAddress":"10.16.45.122","latitude":"31.221517","longitude":"121.382759","signDate":"2016-12-08","signTime":"11:22:48","status":"beLate"}
         * isEnable : false
         * time : 09:00
         * type : amOn
         */

        private DetailBean detail;
        private String isEnable;
        private String time;
        private String type;

        @Override
        public String toString() {
            return "SignbtnsBean{" +
                    "detail=" + detail +
                    ", isEnable='" + isEnable + '\'' +
                    ", time='" + time + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public String getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(String isEnable) {
            this.isEnable = isEnable;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class DetailBean {
            /**
             * addr : 上海市普陀区光复西路靠近汇银铭尊6号楼
             * clientAddress : 10.16.45.122
             * latitude : 31.221517
             * longitude : 121.382759
             * signDate : 2016-12-08
             * signTime : 11:22:48
             * status : beLate
             */

            private String addr;
            private String clientAddress;
            private String latitude;
            private String longitude;
            private String signDate;
            private String signTime;
            private String status;

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getClientAddress() {
                return clientAddress;
            }

            public void setClientAddress(String clientAddress) {
                this.clientAddress = clientAddress;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getSignDate() {
                return signDate;
            }

            public void setSignDate(String signDate) {
                this.signDate = signDate;
            }

            public String getSignTime() {
                return signTime;
            }

            public void setSignTime(String signTime) {
                this.signTime = signTime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            @Override
            public String toString() {
                return "DetailBean{" +
                        "addr='" + addr + '\'' +
                        ", clientAddress='" + clientAddress + '\'' +
                        ", latitude='" + latitude + '\'' +
                        ", longitude='" + longitude + '\'' +
                        ", signDate='" + signDate + '\'' +
                        ", signTime='" + signTime + '\'' +
                        ", status='" + status + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "OaStatusBean{" +
                "signbtns=" + signbtns +
                '}';
    }
}
