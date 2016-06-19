package com.ggl.filebrowser.view;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
 
import javax.swing.JButton;
import javax.swing.JPanel;
 
import com.ggl.filebrowser.model.FileNode;
import com.ggl.security.AESUtils;
 
public class DesktopButtonPanel {
     
    private FileNode fileNode;
     
    private JPanel panel;
     
    public DesktopButtonPanel() {
        createPartControl();
    }
     
    private void createPartControl() {
        panel = new JPanel();
         
        JButton openButton = new JButton("Encryption");
        openButton.addActionListener(new OpenListener());
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
 
    public JPanel getPanel() {
        return panel;
    }
 
    public class OpenListener implements ActionListener {
 
        @Override
        public void actionPerformed(ActionEvent event) {
            //openFile(fileNode);
            String sourceFilePath = fileNode.getFile().getAbsolutePath();
            String destFilePath = fileNode.getFile().getAbsolutePath()+"encrypted";
            try {
				AESUtils.encryptFile(key, sourceFilePath, destFilePath);
				System.out.println("sourceFilePath: "+sourceFilePath);
				System.out.println("destFilePath: "+destFilePath);
				System.out.println("success!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
        }
         
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
        }
         
    }
 
}