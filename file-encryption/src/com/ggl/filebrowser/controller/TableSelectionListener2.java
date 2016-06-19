
package com.ggl.filebrowser.controller;

import java.util.ArrayList;

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
         
             ArrayList<FileNode> Files=new ArrayList<FileNode>();
             if(!lsm.isSelectionEmpty()){
             	 int minIndex = lsm.getMinSelectionIndex();
                  int maxIndex = lsm.getMaxSelectionIndex();
                  FileNode fileNode = null;
                  for (int i = minIndex; i <= maxIndex; i++) {
                 	 if(i==minIndex){
                      System.out.println(" Selections: ");
                 	 }
                      if (lsm.isSelectedIndex(i)) {
                     	 row = table.convertRowIndexToModel(i);
                     	 fileNode = (FileNode) table.getModel().getValueAt(row, 3);
                     	 Files.add(fileNode);
                     	 System.out.println(i);
                        }
                        
                  }
                  frame.setDesktopButtonFileNode2(Files);
             
             }
        	
        	
        	
        	
        	
        }
    }
 
}