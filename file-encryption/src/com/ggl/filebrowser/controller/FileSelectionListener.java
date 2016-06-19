package com.ggl.filebrowser.controller;

import java.io.File;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
 
import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.model.FileNode;
import com.ggl.filebrowser.runnable.AddNodes;
import com.ggl.filebrowser.view.FileBrowserFrame;
 
public class FileSelectionListener implements TreeSelectionListener {
     
    private FileBrowserFrame frame;
     
    private FileBrowserModel model;
 
    public FileSelectionListener(FileBrowserFrame frame, 
            FileBrowserModel model) {
        this.frame = frame;
        this.model = model;
    }
 
    @Override
    public void valueChanged(TreeSelectionEvent event) {
        DefaultMutableTreeNode node = 
                (DefaultMutableTreeNode) 
                event.getPath().getLastPathComponent();
        FileNode fileNode = (FileNode) node.getUserObject();
        
        AddNodes addNodes = new AddNodes(model, node);
        new Thread(addNodes).start();
         
        //model.addChildNodes(node);
        File file = fileNode.getFile();
        frame.updateFileDetail(fileNode);
        //frame.setDesktopButtonFileNode(fileNode);
        if (file.isDirectory()) {
        	frame.clearDefaultTableModel();
            frame.setDefaultTableModel(node);
            frame.setDesktopButtonDestination2(file.getAbsolutePath());
        } else {
        	
           
        }
    }
     
}