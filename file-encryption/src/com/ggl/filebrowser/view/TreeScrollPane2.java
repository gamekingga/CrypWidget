
package com.ggl.filebrowser.view;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
 
//import com.ggl.filebrowser.controller.FileSelectionListener;
import com.ggl.filebrowser.controller.FileSelectionListener2;
import com.ggl.filebrowser.controller.TreeExpandListener;
import com.ggl.filebrowser.controller.TreeExpandListener2;
import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.model.FileBrowserModel2;
import com.ggl.filebrowser.view.renderer.FileTreeCellRenderer;
import com.ggl.filebrowser.view.renderer.FileTreeCellRenderer2;
 
public class TreeScrollPane2 {
     
    private FileBrowserFrame frame;
 
    private FileBrowserModel2 model;
     
    private JScrollPane scrollPane;
     
    private JTree tree;
 
    public TreeScrollPane2(FileBrowserFrame frame,
            FileBrowserModel2 model) {
        this.frame = frame;
        this.model = model;
        createPartControl();
    }
     
    private void createPartControl() {
        tree = new JTree(model.createTreeModel());
        tree.addTreeSelectionListener(
                new FileSelectionListener2(frame, model));
        tree.addTreeWillExpandListener(
                new TreeExpandListener2(model));
        tree.expandRow(1);
        tree.setRootVisible(false);
        tree.setCellRenderer(new FileTreeCellRenderer2(model));
        tree.setShowsRootHandles(true);
         
        scrollPane = new JScrollPane(tree);
         
        Dimension preferredSize = scrollPane.getPreferredSize();
        Dimension widePreferred = new Dimension(
            300, (int) preferredSize.getHeight());
        scrollPane.setPreferredSize( widePreferred );
    }
 
    public JTree getTree() {
        return tree;
    }
 
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
     
}