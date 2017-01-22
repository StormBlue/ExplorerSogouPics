package com.bluestrom.gao.explorersogoupics.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bluestrom.gao.explorersogoupics.pojo.SogouPicPojo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SOGOU_PIC_POJO".
*/
public class SogouPicPojoDao extends AbstractDao<SogouPicPojo, Long> {

    public static final String TABLENAME = "SOGOU_PIC_POJO";

    /**
     * Properties of entity SogouPicPojo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LogId = new Property(0, long.class, "logId", true, "_id");
        public final static Property Id = new Property(1, long.class, "id", false, "ID");
        public final static Property Did = new Property(2, int.class, "did", false, "DID");
        public final static Property ThumbUrl = new Property(3, String.class, "thumbUrl", false, "THUMB_URL");
        public final static Property Thumb_width = new Property(4, int.class, "thumb_width", false, "THUMB_WIDTH");
        public final static Property Thumb_height = new Property(5, int.class, "thumb_height", false, "THUMB_HEIGHT");
        public final static Property SthumbUrl = new Property(6, String.class, "sthumbUrl", false, "STHUMB_URL");
        public final static Property Sthumb_width = new Property(7, int.class, "sthumb_width", false, "STHUMB_WIDTH");
        public final static Property Sthumb_height = new Property(8, int.class, "sthumb_height", false, "STHUMB_HEIGHT");
        public final static Property BthumbUrl = new Property(9, String.class, "bthumbUrl", false, "BTHUMB_URL");
        public final static Property Bthumb_width = new Property(10, int.class, "bthumb_width", false, "BTHUMB_WIDTH");
        public final static Property Bthumb_height = new Property(11, int.class, "bthumb_height", false, "BTHUMB_HEIGHT");
        public final static Property Pic_url = new Property(12, String.class, "pic_url", false, "PIC_URL");
        public final static Property Width = new Property(13, int.class, "width", false, "WIDTH");
        public final static Property Height = new Property(14, int.class, "height", false, "HEIGHT");
        public final static Property Size = new Property(15, int.class, "size", false, "SIZE");
        public final static Property Ori_pic_url = new Property(16, String.class, "ori_pic_url", false, "ORI_PIC_URL");
        public final static Property Ext_url = new Property(17, String.class, "ext_url", false, "EXT_URL");
        public final static Property Page_title = new Property(18, String.class, "page_title", false, "PAGE_TITLE");
        public final static Property Page_url = new Property(19, String.class, "page_url", false, "PAGE_URL");
        public final static Property Title = new Property(20, String.class, "title", false, "TITLE");
        public final static Property Tags = new Property(21, String.class, "tags", false, "TAGS");
        public final static Property Group_mark = new Property(22, String.class, "group_mark", false, "GROUP_MARK");
        public final static Property Group_index = new Property(23, int.class, "group_index", false, "GROUP_INDEX");
        public final static Property Publish_time = new Property(24, String.class, "publish_time", false, "PUBLISH_TIME");
        public final static Property Surr1 = new Property(25, String.class, "surr1", false, "SURR1");
        public final static Property Surr2 = new Property(26, String.class, "surr2", false, "SURR2");
        public final static Property Category = new Property(27, String.class, "category", false, "CATEGORY");
        public final static Property Weight = new Property(28, int.class, "weight", false, "WEIGHT");
        public final static Property Deleted = new Property(29, int.class, "deleted", false, "DELETED");
        public final static Property WapLink = new Property(30, String.class, "wapLink", false, "WAP_LINK");
        public final static Property WebLink = new Property(31, String.class, "webLink", false, "WEB_LINK");
    }

    private final PicsTagsConvert tagsConverter = new PicsTagsConvert();

    public SogouPicPojoDao(DaoConfig config) {
        super(config);
    }
    
    public SogouPicPojoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SOGOU_PIC_POJO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: logId
                "\"ID\" INTEGER NOT NULL ," + // 1: id
                "\"DID\" INTEGER NOT NULL ," + // 2: did
                "\"THUMB_URL\" TEXT," + // 3: thumbUrl
                "\"THUMB_WIDTH\" INTEGER NOT NULL ," + // 4: thumb_width
                "\"THUMB_HEIGHT\" INTEGER NOT NULL ," + // 5: thumb_height
                "\"STHUMB_URL\" TEXT," + // 6: sthumbUrl
                "\"STHUMB_WIDTH\" INTEGER NOT NULL ," + // 7: sthumb_width
                "\"STHUMB_HEIGHT\" INTEGER NOT NULL ," + // 8: sthumb_height
                "\"BTHUMB_URL\" TEXT," + // 9: bthumbUrl
                "\"BTHUMB_WIDTH\" INTEGER NOT NULL ," + // 10: bthumb_width
                "\"BTHUMB_HEIGHT\" INTEGER NOT NULL ," + // 11: bthumb_height
                "\"PIC_URL\" TEXT," + // 12: pic_url
                "\"WIDTH\" INTEGER NOT NULL ," + // 13: width
                "\"HEIGHT\" INTEGER NOT NULL ," + // 14: height
                "\"SIZE\" INTEGER NOT NULL ," + // 15: size
                "\"ORI_PIC_URL\" TEXT," + // 16: ori_pic_url
                "\"EXT_URL\" TEXT," + // 17: ext_url
                "\"PAGE_TITLE\" TEXT," + // 18: page_title
                "\"PAGE_URL\" TEXT," + // 19: page_url
                "\"TITLE\" TEXT," + // 20: title
                "\"TAGS\" TEXT," + // 21: tags
                "\"GROUP_MARK\" TEXT," + // 22: group_mark
                "\"GROUP_INDEX\" INTEGER NOT NULL ," + // 23: group_index
                "\"PUBLISH_TIME\" TEXT," + // 24: publish_time
                "\"SURR1\" TEXT," + // 25: surr1
                "\"SURR2\" TEXT," + // 26: surr2
                "\"CATEGORY\" TEXT," + // 27: category
                "\"WEIGHT\" INTEGER NOT NULL ," + // 28: weight
                "\"DELETED\" INTEGER NOT NULL ," + // 29: deleted
                "\"WAP_LINK\" TEXT," + // 30: wapLink
                "\"WEB_LINK\" TEXT);"); // 31: webLink
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SOGOU_PIC_POJO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SogouPicPojo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getLogId());
        stmt.bindLong(2, entity.getId());
        stmt.bindLong(3, entity.getDid());
 
        String thumbUrl = entity.getThumbUrl();
        if (thumbUrl != null) {
            stmt.bindString(4, thumbUrl);
        }
        stmt.bindLong(5, entity.getThumb_width());
        stmt.bindLong(6, entity.getThumb_height());
 
        String sthumbUrl = entity.getSthumbUrl();
        if (sthumbUrl != null) {
            stmt.bindString(7, sthumbUrl);
        }
        stmt.bindLong(8, entity.getSthumb_width());
        stmt.bindLong(9, entity.getSthumb_height());
 
        String bthumbUrl = entity.getBthumbUrl();
        if (bthumbUrl != null) {
            stmt.bindString(10, bthumbUrl);
        }
        stmt.bindLong(11, entity.getBthumb_width());
        stmt.bindLong(12, entity.getBthumb_height());
 
        String pic_url = entity.getPic_url();
        if (pic_url != null) {
            stmt.bindString(13, pic_url);
        }
        stmt.bindLong(14, entity.getWidth());
        stmt.bindLong(15, entity.getHeight());
        stmt.bindLong(16, entity.getSize());
 
        String ori_pic_url = entity.getOri_pic_url();
        if (ori_pic_url != null) {
            stmt.bindString(17, ori_pic_url);
        }
 
        String ext_url = entity.getExt_url();
        if (ext_url != null) {
            stmt.bindString(18, ext_url);
        }
 
        String page_title = entity.getPage_title();
        if (page_title != null) {
            stmt.bindString(19, page_title);
        }
 
        String page_url = entity.getPage_url();
        if (page_url != null) {
            stmt.bindString(20, page_url);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(21, title);
        }
 
        String[] tags = entity.getTags();
        if (tags != null) {
            stmt.bindString(22, tagsConverter.convertToDatabaseValue(tags));
        }
 
        String group_mark = entity.getGroup_mark();
        if (group_mark != null) {
            stmt.bindString(23, group_mark);
        }
        stmt.bindLong(24, entity.getGroup_index());
 
        String publish_time = entity.getPublish_time();
        if (publish_time != null) {
            stmt.bindString(25, publish_time);
        }
 
        String surr1 = entity.getSurr1();
        if (surr1 != null) {
            stmt.bindString(26, surr1);
        }
 
        String surr2 = entity.getSurr2();
        if (surr2 != null) {
            stmt.bindString(27, surr2);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(28, category);
        }
        stmt.bindLong(29, entity.getWeight());
        stmt.bindLong(30, entity.getDeleted());
 
        String wapLink = entity.getWapLink();
        if (wapLink != null) {
            stmt.bindString(31, wapLink);
        }
 
        String webLink = entity.getWebLink();
        if (webLink != null) {
            stmt.bindString(32, webLink);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SogouPicPojo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getLogId());
        stmt.bindLong(2, entity.getId());
        stmt.bindLong(3, entity.getDid());
 
        String thumbUrl = entity.getThumbUrl();
        if (thumbUrl != null) {
            stmt.bindString(4, thumbUrl);
        }
        stmt.bindLong(5, entity.getThumb_width());
        stmt.bindLong(6, entity.getThumb_height());
 
        String sthumbUrl = entity.getSthumbUrl();
        if (sthumbUrl != null) {
            stmt.bindString(7, sthumbUrl);
        }
        stmt.bindLong(8, entity.getSthumb_width());
        stmt.bindLong(9, entity.getSthumb_height());
 
        String bthumbUrl = entity.getBthumbUrl();
        if (bthumbUrl != null) {
            stmt.bindString(10, bthumbUrl);
        }
        stmt.bindLong(11, entity.getBthumb_width());
        stmt.bindLong(12, entity.getBthumb_height());
 
        String pic_url = entity.getPic_url();
        if (pic_url != null) {
            stmt.bindString(13, pic_url);
        }
        stmt.bindLong(14, entity.getWidth());
        stmt.bindLong(15, entity.getHeight());
        stmt.bindLong(16, entity.getSize());
 
        String ori_pic_url = entity.getOri_pic_url();
        if (ori_pic_url != null) {
            stmt.bindString(17, ori_pic_url);
        }
 
        String ext_url = entity.getExt_url();
        if (ext_url != null) {
            stmt.bindString(18, ext_url);
        }
 
        String page_title = entity.getPage_title();
        if (page_title != null) {
            stmt.bindString(19, page_title);
        }
 
        String page_url = entity.getPage_url();
        if (page_url != null) {
            stmt.bindString(20, page_url);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(21, title);
        }
 
        String[] tags = entity.getTags();
        if (tags != null) {
            stmt.bindString(22, tagsConverter.convertToDatabaseValue(tags));
        }
 
        String group_mark = entity.getGroup_mark();
        if (group_mark != null) {
            stmt.bindString(23, group_mark);
        }
        stmt.bindLong(24, entity.getGroup_index());
 
        String publish_time = entity.getPublish_time();
        if (publish_time != null) {
            stmt.bindString(25, publish_time);
        }
 
        String surr1 = entity.getSurr1();
        if (surr1 != null) {
            stmt.bindString(26, surr1);
        }
 
        String surr2 = entity.getSurr2();
        if (surr2 != null) {
            stmt.bindString(27, surr2);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(28, category);
        }
        stmt.bindLong(29, entity.getWeight());
        stmt.bindLong(30, entity.getDeleted());
 
        String wapLink = entity.getWapLink();
        if (wapLink != null) {
            stmt.bindString(31, wapLink);
        }
 
        String webLink = entity.getWebLink();
        if (webLink != null) {
            stmt.bindString(32, webLink);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public SogouPicPojo readEntity(Cursor cursor, int offset) {
        SogouPicPojo entity = new SogouPicPojo( //
            cursor.getLong(offset + 0), // logId
            cursor.getLong(offset + 1), // id
            cursor.getInt(offset + 2), // did
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // thumbUrl
            cursor.getInt(offset + 4), // thumb_width
            cursor.getInt(offset + 5), // thumb_height
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sthumbUrl
            cursor.getInt(offset + 7), // sthumb_width
            cursor.getInt(offset + 8), // sthumb_height
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // bthumbUrl
            cursor.getInt(offset + 10), // bthumb_width
            cursor.getInt(offset + 11), // bthumb_height
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // pic_url
            cursor.getInt(offset + 13), // width
            cursor.getInt(offset + 14), // height
            cursor.getInt(offset + 15), // size
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // ori_pic_url
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // ext_url
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // page_title
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // page_url
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // title
            cursor.isNull(offset + 21) ? null : tagsConverter.convertToEntityProperty(cursor.getString(offset + 21)), // tags
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // group_mark
            cursor.getInt(offset + 23), // group_index
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // publish_time
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // surr1
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // surr2
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // category
            cursor.getInt(offset + 28), // weight
            cursor.getInt(offset + 29), // deleted
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // wapLink
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31) // webLink
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SogouPicPojo entity, int offset) {
        entity.setLogId(cursor.getLong(offset + 0));
        entity.setId(cursor.getLong(offset + 1));
        entity.setDid(cursor.getInt(offset + 2));
        entity.setThumbUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setThumb_width(cursor.getInt(offset + 4));
        entity.setThumb_height(cursor.getInt(offset + 5));
        entity.setSthumbUrl(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSthumb_width(cursor.getInt(offset + 7));
        entity.setSthumb_height(cursor.getInt(offset + 8));
        entity.setBthumbUrl(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setBthumb_width(cursor.getInt(offset + 10));
        entity.setBthumb_height(cursor.getInt(offset + 11));
        entity.setPic_url(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setWidth(cursor.getInt(offset + 13));
        entity.setHeight(cursor.getInt(offset + 14));
        entity.setSize(cursor.getInt(offset + 15));
        entity.setOri_pic_url(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setExt_url(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setPage_title(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setPage_url(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setTitle(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setTags(cursor.isNull(offset + 21) ? null : tagsConverter.convertToEntityProperty(cursor.getString(offset + 21)));
        entity.setGroup_mark(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setGroup_index(cursor.getInt(offset + 23));
        entity.setPublish_time(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setSurr1(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setSurr2(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setCategory(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setWeight(cursor.getInt(offset + 28));
        entity.setDeleted(cursor.getInt(offset + 29));
        entity.setWapLink(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setWebLink(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SogouPicPojo entity, long rowId) {
        entity.setLogId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SogouPicPojo entity) {
        if(entity != null) {
            return entity.getLogId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SogouPicPojo entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
