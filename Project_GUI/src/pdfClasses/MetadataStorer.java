package pdfClasses;

/**
 * 
 * POJO representation of thesis metadata
 *
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 */
public class MetadataStorer {
	
	private String title;
	private String altTitle;
	private String[] authors = {};
	private String supervisors;
	private String degree;
	private String degreeDiscp;
	private String abstractx;
	private int pageLength;
	private String pageSize;
	
	/**
	 * Get the title information
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set the title field
	 *
	 * @param title string to assign to title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Get the alternative title
	 *
	 * @return altTitle
	 */
	public String getAltTitle() {
		return altTitle;
	}
	
	/**
	 * Set the alternative title 
	 *
	 * @param altTitle to assign as alternative title
	 */
	public void setAltTitle(String altTitle) {
		this.altTitle = altTitle;
	}
	
	/**
	 * Get the supervisors 
	 *
	 * @return supervisors
	 */
	public String getSupervisors() {
		return supervisors;
	}
	
	/**
	 * Set the supervisors 
	 *
	 * @param supervisors
	 */
	public void setSupervisors(String supervisors) {
		this.supervisors = supervisors;
	}
	
	/**
	 * Get the authors 
	 *
	 * @return author array
	 */
	public String[] getAuthors() {
		return authors;
	}
	
	/**
	 * Set the authors 
	 *
	 * @param author array
	 */
	public void setAuthors(String[] authors) {
		this.authors = authors;
	}
	
	/**
	 * Get the degree 
	 *
	 * @return degree value
	 */
	public String getDegree() {
		return degree;
	}
	
	/**
	 * Set the degree 
	 *
	 * @param degree string to set as degree
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	/**
	 * Get the degree discipline 
	 *
	 * @return degreeDiscp value
	 */
	public String getDegreeDiscp() {
		return degreeDiscp;
	}
	
	/**
	 * Set the degreeDiscp 
	 *
	 * @param degreeDiscp to set as degree discipline
	 */
	public void setDegreeDiscp(String degreeDiscp) {
		this.degreeDiscp = degreeDiscp;
	}
	
	/**
	 * Get the abstract content 
	 *
	 * @return abstractx 
	 */
	public String getAbstractx() {
		return abstractx;
	}
	
	/**
	 * Set the abstract content 
	 *
	 * @param abstractx to set as abstract content
	 */
	public void setAbstractx(String abstractx) {
		this.abstractx = abstractx;
	}
	
	/**
	 * Get the page length 
	 *
	 * @return pagelength value
	 */
	public int getPageLength() {
		return pageLength;
	}
	
	/**
	 * Set the page length 
	 *
	 * @param pageLength value to set as page length
	 */
	public void setPageLength(int pageLength) {
		this.pageLength = pageLength;
	}
	
	/**
	 * Get the page size 
	 *
	 * @return pageSize value
	 */
	public String getPageSize() {
		return pageSize;
	}
	
	/**
	 * Set the page size 
	 *
	 * @param pageSize value to set as page size
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}
