package com.example.myplayer.db;

import java.util.List;

public class TopList {
    @Override
    public String toString() {
        return "TopList{" +
                "list=" + list +
                '}';
    }

    private List<ListBean> list;
    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        @Override
        public String toString() {
            return "ListBean{" +
                    "updateFrequency='" + updateFrequency + '\'' +
                    ", coverImgUrl='" + coverImgUrl + '\'' +
                    ", description='" + description + '\'' +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }

        /**
         * updateFrequency : 每天更新
         * coverImgUrl : http://p2.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg
         * description : 云音乐中每天热度上升最快的100首单曲，每日更新。
         * name : 云音乐飙升榜
         * id : 19723756
         */

        private String updateFrequency;
        private String coverImgUrl;
        private String description;
        private String name;
        private String id;

        public String getUpdateFrequency() {
            return updateFrequency;
        }

        public void setUpdateFrequency(String updateFrequency) {
            this.updateFrequency = updateFrequency;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            this.coverImgUrl = coverImgUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
