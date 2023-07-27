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
    private JList taskList;
    private JButton refreshBtn;

    public  TaskListWindow(Project project, ToolWindow toolWindow){
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataUtil.getProjectsBySdk();

                projectList.setModel(new DefaultComboBoxModel(DataCenter.projectDataList.stream().map(projectData -> projectData.getProjectName()).toArray()));
            }
        });
        projectList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equals("comboBoxChanged")) {
                    String projectName = (String) projectList.getSelectedItem();
                    System.out.printf("projectName: %s\n", projectName);
                    String projectId = DataCenter.projectDataList.stream().filter(projectData -> projectData.getProjectName().equals(projectName)).findFirst().get().getProjectId();
                    System.out.printf("projectId: %s\n", projectId);
                    DataUtil.getTasks(projectId);
                    System.out.printf("taskDataList: %s\n", DataCenter.taskDataList.size());
                    taskList.setModel(new DefaultComboBoxModel(DataCenter.taskDataList.stream().map(taskData -> taskData.getName()).toArray()));
                }
            }
        });
        init();
    }
    public JPanel getContentPanel() {
        return taskPanel;
    }
    public void init() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("item1");
        listModel.addElement("item2");
        listModel.addElement("item3");
        taskList.setModel(listModel);

        projectList.setModel(new DefaultComboBoxModel(new String[]{"project1", "project2", "project3"}));


    }
}
