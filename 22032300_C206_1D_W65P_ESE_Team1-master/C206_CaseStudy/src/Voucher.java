public class Voucher  {
private int voucherID;
private int requestID;
private int account;
public Voucher(int voucherID,int requestID,int account) {
	
this.voucherID=voucherID;
this.requestID=requestID;
this.account=account;
	}
	public int getVoucherID() {
		return voucherID;
	}
	public void setVoucherID(int voucherID) {
		this.voucherID = voucherID;
	}
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}


}