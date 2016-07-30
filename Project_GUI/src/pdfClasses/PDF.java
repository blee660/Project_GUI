package pdfClasses;

import java.io.File;

public class PDF {

	private String fileLocation;
	private File file = null;
	
	
	
	public PDF(String fileLocation){
		File tempFile = new File(fileLocation);
		if(tempFile.exists()){
			file = tempFile;
			this.fileLocation = file.getAbsolutePath();
		}
	}
	
	public PDF(File file){
		if(file.exists()){
			this.file = file;
			this.fileLocation = file.getAbsolutePath();
		}
	}
	
	public String getFileLocation(){
		return this.fileLocation;
	}
	
	public File getFile(){
		return this.file;
	}
}
