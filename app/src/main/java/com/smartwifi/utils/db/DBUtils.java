package com.smartwifi.utils.db;

import org.greenrobot.greendao.DbUtils;


/**
 * Created by apanda on 2018/7/17.
 */

public class DBUtils {

    private static DbUtils instance ;

    private DBUtils() {
    }

    public static DbUtils getInstance() {
        synchronized (DbUtils.class) {
            if (instance == null) {
                instance = new DbUtils();
            }
        }
        return instance;
    }



}




