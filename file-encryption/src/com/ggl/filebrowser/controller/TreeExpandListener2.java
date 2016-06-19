package com.ggl.filebrowser.controller;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
 
import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.model.FileBrowserModel2;
import com.ggl.filebrowser.runnable.AddNodes;
import com.ggl.filebrowser.runnable.AddNodes2;
 
public class TreeExpandListener2 implements TreeWillExpandListener {
     
    private FileBrowserModel2 model;
     
    public TreeExpandListener2(FileBrowserModel2 model) {
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
         
        AddNodes2 addNodes = new AddNodes2(model, node);
        new Thread(addNodes).start();
    }
     
}