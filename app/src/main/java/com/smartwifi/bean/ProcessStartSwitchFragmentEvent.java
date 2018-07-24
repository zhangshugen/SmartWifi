package com.smartwifi.bean;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class ProcessStartSwitchFragmentEvent {
    public   int projectPosition;
    public   ProcessStartBean itemData;
    public int diskFolderId;
    public String name;

    public ProcessStartSwitchFragmentEvent(int id, String name) {
        this.diskFolderId = id;
        this.name = name;
    } public ProcessStartSwitchFragmentEvent(ProcessStartBean itemData, int projectPosition) {
       this.itemData =itemData;
       this.itemData.projectPosition=projectPosition;
    }
}
