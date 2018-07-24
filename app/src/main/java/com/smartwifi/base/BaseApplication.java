package com.smartwifi.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.smartwifi.db.DaoMaster;
import com.smartwifi.db.DaoSession;
import com.smartwifi.utils.db.SecurityManageDaoHelper;
import com.smartwifi.widget.ActivityLifecycleHelper;
import com.smartwifi.widget.pagemanage.PageManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/15
 * @Describe
 */

public class BaseApplication extends Application {
    private static Context mContext;
    private static DaoSession mDaoSession;
    private static DaoMaster daoMaster;
    private ActivityLifecycleHelper mActivityLifecycleHelper;
    private final static boolean isDebugMode = true;
    private static Handler mHandler;
    private static int mMainTreadId;
    protected RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mMainTreadId = Process.myTid();
        mContext = getApplicationContext();
        QbSdk.initX5Environment(this, null);
        initSQL();
        initLogger();
        initPageManager();
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());
       refWatcher= setupLeakCanary();
    }
   private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication leakApplication = (BaseApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    private void initPageManager() {
        PageManager.initInApp(mContext);
    }

    private void initSQL() {
        getDaoSession();
    }

    private void initLogger() {
        if (isDebugMode) {
            Logger.init("TASK_MANAGE")
                    .methodCount(4)
                    .hideThreadInfo()
                    .logLevel(LogLevel.FULL);
        } else {
            Logger.init("TASK_MANAGE")
                    .methodCount(3)
                    .hideThreadInfo()
                    .logLevel(LogLevel.NONE);
        }
    }


    /**
     * 取得DaoMaster
     *
     * @return daoMaster
     */
    public static DaoMaster getDaoMaster() {

        SecurityManageDaoHelper helper = new SecurityManageDaoHelper(mContext, "security_manage.db");
        daoMaster = new DaoMaster(helper.getWritableDatabase());

        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @return daoSession
     */
    public static DaoSession getDaoSession() {
        if (mDaoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            mDaoSession = daoMaster.newSession();

        }
        return mDaoSession;
    }


    public static Context getContext() {
        return mContext;
    }

    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static long getMainTreadId() {
        return mMainTreadId;
    }
}
