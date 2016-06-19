package com.ggl.filebrowser.controller;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
 
import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.runnable.AddNodes;
 
public class TreeExpandListener implements TreeWillExpandListener {
     
    private FileBrowserModel model;
     
    public TreeExpandListener(FileBrowserModel model) {
        this.model = model;
    }
 
    @Override
    public void treeWillCollapse(TreeExpansionEvent event)
            throws ExpandVetoException {
    }
 
    @Override
    public void treeWillExpand(TreeExpansionEvent event)
            throws ExpandVetoException {
        TreePath path = event.getPath();
        DefaultMutableTreeNode node = 
                 (DefaultMutableTreeNode) path.getLastPathComponent();
         
        AddNodes addNodes = new AddNodes(model, node);
        new Thread(addNodes).start();
    }
     
}