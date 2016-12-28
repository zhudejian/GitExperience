package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.TextView;



/**
 * Created by hasee on 2016/12/27.
 */

public class AutoList extends ListView implements AbsListView.OnScrollListener {
    public AutoList(Context context) {
        this(context, null);
        //initView(context);
    }

    public AutoList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        //initView(context);
    }

    public AutoList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    // 区分当前操作是刷新还是加载
    public static final int REFRESH = 0;
    public static final int LOAD = 1;

    // 区分PULL和RELEASE的距离的大小
    private static final int SPACE = 20;

    // 定义header的四种状态和当前状态
    private static final int NONE = 0;
    private static final int PULL = 1;
    private static final int RELEASE = 2;
    private static final int REFRESHING = 3;
    private int state;

    private int startY;

    private int firstVisibleItem;
    private int scrollState;
    private int headerContentInitialHeight;
    private int headerContentHeight;
    private int footerViewHeight;

    // 只有在listview第一个item显示的时候（listview滑到了顶部）才进行下拉刷新， 否则此时的下拉只是滑动listview
    private boolean isRecorded;
    private boolean isLoading;// 判断是否正在加载
    private boolean loadEnable = true;// 开启或者关闭加载更多功能
    private boolean isLoadFull;

    private int pageSize = 10;

    private OnRefreshListener onRefreshListener;
    private OnLoadListener onLoadListener;
    private View header;
    private View footer;
    ShowDifferentViews differentViews;
public interface ShowDifferentViews
    {
        /**
         * 处于拉状态但还没到松开刷新状态*/
        public void OnPull();
        /**
         * 松开刷新状态header所要进行的变化*/
        public void onRefresh();
        /**正在加载时，header的画面*/
        public void onrefreshing();
        /**加载更多时进行的画面*/
        public void onLoadingMore();
    }
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void initView(Context context) {

        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        this.setOnScrollListener(this);

    }
    public void setFooter(View foot) {

        if (footer!=null)
        {
            removeFooterView(footer);
        }
        footer=foot;
        footer.measure(0,0);
        footerViewHeight = footer.getMeasuredHeight();
        footer.setPadding(0,-headerContentHeight,0,0);
      addFooterView(footer);
    }public void setHeader(View head) {
        if (header!=null)
        {
            removeHeaderView(header);
        }
        header = head;
        header.measure(0,0);
        headerContentHeight = header.getMeasuredHeight();
        header.setPadding(0,-headerContentHeight,0,0);
        addHeaderView(header);

    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录第一次按下的位置的纵坐标
                startY = (int) ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                //正在刷新状态
                if (state == REFRESHING)
                    return true;

                int deltaY = (int) (ev.getY() - startY);
                int paddingtop = -headerContentHeight + deltaY;
                //说明向下滑动，且第一个被显示了，头部可以慢慢显示了
                if (paddingtop > -headerContentHeight && getFirstVisiblePosition() == 0&&header!=null&&!isLoading) {
                    header.setPadding(0, paddingtop, 0, 0);

                    //从下拉状态进入松开刷新状态
                    if (paddingtop > 0 && state == PULL) {
                        state = RELEASE;
                        refreshHeaderViewByState();
                    } else if (paddingtop < 0 && state == NONE) {
                        state = PULL;
                        refreshHeaderViewByState();
                    }
                    return true;//拦截事件
                }
                break;
            case MotionEvent.ACTION_UP:
                if (state==RELEASE&&header!=null)
                {
                    //滑到一定距离准备刷新了
                    header.setPadding(0,0,0,0);
                    //设置状态为刷新
                    state=REFRESHING;
                    //执行刷新操作
                    refreshHeaderViewByState();
                    if (onRefreshListener!=null)
                        onRefreshListener.onRefresh();

                }else if (state==PULL&&header!=null)
                {
                    state=NONE;
                    refreshHeaderViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    //2种事件的处理
    public void onRefresh() {
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    public void onLoad() {
        if (onLoadListener != null) {
            onLoadListener.onLoad();
        }
    }





    // 用于加载更多结束后的回调
    public void onLoadComplete() {
        isLoading = false;
    }

    public void setDifferentViews(ShowDifferentViews differentViews) {
        this.differentViews = differentViews;
    }

    // 根据当前状态，调整header
    private void refreshHeaderViewByState() {
           switch (state)
           {
               case NONE:
                   if (header!=null)
                   header.setPadding(0,-headerContentHeight,0,0);
                   break;
               case PULL:
                   if (header!=null &&differentViews!=null)
                 differentViews.OnPull();
                   break;
               case  RELEASE:
                   if (header!=null &&differentViews!=null)
                       differentViews.onRefresh();

                   break;
               case REFRESHING:
                   if (header!=null &&differentViews!=null)
                       differentViews.onrefreshing();
                   break;
           }
    }

    //刷新完成后调用，重置状态，在完成数据更新后自己调用
    public void OncompleteRefresh(){
        //是不是加载更多进来的
            if(isLoading)
            {
                if (footer!=null)
                footer.setPadding(0,-footerViewHeight,0,0);
                isLoading=false;
            }else {
                state=NONE;
                refreshHeaderViewByState();
            }

    }
    //滑动时会调用，刚开始滑动时，i=1,非0，停止时(鼠标离开时，)调用i的值为0
    /**
     * SCROLL_STATE_IDLE:闲置状态，就是手指松开
     * SCROLL_STATE_TOUCH_SCROLL：手指触摸滑动，就是按着来滑动
     * SCROLL_STATE_FLING：快速滑动后松开
     */
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
       // Log.i("滑动", "" + scrollState);
      //  this.scrollState = scrollState;
        if (scrollState==OnScrollListener.SCROLL_STATE_IDLE&&getLastVisiblePosition()==(getCount()-1)&&isLoading==false&&footer!=null&&state==NONE ){
                   isLoading=true;
            if (differentViews!=null)
                differentViews.onLoadingMore();
            footer.setPadding(0,0,0,0);
            setSelection(getCount());


            if (onLoadListener!=null)
                onLoadListener.onLoad();

        }
        //ifNeedLoad(absListView, scrollState);
    }


    //滑动过程中不断调用，totalItemCount为listView中view总数，头加尾巴+内部item
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
      //  Log.i("滑动中", "第一个可见的" + firstVisibleItem + "可见总数" + visibleItemCount + "总数" + totalItemCount);
       // Log.i("滑动中1"," "+getFirstVisiblePosition()+" "+getLastVisiblePosition());
    }

    /*
 * 定义下拉刷新接口
 */
    public interface OnRefreshListener {
        public void onRefresh();
    }

    /*
     * 定义加载更多接口
     */
    public interface OnLoadListener {
        public void onLoad();
    }
}
