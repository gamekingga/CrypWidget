package com.ggl.filebrowser.runnable;

import javax.swing.tree.DefaultMutableTreeNode;

import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.model.FileBrowserModel2;
import com.ggl.filebrowser.model.FileNode;
 
public class AddNodes2 implements Runnable {
     
    private DefaultMutableTreeNode node;
     
    private FileBrowserModel2 model;
 
    public AddNodes2(FileBrowserModel2 model, DefaultMutableTreeNode node) {
        this.model = model;
        this.node = node;
    }
 
    @Override
    public void run() {
        FileNode fileNode = (FileNode) node.getUserObject();
        if (fileNode.isGenerateGrandchildren()) {
            model.addGrandchildNodesWoFile(node);
            fileNode.setGenerateGrandchildren(false);
        }
    }
     
}