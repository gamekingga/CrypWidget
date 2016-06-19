
package com.ggl.filebrowser.view;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
import com.ggl.filebrowser.model.FileNode;
import com.ggl.filebrowser.view.DesktopButtonPanel.StrechListener;
import com.ggl.security.AESUtils;
 
public class DesktopButtonPanel2 {
     
    private FileNode fileNode;
    private ArrayList<FileNode> files;  
    private JPanel panel;
    private FileBrowserFrame frame;
    private String destination;
     
    public DesktopButtonPanel2() {
        createPartControl();
    }
     
    private void createPartControl() {
        panel = new JPanel();
         
        JButton openButton = new JButton("Decryption");
        JButton opentree= new JButton(">>");
        JButton deleteButton= new JButton("DeleteAES");
        openButton.addActionListener(new OpenListener());
        opentree.addActionListener(new StrechListener());
        deleteButton.addActionListener(new DeleteListener());
        panel.add(deleteButton);
        panel.add(openButton);
        panel.add(opentree);
    }
     
    public void setFileNode(FileNode fileNode) {
        this.fileNode = fileNode;
    }
    public void setFileNodes(ArrayList<FileNode> fileNodes){
    	files=fileNodes;
    	
    }
 
    public JPanel getPanel() {
        return panel;
    }
    
    public void setFrame(FileBrowserFrame frame){
    	this.frame=frame;
    	
    }
    public void setDestination(String path){
    	this.destination=path;
    	
    }
    
  //...................
    static String key;
    
    static {
        try {
    //key = AESUtils.getSecretKey();
    key = "eVwv3SnHf5NfXP8nmM43jA==";
        	System.out.println(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    static void encryptFile() throws Exception {
        String sourceFilePath = "D:/demo.mp4";
        String destFilePath = "D:/demo_encrypted.mp4";
        AESUtils.encryptFile(key, sourceFilePath, destFilePath);
    }
    
    static void decryptFile() throws Exception {
        String sourceFilePath = "D:/demo_encrypted.mp4";
        String destFilePath = "D:/demo_decrypted.mp4";
        AESUtils.decryptFile(key, sourceFilePath, destFilePath);
    }
    */
    
 
    public class OpenListener implements ActionListener {
 
        @Override
        public void actionPerformed(ActionEvent event) {
        	if(destination!=null&&JOptionPane.showConfirmDialog(null, "Are you sure to decrypte AESfiles?", "Decyption",
        	        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
	            //openFile(fileNode);
        		for(int i=0;i<files.size();i++){
	            String sourceFilePath = files.get(i).getFile().getAbsolutePath();
	            String[] splited = files.get(i).getFile().getName().split(".AESenc");
	          
	          
	            System.out.println(splited[0]);
	            String destFilePath = destination+"\\"+splited[0];
	            try {
					AESUtils.decryptFile(key, sourceFilePath, destFilePath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            frame.setNewModel1();
        		}
            
        	}else{
        		
        		
        	}
        }
         /*
        private void openFile(FileNode fileNode) {
            if (fileNode.getFile().isFile()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.OPEN)) {
                        try {
                            desktop.open(fileNode.getFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }*/
         
    }
    
    
    
    

    public class StrechListener implements ActionListener {
    	 
        @Override
        public void actionPerformed(ActionEvent event) {
            
            frame.strechTreePanel2();
            
            
            
        }
         
    }
    
    
    public class DeleteListener implements ActionListener {
   	 
        @Override
        public void actionPerformed(ActionEvent event) {
        	
        	if(files!=null&&files.size()!=0){
        	
        	if (JOptionPane.showConfirmDialog(null, "Are you sure to delete AESfiles?", "WARNING",
        	        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        		
        		for(int i=0;i<files.size();i++){
    	            try {
						Files.delete(files.get(i).getFile().toPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	          
    	            
            		}
        		files.clear();
        		frame.setNewModel2();
        	    // yes option
        	} else {
        	    // no option
        	}
        	
        	}
            
            
        }
         
    }
 
}