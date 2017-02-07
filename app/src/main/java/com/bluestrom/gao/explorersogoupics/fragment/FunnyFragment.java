package com.bluestrom.gao.explorersogoupics.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluestrom.gao.explorersogoupics.R;
import com.bluestrom.gao.explorersogoupics.adapter.PhotoBeanRecyclerViewAdapter;
import com.bluestrom.gao.explorersogoupics.application.PicsApplication;
import com.bluestrom.gao.explorersogoupics.greendao.SogouPicPojoDao;
import com.bluestrom.gao.explorersogoupics.pojo.SogouPicPojo;
import com.bluestrom.gao.explorersogoupics.pojo.SogouPicsResult;
import com.bluestrom.gao.explorersogoupics.uiutil.PicsRecyclerDecoration;
import com.bluestrom.gao.explorersogoupics.util.Const;
import com.bluestrom.gao.explorersogoupics.util.NetworkCall;
import com.bluestrom.gao.explorersogoupics.util.Pub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FunnyFragment extends Fragment {

    private static final String TAG = "FunnyFragment";

    private HandlerThread mDataHandlerThread;

    private Handler mDataHandler;

    private List<SogouPicPojo> picsList;

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private PhotoBeanRecyclerViewAdapter recyclerViewAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private static final int DATA_HANDLER_REQUEST_PICS_SUCCESS = 0, DATA_HANDLER_REQUEST_PICS_FAILURE = 1;

    private static final int UI_HANDLER_DISMISS_REFRESHLAYOUT = 0, UI_HANDLER_INSERT_PIC = 1,
            UI_HANDLER_SCROLL_PICS = 2, UI_HANDLER_REFRESH_PICS = 3;

    private int currentStartPosition;

    private final int picsRowNum = 2;

    private final int picsRequestNum = 15;

    private final int loadMoreThreshold = 3;

    private boolean isRequestingPics = false;

    private final int PICS_REQUEST_FLAG_INIT = 0, PICS_REQUEST_FLAG_REFRESH = 1, PICS_REQUEST_FLAG_LOADMORE = 2;

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UI_HANDLER_DISMISS_REFRESHLAYOUT:
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    break;
                case UI_HANDLER_INSERT_PIC:
                    recyclerViewAdapter.notifyItemChanged(msg.arg1);
                    break;
                case UI_HANDLER_SCROLL_PICS:
                    recyclerView.scrollToPosition(msg.arg1);
                    break;
                case UI_HANDLER_REFRESH_PICS:
                    recyclerViewAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FunnyFragment() {
        mDataHandlerThread = new HandlerThread("DataProcessThread");
        mDataHandlerThread.start();

        mDataHandler = new Handler(mDataHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                mUIHandler.obtainMessage(UI_HANDLER_DISMISS_REFRESHLAYOUT).sendToTarget();
                switch (msg.what) {
                    case DATA_HANDLER_REQUEST_PICS_SUCCESS:
                        SogouPicsResult picsResult = (SogouPicsResult) msg.obj;
                        List<SogouPicPojo> needInsertPics = picsResult.getAll_items();
                        if (needInsertPics == null) return;
                        needInsertPics.removeAll(picsList);
                        switch (msg.arg1) {
                            case PICS_REQUEST_FLAG_INIT:
                                picsList.addAll(picsResult.getAll_items());
                                mUIHandler.obtainMessage(UI_HANDLER_REFRESH_PICS).sendToTarget();
                                break;
                            case PICS_REQUEST_FLAG_REFRESH:
                                if (!(needInsertPics.size() > 0)) {
                                    break;
                                }
                                for (SogouPicPojo picPojo : needInsertPics) {
                                    picsList.add(picPojo);
                                    Message insertPicMsg = mUIHandler.obtainMessage(UI_HANDLER_INSERT_PIC);
                                    insertPicMsg.arg1 = 0;
                                    insertPicMsg.sendToTarget();
                                }
                                Message scrollPicsMsg = mUIHandler.obtainMessage(UI_HANDLER_SCROLL_PICS);
                                scrollPicsMsg.arg1 = 0;
                                scrollPicsMsg.sendToTarget();
                                break;
                            case PICS_REQUEST_FLAG_LOADMORE:
                                picsList.addAll(needInsertPics);
                                mUIHandler.obtainMessage(UI_HANDLER_REFRESH_PICS).sendToTarget();
                                break;
                            default:
                                break;
                        }
                        currentStartPosition = picsList.size();
                        break;
                    case DATA_HANDLER_REQUEST_PICS_FAILURE:
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @SuppressWarnings("unused")
    public static FunnyFragment newInstance() {
        FunnyFragment fragment = new FunnyFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().getApplication();
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picsList = new ArrayList<>();
        recyclerViewAdapter = new PhotoBeanRecyclerViewAdapter(getActivity(), picsList, mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funny_photo, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        currentStartPosition = 0;
        initView(view);
        getFunnyPics(PICS_REQUEST_FLAG_INIT);
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFunnyPics(PICS_REQUEST_FLAG_REFRESH);
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.funnyBeautyList);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(picsRowNum, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.getItemCount() - getLastVisibleItemPositionFromLayoutManager(layoutManager) < loadMoreThreshold) {
                    getFunnyPics(PICS_REQUEST_FLAG_LOADMORE);
                }
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new PicsRecyclerDecoration(context));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    /**
     * 获取RecyclerView中的最后一个可见Item在list中的位置
     *
     * @param layoutManager
     * @return
     */
    private int getLastVisibleItemPositionFromLayoutManager(StaggeredGridLayoutManager layoutManager) {
        int result = 0;
        int[] positions = layoutManager.findLastVisibleItemPositions(null);
        for (int i = 0; i < picsRowNum; i++) {
            if (positions[i] > result) {
                result = positions[i];
            }
        }
        return result;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDataHandlerThread.quit();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(SogouPicPojo item);
    }

    /**
     * 比对两个list
     *
     * @param shallInsertList
     * @param originList
     * @return
     */
    private List<SogouPicPojo> needInsertPicsToList(List<SogouPicPojo> shallInsertList, List<SogouPicPojo> originList) {
        Long startTime = System.currentTimeMillis();
        List<SogouPicPojo> result = new ArrayList<>();
        tag:
        for (SogouPicPojo picPojo : shallInsertList) {
            for (SogouPicPojo originPojo : originList) {
                if (picPojo.getId() == originPojo.getId()) {
                    continue tag;
                }
            }
            result.add(picPojo);
        }
        return result;
    }

    /**
     * 讲list中未加入数据库的数据加入数据库并返回
     *
     * @param list
     * @return 加入到数据库中的数据
     */
    private List<SogouPicPojo> insertPicsToDatabase(List<SogouPicPojo> list) {
        List<SogouPicPojo> result = new ArrayList<>();
        SogouPicPojoDao dao = PicsApplication.getDaoSession().getSogouPicPojoDao();
        for (int i = 0; i < list.size(); i++) {
            SogouPicPojo picPojo = list.get(i);
            if (!(dao.queryBuilder().where(SogouPicPojoDao.Properties.Id.eq(picPojo.getId())).list().size() > 0)) {
                dao.insert(picPojo);
                result.add(picPojo);
            }
        }
        return result;
    }

    /**
     * 请求搜狗图片列表
     *
     * @param flag 0为刷新，1为加载更多
     */
    private void getFunnyPics(final int flag) {
        if (isRequestingPics) return;
        Map<String, String> params = new HashMap<>();
        params.put("category", "美女");
        params.put("tag", "");
        params.put("start", PICS_REQUEST_FLAG_LOADMORE != flag ? String.valueOf(0) : String.valueOf(currentStartPosition));
        params.put("len", String.valueOf(picsRequestNum));
        Callback picCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                isRequestingPics = false;
                Message msg = mDataHandler.obtainMessage(DATA_HANDLER_REQUEST_PICS_FAILURE);
                msg.sendToTarget();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                isRequestingPics = false;
                try {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    SogouPicsResult result = Pub.getGsonClient().fromJson(response.body().charStream(), SogouPicsResult.class);
                    Message msg = mDataHandler.obtainMessage(DATA_HANDLER_REQUEST_PICS_SUCCESS);
                    msg.obj = result;
                    msg.arg1 = flag;
                    msg.sendToTarget();
                } finally {
                    response.close();
                }
            }
        };
        NetworkCall.asynNetworkGet(Const.SOGOU_PIC_BASIC_URL, null, params, picCallback);
        isRequestingPics = true;
    }
}
