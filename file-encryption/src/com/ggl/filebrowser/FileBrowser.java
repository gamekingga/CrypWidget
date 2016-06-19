package com.ggl.filebrowser;

import javax.swing.SwingUtilities;

import com.ggl.filebrowser.model.FileBrowserModel;
import com.ggl.filebrowser.view.FileBrowserFrame;
 
public class FileBrowser implements Runnable {
 
    @Override
    public void run() {
        new FileBrowserFrame(new FileBrowserModel(),new FileBrowserModel());
    }
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FileBrowser());
    }
 
}