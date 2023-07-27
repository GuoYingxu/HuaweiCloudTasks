package com.qst.extensions.huaweicloudtasks.data;

import com.huaweicloud.sdk.projectman.v4.model.ListProjectsV4ResponseBodyProjects;
import com.qst.extensions.huaweicloudtasks.data.model.ProjectData;
import com.qst.extensions.huaweicloudtasks.data.model.TaskData;

import java.util.ArrayList;
import java.util.List;

public class DataCenter {
    public static List<ListProjectsV4ResponseBodyProjects> projectDataList = new ArrayList<>();
    public static List<TaskData> taskDataList = new ArrayList<>();




    public static void clear() {
        projectDataList.clear();
        taskDataList.clear();
    }

    public static void setProjectDataList(List<ListProjectsV4ResponseBodyProjects> list) {
        projectDataList.clear();
        projectDataList.addAll(list);
    }

    public static void setTaskDataList(List<TaskData> list) {
        taskDataList.clear();
        taskDataList.addAll(list);
    }
}
