package com.smartwifi.manager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.webkit.MimeTypeMap;

import com.smartwifi.R;
import com.smartwifi.constant.Constant;
import com.smartwifi.interfaces.DownLoadListener;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.utils.UIUtils;

import java.io.File;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/12
 * @Describe
 */

public class DownloadManage {

    private   DownloadManager sDManager;

    private DownloadManage() {

    }

    private static class DownloadManagerSingleton {
        private static final DownloadManage INSTANCE = new DownloadManage();

    }

    public static DownloadManage getInstance() {
        return DownloadManage.DownloadManagerSingleton.INSTANCE;
    }

    public   void downloadFile(final FragmentActivity activity, String url, final String title, final DownLoadListener loadListener) {
        LoadingFragmentManager.getInstance().showLoadingDialog(activity, UIUtils.getString(R.string.open_file));
        if (sDManager == null)
            sDManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
// 设置下载路径和文件名
        request.setAllowedOverRoaming(true);//漫游网络是否可以下载
        request.setDescription(title);
        request.setDestinationInExternalPublicDir(Constant.FILE_ROOT_PATH, title);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);
// 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
// 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        try {
            final long refernece = sDManager.enqueue(request);

            // 注册广播接收器，当下载完成时自动安装
            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            activity.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(refernece);//筛选下载任务，传入任务ID，可变参数
                    DownloadManager dManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                    Cursor c = dManager.query(query);
                    if (c.moveToFirst()) {
                        LoadingFragmentManager.getInstance().dismissLoadingDialog();
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        switch (status) {
                            case DownloadManager.STATUS_PAUSED:
                                loadListener.onPaused();
                                break;
                            case DownloadManager.STATUS_PENDING:
                                loadListener.onPending();
                                break;
                            case DownloadManager.STATUS_RUNNING:
                                loadListener.onRunning();
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                File file = new File(Constant.FILE_ROOT_PATH + title);
                                loadListener.onSuccess(file);
                                break;
                            case DownloadManager.STATUS_FAILED:
                                ToastUtils.showShort("下载失败,请重试");
                                loadListener.onFailed();
                                break;
                        }

                    }

                    c.close();
                    long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if (refernece == myDwonloadID) {

                    }
                }
            }, filter);
        } catch (Exception _e) {
            _e.printStackTrace();
        } finally {
            LoadingFragmentManager.getInstance().dismissLoadingDialog();
            sDManager = null;
        }


    }

}
