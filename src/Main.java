import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.dnd.*;

public class Main {
	private boolean sourceType;
	private static final boolean SINGLE = false;
	private static final boolean MULTIPLE = true;
	
	public List<String> sourceFilePathList;// need to new it
	public String destFilePath;
	public String singleSourceFileName;
	private boolean endeFlag;// true: en, false: de
	
	private String password;
	
	/*need access UI*/
	public Text pathView;
	public Text passwordBox;
	
	/*DropTargetListener*/
	public DropTargetListener dndListener;

	/*Selection Adaptors*/
	public SelectionAdapter addFileSA;
	public SelectionAdapter addFolderSA;
	public SelectionAdapter startSA;
	
	public void run(){
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("CryptWidget");
		createSelectionAdapters(shell);
		createContents(shell);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public void createSelectionAdapters(final Shell shell){
		/*addFileSA*/
		this.addFileSA = new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
		        // New a FileDialog with a multi-file choosing function
				FileDialog dlg = new FileDialog(shell, SWT.MULTI);
				
		        // Change the title bar text
		        dlg.setText("Browse for Files");
		        
		        // Calling open() will open and run the dialog.
		        // It will return the selected directory, or null if user cancels
		        if (dlg.open() != null) { //There is a choose in FileDialog
		        	// Build choosedFiles
		        	String[] files = dlg.getFileNames();
		        	List<File> choosedFiles = new ArrayList<File>();
		        	for(String path : files){
		        		path = dlg.getFilterPath() + File.separator + path;
		        		choosedFiles.add(new File(path));
		        	}
		        	// Other two functions
		        	checkSourceType(choosedFiles);
					updateFilePathList(choosedFiles);
					shell.pack();
		        }
			}
		};
		
		/*addFolderSA*/
		this.addFolderSA = new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
		        // New a DirectoryDialog
				DirectoryDialog dlg = new DirectoryDialog(shell);
				
		        // Change the title bar text
		        dlg.setText("Browse for Folder");
		        
