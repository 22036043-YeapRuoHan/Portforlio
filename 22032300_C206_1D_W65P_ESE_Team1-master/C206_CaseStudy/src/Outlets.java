public class Outlets {
     private String outletname;
     private String location;
     private String contactnum;
	
     public Outlets(String outletname, String location, String contactnum) {
    	 this.outletname = outletname;
    	 this.location = location;
    	 this.contactnum = contactnum;
     }
  
	public Outlets(String outletname2) {
		// TODO Auto-generated constructor stub
		this.outletname = outletname2;
	}

	public String getLocation() {
		return location;
	}

	public String getContactnum() {
		return contactnum;
	}

	public String getOutletname() {
		return outletname;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setContactnum(String contactnum) {
		this.contactnum = contactnum;
	}
    
}
