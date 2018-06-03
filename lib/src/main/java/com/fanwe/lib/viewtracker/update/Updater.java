package com.fanwe.lib.viewtracker.update;

/**
 * 动态更新接口
 */
public interface Updater
{
    /**
     * 设置要动态更新的对象
     *
     * @param update
     */
    void setUpdate(Update update);

    /**
     * 开始动态更新
     *
     * @return true-成功开始
     */
    boolean start();

    /**
     * 停止动态更新
     */
    void stop();

    /**
     * 是否已经开始动态更新
     *
     * @return
     */
    boolean isStarted();

    interface Update
    {
        /**
         * 触发更新
         *
         * @return true-更新成功
         */
        boolean update();
    }
}