		        // Calling open() will open and run the dialog.
		        // It will return the selected directory, or null if user cancels
		        String folder = dlg.open();// AbsolutePath of folder
		        if (folder != null) { //There is a choose in DirectoryDialog
		        	// Build choosedFiles
		        	File choosedFolder = new File(folder);
		        	List<File> choosedFiles = new ArrayList<File>();
					choosedFiles.add(choosedFolder);
					// Other two functions
					checkSourceType(choosedFiles);
					updateFilePathList(choosedFiles);
					shell.pack();
		        }
			}
		};
		
		/*startSA*/
		this.startSA = new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				try {
					password = passwordBox.getText();
					byte[] encodedKey = AESUtils.getEncodedSecretKey(password);
					
					// let user to choose target directory
					DirectoryDialog dlg = new DirectoryDialog(shell);
					dlg.setText("Choose Where To Save");
					String parent = dlg.open();
					
					if(parent != null){
						String tempZipPath = parent + File.separator + "temp.zip";
						
						if(!endeFlag){ // decryption
							SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
							destFilePath = parent + File.separator + "decryption_" + sdfDate.format(new Date());
								// destFile Path will be the output folder of unzip section
							AESUtils.decryptFile(encodedKey, sourceFilePathList.get(0), tempZipPath);
							Ziper.unZipIt(tempZipPath, destFilePath);
							new File(tempZipPath).delete();
						}
						else{ // encryption
							if(sourceType == MULTIPLE)
								destFilePath = parent + File.separator + "Encrypted Archive.crypt";// can change
							else
								destFilePath = parent + File.separator + singleSourceFileName + ".crypt";// can change
							Ziper.zipIt(sourceFilePathList, tempZipPath);
							AESUtils.encryptFile(encodedKey, tempZipPath, destFilePath);
							new File(tempZipPath).delete();
						}
						passwordBox.setText("");// Clear passwordBox
					}				
				} catch (Exception e1) { e1.printStackTrace(); }
			}
		};
	}
	
	public void setupDropTargetListener(DropTarget target, final Shell shell){
		final FileTransfer fileTransfer = FileTransfer.getInstance();
	    target.setTransfer(new Transfer[] {fileTransfer}); 
	    target.addDropListener(new DropTargetListener() {
		    public void dragEnter(DropTargetEvent event) {}
		    public void dragOver(DropTargetEvent event) {}
		    public void dragOperationChanged(DropTargetEvent event) {}
		    public void dragLeave(DropTargetEvent event) {}
		    public void dropAccept(DropTargetEvent event) {}
		    
		    public void drop(DropTargetEvent event) {
		    	//if (fileTransfer.isSupportedType(event.currentDataType)){	// Make it comment because of supporting multiple folders DnD
		    		String[] files = (String[])event.data;
		    		List<File> choosedFiles = new ArrayList<File>();
		        	for(String path : files)
		        		choosedFiles.add(new File(path));
		        	checkSourceType(choosedFiles);
					updateFilePathList(choosedFiles);
					shell.pack();
		    	//}
		    }
	    });
	}
	
	public void createContents(final Shell shell){
		shell.setLayout(new GridLayout(8, true));
		
		new Label(shell, SWT.NONE).setText("Relative Path:");
		this.pathView = new Text(shell, SWT.MULTI | SWT.BORDER);
		GridData data3 = new GridData(GridData.FILL_HORIZONTAL);
	    data3.horizontalSpan = 3;
	    this.pathView.setLayoutData(data3);
	    this.pathView.setEditable(false);
	    
	    new Label(shell, SWT.NONE).setText("Password:");
	    this.passwordBox = new Text(shell, SWT.PASSWORD | SWT.BORDER);
	    GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
	    data2.horizontalSpan = 2;
	    this.pathView.setLayoutData(data2);
	    
	    Button addFileButton = new Button(shell, SWT.PUSH);
	    addFileButton.setText("Add File");
	    addFileButton.addSelectionListener(this.addFileSA);
	    Button addFolderButton = new Button(shell, SWT.PUSH);
	    addFolderButton.setText("Add Folder");
	    addFolderButton.addSelectionListener(this.addFolderSA);
	    Button startButton = new Button(shell, SWT.PUSH);
	    startButton.setText("START!");
	    startButton.addSelectionListener(this.startSA);
	    
	    // Allow data to be copied or moved to the drop target
	    int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
	    DropTarget target = new DropTarget(shell, operations);
	    setupDropTargetListener(target, shell);
	}
	
	public void checkSourceType(List<File> sourceFileList){
		if(sourceFileList.size() == 1)
			this.sourceType = SINGLE;
		else
			this.sourceType = MULTIPLE;
	}
	
	/* Includes updating source path and endeFlag */
	public void updateFilePathList(List<File> sourceFileList){
		this.sourceFilePathList = new ArrayList<String>();
		
		String sourceFilePath;
		sourceFilePath = sourceFileList.get(0).getAbsolutePath();
		
		// At first, test if we are going to do decryption.
		// only when there is just 1 source file and it is a .crypt file, we'll do decrypt.
		if(sourceFileList.size() == 1 && sourceFilePath.substring(sourceFilePath.length()-6 , sourceFilePath.length()).equals(".crypt")){
			this.sourceFilePathList.add(sourceFilePath);
			this.pathView.setText(sourceFileList.get(0).getName());
			this.endeFlag = false;
			return;
		}
		
		// else, encryption
		// Consider source type for setting destFilePath.
		String fileNameList = "";
		for(File sourceFile : sourceFileList){
			sourceFilePath = sourceFile.getAbsolutePath();
			this.sourceFilePathList.add(sourceFilePath);
			fileNameList = fileNameList + sourceFile.getName() + '\n';
		}
		fileNameList = fileNameList.substring(0, fileNameList.length()-1);
		
		if(this.sourceType == SINGLE)
			this.singleSourceFileName = new File(sourceFilePath).getName();
		this.pathView.setText(fileNameList);
		this.endeFlag = true;
	}
	
	public static void main(String[] args) {
		new Main().run();
	}
}
