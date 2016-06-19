package com.ggl.filebrowser.runnable;

import javax.swing.tree.DefaultMutableTreeNode;

import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.model.FileNode;
 
public class AddNodes implements Runnable {
     
    private DefaultMutableTreeNode node;
     
    private FileBrowserModel model;
 
    public AddNodes(FileBrowserModel model, DefaultMutableTreeNode node) {
        this.model = model;
        this.node = node;
    }
 
    @Override
    public void run() {
        FileNode fileNode = (FileNode) node.getUserObject();
        if (fileNode.isGenerateGrandchildren()) {
            //model.addGrandchildNodes(node);
            model.addGrandchildNodesWoFile(node);
            fileNode.setGenerateGrandchildren(false);
        }
    }
     
}