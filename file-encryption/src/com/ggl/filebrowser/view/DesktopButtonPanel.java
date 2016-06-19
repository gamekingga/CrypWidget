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
import com.ggl.security.AESUtils;
 
public class DesktopButtonPanel {
     
    private FileNode fileNode;
    private ArrayList<FileNode> files; 
    private JPanel panel;
    private FileBrowserFrame frame;
    private String destination;
     
    public DesktopButtonPanel() {
        createPartControl();
    }
     
    private void createPartControl() {
        panel = new JPanel();
         
        JButton openButton = new JButton("Encryption");
        JButton openTree= new JButton("<<");
        openButton.addActionListener(new OpenListener());
        openTree.addActionListener(new StrechListener());
        panel.add(openTree);
        panel.add(openButton);
        
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
    //......................
    public void setFileNode(FileNode fileNode) {
        this.fileNode = fileNode;
    }
    public void setFileNodes(ArrayList<FileNode> fileNodes){
    	files=fileNodes;
    	
    }
    
    public void setDestination(String path){
    	this.destination=path;
    	
    }
 
    public JPanel getPanel() {
        return panel;
    }
    public void setFrame(FileBrowserFrame frame){
    	this.frame=frame;
    	
    }
    
    public void encryption(String enPath){
    	String sourceFilePath = enPath;
    	File file=new File(enPath);
    	if(destination!=null){
        String destFilePath = destination+"\\"+file.getName()+".AESenc";
        //String destFilePath = fileNode.get
        try {
			AESUtils.encryptFile(key, sourceFilePath, destFilePath);
			System.out.println("sourceFilePath: "+sourceFilePath);
			System.out.println("destFilePath: "+destFilePath);
			System.out.println("success!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        frame.setNewModel2();
    	
    	}
    	
    }
 
    public class OpenListener implements ActionListener {
 
        @Override
        public void actionPerformed(ActionEvent event) {
        	
        	 if (destination!=null&&JOptionPane.showConfirmDialog(null, "Are you sure to encypte the chosen files?", "Encrtption",
         	        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        	
             for(int i=0;i<files.size();i++){
	            String sourceFilePath = files.get(i).getFile().getAbsolutePath();
	            String destFilePath = destination+"\\"+files.get(i).getFile().getName()+".AESenc";
	            //String destFilePath = fileNode.get
	
	            try {
					AESUtils.encryptFile(key, sourceFilePath, destFilePath);
					System.out.println("sourceFilePath: "+sourceFilePath);
					System.out.println("destFilePath: "+destFilePath);
					System.out.println("success!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            frame.setNewModel2();
	            
        	 }
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
            
            frame.strechTreePanel();
            
            
            
        }
         
    }
 
}