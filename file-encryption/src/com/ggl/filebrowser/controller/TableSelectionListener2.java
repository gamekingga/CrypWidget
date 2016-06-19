
package com.ggl.filebrowser.controller;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
 
import com.ggl.filebrowser.model.FileNode;
import com.ggl.filebrowser.view.FileBrowserFrame;
 
public class TableSelectionListener2 implements ListSelectionListener {
     
    private int rowCount;
     
    private FileBrowserFrame frame;
     
    private JTable table;
 
    public TableSelectionListener2(FileBrowserFrame frame, JTable table) {
        this.frame = frame;
        this.table = table;
    }
 
    public int getRowCount() {
        return rowCount;
    }
 
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
 
    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
            ListSelectionModel lsm = 
                    (ListSelectionModel) event.getSource();
            int row = lsm.getMinSelectionIndex();
 
            if ((row >= 0) && (row < rowCount)) {
                row = table.convertRowIndexToModel(row);
                FileNode fileNode = (FileNode) table.getModel()
                        .getValueAt(row, 10);
                frame.updateFileDetail(fileNode);
                frame.setDesktopButtonFileNode2(fileNode);
            }
        }
    }
 
}