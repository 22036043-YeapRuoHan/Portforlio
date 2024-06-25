//Faiz Shafie
import java.time.LocalDate;


public class Request extends User {
	private int requestID;
	private String name;
	private String emailAddress;
	private int contact_number; 
	private String serialNumber;
	private int invoiceNumber;
	private String reasonForReturn;
	private String status;
	private String arrangeDate;
	private Products product;
	private LocalDate purchaseDate;
	
	public Request(int account, int requestID, String name, String emailAddress, int contact_number, String serialNumber,
			int invoiceNumber, String reasonForReturn, Products product, LocalDate purchaseDate) {
		super(account);
		this.requestID = requestID;
		this.name = name;
		this.emailAddress = emailAddress;
		this.contact_number = contact_number;
		this.serialNumber = serialNumber;
		this.invoiceNumber = invoiceNumber;
		this.reasonForReturn = reasonForReturn;
		this.product = product;
		status = "Pending" ;
		this.purchaseDate = purchaseDate;
	}
	
	public Request(int account, int requestID, String name, String emailAddress, int contact_number, String serialNumber,
			int invoiceNumber, String reasonForReturn,String status,Products product, String arrangeDate) {
		super(account);
		this.requestID = requestID;
		this.name = name;
		this.emailAddress = emailAddress;
		this.contact_number = contact_number;
		this.serialNumber = serialNumber;
		this.invoiceNumber = invoiceNumber;
		this.reasonForReturn = reasonForReturn;
		this.status = status;
		this.product = product; 
		this.arrangeDate=arrangeDate;    
		
	}
	
	public Request(int account, int requestID, String name, String emailAddress, int contact_number, String serialNumber,
			int invoiceNumber, String reasonForReturn,String status, Products product, LocalDate purchaseDate) {
		super(account);
		this.requestID = requestID;
		this.name = name;
		this.emailAddress = emailAddress;
		this.contact_number = contact_number;
		this.serialNumber = serialNumber;
		this.invoiceNumber = invoiceNumber;
		this.reasonForReturn = reasonForReturn;
		this.product = product;
		this.status = status;
		this.purchaseDate = purchaseDate;
	}
	
	public String getArrangeDate() {
		return arrangeDate;
	}
	public void setArrangeDate(String arrangeDate) {
		this.arrangeDate = arrangeDate;
	}

	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public String getReasonForReturn() {
		return reasonForReturn;
	}
	public void setReasonForReturn(String reasonForReturn) {
		this.reasonForReturn = reasonForReturn;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getContact_number() {
		return contact_number;
	}
	public void setContact_number(int contact_number) {
		this.contact_number = contact_number;
	}
	public int getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public LocalDate getpurchaseDate() {
		return purchaseDate;
	}

	public void setpurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
}
