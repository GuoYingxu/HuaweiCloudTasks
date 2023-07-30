package com.qst.extensions.huaweicloudtasks.data.model;

import org.joni.Regex;

public class TaskInfo extends TaskData{
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String prefix = "https://devcloud.huaweicloud.com/api/projectman";
        this.description =description.replaceAll("(<img src=\")(.*?\")","$1"+prefix +"$2");
    }

    private String description;

    public static void main(String[] args) {
         String prefix = "https://devcloud.huaweicloud.com/api/projectman";
         String str = "abc<img src=\"/v1/anc\">dddd";
        System.out.println( str.replaceAll("(<img src=\")(.*?\")","$1"+prefix +"$2"));
    }
}
