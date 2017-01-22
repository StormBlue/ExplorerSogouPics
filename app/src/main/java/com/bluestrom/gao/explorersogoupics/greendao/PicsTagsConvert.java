package com.bluestrom.gao.explorersogoupics.greendao;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by Gao-Krund on 2017/1/22.
 */

public class PicsTagsConvert implements PropertyConverter<String[], String> {
    @Override
    public String[] convertToEntityProperty(String databaseValue) {
        return databaseValue.split(";");
    }

    @Override
    public String convertToDatabaseValue(String[] entityProperty) {
        String result = "";
        for (int i = 0; i < entityProperty.length; i++) {
            result += entityProperty[i];
        }
        return result;
    }
}
