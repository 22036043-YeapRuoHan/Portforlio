/*
 * I declare that this code was written by me. 
 * I do not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Yeap Ruo Han 
 * Student ID: 22036043 
 * Class: C208-3B-E65M-A 
 * Date/Time created: Wednesday 01-02-2023 21:53
 */

/**
 * @author 22036043
 *
 */
public class Visitor {
	private String visitorNric4;
	private String visitorName;
	private int contactNo;
	private String dateVisit;
	private String relationship;
	private String patientName;

	public Visitor(String visitorNric4, String visitorName, int contactNo, String dateVisit, String relationship,
			String patientName) {
		this.visitorNric4 = visitorNric4;
		this.visitorName = visitorName;
		this.contactNo = contactNo;
		this.dateVisit = dateVisit;
		this.relationship = relationship;
		this.patientName = patientName;
	}

	public String getVisitorNric4() {
		return this.visitorNric4;
	}

	public String getVisitorName() {
		return this.visitorName;
	}

	public int getContactNo() {
		return this.contactNo;
	}

	public String getDateVisit() {
		return this.dateVisit;
	}

	public String getRelationship() {
		return this.relationship;
	}

	public String getPatientName() {
		return this.patientName;
	}
   
   public void displayVisitorInfo() {
	   String output = String.format("%-20s %-20s %-20s %-30s %-20s %-20s\n", "Visitor Nric4", "Visitor Name", "Contact NO." ,"Date of Visit","Relationship","Patient Name");
	   output += String.format("%-20s %-20s %-20d %-30s %-20s %-20s\n", visitorNric4, visitorName,contactNo,dateVisit,relationship,patientName);
	   System.out.println(output);
	   
   }
}