package com.smartwifi.utils;

public class SPController {
    public static void saveWelcomeInData(boolean f) {
        SharedPreferencesUtil.getInstance().setBoolean("welecome", f);
    }

    public static boolean getWelcomeInData() {
      return   SharedPreferencesUtil.getInstance().getBoolean("welecome", true);
    }
}
