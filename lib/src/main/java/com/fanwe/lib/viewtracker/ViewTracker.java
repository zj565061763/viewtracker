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

import java.lang.ref.WeakReference;

/**
 * view的位置追踪接口
 */
public interface ViewTracker
{
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
     * 设置要追踪的位置，默认左上角对齐
     *
     * @param position
     * @return
     */
    ViewTracker setPosition(Position position);

    /**
     * 设置追踪到指定位置后，x值的偏移量，大于0往右，小于0往左
     * <p>
     * 注意：此方法和{@link #setMarginX(ViewSize)}方法的值最终会叠加
     *
     * @param marginX
     * @return
     */
    ViewTracker setMarginX(int marginX);

    /**
     * 设置追踪到指定位置后，y值的偏移量，大于0往下，小于0往上
     * <p>
     * 注意：此方法和{@link #setMarginY(ViewSize)}方法的值最终会叠加
     *
     * @param marginY
     * @return
     */
    ViewTracker setMarginY(int marginY);

    /**
     * 设置追踪到指定位置后，x值的偏移量
     * <p>
     * 注意：此方法和{@link #setMarginX(int)}方法的值最终会叠加
     *
     * @param viewSize
     * @return
     */
    ViewTracker setMarginX(ViewSize viewSize);

    /**
     * 设置追踪到指定位置后，y值的偏移量
     * <p>
     * 注意：此方法和{@link #setMarginY(int)}方法的值最终会叠加
     *
     * @param viewSize
     * @return
     */
    ViewTracker setMarginY(ViewSize viewSize);

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
         * 与target左边对齐
         */
        Left,
        /**
         * 与target顶部对齐
         */
        Top,
        /**
         * 与target右边对齐
         */
        Right,
        /**
         * 与target底部对齐
         */
        Bottom,
    }

    abstract class Callback
    {
        /**
         * 源view变化回调
         *
         * @param oldSource 旧的源view，可能为null
         * @param newSource 新的源view，可能为null
         */
        public void onSourceChanged(View oldSource, View newSource)
        {
        }

        /**
         * 目标view变化回调
         *
         * @param oldTarget 旧的目标view，可能为null
         * @param newTarget 新的目标view，可能为null
         */
        public void onTargetChanged(View oldTarget, View newTarget)
        {
        }

        /**
         * 在更新追踪信息之前会调用此方法来决定可不可以更新，默认true-可以更新
         *
         * @param source 源view
         * @param target 目标view
         * @return true-可以更新，false-不要更新
         */
        public boolean canUpdate(View source, View target)
        {
            return true;
        }

        /**
         * 按照指定的位置{@link Position}追踪到target后回调，回调source相对于父布局的x和y值
         *
         * @param x      source相对于父布局的x值
         * @param y      source相对于父布局的y值
         * @param source 源view
         * @param target 目标view
         */
        public abstract void onUpdate(int x, int y, View source, View target);
    }

    class ViewSize
    {
        private final WeakReference<View> mView;
        private final boolean mIsWidth;
        private final boolean mIsAdd;

        /**
         * view大小
         *
         * @param view
         * @param isWidth true-宽度，false-高度
         * @param isAdd   true-正值，false-负值
         */
        public ViewSize(View view, boolean isWidth, boolean isAdd)
        {
            mView = new WeakReference<>(view);
            mIsWidth = isWidth;
            mIsAdd = isAdd;
        }

        private View getView()
        {
            return mView == null ? null : mView.get();
        }

        /**
         * 返回大小
         *
         * @return
         */
        public int getSize()
        {
            final View view = getView();
            if (view == null)
                return 0;

            final int size = mIsWidth ? view.getWidth() : view.getHeight();
            return mIsAdd ? size : -size;
        }
    }
}
