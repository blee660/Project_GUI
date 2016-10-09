package pdfClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.MyListItem;

/**
 * 
 * Class representing a PDF document item
 * Contains methods to get and set document information
 * 
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 * */
public class PDF {

	private String fileLocation;
	private String fileName;
	private File file = null;
	private MyListItem listItem;
	private ArrayList<String> keywords = new ArrayList<String>();
	private File HTMLfile = null;
	private File BiblioXML = null;
	public ArrayList<String> searchWords = new ArrayList<String>();
	
	// metadata storer to keep extracted metadata information
	private MetadataStorer mds = new MetadataStorer();

	// constructor using path of file
	public PDF(String fileLocation){
		File tempFile = new File(fileLocation);
		if(tempFile.exists()){
			file = tempFile;
			this.fileLocation = file.getAbsolutePath();
			addFileName();
		}
	}
	
	// constructor using file
	public PDF(File file){
		if(file.exists()){
			this.file = file;
			this.fileLocation = file.getAbsolutePath();
			addFileName();
		}
	}
	
	// populate the metadata information of the document from the metadata storer
	public void populateMeta(File f){
		ObjectMapper mapper = new ObjectMapper();
		try {
			mds = mapper.readValue(f, MetadataStorer.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// update title label of document
		updateListItem();
		updateSearch();
	}
	
	/**
	 * Get the HTML version of PDF
	 *
	 * @return HTMLfile
	 */
	public File getHTMLFile(){
		return this.HTMLfile;
	}
	
	/**
	 * Set the HTML version of PDF
	 *
	 * @param HTMLfile
	 */
	public void setHTMLFile(File html){
		this.HTMLfile = html;
	}
	
	/**
	 * Get the Bibliography file of PDF
	 *
	 * @return BiblioXML
	 */
	public File getBiblioFile(){
		return this.BiblioXML;
	}
	
	/**
	 * Set the Bibliography file of PDF
	 *
	 * @param xml to set as BiblioXML
	 */
	public void setBiblioFile(File xml){
		this.BiblioXML = xml;
	}
	
	/**
	 * Get the file location of PDF
	 *
	 * @return fileLocation
	 */
	public String getFileLocation(){
		return this.fileLocation;
	}
	
	/**
	 * Get the PDF file
	 *
	 * @return file
	 */
	public File getFile(){
		return this.file;
	}
	
	/**
	 * Get the metadata information of PDF
	 *
	 * @return mds
	 */
	public MetadataStorer getMetadata(){
		return this.mds;
	}
	
	/**
	 * Set the list item
	 *
	 * @param mli to set as list item
	 */
	public void setListItem(MyListItem mli){
		this.listItem = mli;
	}
	
	// method to update the title label of the document item in the list
	public void updateListItem(){
		if(this.getMetadata().getTitle()!= null && this.getMetadata().getTitle()!=""){
			this.listItem.updateLabel(this.getMetadata().getTitle());
		}
	}
	
	// update the search terms after metadata extracted
	private void updateSearch(){
		if(mds.getTitle() != null){
			searchWords.add(mds.getTitle());
		}
		
		if(mds.getAbstractx() != null){
			searchWords.add(mds.getAbstractx());
		}
		
		if(mds.getAuthors() != null){
			for(String x : mds.getAuthors()){
				searchWords.add(x);
			}
		}
		
		if(mds.getDegree() != null){
			searchWords.add(mds.getDegree());
		}
		
		if(mds.getDegreeDiscp() != null){
			searchWords.add(mds.getDegreeDiscp());
		}
		
		if(mds.getAltTitle() != null){
			searchWords.add(mds.getAltTitle());
		}
		
		if(mds.getSupervisors() != null){
			searchWords.add(mds.getSupervisors());
		}
	}
	
	// set filename as the string without prefix path 
	private void addFileName(){
		String[] split = file.getName().split("\\.");
		fileName = split[0];
	}
	
	/**
	 * Get the file name of PDF
	 *
	 * @return fileName string
	 */
	public String getFileName(){
		return this.fileName;
	}
	
	/**
	 * Add keyword into keyword list of PDF
	 *
	 * @param keyword
	 */
	public void addKeyWord(String keyword){
		this.keywords.add(keyword);
		this.searchWords.add(keyword);
	}
	
	/**
	 * Get the keyword list of PDF
	 *
	 * @return keywords list
	 */
	public ArrayList<String> getKeyWords(){
		return this.keywords;
	}
}
