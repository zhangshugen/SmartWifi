package com.smartwifi.constant;

import android.os.Environment;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/19
 * @Describe
 */

public class Constant {
    public static String IMAGE_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/SecurityManager/img";
    public static String VIDEO_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/SecurityManager/video";
    public static String RECORD_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/SecurityManager/record";
    public static String FILE_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/SecurityManager/file";

    public static class ADAPTER_FOOT_KEY {

        public static final Integer LOAD_MORE_FOOT_KEY = 100000002;
        public static final Integer REFRESH_RECYCLER_FOOT_KEY = 100000001;
    }

    public static final int GET_FILE_SYSTEM = 1002;
    public static final String TYPE_TRUE = "T";
    public static final String TYPE_FALSE = "F";

    public static class FILE_FROM_TYPE {
        public static final int TYPE_NET = 1;
        public static final int TYPE_LOCAL = 2;
    }

    public static class COMMON_EDIT_PROFILE_TYPE {

        public final static String TYPE_C = "C"; // 下拉菜单
        public final static String TYPE_T = "T"; // 文本类型
        public final static String TYPE_D = "D"; // 日期类型
        public final static String TYPE_N = "N"; // 数字类型
        public final static String TYPE_B = "B"; // 布尔类型
        public final static String TYPE_F = "F"; //文件类型
    }

    public class COMMON_EDIT_PROFILE_CHOOSE_LEVEL {
        public static final String TYPE_CHUJI ="处室";
        public static final String TYPE_REN ="人";
    }
}
