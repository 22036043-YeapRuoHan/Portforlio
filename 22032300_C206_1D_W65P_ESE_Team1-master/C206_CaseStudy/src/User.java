
public class User {
private int account;
private String name;
private String password;
private String role;
private String email;
private int mobile;
private String homeAddress;

public User(int account, String name, String password, String role, String email, String homeAddress, int mobile) {
	super();
	this.account = account;
	this.name = name;
	this.password = password;
	this.role = role;
	this.email = email;
	this.homeAddress = homeAddress;
	this.mobile = mobile;
	

}

public User(int account) {
	super();
	this.account = account;
}

public int getAccount() {
	return account;
}

public void setAccount(int account) {
	this.account = account;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public int getMobile() {
	return mobile;
}

public void setMobile(int mobile) {
	this.mobile = mobile;
}

public String getHomeAddress() {
	return homeAddress;
}

public void setHomeAddress(String homeAddress) {
	this.homeAddress = homeAddress;
}

}
