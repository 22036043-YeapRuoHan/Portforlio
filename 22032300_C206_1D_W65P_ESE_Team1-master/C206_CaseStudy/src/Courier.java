class Courier {
    private String name;
    private String contactNumber;
    private String address;
    private int returnReqID; // New field for the associated request ID

    public Courier(String name, String contactNumber, String address, int returnReqID) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.address = address;
        this.returnReqID = returnReqID;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public int getReturnReqID() {
        return returnReqID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setReturnReqID(int returnReqID) {
        this.returnReqID = returnReqID;
    }
}
