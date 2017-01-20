package com.bluestrom.gao.explorersogoupics.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bluestrom.gao.explorersogoupics.R;
import com.bluestrom.gao.explorersogoupics.fragment.FunnyFragment;
import com.bluestrom.gao.explorersogoupics.fragment.dummy.DummyContent.PhotoBean;
import com.bluestrom.gao.explorersogoupics.pojo.SogouPicPojo;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PhotoBean} and makes a call to the
 * specified {@link FunnyFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PhotoBeanRecyclerViewAdapter extends RecyclerView.Adapter<PhotoBeanRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "PhotoBeanRecyclerViewAdapter";

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
        holder.picThumb.setAspectRatio((float) holder.mItem.getSthumb_width() / holder.mItem.getSthumb_height());
        ImageRequest sthumbRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(holder.mItem.getSthumbUrl()))
                .setCacheChoice(ImageRequest.CacheChoice.SMALL)
                .build();
        DraweeController sthumbController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(sthumbRequest)
                .build();
        holder.picThumb.setController(sthumbController);
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
