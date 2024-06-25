/*
 * I declare that this code was written by me. 
 * I do not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Yeap Ruo Han 
 * Student ID: 22036043 
 * Class: C208-3B-E65M-A 
 * Date/Time created: Wednesday 30-11-2022 10:49
 */

/**
 * @author 22036043
 *
 */
public class Ward {
	private String ward;
	private String description;
	private int bedCount;
	private double bedCharge;

	public Ward(String ward, String description, int bedCount, double bedCharge) {
		this.ward = ward;
		this.description = description;
		this.bedCount = bedCount;
		this.bedCharge = bedCharge;
	}

	public String getWard() {
		return this.ward;
	}

	public String getDescription() {
		return this.description;
	}

	public int getBedCount() {
		return this.bedCount;
	}

	public double getBedCharge() {
		return this.bedCharge;
	}
}
