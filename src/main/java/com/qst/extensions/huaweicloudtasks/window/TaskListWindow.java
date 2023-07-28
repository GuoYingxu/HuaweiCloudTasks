package com.qst.extensions.huaweicloudtasks.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.qst.extensions.huaweicloudtasks.data.DataCenter;
import com.qst.extensions.huaweicloudtasks.data.DataUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskListWindow {

    private JComboBox projectList;
    private JPanel taskPanel;
    private JButton refreshProjectBtn;
    private JButton refreshTaskBtn;
    private JTable tasklistTable;

    public  TaskListWindow(Project project, ToolWindow toolWindow){
        refreshProjectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataUtil.getProjects();
                projectList.setModel(new DefaultComboBoxModel(DataCenter.projectDataList.stream().map(projectData -> projectData.getProject_name()).toArray()));
            }
        });
        projectList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                System.out.println(command);
                if(command.equals("comboBoxChanged")) {
                    String projectName = (String) projectList.getSelectedItem();
                    System.out.printf("projectName: %s\n", projectName);
                    String projectId = DataCenter.projectDataList.stream().filter(projectData -> projectData.getProject_name().equals(projectName)).findFirst().get().getProject_id();
                    System.out.printf("projectId: %s\n", projectId);
                    DataUtil.getTasks(projectId);
                    System.out.printf("taskDataList: %s\n", DataCenter.taskDataList.size());
                    tasklistTable.setModel(DataCenter.taskTableModel);
                }
            }
        });

        // 刷新task
        refreshTaskBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectName = (String) projectList.getSelectedItem();
                String projectId = DataCenter.projectDataList.stream().filter(projectData -> projectData.getProject_name().equals(projectName)).findFirst().get().getProject_id();
                DataUtil.getTasks(projectId);
                tasklistTable.setModel(DataCenter.taskTableModel);

            }
        });


        init();
    }
    public JPanel getContentPanel() {
        return taskPanel;
    }
    public void init() {
        DataUtil.getProjects();

        projectList.setModel(new DefaultComboBoxModel(DataCenter.projectDataList.stream().map(projectData -> projectData.getProject_name()).toArray()));
    }
}
