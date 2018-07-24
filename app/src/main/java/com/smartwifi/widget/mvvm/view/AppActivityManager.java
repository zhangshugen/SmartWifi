
package com.smartwifi.widget.mvvm.view;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import java.util.Stack;

/**
 * activity管理
 */
public class AppActivityManager {
    private static Stack<FragmentActivity> activityStack;
    private static Stack<FragmentActivity> activityStackOk;
    private volatile static AppActivityManager instance;

    private AppActivityManager() {

    }

    /**
     * 单一实例
     */
    public static AppActivityManager getAppActivityManager() {
        if (instance == null) {
            synchronized (AppActivityManager.class) {
                if (instance == null) {
                    instance = new AppActivityManager();
                    activityStack = new Stack();
                }
            }

        }
        return instance;
    }


    public Stack<FragmentActivity> getActivityStack() {
        return activityStack;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(FragmentActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<FragmentActivity>();
        }
        activityStack.add(activity);
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivityOK(FragmentActivity activity) {
        if (activityStackOk == null) {
            activityStackOk = new Stack<FragmentActivity>();
        }
        if (!activityStackOk.contains(activity)) {
            activityStackOk.add(activity);
        }
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public FragmentActivity currentActivity() {
        try {
            FragmentActivity activity = activityStack.lastElement();
            return activity;
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public FragmentActivity preActivity() {
        int index = activityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        FragmentActivity activity = activityStack.get(index);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        FragmentActivity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(FragmentActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(FragmentActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (FragmentActivity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    public void returnToActivity(Class<?> cls) {
        while (activityStack.size() != 0)
            if (activityStack.peek().getClass() == cls) {
                break;
            } else {
                finishActivity(activityStack.peek());
            }
    }


    /**
     * 是否已经打开指定的activity
     *
     * @param cls
     * @return
     */
    public boolean isOpenActivity(Class<?> cls) {
        if (activityStack != null && activityStack.size()!=0) {

            for (FragmentActivity activity : activityStack) {
                if (cls == activity.getClass()) {
                    return true;
                }
            }

//            for (int i = 0, size = activityStack.size(); i < size; i++) {
//                Class<? extends Activity> aClass = activityStack.get(i).getClass();
//                if (cls == aClass) {
//                    return true;
//                }
//            }
        }
        return false;
    }

    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
        } catch (Exception e) {

        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }
}