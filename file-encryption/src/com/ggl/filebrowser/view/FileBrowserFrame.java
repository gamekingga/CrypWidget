package com.ggl.filebrowser.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
 
import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.model.FileNode;
 
public class FileBrowserFrame {
     
    private DesktopButtonPanel desktopButtonPanel;
    
    private DesktopButtonPanel2 desktopButtonPanel2;
     
    private FileBrowserModel model;
    private FileBrowserModel model2;
     
    private FileDetailPanel fileDetailPanel;
     
    private JFrame frame;
     
    private JPanel mainPanel;
     
    private TableScrollPane tableScrollPane;
     
    private TreeScrollPane treeScrollPane;
 
    
    
    private TableScrollPane2 tableScrollPane2;
     
    private TreeScrollPane2 treeScrollPane2;
    
    public FileBrowserFrame(FileBrowserModel model,FileBrowserModel model2) {
        this.model = model;
        this.model2 = model2;
        setLookAndFeel();
        createPartControl();
    }
     
    private void createPartControl() {  
        frame = new JFrame();
        frame.setTitle("File Browser");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });
         
        createMainPanel();
 
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setResizable(false);
    }
 
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
         
        treeScrollPane = new TreeScrollPane(this, model);
        mainPanel.add(treeScrollPane.getScrollPane(), BorderLayout.WEST);
        //.........
        treeScrollPane2 = new TreeScrollPane2(this, model2);
        mainPanel.add(treeScrollPane2.getScrollPane(), BorderLayout.EAST);
        
        
        JPanel CenterPanel = new JPanel();
        CenterPanel.setLayout(new BorderLayout());
         
        tableScrollPane = new TableScrollPane(this, model);
        CenterPanel.add(tableScrollPane.getPanel(), 
                BorderLayout.NORTH);
        
        tableScrollPane2 = new TableScrollPane2(this, model2);
        CenterPanel.add(tableScrollPane2.getPanel(), 
                BorderLayout.SOUTH);
         
        JPanel cPanel = new JPanel();
        cPanel.setLayout(new BorderLayout());
 /*        
        fileDetailPanel = new FileDetailPanel();
        cPanel.add(fileDetailPanel.getPanel(), BorderLayout.NORTH);
     */    
        desktopButtonPanel = new DesktopButtonPanel();
        cPanel.add(desktopButtonPanel.getPanel(), 
                BorderLayout.WEST);
        
        
        //..............
        desktopButtonPanel2 = new DesktopButtonPanel2();
        cPanel.add(desktopButtonPanel2.getPanel(), 
                BorderLayout.EAST);
        
        
        CenterPanel.add(cPanel, BorderLayout.CENTER);
         
        mainPanel.add(CenterPanel, BorderLayout.CENTER);
    }
     
    public void exitProcedure() {
        frame.dispose();
        System.exit(0);
    }
     
    public void updateFileDetail(FileNode fileNode) {
       // fileDetailPanel.setFileNode(fileNode, model);
    }
     
    public void setDefaultTableModel(DefaultMutableTreeNode node) {
        tableScrollPane.setDefaultTableModel(node);
    }
     
    public void clearDefaultTableModel() {
        tableScrollPane.clearDefaultTableModel();
    }
    
    //...........................
    public void setDefaultTableModel2(DefaultMutableTreeNode node) {
        tableScrollPane2.setDefaultTableModel(node);
    }
     
    public void clearDefaultTableModel2() {
        tableScrollPane2.clearDefaultTableModel();
    }
    
    
    
     
    public void setDesktopButtonFileNode(FileNode fileNode) {
        desktopButtonPanel.setFileNode(fileNode);
    }
    //...................
    public void setDesktopButtonFileNode2(FileNode fileNode) {
        desktopButtonPanel2.setFileNode(fileNode);
    }
     
    private void setLookAndFeel() {
        try {
            // Significantly improves the look of the output in
            // terms of the file names returned by FileSystemView!
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch(Exception weTried) {
            weTried.printStackTrace();
        }
    }
 
}