package com.qst.extensions.huaweicloudtasks.data;

import com.qst.extensions.huaweicloudtasks.data.model.ProjectData;

import java.util.List;

public class HwProjectResponse {

    public List<ProjectData> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectData> projects) {
        this.projects = projects;
    }

    private List<ProjectData> projects;
    public HwProjectResponse() {
    }



}
