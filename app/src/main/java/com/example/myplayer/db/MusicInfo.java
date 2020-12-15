package com.example.myplayer.db;

import java.util.List;

public class MusicInfo {

    @Override
    public String toString() {
        return "MusicInfo{" +
                "songs=" + songs +
                '}';
    }

    private List<SongsBean> songs;

    public List<SongsBean> getSongs() {
        return songs;
    }

    public void setSongs(List<SongsBean> songs) {
        this.songs = songs;
    }


    public static class SongsBean {

        @Override
        public String toString() {
            return "SongsBean{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", fee=" + fee +
                    ", al=" + al +
                    ", ar=" + ar +
                    ", mv=" + mv +
                    ", alia=" + alia +
                    '}';
        }

        private String name;
        private int id;
        private int fee;
        private AlBean al;
        private List<ArBean> ar;
        private int mv;
        private List<String> alia;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public AlBean getAl() {
            return al;
        }

        public void setAl(AlBean al) {
            this.al = al;
        }

        public List<ArBean> getAr() {
            return ar;
        }

        public void setAr(List<ArBean> ar) {
            this.ar = ar;
        }

        public int getMv() {
            return mv;
        }

        public void setMv(int mv) {
            this.mv = mv;
        }

        public List<String> getAlia() {
            return alia;
        }

        public void setAlia(List<String> alia) {
            this.alia = alia;
        }

        //专辑名
        public static class AlBean {
            @Override
            public String toString() {
                return "AlBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", picUrl='" + picUrl + '\'' +
                        '}';
            }

            private int id;
            private String name;
            private String picUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

        }

        //歌手名
        public static class ArBean {

            @Override
            public String toString() {
                return "ArBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
            }

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
