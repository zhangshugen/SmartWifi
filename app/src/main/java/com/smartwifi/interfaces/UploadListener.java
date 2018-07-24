package com.smartwifi.interfaces;

import com.smartwifi.bean.ProfileFileBean;

import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/12
 * @Describe
 */

public interface   UploadListener {
        void onUploadSuccess(List<ProfileFileBean> file);

      void onUploadFailed() ;

}
