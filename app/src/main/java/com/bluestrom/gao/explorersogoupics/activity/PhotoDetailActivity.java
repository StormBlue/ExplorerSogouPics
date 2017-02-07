package com.bluestrom.gao.explorersogoupics.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.bluestrom.gao.explorersogoupics.R;
import com.bluestrom.gao.explorersogoupics.pojo.SogouPicPojo;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class PhotoDetailActivity extends AppCompatActivity {

    private static final String TAG = "PhotoDetailActivity";

    public static final String PHOTO_POJO = "sogouPic";
    public static final int UPDATE_TOOLBAR_COLOR = 0;

    private Context mContext;

    private Toolbar toolbar;

    private SogouPicPojo picPojo;

    private SimpleDraweeView picOrigin;

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TOOLBAR_COLOR:
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(picPojo.getTitle());
        picOrigin = (SimpleDraweeView) findViewById(R.id.pic_origin);
        ImageRequest sogouPicRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(picPojo.getPic_url()))
                .setProgressiveRenderingEnabled(true)
                .build();
        ImageRequest originPicrequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(picPojo.getOri_pic_url()))
                .setProgressiveRenderingEnabled(true)
                .build();
        ImageRequest[] requests = {sogouPicRequest, originPicrequest};
        DraweeController originController = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(Uri.parse(picPojo.getSthumbUrl())))
                .setFirstAvailableImageRequests(requests)
                .setOldController(picOrigin.getController())
                .build();
        picOrigin.setAspectRatio((float) picPojo.getWidth() / picPojo.getHeight());
        picOrigin.setController(originController);

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource =
                imagePipeline.fetchImageFromBitmapCache(ImageRequest.fromUri(picPojo.getSthumbUrl()), imagePipeline.);
        try {
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                     @Override
                                     public void onNewResultImpl(@Nullable Bitmap bitmap) {
                                         Palette palette = Palette.from(bitmap).generate();
                                         Message paletteMsg = mUIHandler.obtainMessage(UPDATE_TOOLBAR_COLOR);
                                         paletteMsg.arg1 = palette.getDarkMutedColor(getResources().getColor(R.color.colorPrimary));
                                         paletteMsg.sendToTarget();
                                     }

                                     @Override
                                     public void onFailureImpl(DataSource dataSource) {
                                     }
                                 },
                    CallerThreadExecutor.getInstance());
        } finally {
            dataSource.close();
        }
    }

}
