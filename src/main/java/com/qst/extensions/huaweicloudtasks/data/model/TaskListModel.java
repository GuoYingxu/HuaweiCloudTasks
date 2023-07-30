package com.qst.extensions.huaweicloudtasks.data.model;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class TaskListModel extends AbstractListModel {

   List<TaskData> model ;

   public TaskListModel(List<TaskData> tasks) {
       this.model = tasks;
   }
   @Override
   public int getSize() {
       return model.size();
   }
   @Override
   public TaskData getElementAt(int pos)  {
       return this.model.get(pos);
   }
}
