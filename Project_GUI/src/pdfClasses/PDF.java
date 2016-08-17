package pdfClasses;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.MyListItem;

public class PDF {

	private String fileLocation;
	private File file = null;
	private MyListItem listItem;
	
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
	
	public void populateMeta(File f){
		ObjectMapper mapper = new ObjectMapper();
		try {
			mds = mapper.readValue(f, MetadataStorer.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void setListItem(MyListItem mli){
		this.listItem = mli;
	}
	
	public void updateListItem(){
		if(this.getMetadata().getTitle()!= null && this.getMetadata().getTitle()!=""){
			this.listItem.updateLabel(this.getMetadata().getTitle());
		}
	}
}
