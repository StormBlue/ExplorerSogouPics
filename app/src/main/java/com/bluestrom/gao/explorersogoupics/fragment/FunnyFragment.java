package com.bluestrom.gao.explorersogoupics.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluestrom.gao.explorersogoupics.R;
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

    private List<SogouPicPojo> picsList;

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private PhotoBeanRecyclerViewAdapter recyclerViewAdapter;

    private static final int REFRESH_PICS = 0;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funny_photo, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        Context context = view.getContext();
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getBeautyPics();
        return view;
    }

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PICS:
                    SogouPicsResult picsResult = (SogouPicsResult) msg.obj;
                    picsList = picsResult.getAll_items();
                    recyclerViewAdapter = new PhotoBeanRecyclerViewAdapter(picsList, mListener);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(recyclerViewAdapter);
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

    private void getBeautyPics() {
        Map<String, String> params = new HashMap<>();
        params.put("category", "美女");
        params.put("tag", "青春");
        params.put("start", "0");
        params.put("len", "10");
        Callback picCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    SogouPicsResult result = Pub.getGsonClient().fromJson(response.body().charStream(), SogouPicsResult.class);
                    Message msg = mUIHandler.obtainMessage(REFRESH_PICS);
                    msg.obj = result;
                    msg.sendToTarget();
                } finally {
                    response.close();
                }
            }
        };
        NetworkCall.asynNetworkGet(Const.SOUGOU_PIC_BASIC_URL, null, params, picCallback);
    }
}
