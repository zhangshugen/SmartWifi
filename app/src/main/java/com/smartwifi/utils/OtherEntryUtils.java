package com.smartwifi.utils;

import android.content.Context;
import android.util.Log;

import com.smartwifi.part.user.viewmodel.LoginViewModel;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by wificityios on 2018/7/23.
 */

public class OtherEntryUtils {

    private static LoginViewModel mViewModels;

    public static void showShare(Context context, String title, String text, String url, String imageUrl) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        //oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        //分享本地图片
        if(imageUrl!=null) {
            oks.setImageUrl(imageUrl);
        }
        // 启动分享GUI
        oks.show(context);

    }
    private static PlatformActionListener mActionListener=new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            PlatformDb db = platform.getDb();
            String nickname = db.getUserName();//获取名字
            String icon = db.getUserIcon();//获取头像
            String openid = db.getUserId();//获取用户id
            String type = null;

            if(platform.getName().equals("Wechat")){
                type="wx";
            }else if(platform.getName().equals("QQ")){
                type="qq";
            }
            mViewModels.getThirdLoginData(type,openid,nickname);


          /*  Log.i("ssss",platform.getDb().getUserName());//获取名字
            Log.i("ssss",platform.getDb().getUserIcon());//获取头像
            Log.i("ssss",platform.getDb().getUserId());//获取用户id

            Log.i("ssss",i+"");
            Log.i("ssss",platform.getDb().getToken());
            Log.i("ssss",platform.getDb().exportData());
            Log.i("ssss",platform.getDb().get("unionid"));
            Log.i("ssss",platform.getDb().get("city"));*/
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Log.i("ssss",i+"");
            Log.i("ssss","onError");
            Log.i("ssss",throwable+"");
            throwable.printStackTrace();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Log.i("ssss","onCancel");
        }
    };


    public static void loginWithQQ(LoginViewModel mViewModel) {
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
        mViewModels=mViewModel;
        plat.setPlatformActionListener(mActionListener);

        plat.showUser(null);
    }

    public static void loginWithWeChat(LoginViewModel mViewModel){
        Platform wechat= ShareSDK.getPlatform(Wechat.NAME);
        mViewModels=mViewModel;
        wechat.setPlatformActionListener(mActionListener);

        wechat.showUser(null);
    }


}
