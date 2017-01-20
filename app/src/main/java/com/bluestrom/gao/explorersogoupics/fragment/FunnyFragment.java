package com.bluestrom.gao.explorersogoupics.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.bluestrom.gao.explorersogoupics.pojo.SogouPicPojo;
import com.bluestrom.gao.explorersogoupics.pojo.SogouPicsResult;
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

    private List<SogouPicPojo> picsList;

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private PhotoBeanRecyclerViewAdapter recyclerViewAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private static final int REFRESH_PICS_SUCCESS = 0, REFRESH_PICS_FAILURE = 1;

    private int currentStartPosition;

    private final int picsRequestNum = 15;

    private final int loadMoreThreshold = 3;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FunnyFragment() {
    }

    @SuppressWarnings("unused")
    public static FunnyFragment newInstance() {
        FunnyFragment fragment = new FunnyFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        recyclerViewAdapter = new PhotoBeanRecyclerViewAdapter(picsList, mListener);
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
        getFunnyPics();
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFunnyPics();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.funnyBeautyList);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.getItemCount() - layoutManager.findLastVisibleItemPositions(null)[0] < loadMoreThreshold) {
                    getFunnyPics();
                }
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new PicsRecyclerDecoration(context));
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PICS_SUCCESS:
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    SogouPicsResult picsResult = (SogouPicsResult) msg.obj;
                    currentStartPosition += picsResult.getAll_items().size();
                    for (SogouPicPojo picPojo : picsResult.getAll_items()) {
                        picsList.add(picPojo);
                        recyclerViewAdapter.notifyItemInserted(0);
                    }
//                    recyclerView.scrollToPosition(0);
                    break;
                case REFRESH_PICS_FAILURE:
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(SogouPicPojo item);
    }

    private void getFunnyPics() {
        Map<String, String> params = new HashMap<>();
        params.put("category", "美女");
        params.put("tag", "");
        params.put("start", String.valueOf(currentStartPosition));
        params.put("len", String.valueOf(picsRequestNum));
        Callback picCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = mUIHandler.obtainMessage(REFRESH_PICS_FAILURE);
                msg.sendToTarget();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    SogouPicsResult result = Pub.getGsonClient().fromJson(response.body().charStream(), SogouPicsResult.class);
                    Message msg = mUIHandler.obtainMessage(REFRESH_PICS_SUCCESS);
                    msg.obj = result;
                    msg.sendToTarget();
                } finally {
                    response.close();
                }
            }
        };
        NetworkCall.asynNetworkGet(Const.SOGOU_PIC_BASIC_URL, null, params, picCallback);
    }
}
