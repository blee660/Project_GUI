package pdfClasses;

import java.io.File;

public class PDF {

	private String fileLocation;
	private File file = null;
	
	private MetadataStorer mds = new MetadataStorer();
	
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
	
	public void populateMeta(){
		//GET JSON FILE FROM PROCESSED ITEMS AND STORE IN METADATASTORER USING JACKSON
	}
	
	public String getFileLocation(){
		return this.fileLocation;
	}
	
	public File getFile(){
		return this.file;
	}
	
	public MetadataStorer getMetadata(){
		return this.mds;
	}
}
