package com.dts.vaclass.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zs on 2018/2/27.
 */

public class MusicBean implements Serializable {

    //  http://app.mijia.cnlive.com/api_v1/columnprograms.do?cid=hdedu

    /**
     * programs : [{"title":"陈明憙《童装高跟鞋》主打！","nodeId":"","detail":"《童裝高跟鞋》这首歌在说长大与不长大之间地挣扎及内心的矛盾，为何一定要在女人或女孩之间选边站。只要有自信，童装和高跟鞋也是可以并存。","link":"1","image":"http://yweb2.cnliveimg.com/img/cnlive/170412165958029_158.jpg","type":"hdedu","contId":"625736401"},{"title":"主打抒情《好爱好散》陈势安","nodeId":"","detail":"久违三年的天后级情歌王子陈势安，从心诠释爱情的无奈与体悟，更具穿透的直达纠结内在，想爱怕痛，怕痛难爱。现推出首波抒情主打《好爱好散》MV公开\u2026","link":"1","image":"http://yweb3.cnliveimg.com/img/cnlive/170412170017778_735.jpg","type":"hdedu","contId":"625736435"},{"title":"《FLYHIgh》Dessyslavova单曲","nodeId":"","detail":"DessySlavova抒情献唱单曲《FlyHigh》，动感的音乐旋律加上男女声极其默契的合唱，使这首歌变得更加魅力十足\u2026","link":"1","image":"http://yweb0.cnliveimg.com/img/cnlive/170412170031567_031.jpg","type":"hdedu","contId":"625736182"},{"title":"《NONONO》光头帅哥milow献唱","nodeId":"","detail":"光头帅哥Milow是比利时家喻户晓的唱作者，Milow的声音干燥纯净，用心之至常常让人痛心，似乎在他身上发生着很多的苦难无以言表而只能寄托歌声\u2026","link":"1","image":"http://yweb1.cnliveimg.com/img/cnlive/170412170051646_583.jpg","type":"hdedu","contId":"625736485"},{"title":"《SODA》刘阡羽演绎舞蹈版！","nodeId":"","detail":"在进行了长时间的反复练习与精心打磨后，刘阡羽不仅熟练掌握了舞蹈动作的精髓，还将自己的特色与风格融入其中，为这首舞曲打上了属于刘阡羽的印记\u2026","link":"1","image":"http://yweb2.cnliveimg.com/img/cnlive/170412170116943_888.jpg","type":"hdedu","contId":"625736460"},{"title":"《爸爸妈妈》首播版！李荣浩","nodeId":"","detail":"《爸爸妈妈》是李荣浩首次尝试亲情题材的创作，他是一个不擅于去表达爱的人，所以决定创作一首不煽情的、实实在在的歌曲，表达对父母的爱。","link":"1","image":"http://yweb1.cnliveimg.com/img/cnlive/170412165847839_386.jpg","type":"hdedu","contId":"625735938"}]
     * lottery_url : 查询列表成功
     * errorCode : 0
     */

    private String lottery_url;
    private String errorCode;
    private List<ProgramsBean> programs;

    public String getLottery_url() {
        return lottery_url;
    }

    public void setLottery_url(String lottery_url) {
        this.lottery_url = lottery_url;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<ProgramsBean> getPrograms() {
        return programs;
    }

    public void setPrograms(List<ProgramsBean> programs) {
        this.programs = programs;
    }

    public static class ProgramsBean {
        /**
         * title : 陈明憙《童装高跟鞋》主打！
         * nodeId :
         * detail : 《童裝高跟鞋》这首歌在说长大与不长大之间地挣扎及内心的矛盾，为何一定要在女人或女孩之间选边站。只要有自信，童装和高跟鞋也是可以并存。
         * link : 1
         * image : http://yweb2.cnliveimg.com/img/cnlive/170412165958029_158.jpg
         * type : hdedu
         * contId : 625736401
         */

        private String title;
        private String nodeId;
        private String detail;
        private String link;
        private String image;
        private String type;
        private String contId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContId() {
            return contId;
        }

        public void setContId(String contId) {
            this.contId = contId;
        }
    }
}