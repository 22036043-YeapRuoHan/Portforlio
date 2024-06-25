/*
 * I declare that this code was written by me. 
 * I do not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Yeap Ruo Han 
 * Student ID: 22036043 
 * Class: C208-3B-E65M-A 
 * Date/Time created: Wednesday 30-11-2022 10:50
 */

/**
 * @author 22036043
 *
 */
public class Patient {
	private String nric4;
	private String name;
	private String ward;
	private int bed;
	private String dateWarded;
	private String dateDischarged;
	private int visitorCount;
	
	

	public Patient(String nric4, String name, String ward, int bed,String dateWarded,String dateDischarged,int visitorCount) {
		this.nric4 = nric4;
		this.name = name;
		this.ward = ward;
		this.bed = bed;
		this.dateWarded = dateWarded;
		this.dateDischarged = dateDischarged;
		this.visitorCount = visitorCount;
	}
	public Patient(String nric4, String name, String ward, int bed,String dateWarded) {
		this.nric4 = nric4;
		this.name = name;
		this.ward = ward;
		this.bed = bed;
		this.dateWarded = dateWarded;
		dateDischarged = "";
		visitorCount = 0;
		
	}
	public String getNric4() {
		return this.nric4;
	}
	public String getName() {
		return this.name;
	}
	public String getWard() {
		return this.ward;
	}
	public int getBed() {
		return this.bed;
	}

	public String getDateWarded() {
		return this.dateWarded;
	}
	public String getDateDischarged() {
		return this.dateDischarged;
	}

	public void setDateDischarged(String dateDischarged ) {
		this.dateDischarged = dateDischarged;
	}
	
	public int getVisitorCount() {
		return this.visitorCount;
	}
	
	public void setVisitorCount(int visitorCount ) {
		this.visitorCount = visitorCount;
	}
	
	public void display() {
		 
		 String output=String.format(" %-20s : %-15s\n ","Patient Name",name);
		 output+=String.format("%-20s : %-15s\n ","Ward",ward);
		 output+=String.format("%-20s : %-15s\n ","Bed",bed);
		 output+=String.format("%-20s : %-15s\n ","Date warded",dateWarded);
		 output+=String.format("%-20s : %-15s\n ","Date discharged",dateDischarged);
		 output+=String.format("%-20s : %-15s\n ","No of visitor(s)",visitorCount);
		System.out.println(output);
	
		
	}
	
	

	

}
