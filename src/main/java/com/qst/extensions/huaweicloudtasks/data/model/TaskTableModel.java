package com.qst.extensions.huaweicloudtasks.data.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TaskTableModel  extends AbstractTableModel {
    public String[] columnNames = {
            "任务ID",
            "任务描述", "类型", "状态"
    };
    public Object[][] data = {} ;
    public TaskTableModel(){
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return data[0][columnIndex].getClass();
    }

    public void  accessTaskData(List<TaskData> dataList) {
        data = new  Object[dataList.size()][4];
        for (int i = 0; i < dataList.size(); i++) {
            this.setValueAt(dataList.get(i).getId(), i,0);
            this.setValueAt(dataList.get(i).getName(), i,1);
            this.setValueAt(dataList.get(i).getStatus().getName(), i,2);
            this.setValueAt(dataList.get(i).getTracker().getName(), i,3);
        }
    }
}
