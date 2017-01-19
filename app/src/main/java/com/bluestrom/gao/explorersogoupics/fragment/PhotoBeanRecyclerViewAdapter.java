package com.bluestrom.gao.explorersogoupics.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluestrom.gao.explorersogoupics.R;
import com.bluestrom.gao.explorersogoupics.application.PicsApplication;
import com.bluestrom.gao.explorersogoupics.fragment.dummy.DummyContent.PhotoBean;
import com.bluestrom.gao.explorersogoupics.pojo.SogouPicPojo;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PhotoBean} and makes a call to the
 * specified {@link FunnyFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PhotoBeanRecyclerViewAdapter extends RecyclerView.Adapter<PhotoBeanRecyclerViewAdapter.ViewHolder> {

    private final List<SogouPicPojo> mValues;
    private final FunnyFragment.OnListFragmentInteractionListener mListener;

    public PhotoBeanRecyclerViewAdapter(List<SogouPicPojo> items, FunnyFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_photobean, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.picThumb.setAspectRatio(holder.mItem.getThumb_width() / holder.mItem.getThumb_height());
        holder.picThumb.setImageURI(holder.mItem.getThumbUrl());
        List<String> tags = holder.mItem.getTags();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 0, 0, 0);
        for (String tag : tags) {
            TextView tv = new TextView(PicsApplication.getInstance());
            tv.setText(tag);
            holder.tagsLayout.addView(tv, layoutParams);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final SimpleDraweeView picThumb;
        public final LinearLayout tagsLayout;
        public SogouPicPojo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            picThumb = (SimpleDraweeView) view.findViewById(R.id.pic_thumb);
            tagsLayout = (LinearLayout) view.findViewById(R.id.tags_layout);
        }
    }
}
