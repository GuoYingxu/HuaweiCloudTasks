package com.qst.extensions.huaweicloudtasks.data.model;

import java.math.BigInteger;

public class TaskData {
    private BigInteger id;
    private String name;
    private TaskStatus status; // 类型 1 新建 2 进行中 3 已解决
    private TaskTracker tracker; // 类型 2 Task 3 bug

    private IterationData iteration; // 迭代

    private AssignedUserData assigned_user; // 负责人

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskTracker getTracker() {
        return tracker;
    }

    public void setTracker(TaskTracker tracker) {
        this.tracker = tracker;
    }

    public IterationData getIteration() {
        return iteration;
    }

    public void setIteration(IterationData iteration) {
        this.iteration = iteration;
    }

    public AssignedUserData getAssigned_user() {
        return assigned_user;
    }

    public void setAssigned_user(AssignedUserData assigned_user) {
        this.assigned_user = assigned_user;
    }
}
