package com.smartwifi.part.commonpage.contract;

import android.databinding.ViewDataBinding;

import com.smartwifi.bean.TaskListBean;
import com.smartwifi.widget.mvvm.model.BaseModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMView;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class TaskListContract {
    public abstract static class Model extends BaseModel {


    }

    public interface View extends BaseMVVMView {


        void notifyAdapter(TaskListBean bean, int itemPosition);
    }

    public abstract static class ViewModel<V extends BaseMVVMView, D extends ViewDataBinding, M extends BaseModel> extends BaseViewModel<V, D, M>  {
        public abstract void openItem(TaskListBean bean, int itemPosition);

        public abstract void requestApproval(TaskListBean bean, int itemPosition);

    }

}
