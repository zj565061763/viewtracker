/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.viewtracker;

import android.view.View;

import com.fanwe.lib.viewtracker.update.Updater;

/**
 * view的位置追踪接口
 */
public interface ViewTracker extends Updater.Update
{
    /**
     * 设置是否调试模式，过滤日志tag:ViewTracker
     *
     * @param debug
     * @return
     */
    ViewTracker setDebug(boolean debug);

    /**
     * 是否调试模式
     *
     * @return
     */
    boolean isDebug();

    /**
     * 设置回调
     *
     * @param callback
     * @return
     */
    ViewTracker setCallback(Callback callback);

    /**
     * 设置源view
     *
     * @param source
     * @return
     */
    ViewTracker setSource(View source);

    /**
     * 设置目标view
     *
     * @param target
     * @return
     */
    ViewTracker setTarget(View target);

    /**
     * 设置要追踪的位置
     *
     * @param position
     * @return
     */
    ViewTracker setPosition(Position position);

    /**
     * 设置追踪到指定位置后，x轴方向的偏移量，大于0往右，小于0往左
     *
     * @param marginX
     * @return
     */
    ViewTracker setMarginX(int marginX);

    /**
     * 设置追踪到指定位置后，y轴方向的偏移量，大于0往下，小于0往上
     *
     * @param marginY
     * @return
     */
    ViewTracker setMarginY(int marginY);

    /**
     * 返回想要追踪目标的源view
     *
     * @return
     */
    View getSource();

    /**
     * 返回目标view
     *
     * @return
     */
    View getTarget();

    /**
     * 设置一个动态更新对象
     *
     * @param updater
     * @return
     */
    ViewTracker setUpdater(Updater updater);

    /**
     * 返回设置的动态更新对象
     *
     * @return
     */
    Updater getUpdater();

    /**
     * 触发一次追踪信息更新
     *
     * @return true-此次更新成功
     */
    boolean update();

    enum Position
    {
        /**
         * 与target左上角对齐
         */
        TopLeft,
        /**
         * 与target顶部中间对齐
         */
        TopCenter,
        /**
         * 与target右上角对齐
         */
        TopRight,

        /**
         * 与target左边中间对齐
         */
        LeftCenter,
        /**
         * 中间对齐
         */
        Center,
        /**
         * 与target右边中间对齐
         */
        RightCenter,

        /**
         * 与target左下角对齐
         */
        BottomLeft,
        /**
         * 与target底部中间对齐
         */
        BottomCenter,
        /**
         * 与target右下角对齐
         */
        BottomRight,

        /**
         * 在target的顶部外侧靠左对齐
         */
        TopOutsideLeft,
        /**
         * 在target的顶部外侧左右居中
         */
        TopOutsideCenter,
        /**
         * 在target的顶部外侧靠右对齐
         */
        TopOutsideRight,

        /**
         * 在target的底部外侧靠左对齐
         */
        BottomOutsideLeft,
        /**
         * 在target的底部外侧左右居中
         */
        BottomOutsideCenter,
        /**
         * 在target的底部外侧靠右对齐
         */
        BottomOutsideRight,

        /**
         * 在target的左边外侧靠顶部对齐
         */
        LeftOutsideTop,
        /**
         * 在target的左边外侧上下居中
         */
        LeftOutsideCenter,
        /**
         * 在target的左边外侧靠底部对齐
         */
        LeftOutsideBottom,

        /**
         * 在target的右边外侧靠顶部对齐
         */
        RightOutsideTop,
        /**
         * 在target的右边外侧上下居中
         */
        RightOutsideCenter,
        /**
         * 在target的右边外侧靠底部对齐
         */
        RightOutsideBottom,
    }

    interface Callback
    {
        /**
         * source按照指定的位置追踪到target后回调
         *
         * @param x      追踪到target后，source相对于父布局的x
         * @param y      追踪到target后，source相对于父布局的y
         * @param source
         */
        void onTrack(int x, int y, View source);
    }
}
