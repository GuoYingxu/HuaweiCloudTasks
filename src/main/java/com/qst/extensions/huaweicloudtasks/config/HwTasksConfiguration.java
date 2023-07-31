package com.qst.extensions.huaweicloudtasks.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.qst.extensions.huaweicloudtasks.data.DataUtil;
import com.qst.extensions.huaweicloudtasks.service.HwTaskConfigService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class HwTasksConfiguration implements Configurable {

    private final JComponent component;
    private final  JLabel hwAccessKeyLabel;
    private final  JLabel hwSecretKeyLabel;
    private final  JLabel hwProjectsLabel;
    private final  JLabel hwProjectAssignedUserLabel;
    private final JTextField hwAccessKey;
    private final JTextField hwSecretKey;
    private final JTextField hwProjects;
    private final JTextField hwProjectAssignedUser;

    private final HwTaskConfigService hwTaskConfigService = ApplicationManager.getApplication().getService(HwTaskConfigService.class);
    public static String HW_ACCESS_KEY = "hwAccessKey";
    public static String HW_SECRET_KEY = "hwSecretKey";
    public static String HW_PROJECT_IDS = "hwProjectIds";
    public static String HW_PROJECT_ASSIGNED_USER = "hwProjectAssignedUser";

    private final static String keyHint = "AK";
    private final static String secretHint = "SK";
    private final static String projectHint = "请输入华为云项目ID(用逗号分割)";
    private final static String assignedUserHint = "请输入任务处理人num_id(用逗号分割)";
    public HwTasksConfiguration() {
        this.component = new JPanel();
        this.component.setLayout(new GridLayout(15,1));

        this.hwAccessKeyLabel = new JLabel("华为云访问密钥AK:");
        this.hwSecretKeyLabel = new JLabel("华为云访问密钥SK:");
        this.hwProjectsLabel = new JLabel("华为云项目IDs:");
        this.hwProjectAssignedUserLabel = new JLabel("华为云项目任务处理人:");
        this.hwAccessKey = new JTextField();
        this.hwAccessKey.setForeground(Color.GRAY);
        this.hwAccessKey.setText(keyHint);
        this.hwAccessKey.addFocusListener(new TextFieldListener(this.hwAccessKey, keyHint));
        this.hwSecretKey = new JTextField();
        this.hwSecretKey.setForeground(Color.GRAY);
        this.hwSecretKey.setText(secretHint);
        this.hwSecretKey.addFocusListener(new TextFieldListener(this.hwSecretKey, secretHint));

        this.hwProjectAssignedUser = new JTextField();
        this.hwProjectAssignedUser.setForeground(Color.GRAY);
        this.hwProjectAssignedUser.setText(assignedUserHint);
        this.hwProjectAssignedUser.addFocusListener(new TextFieldListener(this.hwProjectAssignedUser, assignedUserHint));

        this.hwProjects = new JTextField();
        this.hwProjects.setForeground(Color.GRAY);
        this.hwProjects.setText(projectHint);
        this.hwProjects.addFocusListener(new TextFieldListener(this.hwProjects, projectHint));
        this.component.add(this.hwAccessKeyLabel);
        this.component.add(this.hwAccessKey);
        this.component.add(this.hwSecretKeyLabel);
        this.component.add(this.hwSecretKey);
        this.component.add(this.hwProjectsLabel);
        this.component.add(this.hwProjects);
        this.component.add(this.hwProjectAssignedUserLabel);
        this.component.add(this.hwProjectAssignedUser);



    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "HwTasksConfiguration";
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException{
        this.hwTaskConfigService.save(HW_ACCESS_KEY, this.hwAccessKey.getText().trim());
        this.hwTaskConfigService.save(HW_SECRET_KEY,this.hwSecretKey.getText().trim());
        this.hwTaskConfigService.save(HW_PROJECT_IDS, this.hwProjects.getText().trim());
        this.hwTaskConfigService.save(HW_PROJECT_ASSIGNED_USER,this.hwProjectAssignedUser.getText().trim());
    }

    @Override
    public JComponent createComponent() {
        if(hwTaskConfigService.get(HW_ACCESS_KEY, "") != "") {
            this.hwAccessKey.setText(hwTaskConfigService.get(HW_ACCESS_KEY, keyHint));
            this.hwAccessKey.setForeground(Color.white);
        }

        if(hwTaskConfigService.get(HW_SECRET_KEY,"")!= "") {
            this.hwSecretKey.setText(hwTaskConfigService.get(HW_SECRET_KEY,secretHint));
            this.hwSecretKey.setForeground(Color.white);
        }
        if(hwTaskConfigService.get(HW_PROJECT_IDS, "") != "") {
            this.hwProjects.setText(hwTaskConfigService.get(HW_PROJECT_IDS, projectHint));
            this.hwProjects.setForeground(Color.white);
        }
        if(hwTaskConfigService.get(HW_PROJECT_ASSIGNED_USER, "") != "") {
            this.hwProjectAssignedUser.setText(hwTaskConfigService.get(HW_PROJECT_ASSIGNED_USER, assignedUserHint));
            this.hwProjectAssignedUser.setForeground(Color.white);
        }
        return this.component;
    }

    static class TextFieldListener implements FocusListener {
        private final String defaultHint;
        private final JTextField textField;
        public TextFieldListener(JTextField textField, String keyHint) {
            this.defaultHint = keyHint;
            this.textField = textField;
        }
        @Override
        public  void focusGained(FocusEvent e) {
            if (textField.getText().equals(defaultHint)) {
                textField.setText("");
                textField.setForeground(Color.white);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().equals("")) {
                textField.setText(defaultHint);
                textField.setForeground(Color.GRAY);
            }else {
                textField.setForeground(Color.white);
            }
        }
    }
}
