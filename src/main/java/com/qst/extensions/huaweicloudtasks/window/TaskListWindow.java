package com.qst.extensions.huaweicloudtasks.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBLabel;
import com.qst.extensions.huaweicloudtasks.data.DataCenter;
import com.qst.extensions.huaweicloudtasks.data.DataUtil;
import com.qst.extensions.huaweicloudtasks.data.model.TaskData;
import com.qst.extensions.huaweicloudtasks.data.model.TaskInfo;
import com.qst.extensions.huaweicloudtasks.data.model.TaskListModel;

import javax.swing.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class TaskListWindow {

    private JComboBox projectList;
    private JPanel taskPanel;
    private JButton refreshProjectBtn;
    private JButton refreshTaskBtn;
//    private JTable tasklistTable;
    private JList taskList;

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
                    taskList.setModel(new TaskListModel(DataCenter.taskDataList));
                    taskList.setCellRenderer(new TaskCellRender());
//                    tasklistTable.setModel(DataCenter.taskTableModel);
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
//                tasklistTable.setModel(DataCenter.taskTableModel);
                System.out.printf("taskDataList: %s\n", DataCenter.taskDataList.size());
                taskList.setModel( new TaskListModel(DataCenter.taskDataList));

                taskList.setCellRenderer(new TaskCellRender());

            }
        });
        taskList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event){
                if(taskList.getSelectedIndex()!=-1){
                    if(event.getClickCount()==1) {
                        //单击
                    }
                    if(event.getClickCount() == 2)  {
                        //双击
                        try {
                            String projectName = (String) projectList.getSelectedItem();
                            String projectId = DataCenter.projectDataList.stream().filter(projectData -> projectData.getProject_name().equals(projectName)).findFirst().get().getProject_id();
                            TaskData task = (TaskData) taskList.getSelectedValue();
                            String url = "https://devcloud.huaweicloud.com/projectman/scrum/"+projectId+"/task/detail/"+ task.getId();
//                            Runtime.getRuntime().exec("cmd.exe /c start " + url);

                            Desktop desktop = Desktop.getDesktop();
                            desktop.browse(new URI(url));
//                            showTaskInfo((TaskData) taskList.getSelectedValue());
                        }catch (Exception e) {
                            System.out.printf("error"+ e.getMessage());
                        }
                    }
                }

            }
        });

        init();
    }
    public void showTaskInfo(TaskData value) throws Exception {
        System.out.printf("showInfo::"+value.getName());

        JBPopupFactory instance = JBPopupFactory.getInstance();
        JPanel panel = new JPanel();
        panel.setSize(40,20);
        JTextPane jTextPane = new JTextPane();
        HTMLEditorKit editor = new HTMLEditorKit();
        HTMLDocument text_html =(HTMLDocument) editor.createDefaultDocument();
        jTextPane.setEditorKit(editor);
        jTextPane.setContentType("text/html");
        jTextPane.setDocument(text_html);

        panel.add(jTextPane);
        instance.createComponentPopupBuilder(panel,new JBLabel())
                .setTitle( value.getTracker().getName()+" #"+value.getId())
                .setMovable(true)
                .setResizable(true)
                .setNormalWindowLevel(false)
                .setMinSize(new Dimension(600,300))
                .createPopup()
                .showInFocusCenter();


        String projectName = (String) projectList.getSelectedItem();
        String projectId = DataCenter.projectDataList.stream().filter(projectData -> projectData.getProject_name().equals(projectName)).findFirst().get().getProject_id();

        TaskInfo task = DataUtil.getTaskInfo(projectId,value);
//        jTextPane.setDocument(HTMLDocument.);
        System.out.printf(task.getDescription());
        editor.insertHTML(text_html,0,task.getDescription(),0,0, HTML.Tag.P);

    }
    public JPanel getContentPanel() {
        return taskPanel;
    }
    public void init() {
        DataUtil.getProjects();

        projectList.setModel(new DefaultComboBoxModel(DataCenter.projectDataList.stream().map(projectData -> projectData.getProject_name()).toArray()));
    }

//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//    }
}
