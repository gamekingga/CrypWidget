package com.ggl.filebrowser.view;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
 
import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.model.FileBrowserModel2;
import com.ggl.filebrowser.model.FileNode;
 
public class FileBrowserFrame {
     
    private DesktopButtonPanel desktopButtonPanel;
    
    private DesktopButtonPanel2 desktopButtonPanel2;
     
    private FileBrowserModel model;
    private FileBrowserModel2 model2;
     
    private FileDetailPanel fileDetailPanel;
     
    private JFrame frame;
     
    private JPanel mainPanel;
     
    private TableScrollPane tableScrollPane;
     
    private TreeScrollPane treeScrollPane;
 
    private JPanel CenterPanel;
    
    private TableScrollPane2 tableScrollPane2;
     
    private TreeScrollPane2 treeScrollPane2;
    
    private DropTargetListener panelDropOutListener;
    
    public FileBrowserFrame(FileBrowserModel model,FileBrowserModel2 model2) {
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
        
        
        CenterPanel = new JPanel();
        CenterPanel.setLayout(new BorderLayout());
         
        tableScrollPane = new TableScrollPane(this, model);
        CenterPanel.add(tableScrollPane.getPanel(), 
                BorderLayout.NORTH);
        
        tableScrollPane2 = new TableScrollPane2(this, model2);
        CenterPanel.add(tableScrollPane2.getPanel(), 
                BorderLayout.SOUTH);
        
        
        //......................
       DropTarget target = new DropTarget();
       target.setActive(true);
       try {
		target.addDropTargetListener(this.panelDropOutListener);
	} catch (TooManyListenersException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       this.tableScrollPane2.getPanel().setDropTarget(target);
       
         
        JPanel cPanel = new JPanel();
        cPanel.setLayout(new BorderLayout());
 /*        
        fileDetailPanel = new FileDetailPanel();
        cPanel.add(fileDetailPanel.getPanel(), BorderLayout.NORTH);
     */    
        desktopButtonPanel = new DesktopButtonPanel();
        desktopButtonPanel .setFrame(this);
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
    
    public void setNewModel1(){
    	model=new FileBrowserModel();    	
              
    	
    	
    	CenterPanel.remove(tableScrollPane.getPanel());
    	tableScrollPane = new TableScrollPane(this, model);
        CenterPanel.add(tableScrollPane.getPanel(), 
                BorderLayout.NORTH);
        CenterPanel.updateUI();
        
        mainPanel.remove(treeScrollPane.getScrollPane());
    	treeScrollPane = new TreeScrollPane(this, model);  
    	mainPanel.add(treeScrollPane.getScrollPane(), BorderLayout.WEST);
    	mainPanel.updateUI();
        
       
    	
    }
    
    public DropTargetListener panelDropOutListener2=new DropTargetListener(){
    	
    	public void drop(DropTargetDropEvent dtde){
               Transferable tf=dtde.getTransferable();
               
               if(tf.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
            	   int action =dtde.getDropAction();
            	   dtde.acceptDrop(action);
            	   List<?> List;
				try {
					List = (List<?>)tf.getTransferData(DataFlavor.javaFileListFlavor);
				
				for(Object o: List){
            		   System.out.println("file"+o);
            	   }
            	      
				
				} catch (UnsupportedFlavorException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	            	   
               }
    	}

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragExit(DropTargetEvent dte) {
			// TODO Auto-generated method stub
			
		}
    	
    	
    };
    
   

    
    
    public void setNewModel2(){
    	model2=new FileBrowserModel2();
    	
    	mainPanel.remove(treeScrollPane2.getScrollPane());
    	treeScrollPane2 = new TreeScrollPane2(this, model2);
        mainPanel.add(treeScrollPane2.getScrollPane(), BorderLayout.EAST);
        
        CenterPanel.remove(tableScrollPane2.getPanel());
    	tableScrollPane2 = new TableScrollPane2(this, model2);
        CenterPanel.add(tableScrollPane2.getPanel(), 
                BorderLayout.SOUTH);
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