import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.*;

public class Ziper {
	static public void zipIt(List<String> srcPathList, String destZipFile) throws Exception{
		ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(destZipFile));
		File folder = null;
		for(String srcPath : srcPathList){
			folder = new File(srcPath);
			if(folder.isDirectory())
				addFolderToZip("", srcPath, zip);
			else
				addFileToZip("", srcPath, zip);
			folder = null;
		}
		zip.flush();
		zip.close();
	}
	static private void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFile); // source file can be a file or a directory
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		}
		else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			if(path == "")
				zip.putNextEntry(new ZipEntry(folder.getName()));
			else
				zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
			in.close();
		}
	}
	static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);
		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			}
			else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}
	
	
	static public void unZipIt(String zipFile, String outputFolder) throws Exception{
		//create output directory if it not exists
		File folder = new File(outputFolder);
		if(!folder.exists()){
			folder.mkdir();
		}

		//get the zip file content
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		//get the zipped file list entry
		ZipEntry ze = zis.getNextEntry();

		while(ze!=null){
			String fileName = ze.getName();
			File newFile = new File(outputFolder + File.separator + fileName);

			// create all non exists folders, else you will hit FileNotFoundException for compressed folder
			new File(newFile.getParent()).mkdirs();

			FileOutputStream fos = new FileOutputStream(newFile);
			byte[] buf = new byte[1024];
			int len;
			while ((len = zis.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}

			fos.close();
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
	}
}