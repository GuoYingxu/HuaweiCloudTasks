package com.qst.extensions.huaweicloudtasks.window;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import com.qst.extensions.huaweicloudtasks.data.model.TaskData;

import javax.swing.*;
import java.awt.*;
public class TaskCellRender extends JLabel  implements ListCellRenderer   {
//    private JLabel label;
    private TaskData data;
    private ImageIcon icon;

//    public TaskCellRender() {
//        super();
//        this.setLayout(new FlowLayout(FlowLayout.LEFT));
//        label = new JLabel();
//        label.setPreferredSize(new Dimension(300,28));
//        this.add(label);
//
//
//    }
    @Override
    public Component getListCellRendererComponent(JList  list, Object value,int index,boolean isSelected, boolean cellHasFocus) {
        data = (TaskData) value;
        ClassLoader classLoader = TaskCellRender.class.getClassLoader();
//        System.out.println("render:::"+index+"::"+ data.getTracker().getName());
        if(data!=null &&  data.getTracker() !=null && data.getTracker().getName().equals("Task")) {
           icon  = new FlatSVGIcon("img/task.svg",20,20,classLoader);

        }else {
            icon = new FlatSVGIcon("img/bug.svg",20,20,classLoader);
        }
        setText(data.getName());
        setIcon(icon);
        setPreferredSize(new Dimension(280,28));
//        if(isSelected)  {
//            setBackground(Color.lightGray);
//            setForeground(Color.white);
//        }else  {
//            setBackground(Color.GRAY);
//            setForeground(Color.WHITE);
//        }
//
//        label.setText(data.getName());
//        label.setIcon(icon);

        return this;
    }
}
