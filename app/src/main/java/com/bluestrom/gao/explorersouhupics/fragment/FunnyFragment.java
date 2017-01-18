package com.bluestrom.gao.explorersouhupics.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluestrom.gao.explorersouhupics.R;
import com.bluestrom.gao.explorersouhupics.fragment.dummy.DummyContent;
import com.bluestrom.gao.explorersouhupics.fragment.dummy.DummyContent.PhotoBean;
import com.bluestrom.gao.explorersouhupics.pojo.SouhuPicsResult;
import com.bluestrom.gao.explorersouhupics.util.Const;
import com.bluestrom.gao.explorersouhupics.util.NetworkCall;
import com.bluestrom.gao.explorersouhupics.util.Pub;

import java.io.IOException;
import java.util.HashMap;
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

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FunnyFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FunnyFragment newInstance(int columnCount) {
        FunnyFragment fragment = new FunnyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funny_photo, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new PhotoBeanRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        return view;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PhotoBean item);
    }

    private void getBeautyPics() {
        Map<String, String> params = new HashMap<>();
        params.put("category", "美女");
        params.put("tag", "");
        params.put("start", "0");
        params.put("len", "1");
        Callback picCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    SouhuPicsResult result = Pub.getGsonClient().fromJson(response.body().charStream(), SouhuPicsResult.class);
                } finally {
                    response.close();
                }
            }
        };
        NetworkCall.asynNetworkGet(Const.SOUHU_PIC_BASIC_URL, null, params, picCallback);
    }
}
