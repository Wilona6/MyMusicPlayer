package com.example.myplayer.interfaces;

public interface IPlayerController {

    int PLAY_STATE_START = 0;
    int PLAY_STATE_PAUSE = 1;
    int PLAY_STATE_STOP = 2;

    /**
     * 绑定UI控制器
     * 将UI控制提交给逻辑层
     * @param iPlayerViewController
     */
    void registerIPlayViewController(com.example.myplayer.interfaces.IPlayerViewController iPlayerViewController);

    /**
     * 取消绑定的控制器
     */
    void unregisterIPlayViewController();

    /**
     * 开始播放
     * @param url 播放音乐的地址
     */
    void start(String url,boolean isLike);

    /**
     * 暂停或继续播放
     */
    void pauseOrResume();


    /**
     * 停止播放
     */
    void stop();

    /**
     * 进度更新
     * @param seek
     */
    void seekTo(int seek);
}
