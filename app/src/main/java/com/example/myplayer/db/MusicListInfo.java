package com.example.myplayer.db;

public class MusicListInfo {

    @Override
    public String toString() {
        return "MusicListInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mvid=" + mvid +
                ", fee=" + fee +
                ", albumName='" + albumName + '\'' +
                ", artistsName=" + artistsName +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                ", alia=" + alia +
                '}';
    }

    public void setMusicInfo(MusicInfo.SongsBean songsBean){
        this.id = Integer.toString(songsBean.getId());
        this.name = songsBean.getName();
        this.mvid = songsBean.getMv();
        this.fee = songsBean.getFee();
        this.albumName = songsBean.getAl().getName();
        this.artistsName = "";
        for (MusicInfo.SongsBean.ArBean arBean : songsBean.getAr()){
            this.artistsName += arBean.getName();
        }
        this.alia = "";
        for (String alia: songsBean.getAlia()){
            this.alia += alia;
        }
        this.picUrl = songsBean.getAl().getPicUrl();
    }


    public void setUrl(String url) {
        this.url = url;
    }

    //音乐ID
    private String id;
    //音乐名
    private String name;
    //mvID
    private int mvid;
    //是否付费
    private int fee;
    //专辑名
    private String albumName;
    //作家名
    private String artistsName;

    //音乐图片地址
    private String picUrl;
    //音乐播放地址
    private String url;

    //音乐附件信息
    private String alia;

    public String getAlia() {
        return alia;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistsName() {
        return artistsName;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMvid() {
        return mvid;
    }

    public int getFee() {
        return fee;
    }


    public String getPicUrl() {
        return picUrl;
    }


    public String getUrl() {
        return url;
    }
}
