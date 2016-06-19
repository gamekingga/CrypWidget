
package com.ggl.filebrowser.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.text.NumberFormat;
import java.util.Enumeration;
 
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;
 
//import com.ggl.filebrowser.controller.TableSelectionListener;
import com.ggl.filebrowser.controller.TableSelectionListener2;
import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.model.FileBrowserModel2;
import com.ggl.filebrowser.model.FileNode;
import com.ggl.filebrowser.model.FileTableModel;
import com.ggl.filebrowser.model.FileTableModel2;
 
public class TableScrollPane2 {
     
    private FileBrowserFrame frame;
     
    private FileBrowserModel2 model;
    private DefaultMutableTreeNode currentnode;
     
    private FileTableModel2 ftModel;
     
    private JLabel countLabel;
     
    private JPanel panel;
     
    private JScrollPane scrollPane;
     
    private JTable table;
    private String path;
     
    private TableSelectionListener2 tsListener;
 
    public TableScrollPane2(FileBrowserFrame frame, 
            FileBrowserModel2 model) {
        this.frame = frame;
        this.model = model;
        createPartControl();
    }
     
    private void createPartControl() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
         
        JPanel countPanel = new JPanel();
         
        countLabel = new JLabel(" ");
         
        countPanel.add(countLabel);
        panel.add(countPanel, BorderLayout.NORTH);
         
        ftModel = new FileTableModel2();
         
        table = new JTable(ftModel);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setColumnSelectionAllowed(false);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
         
        tsListener = new TableSelectionListener2(frame, table);
        tsListener.setRowCount(ftModel.getRowCount());
         
        ListSelectionModel lsm = table.getSelectionModel();
        lsm.addListSelectionListener(tsListener);
         
        int width = ftModel.setColumnWidths(table);
        table.setPreferredScrollableViewportSize(
                new Dimension(width, table.getRowHeight() * 12));
         
        scrollPane = new JScrollPane(table);
         
        panel.add(scrollPane, BorderLayout.CENTER);
    }
     
    public String buildLabelString(int count) {
        NumberFormat nf = NumberFormat.getInstance();
        StringBuilder builder = new StringBuilder();
        builder.append("Path:  "+path+"                ");
        builder.append(nf.format(count));
        builder.append(" files / directories");
        return builder.toString();
    }
 
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
 
    public JPanel getPanel() {
        return panel;
    }
     
    public void clearDefaultTableModel() {
        ftModel.removeRows();
        countLabel.setText(" ");
        ftModel.fireTableDataChanged();
    }
     
    public void setDefaultTableModel(DefaultMutableTreeNode node) {
        ftModel.removeRows();
        currentnode=node; 
        FileNode fileNode = (FileNode) node.getUserObject();
        path=fileNode.getFile().getAbsolutePath();
        File file = fileNode.getFile();
        if (file.isDirectory()) {
        	
        	/*
            Enumeration<?> enumeration = node.children();
            while (enumeration.hasMoreElements()) {
                DefaultMutableTreeNode childNode =
                        (DefaultMutableTreeNode)
                        enumeration.nextElement();
                FileNode childFileNode = 
                        (FileNode) childNode.getUserObject();
                ftModel.addRow(model, childFileNode);
            }*/
        	  for (File child : file.listFiles()) {
              	String name=child.getName();
              	if(child.isDirectory()){ 
              		ftModel.addRow(model,  new FileNode(child));
                  
              	}else if(name.indexOf(".AESenc")!=-1){
              		ftModel.addRow(model,  new FileNode(child));
              		
              	}
              }
            
            
        }
         
        tsListener.setRowCount(ftModel.getRowCount());
        countLabel.setText(buildLabelString(ftModel.getRowCount()));
        ftModel.fireTableDataChanged();
        scrollPane.getVerticalScrollBar().setValue(0);
    }
    
    public void refresh(){
    	ftModel.removeRows();
        FileNode fileNode = (FileNode) currentnode.getUserObject();
        path=fileNode.getFile().getAbsolutePath();
        
        File file = fileNode.getFile();
        if (file.isDirectory()) {
        	/*
            Enumeration<?> enumeration = node.children();
            while (enumeration.hasMoreElements()) {
                DefaultMutableTreeNode childNode =
                        (DefaultMutableTreeNode)
                        enumeration.nextElement();
                FileNode childFileNode = 
                        (FileNode) childNode.getUserObject();
                ftModel.addRow(model, childFileNode);
                
            }*/
            for (File child : file.listFiles()) {
            	String name=child.getName();
            	if(child.isDirectory()){   
            		ftModel.addRow(model, new FileNode(child));
               
            		
            	}else if(name.indexOf(".AESenc")!=-1){
            		ftModel.addRow(model, new FileNode(child));
            		
            	}
            }
            
        }
        tsListener.setRowCount(ftModel.getRowCount());
        countLabel.setText(buildLabelString(ftModel.getRowCount()));
        ftModel.fireTableDataChanged();
        scrollPane.getVerticalScrollBar().setValue(0);
    }
    
     
}