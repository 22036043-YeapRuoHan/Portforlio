import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CRiTS {
	private static final int OPTIONADMIN_QUIT = 5;
	private static int OPTIONCSP_QUIT = -99;
	public static int attempts = 0;
	public static String emailTemplate = "Sent to email address:{0}\nRequestID:{1}\nProduct:{2}\nDear {3},\nHere is your refund voucher worth ${4}\nwe hope you had a great experience shopping!";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int option = 0;
		User loggedInUser = null;

		while (option != 3) {
			mainmenu();
			option = Helper.readInt("Enter choice > ");

			if (option == 1) {
				loadUser();
				String name = Helper.readString("Enter name:");
				String password = Helper.readString("Enter password:");
				loggedInUser = login(loggedInUser, name, password);
				attempts = 0;

				if (loggedInUser != null && !loggedInUser.getRole().equalsIgnoreCase("Administrator")) {

					runSoftware(loggedInUser);

				} else if (loggedInUser != null && loggedInUser.getRole().equalsIgnoreCase("Administrator")) {

					runSoftwareAdmin(loggedInUser);
				}

			} else if (option == 2) {
				String name = Helper.readString("Enter name:");
				String password = Helper.readString("Enter password:");
				String email = Helper.readString("Enter email: ");
				String homeAddress = Helper.readString("Enter home address:");
				int mobile = Helper.readInt("Enter mobile:");

				RegisterAccount(name, password, email, homeAddress, mobile);

			} else if (option == 3) {

				System.out.println("Thank you for using this software!");

			} else {
				System.out.println("Invalid option!");
			}
		}
	}

	private static void mainmenu() {
		Helper.line(70, "-");
		System.out.println("\t\tWELCOME TO RP Customer Return Service");
		Helper.line(70, "-");
		System.out.println("1. Log in");
		System.out.println("2. Register User");
		System.out.println("3. Quit");
	}

	private static void CustomerMenu() {

		Helper.line(70, "-");
		System.out.println("SELECT YOUR OPTIONS");
		Helper.line(70, "-");
		System.out.println("1. Make a Return Request");
		System.out.println("2. Check statuses on Return Requests");
		System.out.println("3. Manage Account");
		System.out.println("4. Arrange For Courier Pickup");
		System.out.println("5. Quit");
	}

	private static void CustomerSPMenu() {
		Helper.line(70, "-");
		System.out.println("SELECT YOUR OPTIONS");
		Helper.line(70, "-");
		System.out.println("1. Approve a Customer's Return Request");
		System.out.println("2. Check statuses on Return Requests for all Customers");
		System.out.println("3. Manage Account");
		System.out.println("4. Arrange Date for Product Exchange");
		System.out.println("5. Email Refund voucher");
		System.out.println("6. Quit");

	}

	private static void AdministratorMenu() {
		Helper.line(70, "-");
		System.out.println("SELECT YOUR OPTIONS");
		Helper.line(70, "-");
		System.out.println("1. Manage Products");
		System.out.println("2. Manage Outlets");
		System.out.println("3. Show list of products");
		System.out.println("4. Show list of outlets");
		System.out.println("5. Quit");
	}

	private static void ManageProduct() {
		System.out.println("");
		System.out.println("1. Add product");
		System.out.println("2. Delete product");
	}

	private static void ManageOutlet() {
		System.out.println("");
		System.out.println("1. Add Outlet");
		System.out.println("2. Edit Outlet's information");
		System.out.println("3. Delete Outlet");
	}

	private static void runSoftware(User loginAcct) {
		int memberOption = -1;

		while (memberOption != 5 && loginAcct.getRole().equals("Customer")
				|| memberOption != 6 && loginAcct.getRole().equals("CustomerSP")) {
			ArrayList<Products> productList = loadprod();
			ArrayList<Request> requestList = loadReturnRequests();
			ArrayList<Courier> courierList = loadCourierList();

			if (loginAcct.getRole().equals("Customer")) {
				CustomerMenu();
				memberOption = Helper.readInt("Enter choice > ");

				if (memberOption == 1) {
					// Faiz
					requestList.add(submitReturnRequest(loginAcct, requestList, productList));

				} else if (memberOption == 2) {
					// Faiz
					customerCheckReturnRequests(requestList, loginAcct.getAccount());

				} else if (memberOption == 3) {

				} else if (memberOption == 4) {
					requestCourierPickup(requestList, courierList, null, null, null);
				} else if (memberOption == 5 && loginAcct.getRole().equals("Customer")) {
					System.out.println("Logging out.");
				} else if (memberOption == 6 && loginAcct.getRole().equals("CustomerSP")) {
					System.out.println("Logging out.");
				}

				saveReturnRequests(requestList);

			} else {
				CustomerSPMenu();
				memberOption = Helper.readInt("Enter choice > ");
				Helper.line(70, "-");

				if (memberOption == 1) {
					CustomerSPGetRequest(requestList);
				} else if (memberOption == 2) {
					customerSPTrackReturnRequests(requestList);

				} else if (memberOption == 3) {

				} else if (memberOption == 4) {
					arrangeProductExchange();
					requestList = loadReturnRequests();
					int RequestNo = Helper.readInt("Enter RequestID of request to arrange product exchange >");
					String arrange = Helper
							.readString("Enter the date for product exchange in the format YYYY-MM-DD: >");
					saveArrangeProductExchange(requestList, RequestNo, arrange);

				} else if (memberOption == 5) {
					arrangeProductExchange();
					requestList = loadReturnRequests();
					int RequestNo = Helper.readInt("Enter RequestID of request to email refund >");

					EmailRefundVoucher(requestList, RequestNo);

				} else if (memberOption == 6) {

					System.out.println("Logging out.");
				}
			}
		}

	}

	public static String CustomerSPApprovalMenu() {
		// TODO Auto-generated method stub
		String stat = "";
		char choice = 'e';
		choice = Helper.readChar("Approve?\nY/N");
		while (true) {
			
			char yes = 'y';
			char yes2 = 'Y';
			char no = 'n';
			char no2 = 'N';
			
			if (choice == yes || choice == yes2 ) {
				stat = "Approved";
				break;
			} else if (choice == no || choice == no2) {
				stat = "Denied";
				break;
			} else {
				System.out.println("Invalid choice.");
				break;
			}
		}

		return stat;
	}

	public static void CustomerSPGetRequest(ArrayList<Request> requestList) {
		boolean found = false;
		int sn = Helper.readInt("Enter number of request id to review. >");

		for (Request r : requestList) {
			if (sn == r.getRequestID()) {
				found = true;
				System.out.println("Match Found.");
				System.out.printf("%-15s%-25s%-25s%-25s%-25s%-35s%-25s%s\n", "NAME", "EMAIL ADDRESS", "CONTACT NUMBER",
						"PRODUCT NAME", "SERIAL NUMBER", "INVOICE NUMBER", "REASON FOR RETURN", "STATUS");
				Helper.line(250, "-");
				String name = r.getName();
				String emailAddress = r.getEmailAddress();
				int contact_number = r.getContact_number();
				String productName = r.getProduct().getName();
				String serialNumber = r.getSerialNumber();
				int invoiceNumber = r.getInvoiceNumber();
				String status = r.getStatus();
				String reasonForReturn = r.getReasonForReturn();
				System.out.printf("%-15s%-25s%-25s%-25s%-25s%-35s%-25s%s\n", name, emailAddress,
						contact_number, productName, serialNumber, invoiceNumber,
						reasonForReturn, status);
				r.setStatus(CustomerSPApprovalMenu());
				saveReturnRequests(requestList);

			}
		}
		if (found == false) {
			System.out.println("No such invoice.");
		}

	}

	private static void runSoftwareAdmin(User loginAcct) {

		int Option = -1;

		while (Option != OPTIONADMIN_QUIT) {
			ArrayList<Request> requestList = loadReturnRequests();
			ArrayList<Products> productlist = loadprod();
			ArrayList<Outlets> outletlist = loadoutl();
			AdministratorMenu();
			Option = Helper.readInt("Enter option > ");

			if (Option == 1) {
				ManageProduct();
				int choice = Helper.readInt("Enter choice > ");
				if (choice == 1) {
					if (outletlist.size() == 0) {
						System.out.println("Cannot add product if no there is no registered outlet in the software.");
					} else {
						addProduct(productlist, outletlist);
					}
				} else if (choice == 2) {
					if (productlist.size() == 0) {
						System.out.println("");
						System.out.println("Nothing to delete");
						System.out.println("");
					} else {

						showproduct(productlist);
						deleteProduct(productlist, requestList);
					}
				}

			} else if (Option == 2) {
				ManageOutlet();
				int choice = Helper.readInt("Enter choice > ");
				if (choice == 1) {
					addOutlet(outletlist);
				} else if (choice == 2) {
					editOutlet(outletlist, productlist, requestList);
				} else if (choice == 3) {
					if (outletlist.size() == 0) {
						System.out.println("");
						System.out.println("Nothing to delete");
						System.out.println("");
					} else {
						showoutlet(outletlist);
						deleteOutlet(outletlist, productlist, requestList);

					}
				} else {
					System.out.println("Invalid choice");
				}
			} else if (Option == 3) {
				if (productlist.size() == 0) {
					System.out.println("");
					System.out.println("Nothing to show here");
					System.out.println("");
				} else {
					showproduct(productlist);
				}
			} else if (Option == 4) {
				if (outletlist.size() == 0) {
					System.out.println("");
					System.out.println("Nothing to show here");
					System.out.println("");
				} else {
					showoutlet(outletlist);
				}
			} else if (Option == OPTIONADMIN_QUIT) {
				System.out.println("Logging out");
			} else {
				System.out.println("Invalid option!");
			}
			requestList = loadReturnRequests();
			productlist = loadprod();
			outletlist = loadoutl();
		}

	}

	// --------------------------------------------------------------------------------------------------------------------------Don
	public static ArrayList<Voucher> loadVoucher() {
		ArrayList<Voucher> voucherList = new ArrayList<Voucher>();

		try {
			File file = new File("Voucher.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			while (line != null) {
				String[] parts = line.split(",");

				try {
					int voucherID = Integer.parseInt(parts[0].trim());
					int requestID = Integer.parseInt(parts[1].trim());
					int account = Integer.parseInt(parts[2].trim());

					voucherList.add(new Voucher(voucherID, requestID, account));
				} catch (NumberFormatException e) {
					int voucherID = 0;
					int requestID = Integer.parseInt(parts[0].trim());
					int account = Integer.parseInt(parts[1].trim());

					voucherList.add(new Voucher(voucherID, requestID, account));

				}

				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file could not be found :(");
		} catch (IOException io) {
			System.out.println("There was an 1/0 error");
		}

		return voucherList;
	}

	public static String EmailRefundVoucher(ArrayList<Request> requestList, int RequestNo) {
		ArrayList<Voucher> voucherList = loadVoucher();
		boolean isfound = false;
		String email = "";

		for (Request i : requestList) {

			if (RequestNo == i.getRequestID()) {
				isfound = true;

				if (i.getStatus().equalsIgnoreCase("Approved")) {
					email += MessageFormat.format(emailTemplate, i.getEmailAddress(), RequestNo,
							i.getProduct().getName(), i.getName(), i.getProduct().getPrice());
					System.out.println(email);

					try {
						File file = new File("Voucher.txt");
						FileWriter fw = new FileWriter(file, true);
						BufferedWriter bw = new BufferedWriter(fw);
						String output = String.format("%d,%d,%d", voucherList.size() + 1, i.getRequestID(),
								i.getAccount());

						bw.write(output);
						bw.newLine();
						bw.close();
						loadUser();

					} catch (IOException io) {
						System.out.println("There was an error writing to the file.");
						return "There was an error writing to the file.";

					}

				} else if (!i.getStatus().equalsIgnoreCase("Approved")) {
					System.out.println("The Request is not approved");
					return ("The Request is not approved");
				}
			}
		}
		if (isfound == false) {
			System.out.println("Invalid RequestID");
			return ("Invalid RequestID");
		} else {
			return email;
		}

	}

	public static String validateRegistrationDetails(String name, String password, String email, String homeAddress,
			int mobile) {
		String Erroroutput = "[Registration error]\n";

		ArrayList<User> userList = loadUser();

		if (homeAddress.isEmpty() || name.isEmpty() || password.isEmpty() || Integer.valueOf(mobile) == null
				|| email.isEmpty()) {
			Erroroutput += "not all fields filled in\n";

		} else {
			for (User user : userList) {
				if (user.getEmail().equals(email)) {
					Erroroutput += "email already registered\n";
				}
				if (user.getMobile() == (mobile)) {
					Erroroutput += "mobile number already registered\n";
				}
				if (user.getPassword().equals(password)) {
					Erroroutput += "password already taken\n";
				}
			}
			if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).{8,64}$")) {
				Erroroutput += "password requirements not met(minimum 8 characters, maximum 64 characters, minimum of 1 number and 1 alphabet)\n";
			}
			if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
				Erroroutput += "email format is wrong\n";
			}
		}
		return Erroroutput;
	}

	public static String RegisterAccount(String name, String password, String email, String homeAddress, int mobile) {
		ArrayList<User> userList = loadUser();
		if (validateRegistrationDetails(name, password, email, homeAddress, mobile).equals("[Registration error]\n")) {
			String role = "Customer";
			String output = String.format("%d,%s,%s,%s,%s,%s,%d", userList.size() + 1, name, password, role, email,
					homeAddress, mobile);
			try {
				File file = new File("UserDatabase.txt");
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(output);
				bw.newLine();
				bw.close();
				loadUser();
				System.out.println("[registered succesfully]");
				return ("[registered succesfully]");

			} catch (IOException io) {
				System.out.println("There was an error writing to the file.");
				return ("There was an error writing to the file.");
			}

		} else {
			System.out.println(validateRegistrationDetails(name, password, email, homeAddress, mobile));
			return (validateRegistrationDetails(name, password, email, homeAddress, mobile));
		}

	}

	public static ArrayList<User> loadUser() {
		ArrayList<User> userList = new ArrayList<User>();

		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			while (line != null) {
				String[] parts = line.split(",");

				try {
					int account = Integer.parseInt(parts[0].trim());
					String name = parts[1].trim();
					String password = parts[2].trim();
					String role = parts[3].trim();
					String email = parts[4].trim();
					String homeAddress = parts[5].trim();
					int mobile = Integer.parseInt(parts[6].trim());
					userList.add(new User(account, name, password, role, email, homeAddress, mobile));
				} catch (NumberFormatException e) {
					int account = 0;
					String name = parts[0].trim();
					String password = parts[1].trim();
					String role = parts[2].trim();
					String email = parts[3].trim();
					String homeAddress = parts[4].trim();
					int mobile = Integer.parseInt(parts[5].trim());
					userList.add(new User(account, name, password, role, email, homeAddress, mobile));
				}

				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file could not be found :(");
		} catch (IOException io) {
			System.out.println("There was an 1/0 error");
		}

		return userList;
	}

	public static User login(User loggedInUser, String name, String password) {
		boolean successLogIn = false;

		ArrayList<User> userList = loadUser();

		while (!successLogIn && attempts < 3) {

			if ((name.isEmpty() || password.isEmpty()) && attempts < 3 && attempts != 0) {

				name = Helper.readString("Enter name:");
				password = Helper.readString("enter password:");

			} else if (attempts != 0) {
				name = Helper.readString("Enter name:");
				password = Helper.readString("enter password:");
			}

			
			for (User user : userList) {
				if (user.getPassword().equals(password) && user.getName().equals(name)) {
					loggedInUser = user;
					successLogIn = true;

				}

			}

			if (successLogIn) {

				System.out.println("[successful login]");

				return loggedInUser;
			} else {
				if (attempts !=2) {
				System.out.println("[unsuccesful login]");
				attempts++;
				}else {
					attempts++;
				}
			}
			if (!successLogIn && attempts == 3) {
				System.out.println("[You have exceeded maximum 3 login attempts.]");

				return null;

			}

		}

		return null;
	}
	
	//Faiz User Story 13
	public static Request submitReturnRequest(User user, ArrayList<Request> rList, ArrayList<Products> pList) {
		Helper.line(70, "-");
		System.out.println("SUBMIT RETURN REQUEST");
		Helper.line(70, "-");
		System.out.println();
		Request r = null;
		int requestID = rList.size() + 1;
		String productName = promptProductName();
		String serialNumber = promptSerialNumber();
		int invoiceNumber = promptInvoiceNumber();
		String reasonForReturn = null;
		System.out.println();
		System.out.println("1. Product not as expected");
		System.out.println("2. Defective or damaged product");
		System.out.println("3. Incorrect product received");
		System.out.println("4. Product recall");
		System.out.println("5. Other Reason");
		System.out.println();

		int reasonForReturnOption = Helper.readInt("Select Reason For Returning Product > ");

		if (reasonForReturnOption == 1) {
			reasonForReturn = "Product not as expected";
			System.out.println();
			String outletName = promptOutlet();
			Products product = createProductObject(productName, outletName);
			LocalDate purchaseDate = promptPurchaseDate();
			r = createReturnRequest(user, requestID, serialNumber, invoiceNumber, reasonForReturn, product, purchaseDate);
		} 
		else if (reasonForReturnOption == 2) {
			reasonForReturn = "Defective or damaged product";
			System.out.println();
			String outletName = promptOutlet();
			Products product = createProductObject(productName, outletName);
			LocalDate purchaseDate = promptPurchaseDate();
			r = createReturnRequest(user, requestID, serialNumber, invoiceNumber, reasonForReturn, product, purchaseDate);
		} 
		else if (reasonForReturnOption == 3) {
			reasonForReturn = "Incorrect product received";
			System.out.println();
			String outletName = promptOutlet();
			Products product = createProductObject(productName, outletName);
			LocalDate purchaseDate = promptPurchaseDate();
			r = createReturnRequest(user, requestID, serialNumber, invoiceNumber, reasonForReturn, product, purchaseDate);
		} 
		else if (reasonForReturnOption == 4) {
			reasonForReturn = "Product recall";
			System.out.println();
			String outletName = promptOutlet();
			Products product = createProductObject(productName, outletName);
			LocalDate purchaseDate = promptPurchaseDate();
			r = createReturnRequest(user, requestID, serialNumber, invoiceNumber, reasonForReturn, product, purchaseDate);
		} 
		else if (reasonForReturnOption == 5) {
			reasonForReturn = Helper.readString("Enter other reason (Max 50 Chars) > ");
			if (reasonForReturn.length() > 50) {
				System.out.println("Input entered more than 50 characters! Return Request Rejected!");
			} 
			else {
				System.out.println();
				String outletName = promptOutlet();
				Products product = createProductObject(productName, outletName);
				LocalDate purchaseDate = promptPurchaseDate();
				r = createReturnRequest(user, requestID, serialNumber, invoiceNumber, reasonForReturn, product, purchaseDate);
			}
		} 
		else {
			System.out.println("Invalid option!");
		}
		
		return r;
	}
	
	//Faiz User Story 13 
	public static String promptProductName() {
		ArrayList<Products> pList = loadprod();
		String productName = Helper.readString("Enter Product Name > ");
		boolean validProduct = false;
		for (Products p:pList) {
			if (productName.equalsIgnoreCase(p.getName())) {
				productName = p.getName();
				validProduct = true;
				break;
			}
		}
		if(validProduct == false) {
			productName = null;
		}
		return productName;
	}
	
	//Faiz User Story 13
	public static int promptInvoiceNumber() {
		int invoiceNumber = Helper.readInt("Enter Invoice Number > ");
		if(invoiceNumber < 100 || invoiceNumber > 99999) {
			invoiceNumber = 0;
		}
		return invoiceNumber;
	}
	
	//Faiz User Story 13
	public static String promptOutlet() {
		ArrayList<Outlets> oList = loadoutl();
		String outletName = Helper.readString("Enter outlet name > ");
		for (Outlets o : oList) {
			if (outletName.equalsIgnoreCase(o.getOutletname())) {
				outletName = o.getOutletname();
				break;
			}
		}
		return outletName;
	}
	
	//Faiz User Story 13
	public static LocalDate promptPurchaseDate() {
		LocalDate purchaseDate = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			try {
				String purchaseDateString = Helper.readString("Enter Purchase Date (DD-MM-YYYY) > ");
				purchaseDate = LocalDate.parse(purchaseDateString.trim(), formatter);
			} 
			catch (Exception e) {
				
			}
		return purchaseDate;
	}
	
	//Faiz User Story 13
	public static Products createProductObject(String productName, String outletName) {
		Products product = null;
		ArrayList<Products> pList = loadprod();
		for (Products p : pList) {
			if (p.getName().equalsIgnoreCase(productName)
					&& p.getOutletname().equalsIgnoreCase(outletName)) {
				product = p;
			}
		}
		return product;
	}
	
	//Faiz User Story 13
	public static void displayConfimationOfReturnRequest(int requestID) {
		System.out.println("Return Request ID: " + requestID);
		System.out.println("Return Request Has Been Submitted! Waiting For Approval");
	}
	
	//Faiz User Story 13
	public static String promptSerialNumber() {
		String serialNumber = Helper.readString("Enter Serial Number Of Product > ");
		String pattern = "[S|N]\\d{3}\\w";
	    boolean matchFound = Pattern.matches(pattern, serialNumber);
	    if(matchFound == false) {
	    	serialNumber = null;
	    }
		return serialNumber;
	}
	
	//Faiz User Story 13
	public static Request createReturnRequest(User user, int requestID, String serialNumber, int invoiceNumber, String reasonForReturn, Products product, LocalDate purchaseDate) {
		Request r = null;
		boolean correctSerialNumber = false;
		boolean correctInvoiceNumber = false;
		boolean correctProduct = false;
		boolean correctPurchaseDate = false;
		
		if(serialNumber != null) {
			correctSerialNumber = true;
		}
		else {
			System.out.println("Incorrect Serial Number!");
		}
		
		if(invoiceNumber > 0) {
			correctInvoiceNumber = true;
		}
		else {
			System.out.println("Incorrect Invoice Number!");
		}
		
		if(product != null) {
			correctProduct = true;
		}
		else {
			System.out.println("Incorrect Product Name or Outlet Name!");
		}
		
		if(purchaseDate != null) {
			correctPurchaseDate = true;
		}
		else {
			System.out.println("Incorrect Purchase Date!");
		}
		
		if(correctSerialNumber == true && correctInvoiceNumber == true && correctProduct == true && correctPurchaseDate == true) {
			r = new Request(user.getAccount(), requestID, user.getName(), user.getEmail(), user.getMobile(), serialNumber, invoiceNumber,
					reasonForReturn, product, purchaseDate);
			displayConfimationOfReturnRequest(requestID);
		}
		else {
			System.out.println("Return Request has been rejected!");
		}
		return r;
	}
	
	//Faiz User Story 13
	public static void saveReturnRequests(ArrayList<Request> r) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			File file = new File("ReturnRequests.txt");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			String output = "";

			for (Request re : r) {
				if (re != null) {
					output = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", String.valueOf(re.getAccount()),
							String.valueOf(re.getRequestID()), re.getName(), re.getEmailAddress(),
							String.valueOf(re.getContact_number()), re.getSerialNumber(),
							String.valueOf(re.getInvoiceNumber()), re.getReasonForReturn(), re.getStatus(),
							re.getProduct().getName(), re.getProduct().getOutletname(),
							formatter.format(re.getpurchaseDate()));
					bw.write(output);
				}
			}

			bw.close();
			fw.close();
		} catch (Exception e) {
			System.out.println("Error occured while saving return requests to system!");
		}
	}

	//Faiz User Story 13
	public static ArrayList<Request> loadReturnRequests() {
		ArrayList<Request> r = new ArrayList<Request>();
		ArrayList<Products> pList = loadprod();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		try {
			File file = new File("ReturnRequests.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {

				String[] values = line.split(",");

				int account = Integer.parseInt(values[0].trim());
				int requestID = Integer.parseInt(values[1].trim());
				String customer_Name = values[2].trim();
				String customer_EmailAddress = values[3].trim();
				int customer_ContactNumber = Integer.parseInt(values[4].trim());
				String serialNumber = values[5].trim();
				int invoiceNumber = Integer.parseInt(values[6].trim());
				String reasonForReturn = values[7].trim();
				String status = values[8].trim();
				String productName = values[9].trim();
				String outletName = values[10].trim();
				LocalDate purchaseDate = LocalDate.parse(values[11].trim(), formatter);
				Products product = null;
				for (Products p : pList) {
					if (productName.equalsIgnoreCase(p.getName()) && outletName.equalsIgnoreCase(p.getOutletname())) {
						product = p;
						break;
					}
				}

				r.add(new Request(account, requestID, customer_Name, customer_EmailAddress, customer_ContactNumber,
						serialNumber, invoiceNumber, reasonForReturn, status, product, purchaseDate));
			}

			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	//Faiz User Story 14
	public static void customerCheckReturnRequests(ArrayList<Request> rList, int accountNumber) {
			System.out.println();
			Helper.line(150, "-");
			System.out.println(
			"RequestID  Product Name                             Serial Number Invoice Number Reason                                             Status");
			Helper.line(150, "-");
			System.out.println(displayReturnRequests(rList, accountNumber));
		}
	
	//Faiz User Story 14
	public static String displayReturnRequests(ArrayList<Request> rList, int accountNumber) {
		String output = "";
		for (Request r : rList) {
			if (r.getAccount() == accountNumber) {
				output += String.format("%-10d %-40s %-13s %-14d %-50s %s\n", r.getRequestID(), r.getProduct().getName(),
				r.getSerialNumber(), r.getInvoiceNumber(), r.getReasonForReturn(), r.getStatus());
			}
		}
		return output;
	}
	
	//Faiz User Story 15
	public static void customerSPTrackReturnRequests(ArrayList<Request> requestList) {
		customerSPTrackingMenuHeader();
		System.out.println(retrieveReturnRequest(requestList));
		while (OPTIONCSP_QUIT != 6) {
			System.out.println("1. Search by RequestID");
			System.out.println("2. Search by AccountID");
			System.out.println("3. Search by Serial Number");
			System.out.println("4. Search by Invoice Number");
			System.out.println("5. Reset Searching");
			System.out.println("6. Exit Tracking System");
			OPTIONCSP_QUIT = Helper.readInt("Enter option > ");

			if (OPTIONCSP_QUIT == 1) {
				int requestID = Helper.readInt("Enter Request ID > ");
				customerSPTrackingMenuHeader();
				System.out.println(retrieveReturnRequestFilterRequestID(requestList, requestID));
			} else if (OPTIONCSP_QUIT == 2) {
				int accountID = Helper.readInt("Enter Account ID > ");
				customerSPTrackingMenuHeader();
				System.out.println(retrieveReturnRequestFilterAccountID(requestList, accountID));
			} else if (OPTIONCSP_QUIT == 3) {
				String serialNumber = Helper.readString("Enter Serial Number > ");
				customerSPTrackingMenuHeader();
				System.out.println(retrieveReturnRequestFilterSerialNumber(requestList, serialNumber));
			} else if (OPTIONCSP_QUIT == 4) {
				int invoiceNumber = Helper.readInt("Enter Invoice Number > ");
				customerSPTrackingMenuHeader();
				System.out.println(retrieveReturnRequestFilterInvoiceNumber(requestList, invoiceNumber));
			} else if (OPTIONCSP_QUIT == 5) {
				customerSPTrackingMenuHeader();
				System.out.println(retrieveReturnRequest(requestList));
			} else if (OPTIONCSP_QUIT == 6) {
				System.out.println("Have a great day ahead!");
			}
		}
	}
	
	//Faiz User Story 15
	public static void customerSPTrackingMenuHeader() {
		System.out.println();
		Helper.line(230, "-");
		System.out.println(
				"RequestID  AccountID  Product ID  Name                        Product Name          Outlet           Serial Number Invoice Number Reason                                             Status     Purchase Date");
		Helper.line(230, "-");
	}
	
	//Faiz User Story 15
	public static String retrieveReturnRequest(ArrayList<Request> requestList) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String output = "";
		for (Request r : requestList) {
			output += String.format("%-10d %-10d %-11d %-27s %-21s %-16s %-13s %-14d %-50s %-10s %s\n", r.getRequestID(),
					r.getAccount(), r.getProduct().getproductID(),r.getName(), r.getProduct().getName(), r.getProduct().getOutletname(),
					r.getSerialNumber(), r.getInvoiceNumber(), r.getReasonForReturn(), r.getStatus(),
					r.getpurchaseDate().format(formatter));
		}
		return output;
	}
	
	//Faiz User Story 15
	public static String retrieveReturnRequestFilterRequestID(ArrayList<Request> requestList, int requestID) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String output = "";
		for (Request r : requestList) {
			if (r.getRequestID() == requestID) {
				output += String.format("%-10d %-10d %-11d %-27s %-21s %-16s %-13s %-14d %-50s %-10s %s\n", r.getRequestID(),
						r.getAccount(), r.getProduct().getproductID(),r.getName(), r.getProduct().getName(), r.getProduct().getOutletname(),
						r.getSerialNumber(), r.getInvoiceNumber(), r.getReasonForReturn(), r.getStatus(),
						r.getpurchaseDate().format(formatter));
			}
		}
		return output;
	}
	
	//Faiz User Story 15
	public static String retrieveReturnRequestFilterAccountID(ArrayList<Request> requestList, int accountID) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String output = "";
		for (Request r : requestList) {
			if (r.getAccount() == accountID) {
				output += String.format("%-10d %-10d %-11d %-27s %-21s %-16s %-13s %-14d %-50s %-10s %s\n", r.getRequestID(),
						r.getAccount(), r.getProduct().getproductID(),r.getName(), r.getProduct().getName(), r.getProduct().getOutletname(),
						r.getSerialNumber(), r.getInvoiceNumber(), r.getReasonForReturn(), r.getStatus(),
						r.getpurchaseDate().format(formatter));
			}
		}
		return output;
	}
	
	//Faiz User Story 15
	public static String retrieveReturnRequestFilterSerialNumber(ArrayList<Request> requestList, String serialNumber) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String output = "";
		for (Request r : requestList) {
			if (r.getSerialNumber().equals(serialNumber)) {
				output += String.format("%-10d %-10d %-11d %-27s %-21s %-16s %-13s %-14d %-50s %-10s %s\n", r.getRequestID(),
						r.getAccount(), r.getProduct().getproductID(),r.getName(), r.getProduct().getName(), r.getProduct().getOutletname(),
						r.getSerialNumber(), r.getInvoiceNumber(), r.getReasonForReturn(), r.getStatus(),
						r.getpurchaseDate().format(formatter));
			}
		}
		return output;
	}
	
	//Faiz User Story 15
	public static String retrieveReturnRequestFilterInvoiceNumber(ArrayList<Request> requestList, int invoiceNumber) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String output = "";
		for (Request r : requestList) {
			if (r.getInvoiceNumber() == invoiceNumber) {
				output += String.format("%-10d %-10d %-11d %-27s %-21s %-16s %-13s %-14d %-50s %-10s %s\n", r.getRequestID(),
						r.getAccount(), r.getProduct().getproductID(),r.getName(), r.getProduct().getName(), r.getProduct().getOutletname(),
						r.getSerialNumber(), r.getInvoiceNumber(), r.getReasonForReturn(), r.getStatus(),
						r.getpurchaseDate().format(formatter));
			}
		}
		return output;
	}

