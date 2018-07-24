package com.smartwifi.widget.pagemanage;

import android.text.TextUtils;
import android.view.View;

import static com.smartwifi.widget.pagemanage.PageManager.NO_LAYOUT_ID;


public abstract class PageListener
{


    public abstract void setRetryEvent(View retryView);

    public void setLoadingEvent(View loadingView)
    {
    }

    public void setEmptyEvent(View emptyView)
    {
    }

    public int generateLoadingLayoutId()
    {
        return NO_LAYOUT_ID;
    }

    public int generateRetryLayoutId()
    {
        return NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId()
    {
        return NO_LAYOUT_ID;
    }

    public View generateLoadingLayout()
    {
        return null;
    }

    public View generateRetryLayout()
    {
        return null;
    }

    public View generateEmptyLayout()
    {
        return null;
    }
    public String generateLoadingMessage()
    {
        return null;
    }

    public String generateRetryMessage()
    {
        return null;
    }

    public String generateEmptyLayoutMessage()
    {
        return null;
    }

    public int isSetLoadingLayout()
    {
        if (generateLoadingLayoutId() != NO_LAYOUT_ID){
            return PageManager.TYPE_CUSTOM_LAYOUT_ID;
        }else if (generateLoadingLayout() != null){
            return PageManager.TYPE_CUSTOM_VIEW;
        }else if (!TextUtils.isEmpty(generateLoadingMessage())){
            return PageManager.TYPE_CUSTOM_MESSAGE;
        }else {
            return PageManager.TYPE_CUSTOM_NONE;
        }
    }

    public int isSetRetryLayout()
    {
        if (generateRetryLayoutId() != NO_LAYOUT_ID){
            return PageManager.TYPE_CUSTOM_LAYOUT_ID;
        }else if (generateRetryLayout() != null){
            return PageManager.TYPE_CUSTOM_VIEW;
        }else if (!TextUtils.isEmpty(generateRetryMessage())){
            return PageManager.TYPE_CUSTOM_MESSAGE;
        }else {
            return PageManager.TYPE_CUSTOM_NONE;
        }
    }

    public int  isSetEmptyLayout()
    {
        if (generateEmptyLayoutId() != NO_LAYOUT_ID){
            return PageManager.TYPE_CUSTOM_LAYOUT_ID;
        }else if (generateEmptyLayout() != null){
            return PageManager.TYPE_CUSTOM_VIEW;
        }else if (!TextUtils.isEmpty(generateEmptyLayoutMessage())){
            return PageManager.TYPE_CUSTOM_MESSAGE;
        }else {
            return PageManager.TYPE_CUSTOM_NONE;
        }
    }


}