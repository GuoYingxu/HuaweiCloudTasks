package com.qst.extensions.huaweicloudtasks.data;

import com.qst.extensions.huaweicloudtasks.data.model.TaskData;
import com.qst.extensions.huaweicloudtasks.data.model.TaskStatus;

import java.math.BigInteger;
import java.util.List;

public class HwTaskResponse {

    private List<TaskData> issues;

    public List<TaskData> getIssues() {
        return issues;
    }

    public void setIssues(List<TaskData> issues) {
        this.issues = issues;
    }


}
