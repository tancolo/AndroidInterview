package com.shrimpcolo.www.mytodo.tasks;

import android.support.annotation.NonNull;

import com.shrimpcolo.www.mytodo.BasePresenter;
import com.shrimpcolo.www.mytodo.BaseView;
import com.shrimpcolo.www.mytodo.data.Task;

import java.util.List;

/**
 * Created by Johnny Tam on 2017/3/11.
 */

public interface TasksContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showTasks(List<Task> tasks);

        void showAddTask();

        void showTaskDetailsUi(String taskId);

        void showLoadingTasksError();

        void showNoTasks();
    }

    interface Presenter extends BasePresenter{

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        void openTaskDetails(@NonNull Task requestedTask);

    }
}
