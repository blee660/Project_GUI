package pdfClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.PDFListController;
import workerThreads.*;

public class Library {

	/*
	 * singleton implementation of a library
	 */
	protected static Library instance;
	
	protected Library(){
		startWorkers();
	}
	
	public static Library getInstance(){
		if (instance == null){
			instance = new Library();
		}
		
		return instance;
	}
	
	
	//======================================================================
	
	// list of workers
	private List<TemplateThread> workerList = new ArrayList<TemplateThread>();
	private List<TemplateThread> initialWorkers = new ArrayList<TemplateThread>();
	private void startWorkers(){
		MetadataWorker mw = new MetadataWorker();
		KeyphraseExtractionWorker ct = new KeyphraseExtractionWorker();
		HTMLConWorker hcw = new HTMLConWorker();
		BibliographyWorker bw = new BibliographyWorker();
		
		// add each worker into list of workers
		workerList.add(mw);
		initialWorkers.add(mw);
		
		workerList.add(ct);
		// classification relies on metadata extraction
		mw.registerReliant(ct);
		
		workerList.add(hcw);
		initialWorkers.add(hcw);
		
		workerList.add(bw);
		initialWorkers.add(bw);
	}
	
	
	
	
	//======================================================================
	private PDFListController plc;
	//register controller for list of PDFs in library
	public void registerDisplay(PDFListController plc){
		this.plc = plc;
	};
	
	//======================================================================
	//library implementation
	private ArrayList<PDF> PDFList = new ArrayList<PDF>();
	
	// adding PDF documents
	public void addPDF(PDF pdf){	
		// add document into PDF list
		PDFList.add(pdf);
		// add document into PDF list controller
		plc.addItem(pdf);
		
		// add document into all workers in initial worker list
		for(TemplateThread tt: initialWorkers){
			tt.addPDF(pdf);
		}
	}
	
	// adding multiple PDF documents
	public void addPDFs(List<PDF> pdfs){
		for(PDF pdf:pdfs){
			this.addPDF(pdf);
		}
	}
	
	// adding multiple files
	public void addPDFFiles(List<File> pdfFiles){
		// convert file into PDF class format
		for(File f : pdfFiles){
			PDF pdf = new PDF(f);
			this.addPDF(pdf);
		}
	}
	
	// removing PDF documents from library
	public void removePDF(PDF pdf){
		if(this.contains(pdf)){
			// remove from list of PDFs
			PDFList.remove(pdf);
			for(TemplateThread tt: workerList){
				// remove document from worker threads
				tt.removePDF(pdf);
			}
		}
	}
	
	// print all paths of documents in library
	public void printAllPaths(){
		for(PDF pdf : PDFList){
			System.out.println(pdf.getFileLocation());
		}
	}
	
	// return number of documents in the PDF list
	public int size(){
		return PDFList.size();
	}

	// check if document already exists in PDF list
	public boolean contains(PDF pdf){
		if(PDFList.contains(pdf)){
			return true;
		}
		
		return false;
	}
	
	// check if document already exists in PDF list using path
	public boolean contains(String path){
		File input = new File(path);
		
		for(PDF pdf : PDFList){
			try {
				if(pdf.getFileLocation().equals(input.getCanonicalPath())){
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}	
}
