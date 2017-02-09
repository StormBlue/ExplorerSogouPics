package com.bluestrom.gao.explorersogoupics.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluestrom.gao.explorersogoupics.R;
import com.bluestrom.gao.explorersogoupics.pojo.SogouPicPojo;
import com.bluestrom.gao.explorersogoupics.util.Pub;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

public class PhotoDetailActivity extends AppCompatActivity {

    private static final String TAG = "PhotoDetailActivity";

    public static final String PHOTO_POJO = "sogouPic";
    public static final int UPDATE_TOOLBAR_COLOR = 0;

    private Context mContext;

    private Toolbar toolbar;

    private SogouPicPojo picPojo;

    private SimpleDraweeView picOrigin;

    private LinearLayout tagsLayout;

    private boolean hasSetStatusBarColor;

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TOOLBAR_COLOR:
                    hasSetStatusBarColor = true;
                    getWindow().setStatusBarColor(msg.arg1);
                    toolbar.setBackgroundColor(msg.arg1);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_photo_detail);
        init();
    }

    private void init() {
        picPojo = (SogouPicPojo) getIntent().getExtras().get(PHOTO_POJO);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.activity_detail_toolbar);
        tagsLayout = (LinearLayout) findViewById(R.id.tags_layout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(picPojo.getTitle());
        picOrigin = (SimpleDraweeView) findViewById(R.id.pic_origin);
        Postprocessor getPaletteProcessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "getPaletteProcessor";
            }

            @Override
            public void process(Bitmap bitmap) {
                if (hasSetStatusBarColor || null == bitmap || bitmap.isRecycled()) return;
                Palette palette = Palette.from(bitmap).generate();
                Message paletteMsg = mUIHandler.obtainMessage(UPDATE_TOOLBAR_COLOR);
                paletteMsg.arg1 = palette.getMutedColor(getResources().getColor(R.color.colorPrimary));
                paletteMsg.sendToTarget();
            }
        };
        ImageRequest lowPicRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(picPojo.getSthumbUrl()))
                .setProgressiveRenderingEnabled(true)
                .setPostprocessor(getPaletteProcessor)
                .build();
        ImageRequest sogouPicRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(picPojo.getPic_url()))
                .setProgressiveRenderingEnabled(true)
                .setPostprocessor(getPaletteProcessor)
                .build();
        ImageRequest originPicrequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(picPojo.getOri_pic_url()))
                .setProgressiveRenderingEnabled(true)
                .setPostprocessor(getPaletteProcessor)
                .build();
        ImageRequest[] requests = {sogouPicRequest, originPicrequest};
        DraweeController originController = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(lowPicRequest)
                .setFirstAvailableImageRequests(requests)
                .setOldController(picOrigin.getController())
                .build();
        picOrigin.setAspectRatio((float) picPojo.getWidth() / picPojo.getHeight());
        picOrigin.setController(originController);
        LinearLayout.LayoutParams tagLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tagLayoutParams.setMargins(16, 0, 0, 0);
        for (int i = 0; i < picPojo.getTags().length; i++) {
            TextView tagTextView = new TextView(getApplicationContext());
            tagTextView.setLayoutParams(tagLayoutParams);
            tagTextView.setBackgroundColor(getResources().getColor(R.color.tag_background_color));
            tagTextView.setTextColor(getResources().getColor(android.R.color.black));
            tagTextView.setPadding(5, 3, 5, 3);
            tagTextView.setText(picPojo.getTags()[i]);
            tagsLayout.addView(tagTextView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasSetStatusBarColor = false;
    }
}
