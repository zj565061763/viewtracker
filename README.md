# About
让某个源view追踪某个目标view，追踪到指定的位置后，回调源view相对于其父布局的x和y

# Gradle
`implementation 'com.fanwe.android:viewtracker:1.0.0-beta2'`

# ViewTracker接口
```java
/**
 * view的位置追踪接口
 */
public interface ViewTracker
{
    /**
     * 设置是否调试模式，过滤日志tag:ViewTracker
     *
     * @param debug
     */
    void setDebug(boolean debug);

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
     */
    void setCallback(Callback callback);

    /**
     * 设置源view
     *
     * @param source
     */
    void setSource(View source);

    /**
     * 设置目标view
     */
    void setTarget(View target);

    /**
     * 设置要追踪的位置
     */
    void setPosition(Position position);

    /**
     * 设置追踪到后x轴方向的偏移量，大于0往右，小于0往左
     *
     * @param marginX
     */
    void setMarginX(int marginX);

    /**
     * 设置追踪到y轴方向的偏移量，大于0往下，小于0往上
     *
     * @param marginY
     */
    void setMarginY(int marginY);

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
     * 触发追踪
     *
     * @return
     */
    boolean track();

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
```

# DynamicViewTracker接口
```java
/**
 * 实时实时追踪view位置接口
 */
public interface DynamicViewTracker extends ViewTracker
{
    /**
     * 开始追踪
     *
     * @return true-正在追踪中
     */
    boolean start();

    /**
     * 停止追踪
     */
    void stop();

    /**
     * 是否正在追踪中
     *
     * @return
     */
    boolean isTracking();
}
```