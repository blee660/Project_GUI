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
	private List<TemplateThread> workerList = new ArrayList<TemplateThread>();
	private List<TemplateThread> initialWorkers = new ArrayList<TemplateThread>();
	private void startWorkers(){
		MetadataWorker mw = new MetadataWorker();
		ClassificationThread ct = new ClassificationThread();
		HTMLConWorker hcw = new HTMLConWorker();
		
		workerList.add(mw);
		initialWorkers.add(mw);
		
		workerList.add(ct);
		mw.registerReliant(ct);
		
		workerList.add(hcw);
		initialWorkers.add(hcw);
	}
	
	
	
	
	//======================================================================
	private PDFListController plc;
	public void registerDisplay(PDFListController plc){
		this.plc = plc;
	};
	
	//======================================================================
	//library implementation
	private ArrayList<PDF> PDFList = new ArrayList<PDF>();
	
	public void addPDF(PDF pdf){		
		PDFList.add(pdf);
		plc.addItem(pdf);
		
		for(TemplateThread tt: initialWorkers){
			tt.addPDF(pdf);
		}
	}
	
	public void addPDFs(List<PDF> pdfs){
		for(PDF pdf:pdfs){
			this.addPDF(pdf);
		}
	}
	
	public void addPDFFiles(List<File> pdfFiles){
		for(File f : pdfFiles){
			PDF pdf = new PDF(f);
			this.addPDF(pdf);
		}
	}
	
	public void removePDF(PDF pdf){
		if(this.contains(pdf)){
			PDFList.remove(pdf);
			for(TemplateThread tt: workerList){
				tt.removePDF(pdf);
			}
		}
	}
	
	public void printAllPaths(){
		for(PDF pdf : PDFList){
			System.out.println(pdf.getFileLocation());
		}
	}
	
	public int size(){
		return PDFList.size();
	}

	
	public boolean contains(PDF pdf){
		if(PDFList.contains(pdf)){
			return true;
		}
		
		return false;
	}
	
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
