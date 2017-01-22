package com.bluestrom.gao.explorersogoupics.pojo;

import org.greenrobot.greendao.annotation.Entity;

import java.util.List;

/**
 * Created by Gao-Krund on 2017/1/18.
 */

public class SogouPicsResult {
    private String category;

    private String tag;

    private int startIndex;

    private int maxEnd;

    private String items;

    private List<SogouPicPojo> all_items;

    private String newsResult;

    private int itemsOnPage;

    private String fromItem;

    private String groupList;

    private String resolution;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public void setMaxEnd(int maxEnd) {
        this.maxEnd = maxEnd;
    }

    public int getMaxEnd() {
        return this.maxEnd;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getItems() {
        return this.items;
    }

    public void setAll_items(List<SogouPicPojo> all_items) {
        this.all_items = all_items;
    }

    public List<SogouPicPojo> getAll_items() {
        return this.all_items;
    }

    public void setNewsResult(String newsResult) {
        this.newsResult = newsResult;
    }

    public String getNewsResult() {
        return this.newsResult;
    }

    public void setItemsOnPage(int itemsOnPage) {
        this.itemsOnPage = itemsOnPage;
    }

    public int getItemsOnPage() {
        return this.itemsOnPage;
    }

    public void setFromItem(String fromItem) {
        this.fromItem = fromItem;
    }

    public String getFromItem() {
        return this.fromItem;
    }

    public void setGroupList(String groupList) {
        this.groupList = groupList;
    }

    public String getGroupList() {
        return this.groupList;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getResolution() {
        return this.resolution;
    }
}
