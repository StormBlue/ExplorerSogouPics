package com.bluestrom.gao.explorersogoupics.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluestrom.gao.explorersogoupics.greendao.PicsTagsConvert;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Gao-Krund on 2017/1/18.
 */

@Entity()
public class SogouPicPojo implements Parcelable {

    @Id(autoincrement = true)
    private long logId;

    private long id;

    private int did;

    private String thumbUrl;

    private int thumb_width;

    private int thumb_height;

    private String sthumbUrl;

    private int sthumb_width;

    private int sthumb_height;

    private String bthumbUrl;

    private int bthumb_width;

    private int bthumb_height;

    private String pic_url;

    private int width;

    private int height;

    private int size;

    private String ori_pic_url;

    private String ext_url;

    private String page_title;

    private String page_url;

    private String title;

    @Convert(converter = PicsTagsConvert.class, columnType = String.class)
    private String[] tags;

    private String group_mark;

    private int group_index;

    private String publish_time;

    private String surr1;

    private String surr2;

    private String category;

    private int weight;

    private int deleted;

    private String wapLink;

    private String webLink;

    @Generated(hash = 480009525)
    public SogouPicPojo(long logId, long id, int did, String thumbUrl,
                        int thumb_width, int thumb_height, String sthumbUrl, int sthumb_width,
                        int sthumb_height, String bthumbUrl, int bthumb_width,
                        int bthumb_height, String pic_url, int width, int height, int size,
                        String ori_pic_url, String ext_url, String page_title, String page_url,
                        String title, String[] tags, String group_mark, int group_index,
                        String publish_time, String surr1, String surr2, String category,
                        int weight, int deleted, String wapLink, String webLink) {
        this.logId = logId;
        this.id = id;
        this.did = did;
        this.thumbUrl = thumbUrl;
        this.thumb_width = thumb_width;
        this.thumb_height = thumb_height;
        this.sthumbUrl = sthumbUrl;
        this.sthumb_width = sthumb_width;
        this.sthumb_height = sthumb_height;
        this.bthumbUrl = bthumbUrl;
        this.bthumb_width = bthumb_width;
        this.bthumb_height = bthumb_height;
        this.pic_url = pic_url;
        this.width = width;
        this.height = height;
        this.size = size;
        this.ori_pic_url = ori_pic_url;
        this.ext_url = ext_url;
        this.page_title = page_title;
        this.page_url = page_url;
        this.title = title;
        this.tags = tags;
        this.group_mark = group_mark;
        this.group_index = group_index;
        this.publish_time = publish_time;
        this.surr1 = surr1;
        this.surr2 = surr2;
        this.category = category;
        this.weight = weight;
        this.deleted = deleted;
        this.wapLink = wapLink;
        this.webLink = webLink;
    }

    @Generated(hash = 765004966)
    public SogouPicPojo() {
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getDid() {
        return this.did;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getThumbUrl() {
        return this.thumbUrl;
    }

    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    public int getThumb_width() {
        return this.thumb_width;
    }

    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }

    public int getThumb_height() {
        return this.thumb_height;
    }

    public void setSthumbUrl(String sthumbUrl) {
        this.sthumbUrl = sthumbUrl;
    }

    public String getSthumbUrl() {
        return this.sthumbUrl;
    }

    public void setSthumb_width(int sthumb_width) {
        this.sthumb_width = sthumb_width;
    }

    public int getSthumb_width() {
        return this.sthumb_width;
    }

    public void setSthumb_height(int sthumb_height) {
        this.sthumb_height = sthumb_height;
    }

    public int getSthumb_height() {
        return this.sthumb_height;
    }

    public void setBthumbUrl(String bthumbUrl) {
        this.bthumbUrl = bthumbUrl;
    }

    public String getBthumbUrl() {
        return this.bthumbUrl;
    }

    public void setBthumb_width(int bthumb_width) {
        this.bthumb_width = bthumb_width;
    }

    public int getBthumb_width() {
        return this.bthumb_width;
    }

    public void setBthumb_height(int bthumb_height) {
        this.bthumb_height = bthumb_height;
    }

    public int getBthumb_height() {
        return this.bthumb_height;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPic_url() {
        return this.pic_url;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public void setOri_pic_url(String ori_pic_url) {
        this.ori_pic_url = ori_pic_url;
    }

    public String getOri_pic_url() {
        return this.ori_pic_url;
    }

    public void setExt_url(String ext_url) {
        this.ext_url = ext_url;
    }

    public String getExt_url() {
        return this.ext_url;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public String getPage_title() {
        return this.page_title;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

    public String getPage_url() {
        return this.page_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setGroup_mark(String group_mark) {
        this.group_mark = group_mark;
    }

    public String getGroup_mark() {
        return this.group_mark;
    }

    public void setGroup_index(int group_index) {
        this.group_index = group_index;
    }

    public int getGroup_index() {
        return this.group_index;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getPublish_time() {
        return this.publish_time;
    }

    public void setSurr1(String surr1) {
        this.surr1 = surr1;
    }

    public String getSurr1() {
        return this.surr1;
    }

    public void setSurr2(String surr2) {
        this.surr2 = surr2;
    }

    public String getSurr2() {
        return this.surr2;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getDeleted() {
        return this.deleted;
    }

    public void setWapLink(String wapLink) {
        this.wapLink = wapLink;
    }

    public String getWapLink() {
        return this.wapLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getWebLink() {
        return this.webLink;
    }

    private SogouPicPojo(Parcel in) {
        title = in.readString();
        sthumbUrl = in.readString();
        pic_url = in.readString();
        width = in.readInt();
        height = in.readInt();
        ori_pic_url = in.readString();
        tags = in.createStringArray();
    }

    // 用来创建自定义的Parcelable的对象
    public static final Parcelable.Creator<SogouPicPojo> CREATOR
            = new Parcelable.Creator<SogouPicPojo>() {
        public SogouPicPojo createFromParcel(Parcel in) {
            return new SogouPicPojo(in);
        }

        public SogouPicPojo[] newArray(int size) {
            return new SogouPicPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(sthumbUrl);
        dest.writeString(pic_url);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(ori_pic_url);
        dest.writeStringArray(tags);
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((SogouPicPojo) obj).getId();
    }
}