//--------------------------------------------------------------------------------------------------------------------------Isaac
	public static ArrayList<Products> loadprod() {
		ArrayList<Products> prodlist = new ArrayList<Products>();
		try {
			File file = new File("Products.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			while (line != null) {
				String[] product = line.split(", ");
				int productid = Integer.parseInt(product[0]);
				String productName = product[1];
				String productPrice = product[2];
				String outletName = product[3];
				String returnpolicy = product[4];
				Products products = new Products(productid, productName, Double.parseDouble(productPrice), outletName,
						returnpolicy);
				prodlist.add(products);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found.");
		} catch (IOException io) {
			System.out.println("I/O error.");
		}
		return prodlist;
	}

	// int id, String product, double price, String outletname, String returnpolicy
	public static void addProduct(ArrayList<Products> list, ArrayList<Outlets> list1) {
		boolean check = false;
		DecimalFormat df = new DecimalFormat("#.00");
		while (!check) {
			boolean duplicate = false;
			boolean dupoutlet = false;
			boolean dupeid = false;
			int id = Helper.readInt("Enter a new product ID >");
			String name = Helper.readString("Enter product name >");
			double price = Helper.readDouble("Enter product price tag >$");
			String outlet = Helper.readString("Enter outlet name >");
			String returnpolicy = Helper.readString("Enter the new written policy for product >");
			
			if (name.equals("") || outlet.equals("") || returnpolicy.equals("")) {
				System.out.println("Please fill in all fields");
			} else if (price <= 0) {
				System.out.println("Please enter a valid price");
			} else if (id <= 0) {
				System.out.println("Please enter a valid ID");
			} else {
				for (Products p1 : list) {
					String outletName = p1.getOutletname();
					if (p1.getName().equals(name.trim()) && outletName.equals(outlet.trim())) {
						duplicate = true;
						break;
					}
				}

				for (Outlets o1 : list1) {
					String outletName = o1.getOutletname();
					if (outlet.trim().equals(outletName)) {
						dupoutlet = true;
						break;
					}
				}

				for (Products p2 : list) {
					if (p2.getproductID() == id) {
						dupeid = true;
						break;
					}
				}
				if (dupoutlet) {
					if (!duplicate) {
						if (!dupeid) {
							try {
								File file = new File("Products.txt");
								FileWriter fw = new FileWriter(file, true);
								BufferedWriter bw = new BufferedWriter(fw);

								bw.write(String.format("%d, %s, %s, %s, %s", id, name.trim(), df.format(price), outlet.trim(), returnpolicy.trim()));
								System.out.println("Product successfully added!");
								bw.newLine();
								bw.close();
								check = true;

							} catch (IOException io) {
								System.out.println("There was an error writing to this file");
							}
						} else {
							System.out.println("Duplicate product ID found!");
						}
					} else {
						System.out.println("Duplicate product found!");
					}
				} else {
					System.out.println("Invalid Outlet!");
				}
			}
		}

	}

	public static void deleteProduct(ArrayList<Products> list, ArrayList<Request> reqlist) {
	    
		boolean empty = false;
		boolean check = false;
		ArrayList<Products> newlist = new ArrayList<Products>();
		System.out.println("Enter q to exit");
		while (!empty) {
             
            int id = Helper.readInt("Enter the product ID to delete it >");
    
			if (id <= 0) {
				System.out.println("Please fill in a valid ID");
			} else {
				if (!checkProductAva(id, reqlist)) {
					System.out.println();
					System.out.println(
							"Product cannot be deleted as it still exists in the return requests section.\nPlease clear the product from any return request to delete it.");
					System.out.println("");
				} else {
					try {
						File file = new File("Products.txt");
						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);
						for (int i = 0; i < list.size(); i++) {
							if (id==list.get(i).getproductID()) {
								list.remove(i);
								newlist = list;
								check = true;
								break;
							}
						}
						if (check) {
							for (Products a : newlist) {
								bw.write(String.format("%d, %s, %.2f, %s, %s", a.getproductID(),a.getName(), a.getPrice(), a.getOutletname(), a.getreturnpolicy()));
								bw.newLine();
							}
							System.out.println("Product successfully deleted!");
							bw.close();
						}
					} catch (IOException io) {
						System.out.println("There was an error writing to this file");
					}if (!check) {
						System.out.println("Invalid input!");
					} else {
						empty = true;
					}
				}
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	private static void deleteProductFromOutlet(ArrayList<Products> list, String removeoutlet,
			ArrayList<Request> reqlist) {
		ArrayList<Products> newdelist = new ArrayList<Products>();
		boolean check = false;

		for (Products b : list) {
			String outletName = b.getOutletname();
			if (outletName.equals(removeoutlet)) {
				check = true;
				break;
			}
		}
		if (check) {
			try {
				File file = new File("Products.txt");
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);

				for (int i = list.size() - 1; i >= 0; i--) {
					String outletName = list.get(i).getOutletname();
					if (outletName.equals(removeoutlet)) {
						list.remove(i);
					}
				}
				newdelist = list;
				for (Products a : newdelist) {
					bw.write(String.format("%d, %s, %.2f, %s, %s", a.getproductID(),a.getName(), a.getPrice(), a.getOutletname(), a.getreturnpolicy()));
					bw.newLine();
				}
				System.out.println("Products successfully deleted related to Outlet!");
				bw.close();

			} catch (IOException io) {
				System.out.println("There was an error writing to this file");
			}
		} else {
			System.out.println("No product to be deleted");
		}
	}

	public static ArrayList<Outlets> loadoutl() {
		ArrayList<Outlets> outletlist = new ArrayList<Outlets>();
		try {
			File file = new File("OutletList.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			while (line != null) {
				String[] outlet = line.split(", ");
				String outletName = outlet[0];
				String outletLocation = outlet[1];
				String outletContactNo = outlet[2];
				Outlets outlets = new Outlets(outletName, outletLocation, outletContactNo);
				outletlist.add(outlets);
				line = br.readLine();
			}

			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found.");
		} catch (IOException io) {
			System.out.println("I/O error.");
		}
		return outletlist;
	}

	public static void addOutlet(ArrayList<Outlets> list) {
		boolean check = false;
		boolean duplicate = false;

		while (!check) {
			String outlet = Helper.readString("Enter outlet name >");
			String location = Helper.readString("Enter Outlet location >");
			String number = Helper.readString("Enter Outlet's contact no >");
			if (outlet.equals("") || location.equals("")) {
				System.out.println("Please fill in the outlet field");
			} else if (!number.matches("\\+(\\d{1,3}) (\\d{8})")) {
				System.out.println("Please for this format: +XX XXXXXXXX >");
			} else {

				for (Outlets a : list) {
					String outletName = a.getOutletname();
					if (outletName.equals(outlet.trim())) {
						duplicate = true;
						System.out.println("Duplicate Outlet found!");
						break;
					} else {
						if (a.getContactnum().equals(number)) {
							duplicate = true;
							System.out.println("Duplicate Number found!");
							break;
						}
					}
				}

				if (!duplicate) {
					try {
						File file = new File("OutletList.txt");
						FileWriter fw = new FileWriter(file, true);
						BufferedWriter bw = new BufferedWriter(fw);

						bw.write(String.format("%s, %s, %s", outlet.trim(), location.trim(), number.trim()));
						System.out.println("Outlet successfully added!");
						bw.newLine();
						bw.close();
						check = true;

					} catch (IOException io) {
						System.out.println("There was an error writing to this file");
					}
				}

			}
		}

	}

	public static void editOutlet(ArrayList<Outlets> list, ArrayList<Products> prodlist, ArrayList<Request> reqlist) {
		boolean check = false;
		String oldnum = "";
		String newnumber = "";

		while (!check) {
			boolean found = false;
			boolean valid = false;
			String chosenoutlet = Helper.readString("Enter the Outlet name you would like to edit >");

			for (Outlets a : list) {
				String outletName = a.getOutletname();
				if (outletName.equals(chosenoutlet.trim())) {
					found = true;
					oldnum = a.getContactnum();
					System.out.println("Outlet found!");
					break;
				}
			}
			
			if (found) {
				boolean duplicate = false;
				boolean empty = false;
				boolean numcheck = false;
				String newlocation = Helper.readString("Enter new Outlet location >");
				char variable = Helper.readChar("Do you want to change the location's ContactNo?(Y/N)>");
				if (variable == 'y' || variable == 'Y') {
					newnumber = Helper.readString("Enter new Outlet's contact no >");
				} else if (variable == 'n' || variable == 'N') {
					newnumber = oldnum;
					valid = true;
				}
				
				if (newlocation.equals("") || newnumber.equals("")) {
					System.out.println("Please fill in the fields");
					empty = true;
				} else if (!newnumber.matches("\\+(\\d{1,3}) (\\d{8})")) {
					System.out.println("Please for this format: +XX XXXXXXXX >");
					numcheck = true;
				} else {
					for (Outlets b : list) {
						String contactNo = b.getContactnum();
						if (contactNo.equals(newnumber) && !valid) {
							duplicate = true;
							System.out.println(
									"Duplicate Number found!\nEnsure that the number you are entering is not the same as the old one or is not the same as others.");
						}
					}
				}
			
				if (!duplicate && !empty && !numcheck) {
					
					deleteOutletforupdate(chosenoutlet, list, prodlist, reqlist);
					try {
						File file = new File("OutletList.txt");
						FileWriter fw = new FileWriter(file, true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(
								String.format("%s, %s, %s", chosenoutlet.trim(), newlocation.trim(), newnumber.trim()));
						System.out.println("Outlet successfully updated!");
						bw.newLine();
						bw.close();
						check = true;

					} catch (IOException io) {
						System.out.println("There was an error writing to this file");
					}
				
			}
			 
			} else {
				System.out.println("Invalid Outlet entered!");
				check = true;
			}
		  
		}
	}

	public static void deleteOutletforupdate(String removeoutlet, ArrayList<Outlets> list, ArrayList<Products> prodlist,
			ArrayList<Request> reqlist) {

		boolean empty = false;

		ArrayList<Outlets> newlist = new ArrayList<Outlets>();
		while (!empty) {
			boolean check = false;

			if (removeoutlet.equals("")) {
				System.out.println("Please fill in the outlet field");
			} else {
				try {
					File file = new File("OutletList.txt");
					FileWriter fw = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(fw);
					for (int i = 0; i < list.size(); i++) {
						String outletName = list.get(i).getOutletname();
						if (outletName.equals(removeoutlet)) {
							list.remove(i);
							check = true;
							break;
						}
					}
					if (check) {
						newlist = list;
						for (Outlets a : newlist) {
							bw.write(
									String.format("%s, %s, %s", a.getOutletname(), a.getLocation(), a.getContactnum()));
							bw.newLine();
						}

						bw.close();
					}
				} catch (IOException io) {
					System.out.println("There was an error writing to this file");
				}
				if (!check) {
					System.out.println("Invalid input!");
				} else {
					empty = true;
				}
			}
		}

	}

	public static void deleteOutlet(ArrayList<Outlets> list, ArrayList<Products> prodlist, ArrayList<Request> reqlist) {

		boolean empty = false;

		ArrayList<Outlets> newlist = new ArrayList<Outlets>();
		while (!empty) {
			boolean check = false;
			String removeoutlet = Helper.readString("Enter the outlet you are trying to remove >");

			if (removeoutlet.equals("")) {
				System.out.println("Please fill in the outlet field");

			} else if (!checkOutletAva(removeoutlet, reqlist)) {
				System.out.println();
				System.out.println(
						"Outlet cannot be deleted as it still exists in the return requests section.\nPlease clear the Outlet from any return request to delete it.");
				System.out.println("");
			} else {
				try {
					File file = new File("OutletList.txt");
					FileWriter fw = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(fw);
					for (int i = 0; i < list.size(); i++) {
						String outletName = list.get(i).getOutletname();
						if (outletName.equals(removeoutlet)) {
							list.remove(i);
							check = true;
							break;
						}
					}
					if (check) {
						deleteProductFromOutlet(prodlist, removeoutlet.trim(), reqlist);
						newlist = list;
						for (Outlets a : newlist) {
							bw.write(
									String.format("%s, %s, %s", a.getOutletname(), a.getLocation(), a.getContactnum()));
							bw.newLine();
						}
						System.out.println("Outlet successfully deleted!");
						bw.close();
					}
				} catch (IOException io) {
					System.out.println("There was an error writing to this file");
				}
				if (!check) {
					System.out.println("Invalid input!");
				} else {
					empty = true;
				}
			}
		}

	}

	private static void showproduct(ArrayList<Products> list) {
		Helper.line(200, "-");
		System.out.println(String.format("%-10s %-50s %-20s %-30s %s", "ID","PRODUCTS", "PRICE", "OUTLETS","RETURN POLICY"));
		Helper.line(200, "-");
		for (Products i : list) {
			System.out.println(String.format("%-10d %-50s %-20s %-30s %s", i.getproductID(), i.getName(), i.getPrice(), i.getOutletname(), i.getreturnpolicy()));
		}
		System.out.println("");
	}

	private static void showoutlet(ArrayList<Outlets> list) {
		Helper.line(70, "-");
		System.out.println(String.format("%-30s %-20s %s", "OUTLETS", "LOCATION", "CONTACT NO"));
		Helper.line(70, "-");
		for (Outlets i : list) {
			System.out.println(String.format("%-30s %-20s %s", i.getOutletname(), i.getLocation(), i.getContactnum()));
			Helper.line(70, "-");
		}
		System.out.println("");
	}

	public static boolean checkProductAva(int id, ArrayList<Request> reqlist) {
		boolean check = true;
		for (Request b : reqlist) {

			if(b.getProduct()!=null) {
			if (id==b.getProduct().getproductID() && b.getStatus().equals("Pending")) {
				check = false;

			}
		   }
		}
		return check;
	}

	public static boolean checkOutletAva(String name, ArrayList<Request> reqlist) {
		boolean check = true;
		for (Request b : reqlist) {
			if(b.getProduct() != null) {
			String outletName = b.getProduct().getOutletname();
			if (name.equals(outletName) && b.getStatus().equals("Pending")) {
				check = false;
			}
		  }
		}
		return check;
	}
	// Somethings wrong
	//updated

	// ------------------------------------------------------------------------------------
	public static void saveArrangeProductExchange(ArrayList<Request> requestList, int RequestNo, String arrange) {

		boolean isfound = false;

		for (Request i : requestList) {
			if (RequestNo == i.getRequestID()) {
				isfound = true;
				boolean checkStatusApprove = i.getStatus().equalsIgnoreCase("Approved");
				if (checkStatusApprove) {
                    boolean checkValidReason = i.getReasonForReturn().equalsIgnoreCase("Product not as expected")|i.getReasonForReturn().equalsIgnoreCase("Defective or damaged product");
					if(checkValidReason){
					LocalDate date = LocalDate.parse(arrange);
					LocalDate currentDate = LocalDate.now();
					if (date.compareTo(currentDate) > 0) {
						i.setArrangeDate(arrange);
						try {
							File file = new File("ArrangeProductExchange.txt");
							FileWriter bw = new FileWriter(file);
							BufferedWriter fw = new BufferedWriter(bw);

							String output = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", i.getAccount(),
									i.getRequestID(), i.getName(), i.getEmailAddress(),
									String.valueOf(i.getContact_number()),i.getSerialNumber(), String.valueOf(i.getInvoiceNumber()), i.getReasonForReturn(),
									i.getStatus(), i.getProduct().getName(),i.getProduct().getOutletname(),arrange);
							bw.write(output);
							System.out.println("Date for Exchange Arranged Successfully !");
							bw.close();
							fw.close();
						} catch (IOException io) {
							System.out.println("Error occured while saving arrange product exchange to system!");
						}
					} else {
						System.out.println("Invalid Date");
					}
                    }else  {System.out.println("Reason is invalid for product exchange!");}

				} else if (!checkStatusApprove) {
					System.out.println("The Request does not approved");
				}
			}
		}
		if (isfound == false) {
			System.out.println("Invalid RequestID");
		}
	}
	public static ArrayList<Request> loadDateArrange() {

	    ArrayList<Request> dateArrangeList = new ArrayList<>();
	    ArrayList<Products> productList = new ArrayList<>();

	    try {
	        File file = new File("ArrangeProductExchange.txt");
	        FileReader fr = new FileReader(file);
	        BufferedReader br = new BufferedReader(fr);

	        String line = br.readLine();
	        while (line != null) {

	            String[] values = line.split(",");

	            int account = Integer.parseInt(values[0].trim());
	            int requestID = Integer.parseInt(values[1].trim());
	            String customer_Name = values[2].trim();
	            String customer_EmailAddress = values[3].trim();
	            int customer_ContactNumber = Integer.parseInt(values[4].trim());
	            String serialNumber = values[5].trim();
	            int invoiceNumber = Integer.parseInt(values[6].trim());
	            String reasonForReturn = values[7].trim();
	            String status = values[8].trim();
	            String productName = values[9].trim();
	            String outletName = values[10].trim();
	            String arrangeDate = values[11].trim();
           
	            
	            Products product = null;
	            for (Products p : productList) {
	                boolean checkOutlet = productName.equalsIgnoreCase(p.getName()) && outletName.equalsIgnoreCase(p.getOutletname());
					if (checkOutlet) {
	                    product = p;
	                    break;
	                }
	            }

	            dateArrangeList.add(new Request(account, requestID, customer_Name, customer_EmailAddress,
	                    customer_ContactNumber, serialNumber, invoiceNumber, reasonForReturn, status, product,
	                    arrangeDate));

	            line = br.readLine(); 
	        }

	        br.close();
	        fr.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException io) {
	        io.printStackTrace();
	    }
	    return dateArrangeList;
	}




	public static void arrangeProductExchange() {

		try {
			File file = new File("ArrangeProductExchange.txt");
			FileWriter bw = new FileWriter(file);
			BufferedWriter fw = new BufferedWriter(bw);

			bw.close();
			fw.close();
		} catch (IOException io) {
			System.out.println("Error occured while creating the txt file to system!");
		}

	}

	// ------------------------------------------------------------------------------------
	// Courier Sprint 1 Task (Anson Tan)
	// This method allows a user to request a courier pickup for an approved return request.
	// It takes the request list, courier list, customer's name, contact number, and address as parameters.
	  // Constant to represent the "Approved" status
	private static final String STATUS_APPROVED = "Approved";

	// Display header for the Request Courier Pickup section
	private static void displayRequestCourierHeader() {
		Helper.line(70, "-");
		System.out.println("REQUEST COURIER PICKUP");
		Helper.line(70, "-");
	}

	// Find a request by its ID in the list
	private static Request findRequestByID(ArrayList<Request> requestList, int requestID) {
		for (Request request : requestList) {
			if (request.getRequestID() == requestID) {
				return request;
			}
		}
		return null;
	}

	// Create and add a new courier to the list
	private static void createAndAddCourier(ArrayList<Courier> courierList, String name, String contactNumber,
			String address, int requestID) {
		Courier courier = new Courier(name, contactNumber, address, requestID);
		courierList.add(courier);
		System.out.println("Courier pickup requested successfully.");
	}

	// Method to request a courier pickup
	static void requestCourierPickup(ArrayList<Request> requestList, ArrayList<Courier> courierList, String name,
			String contactNumber, String address) {
		System.out.println();
		displayRequestCourierHeader();

		// Read the request ID from the user
		int requestID = Helper.readInt("Enter Request ID for Courier Pickup > ");
		Request request = findRequestByID(requestList, requestID);

		// Check if the request is found and if it is approved
		if (request != null && request.getStatus().equalsIgnoreCase(STATUS_APPROVED)) {
			// If approved, create and add a courier
			createAndAddCourier(courierList, name, contactNumber, address, requestID);
		} else if (request != null) {
			// Display a message if the request is not approved
			System.out.println("Courier pickup can only be requested for approved return requests.");
		} else {
			// Display a message if the request is not found
			System.out.println("No request found with the given Request ID.");
		}
	}

	// Method to load courier data from a file
	private static ArrayList<Courier> loadCourierList() {
		ArrayList<Courier> courierList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("courier_data.txt"))) {
			String line;
			// Read each line from the file
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 4) {
					// Extract information from the line and create a new Courier object
					String name = parts[0].trim();
					String contactNumber = parts[1].trim();
					String address = parts[2].trim();
					int returnReqID = Integer.parseInt(parts[3].trim());

					Courier courier = new Courier(name, contactNumber, address, returnReqID);
					courierList.add(courier);
				}
			}
		} catch (IOException e) {
			// Handle the exception appropriately (e.g., log the error, show an error
			// message)
			System.err.println("Error loading courier data: " + e.getMessage());
		}

		return courierList;
	}

}
