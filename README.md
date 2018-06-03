# About
让某个源view追踪某个目标view，追踪到指定的位置后，回调源view相对于其父布局的x和y

# Gradle
`implementation 'com.fanwe.android:viewtracker:1.0.0-rc5'`

# 简单demo
```java
public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    /**
     * 创建对象
     */
    private final ViewTracker mViewTracker = new FViewTracker();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 设置回调对象
         */
        mViewTracker.setCallback(new ViewTracker.Callback()
        {
            /**
             * 在更新追踪信息之前会调用此方法来决定可不可以更新，默认true-可以更新
             *
             * @param source       源view
             * @param sourceParent 源view的父view
             * @param target       目标view
             * @return true-可以更新，false-不要更新
             */
            @Override
            public boolean canUpdate(View source, View sourceParent, View target)
            {
                return super.canUpdate(source, sourceParent, target);
            }

            /**
             * 按照指定的位置{@link Position}追踪到target后回调，回调source在x和y方向需要是什么值才可以到指定的位置
             *
             * @param x            source相对于父布局需要的x值
             * @param y            source相对于父布局需要的y值
             * @param source       源view
             * @param sourceParent 源view的父view
             * @param target       目标view
             */
            @Override
            public void onUpdate(int x, int y, View source, View sourceParent, View target)
            {
                Log.i(TAG, x + "," + y);
            }
        });

        mViewTracker
                /**
                 * 设置源view
                 */
                .setSource(findViewById(R.id.btn_source))
                /**
                 * 设置目标view
                 */
                .setTarget(findViewById(R.id.btn_target))
                /**
                 * 设置要追踪的位置
                 */
                .setPosition(ViewTracker.Position.TopLeft)
                /**
                 * 设置实时更新对象，可以实时更新追踪信息
                 */
                .setUpdater(new ActivityUpdater(this));

        /**
         * 触发一次追踪信息更新
         */
        mViewTracker.update();
        /**
         * 开始实时更新追踪信息，调用此方法前必须先设置一个实时更新对象
         */
        mViewTracker.start();
    }
}
```

# ViewTracker接口
```java
/**
 * view的位置追踪接口
 */
public interface ViewTracker extends Updater.Updatable
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
     * 设置实时更新对象，可以实时更新追踪信息
     *
     * @param updater
     * @return
     */
    ViewTracker setUpdater(Updater updater);

    /**
     * 开始实时更新追踪信息，调用此方法前必须先设置一个实时更新对象{@link #setUpdater(Updater)}
     *
     * @return true-成功开始
     */
    boolean start();

    /**
     * 停止实时更新追踪信息
     */
    void stop();

    /**
     * 是否已经开始实时更新
     *
     * @return
     */
    boolean isStarted();

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

    abstract class Callback
    {
        /**
         * 在更新追踪信息之前会调用此方法来决定可不可以更新，默认true-可以更新
         *
         * @param source       源view
         * @param sourceParent 源view的父view
         * @param target       目标view
         * @return true-可以更新，false-不要更新
         */
        public boolean canUpdate(View source, View sourceParent, View target)
        {
            return true;
        }

        /**
         * 按照指定的位置{@link Position}追踪到target后回调，回调source在x和y方向需要是什么值才可以到指定的位置
         *
         * @param x            source相对于父布局需要的x值
         * @param y            source相对于父布局需要的y值
         * @param source       源view
         * @param sourceParent 源view的父view
         * @param target       目标view
         */
        public abstract void onUpdate(int x, int y, View source, View sourceParent, View target);
    }
}

```

# Updater接口
```java
/**
 * 实时更新接口
 */
public interface Updater
{
    /**
     * 设置要实时更新的对象
     *
     * @param updatable
     */
    void setUpdatable(Updatable updatable);

    /**
     * 开始实时更新
     *
     * @return true-成功开始
     */
    boolean start();

    /**
     * 停止实时更新
     */
    void stop();

    /**
     * 是否已经开始实时更新
     *
     * @return
     */
    boolean isStarted();

    interface Updatable
    {
        /**
         * 触发更新
         *
         * @return true-更新成功
         */
        boolean update();
    }
}
```