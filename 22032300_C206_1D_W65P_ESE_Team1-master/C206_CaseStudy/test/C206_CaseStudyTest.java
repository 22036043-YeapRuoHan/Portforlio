import static org.junit.Assert.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class C206_CaseStudyTest {
	private Products product1;
	private Products product2;
	private Outlets one;
	private Outlets two;
	
	private ArrayList<Products> prodlist;
	private ArrayList<Outlets> outlist;
	private ArrayList<Request> reqlist;
	@Before
	
	public void setUp() throws Exception {
		ArrayList<User> userList = CRiTS.loadUser(); //load data from UserDatabase.txt to userList
		product1 = new Products(999,"test1", 0.01, "MyKey","Test policy");
		one = new Outlets("MyKey","America", "+65 90123033");
		
		prodlist = CRiTS.loadprod();
		outlist = CRiTS.loadoutl();
        reqlist = CRiTS.loadReturnRequests();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void c206_test() {
		//fail("Not yet implemented"); 
		assertTrue("C206_CaseStudy_SampleTest ",true);
	}
	
	@Before
	public void setUpEmailRefundVoucher() throws Exception {
		File file = new File("Voucher.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write("");//reset database
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	
	}
	@Test
	public void testValidEmailRefundVoucher1() {
		LocalDate date = LocalDate.of(2023, 12, 12);
		ArrayList<Request>requestList=new ArrayList<Request>();
	    Request request1 = new Request(16, 12,"jake","jake@gmail.com",800,"ddd",333,"broke","Approved" ,product1,date);
	    requestList.add(request1);
		 

	    assertEquals("Sent to email address:jake@gmail.com\n"+"RequestID:12\n"+"Product:test1\n"+
	    "Dear jake,\n"+"Here is your refund voucher worth $0.01\n"+"we hope you had a great experience shopping!",
	    (CRiTS.EmailRefundVoucher(requestList,12)));
		boolean addedtoDatabase = false;
		try {
			File file = new File("Voucher.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("1,12,16")) {
					addedtoDatabase=true;
					assertTrue(addedtoDatabase);
				}
		
			}
		
			br.close();
			fr.close();
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
		 //unit test case 2
	@Test
	public void testValidEmailRefundVoucher2() {
		LocalDate date = LocalDate.of(2023, 12, 12);
		ArrayList<Request>requestList=new ArrayList<Request>();
		 Request request2 = new Request(17, 13,"mo","mo@gmail.com",802,"ddd",332,"broke","Approved" ,product1,date);
		 requestList.add(request2);
	    assertEquals("Sent to email address:mo@gmail.com\n"+"RequestID:13\n"+"Product:test1\n"+
		 "Dear mo,\n"+"Here is your refund voucher worth $0.01\n"+"we hope you had a great experience shopping!",
	    		(CRiTS.EmailRefundVoucher(requestList,13)));
		boolean addedtoDatabase2 = false;
		
		try {
			File file = new File("Voucher.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("2,13,17")) {
					addedtoDatabase2=true;
					assertTrue(addedtoDatabase2);
				}
		
			}
		
			br.close();
			fr.close();
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	@Test
	public void testUnapprovedRequestID_EmailRefundVoucher() {
		LocalDate date = LocalDate.of(2023, 12, 12);
		ArrayList<Request>requestList=new ArrayList<Request>();
	    Request request3 = new Request(18, 14,"ko","ko@gmail.com",803,"ddd",331,"broke","Pending" ,product1,date);
	    requestList.add(request3);
		 
		
	    assertEquals("The Request is not approved",(CRiTS.EmailRefundVoucher(requestList,14)));
	    boolean addedtoDatabase3 = false;
		try {
			File file = new File("Voucher.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("14,18")) {
					addedtoDatabase3=true;
					
				}
		
			}
		assertFalse(addedtoDatabase3);
			br.close();
			fr.close();
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	@Test
	public void testinvalidRequestID_EmailRefundVoucher() {
		
		ArrayList<Request>requestList=new ArrayList<Request>();
	    assertEquals("Invalid RequestID",(CRiTS.EmailRefundVoucher(requestList,33)));
	    boolean addedtoDatabase4 = false;
		try {
			File file = new File("Voucher.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("33")) {
					addedtoDatabase4=true;
					
				}
		
			}
		assertFalse(addedtoDatabase4);
			br.close();
			fr.close();
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	

	@Before
	public void setUptestRegisterAccount() throws Exception {
		
			File file = new File("UserDatabase.txt");
			FileWriter fw;
			try {
				fw = new FileWriter(file);
				fw.write("");//reset database
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	 
		}
	@Test
	public void testRegisterValidAccount() {
		assertEquals("[registered succesfully]",
				(CRiTS.RegisterAccount("test1","test1@123","test1@gmail.com","address1", 8000001)));
		boolean addedtoDatabase = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("1,test1,test1@123,Customer,test1@gmail.com,address1,8000001")) {
					addedtoDatabase=true;
					assertTrue(addedtoDatabase);
				}
		
			}
			br.close();
			fr.close();
		}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
	@Test 
	public void testRegisterTakenAccount() {
	CRiTS.RegisterAccount("test1","test1@123","test1@gmail.com","address1", 8000001);
		assertEquals("[Registration error]\n"+"email already registered\n"+ "mobile number already registered\n"+"password already taken\n",
				(CRiTS.RegisterAccount("test1","test1@123","test1@gmail.com","address1", 8000001)));
		boolean addedtoDatabase2 = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("2,test1,test1@123,Customer,test1@gmail.com,address1,8000001")) {
					addedtoDatabase2=true;
					
				}
		
			}
		assertFalse(addedtoDatabase2);
			br.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testValidateEmail() {
	
		assertEquals("[Registration error]\n"+"email format is wrong\n",
				(CRiTS.RegisterAccount("test3","test3@123","test3","address3", 8000003))); 
		boolean addedtoDatabase3 = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("test3,test3@123,Customer,test3,address3,8000003")) {
					addedtoDatabase3=true;
					
				}
		
			}
		assertFalse(addedtoDatabase3);
			br.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRegisterBlankFields() {
		assertEquals("[Registration error]\n"+"not all fields filled in\n",
				(CRiTS.RegisterAccount("","", "","", 8000004)));
		boolean addedtoDatabase4 = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("8000004")) {
					addedtoDatabase4=true;
					
				}
		
			}
		assertFalse(addedtoDatabase4);
			br.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testPasswordLowerBoundary() {
		assertEquals("[registered succesfully]",
				(CRiTS.RegisterAccount("test5","test5@12","test5@gmail.com", "address5",8000005)));
		
		boolean addedtoDatabase5 = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("2,test5,test5@12,Customer,test5@gmail.com,address5,8000005")) {
					addedtoDatabase5=true;
					assertTrue(addedtoDatabase5); 
				}
		
			}
			br.close();
			fr.close();
		}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
	@Test
	public void testPasswordUpperBoundary() {
		assertEquals("[registered succesfully]",
				(CRiTS.RegisterAccount("test6","kkooiiiiuuuu7777hhh6666#####fvgg222222jjjjjnnnnnn222255555777788","test6@gmail.com","address6", 8000006)));
		boolean addedtoDatabase6 = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("3,test6,kkooiiiiuuuu7777hhh6666#####fvgg222222jjjjjnnnnnn222255555777788,Customer,test6@gmail.com,address6,8000006")) {
					addedtoDatabase6=true;
					assertTrue(addedtoDatabase6);
				}
		
			}
			br.close();
			fr.close();
		}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
	@Test
	public void testPasswordZeroNumbers() {
		assertEquals("[Registration error]\n"+"password requirements not met(minimum 8 characters, maximum 64 characters, minimum of 1 number and 1 alphabet)\n",
				(CRiTS.RegisterAccount("test7","quertyuio","test7@gmail.com","address7", 8000007)));
		boolean addedtoDatabase7 = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("test7,quertyuio,Customer,test7@gmail.com,address7,8000007")) {
					addedtoDatabase7=true;
					
				}
		
			}
		assertFalse(addedtoDatabase7);
			br.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testPasswordZeroAlphabets() {
		assertEquals("[Registration error]\n"+"password requirements not met(minimum 8 characters, maximum 64 characters, minimum of 1 number and 1 alphabet)\n",
				(CRiTS.RegisterAccount("test8","123456789","test8@gmail.com","address8", 8000008)));
		boolean addedtoDatabase8 = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("test8,123456789,Customer,test8@gmail.com,address8,8000008")) {
					addedtoDatabase8=true;
					
				}
		
			}
		assertFalse(addedtoDatabase8);
			br.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testPasswordAboveMaxCharacters() {
		assertEquals("[Registration error]\n"+"password requirements not met(minimum 8 characters, maximum 64 characters, minimum of 1 number and 1 alphabet)\n",
				(CRiTS.RegisterAccount("test9","kkooiiiiuuuu7777hhh6666#####fvgg222222jjjjjnnnnnn2222555557777888","test9@gmail.com","address9", 8000009)));
		boolean addedtoDatabase9 = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("test9,kkooiiiiuuuu7777hhh6666#####fvgg222222jjjjjnnnnnn2222555557777888,Customer,test9@gmail.com,address9,8000009")) {
					addedtoDatabase9=true;
					
				}
		
			}
		assertFalse(addedtoDatabase9);
			br.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testPasswordBelowMinCharacters() {
		assertEquals("[Registration error]\n"+"password requirements not met(minimum 8 characters, maximum 64 characters, minimum of 1 number and 1 alphabet)\n",
				(CRiTS.RegisterAccount("test10","test@10","test10@gmail.com","address10", 8000010)));
		boolean addedtoDatabase10 = false;
		try {
			File file = new File("UserDatabase.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("test10,test@10,Customer,test10@gmail.com,address10,8000010")) {
					addedtoDatabase10=true;
					
				}
		
			}
		assertFalse(addedtoDatabase10);
			br.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
@After

public void tearDownRegisterAccount() throws Exception {

		File file = new File("UserDatabase.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write("");//reset database
		} catch (IOException e) {
			e.printStackTrace();
		}
	
 
	}

@Test

public void testLoginValid() {
	User loggedInUser=null;
	CRiTS.attempts=0;
		CRiTS.RegisterAccount("test1","test1@123","test1@gmail.com","address1", 8000001);
		loggedInUser = CRiTS.login(loggedInUser,"test1", "test1@123");
		

		
		assertNotNull(loggedInUser);//checks user is logged in
		//checks correct account is logged in
		assertEquals(1,loggedInUser.getAccount());
		assertEquals("test1", loggedInUser.getName());
		assertEquals("test1@123",loggedInUser.getPassword());
		assertEquals("Customer", loggedInUser.getRole());
		assertEquals("test1@gmail.com", loggedInUser.getEmail());
		assertEquals("address1", loggedInUser.getHomeAddress());
		assertEquals(8000001, loggedInUser.getMobile());
}
@Test
public void testLoginInvalid() {
		//unit test case 2// follow instructions for input
	CRiTS.attempts=0;
		User loggedInUser=null;
		loggedInUser = CRiTS.login(loggedInUser,"invalidName", "invalidPassword");
		assertNull(loggedInUser);//check user not logged in
		assertEquals(CRiTS.attempts,3);//check upper boundary
}
@Test
public void testLoginBlankFields() {
		//unit test case 3// follow instructions for input
		User loggedInUser=null;
		CRiTS.attempts=0;
		loggedInUser = CRiTS.login(loggedInUser,"", "");
		assertNull(loggedInUser);//check user not logged in
		assertEquals(CRiTS.attempts,3);//check upper boundary 
		loggedInUser=null;

}

		
		
		//Faiz User Story ID 13
		@Test
		public void test51CharInputOtherReason() {
			ArrayList<Request> rList = new ArrayList<Request>(); //Create an empty list for Request objects
			ArrayList<Products> pList= CRiTS.loadprod(); //Create a list a load it with products from product.txt database
			User user = new User(1,"c","pass","Customer","don@gmail.com","homee",12345678); //Create new user object
			assertNull(CRiTS.submitReturnRequest(user, rList, pList)); //Ensure that when other reason is typed and the character count is 51, the return request will return null
		}
		
		// Faiz User Story ID 13
		@Test
		public void testInvalidProductName() {
			String output = CRiTS.promptProductName(); //output is set to a Product that is not in the product.txt database
			assertNull(output); //Ensure that output is null
		}
		
		// Faiz User Story ID 13
		@Test
		public void testValidProductName() {
			String expectedOutput = "Nike Airmax"; //Set expected output to the name of a product that is in the product.txt database
			String output = CRiTS.promptProductName(); //Prompt for valid product name
			assertNotNull(output); //Ensure that output is not null
			assertEquals(expectedOutput, output); //Ensure that output and expectedOutput are equal
		}
		
		// Faiz User Story ID 13
		@Test
		public void testValidPurchaseDate() {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //Formatter is set to the same format used in submit return request
			String expectedOutput = "12-02-2023"; //expected output is set to a valid purchase date
			LocalDate outputDate = CRiTS.promptPurchaseDate(); //prompt for purchasedate
			String output = formatter.format(outputDate); //Convert purchasedate from LocalDate object to String for comparison
			assertEquals(expectedOutput, output); //Ensure that ouput and expected output are equal
		}
		
		// Faiz User Story ID 13
		@Test
		public void testInvalidPurchaseDate() {
			LocalDate outputDate = CRiTS.promptPurchaseDate(); //Prompt for invalid purchase date
			assertNull(outputDate); //Ensure that outputDate is null
		}
		
		//Faiz User Story ID 13
		@Test
		public void testSubmitReturnRequest() {
			File file = new File("ReturnRequests.txt"); //Create ReturnRequests.txt database
			FileWriter fw;
			try {
				fw = new FileWriter(file);
				fw.write("");
			} catch (IOException e) {
				e.printStackTrace();
			}
			ArrayList<Request> rList = new ArrayList<Request>(); //Create return request list
			ArrayList<Products> pList = CRiTS.loadprod(); //Create Products list and load with Products from products.txt database
			User user = new User(1,"c","pass","Customer","don@gmail.com","homee",12345678); //Create new user object
			Request r1 = null; 
			r1 = CRiTS.submitReturnRequest(user, rList, pList);
			assertNotNull(r1); //Ensure that r1 is not null meaning it is a valid return request
			rList.add(r1);
			CRiTS.saveReturnRequests(rList);
			ArrayList<Request> rList2 = CRiTS.loadReturnRequests();
			Request r2 = rList2.get(0);
			assertEquals(r1.getAccount(),r2.getAccount()); //Ensure that each field is equal to the other object
			assertEquals(r1.getRequestID(),r2.getRequestID());
			assertEquals(r1.getName(),r2.getName());
			assertEquals(r1.getEmailAddress(),r2.getEmailAddress());
			assertEquals(r1.getContact_number(),r2.getContact_number());
			assertEquals(r1.getProduct().getName(),r2.getProduct().getName());
			assertEquals(r1.getSerialNumber(),r2.getSerialNumber());
			assertEquals(r1.getInvoiceNumber(),r2.getInvoiceNumber());
			assertEquals(r1.getReasonForReturn(),r2.getReasonForReturn());
			assertEquals(r1.getStatus(),r2.getStatus());
			assertEquals(r1.getpurchaseDate(), r2.getpurchaseDate());
		}
		
		// Faiz User Story ID 13
		@Test
		public void testInvalidSerialNumber() {
			String serialNumber = CRiTS.promptSerialNumber(); //prompt for serial number
			assertNull(serialNumber); //Ensure that a null object return due to invalid serial number
		}
		
		// Faiz User Story ID 13
		@Test
		public void testValidSerialNumber() {
			String expectedSerialNumber = "S900A"; //Expected output
			String serialNumber = CRiTS.promptSerialNumber(); //prompt for serial number
			assertNotNull(serialNumber); //Ensure that a valid serial number has been instantiated
			assertEquals(expectedSerialNumber, serialNumber); //Ensure that both expected and actual output are the same
		}
		
		// Faiz User Story ID 13
		@Test
		public void testInvalidInvoiceNumber() {
			int expectedOutput = 0; //Expected output as any invalid Invoice Number will be set to 0
			int output = CRiTS.promptInvoiceNumber(); //Prompt for invoice number
			assertEquals(expectedOutput, output); //Ensure that output is 0
		}
		
		// Faiz User Story ID 13
		@Test
		public void testValidInvoiceNumber() {
			int expectedOutput = 123; //Expected output
			int output = CRiTS.promptInvoiceNumber(); //prompt for output
			assertEquals(expectedOutput, output); //Ensure that expected output and output are equal
		}
		
		//Faiz User Story ID 14
		@Test
		public void testCustomerCheckReturnRequestNormal() {
		reqlist = new ArrayList<Request>(); //Create new ArrayList
		product1 = new Products(999,"test1", 0.01, "MyKey","Test policy"); //Create new Product object as Request object needs it
		LocalDate purchaseDate = LocalDate.now(); //Set the purchase date to the current date for simplicity
		Request r = new Request(1, 1, "Faiz", "Faiz@Gmail.com", 12345678, "S900N", 123, "Product not as expected", product1, purchaseDate); //Create a Request Object
		reqlist.add(r); //Add request object to the reqlist ArrayList
		String output = CRiTS.displayReturnRequests(reqlist, 1); //Use method and capture the output into a String
		String expectedOutput = "1          test1                                    S900N         123            Product not as expected                            Pending\n"; //Expected Output based on String Format
		System.out.println(output);
		assertEquals(expectedOutput, output);
		}
		
		//Faiz User Story ID 14
		@Test
		public void testCustomerCheckReturnRequestError() {
		reqlist = new ArrayList<Request>(); //Create new ArrayList
		assertEquals(0, reqlist.size()); //Verify that ArrayList created is empty
		String output = CRiTS.displayReturnRequests(reqlist, 1); //Put output into a String
		String expectedOutput = ""; //Expected output should be empty
		assertEquals(expectedOutput, output); //Assert that output and expected output are equal
		}
		
		//Faiz User Story ID 15
		@Test 
		public void testSearchRequestIDOutOfBounds() {
		reqlist = new ArrayList<Request>(); //Create new ArrayList
		product1 = new Products(999,"test1", 0.01, "MyKey","Test policy"); //Create new Product object as Request object needs it
		LocalDate purchaseDate = LocalDate.now(); //Set the purchase date to the current date for simplicity
		Request r = new Request(1, 1, "Faiz", "Faiz@Gmail.com", 12345678, "S900N", 123, "Product not as expected", product1, purchaseDate); //Create a Request Object
		reqlist.add(r); //Add request object to the reqlist ArrayList
		int requestID = reqlist.size() + 1;
		String output = CRiTS.retrieveReturnRequestFilterRequestID(reqlist, requestID);
		String expectedOutput = "";
		assertEquals(output, expectedOutput);
		}

		//Faiz User Story ID 15
		@Test 
		public void testSearchRequestIDError() {
		reqlist = new ArrayList<Request>(); //Create new ArrayList
		product1 = new Products(999,"test1", 0.01, "MyKey","Test policy"); //Create new Product object as Request object needs it
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String date = "12-02-2023";
		LocalDate purchaseDate = LocalDate.parse(date, formatter); //Set the purchase date to the current date for simplicity
		Request r = new Request(1, 1, "Faiz", "Faiz@Gmail.com", 12345678, "S900N", 123, "Product not as expected", product1, purchaseDate); //Create a Request Object
		reqlist.add(r); //Add request object to the reqlist ArrayList
		int requestID = -99;
		String output = CRiTS.retrieveReturnRequestFilterRequestID(reqlist, requestID);
		String expectedOutput = "";
		assertEquals(output, expectedOutput);
		}
		
		//Faiz User Story ID 15
		@Test 
		public void testSearchRequestIDNormal() {
		reqlist = new ArrayList<Request>(); //Create new ArrayList
		product1 = new Products(999,"test1", 0.01, "MyKey","Test policy"); //Create new Product object as Request object needs it
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //Create a DateTimeFormatter object to format date
		String date = "12-02-2023"; //Create a date test data
		LocalDate purchaseDate = LocalDate.parse(date, formatter); //Create LocalDate object
		Request r = new Request(1, 1, "Faiz", "Faiz@Gmail.com", 12345678, "S900N", 123, "Product not as expected", product1, purchaseDate); //Create a Request Object
		reqlist.add(r); //Add request object to the reqlist ArrayList
		int requestID = 1;
		String output = CRiTS.retrieveReturnRequestFilterRequestID(reqlist, requestID);
		String expectedOutput = "1          1          999         Faiz                        test1                 MyKey            S900N         123            Product not as expected                            Pending    12-02-2023\n";
		assertEquals(expectedOutput, output);
		}
		
		@Test
		public void testsaveArrangeProductExchange() {
			// Invalid ID
			
			ArrayList<Request> arrangeList = new ArrayList<Request>();
			
			int RequestNo = 4;
			String arrange = "2023-12-20";

			CRiTS.saveArrangeProductExchange(reqlist, RequestNo, arrange);
			arrangeList=CRiTS.loadDateArrange();
			assertTrue(arrangeList.isEmpty());
			

			// invalid reason

			String arrange3 = "2023-12-20";
			
			
			int RequestNo3 = 3;

			CRiTS.saveArrangeProductExchange(reqlist, RequestNo3, arrange3);
			arrangeList=CRiTS.loadDateArrange();
			assertTrue(arrangeList.isEmpty());
			
			//boundary
			
			LocalDate date = LocalDate.now().minusDays(1);
			int RequestNo4 = 1;
			String dateString = date.toString();
			
            
			CRiTS.saveArrangeProductExchange(reqlist, RequestNo4, dateString);
			arrangeList=CRiTS.loadDateArrange();
			assertTrue(arrangeList.isEmpty());
			
		    // normal
			
			int RequestNo2 = 1;
			String arrange2 = "2023-12-20";
            
			CRiTS.saveArrangeProductExchange(reqlist, RequestNo2, arrange2);
			arrangeList=CRiTS.loadDateArrange();
			assertEquals(1, arrangeList.size());
		}
		
		@Test //Story ID 11, Jordan Tong
		public void testCustomerAcceptance() {
	        reqlist = CRiTS.loadReturnRequests();
			LocalDate date = LocalDate.of(2023, 12, 12);
		    Request request1 = new Request(14,15,"Jane","Jane@gmail.com",801,"dda",331,"broke","Pending",product1,date);
		    reqlist.add(request1);
		    
		    CRiTS.CustomerSPGetRequest(reqlist);
		    
		    assertEquals("Approved",request1.getStatus());
		}

		@Test //Story ID 11, Jordan Tong
		public void testCustomerDenied() {
	        reqlist = CRiTS.loadReturnRequests();
			LocalDate date = LocalDate.of(2023, 12, 12);
		    Request request1 = new Request(14,14,"John","John@gmail.com",802,"dda",332,"broke","Pending",product1,date);
		    reqlist.add(request1);
		    
		    CRiTS.CustomerSPGetRequest(reqlist);
		    
		    assertEquals("Denied",request1.getStatus());
		}
		@Test //Story ID 11, Jordan Tong
		public void testCustomerError() {
	        reqlist = CRiTS.loadReturnRequests();
			LocalDate date = LocalDate.of(2023, 12, 12);
		    Request request1 = new Request(16, 12,"jake","jake@gmail.com",800,"ddd",333,"broke","---" ,product1,date);
		    reqlist.add(request1);
		    
		    CRiTS.CustomerSPGetRequest(reqlist);
		    
		    assertFalse(request1.getStatus().equals("Pending")||request1.getStatus().equals("Dendied")||request1.getStatus().equals("Approved"));	
		}
		@After
		public void tearDownCustomerRequest() throws Exception{
			reqlist.clear();
		}
		
		
		 @Test // Story ID 9 (Anson Tan)
		    public void testRequestCourierPickup_SuccessfulPickup() {
		        ArrayList<Request> requestList = new ArrayList<>();
		        ArrayList<Courier> courierList = new ArrayList<>();

		        // Prepare an approved request
		        Request approvedRequest = new Request(0, 0, null, null, 0, null, 0, null, product1, null);
		        approvedRequest.setStatus("Approved");
		        requestList.add(approvedRequest);

		        // Prepare user input for courier pickup
		        String name = "Alice Johnson";
		        String contactNumber = "5555555555";
		        String address = "789 Oak Ave.";

		        // Call the method
		        CRiTS.requestCourierPickup(requestList, courierList, name, contactNumber, address);

		        // Verify that a courier is added
		        assertEquals(1, courierList.size());
		        assertEquals(approvedRequest.getRequestID(), courierList.get(0).getReturnReqID());
		    }
		   @Test //Story ID 9 (Anson Tan)
		    public void testRequestCourierPickup_InvalidInformation() {
		        // Similar to the previous invalid information test, but using Constructor Variant 1
		        ArrayList<Request> requestList = new ArrayList<>();  
		        ArrayList<Courier> courierList = new ArrayList<>();

		        // Prepare an approved request
		        Request approvedRequest = new Request(0, 0, null, null, 0, null, 0, null, product1, null);
		        approvedRequest.setStatus("Approved");
		        requestList.add(approvedRequest);

		        // Prepare user input with incorrect contact number format
		        String name = "Alice Johnson";
		        String contactNumber = "5555555555"; // Valid format
		        String address = "789 Oak Ave.";

		        // Call the method
		        CRiTS.requestCourierPickup(requestList, courierList, name, contactNumber, address);

		        // Verify that no courier is added
		        assertEquals(0, courierList.size());
		    }
		

		@Test //Story ID 9 (Anson Tan)
		public void testRequestCourierPickup_RequestNotFound() {
		    ArrayList<Request> requestList = new ArrayList<>();
		    ArrayList<Courier> courierList = new ArrayList<>();

		    // Prepare an empty request list

		    // Prepare user input for courier pickup
		    String name = "Alice Johnson";
		    String contactNumber = "5555555555";
		    String address = "789 Oak Ave.";

		    // Call the method
		    CRiTS.requestCourierPickup(requestList, courierList, name, contactNumber, address);

		    // Verify that no courier is added
		    assertEquals(0, courierList.size());
		}
		
		//TYJ-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		@Test
		 public void testaddproduct() {
			  System.out.println("Add 999,test1, 0.01, MyKey, Test policy");
		      assertNotNull("Valid arraylist?",prodlist);
		      CRiTS.addProduct(prodlist, outlist);
		      prodlist = CRiTS.loadprod();
		      assertEquals("Check if the product added is the same as the one we have recently added.",prodlist.get(prodlist.size()-1).getName(),product1.getName());
		 }
		  @Test
		  public void testboundaryprice() {
			  boolean check = false;
			  prodlist = CRiTS.loadprod();
			  if(prodlist.get(prodlist.size()-1).getPrice()==0.01) {
				  check = true;
			  }
			  assertTrue("Check if the test product added has 0.01. This means that 0.01 is the min amount we can set for a product's price. Anything equal or below 0 is false and rejected",check);
		  }
		  //Continue from addproduct to finish the entire test for add and delete product
		  
		  @Test
	      public void testdeleteproduct() {
			  prodlist = CRiTS.loadprod();
		      int oldsize = prodlist.size();
		      int checknowsize = oldsize - 1;
		      CRiTS.deleteProduct(prodlist,reqlist);
		      prodlist = CRiTS.loadprod();
		      assertEquals("Check that the size of arraylist is different after deleting a product",checknowsize, prodlist.size());
		      
	   }
		
         @Test
		 public void testcantbedeleted() {
       	 System.out.println("For this to work, ensure to add a return request for this product");
			 int productid = 999;
			 Boolean itsbeingused = CRiTS.checkProductAva(productid, reqlist);
			 assertFalse("If its false, that means a product is being used, and it cannot be deleted. This method is used to check for such events",itsbeingused);
		 }
		
		@Test public void testaddoutlet() {
			System.out.println("enter MyKey, America, +65 90123033");
			CRiTS.addOutlet(outlist);
			outlist = CRiTS.loadoutl();
			assertEquals("Check if the outlet added is the same as the one we have recently added.", outlist.get(outlist.size()-1).getOutletname(),one.getOutletname());
		}
		
		@Test public void testdeleteoutlet() {
			outlist = CRiTS.loadoutl();
		    int expectedsize = outlist.size() - 1;
		    CRiTS.deleteOutlet(outlist, prodlist, reqlist);
		    assertEquals("Check that the size of the arraylist is different after deleting a product", expectedsize, outlist.size());
		}
		@Test
		public void testcantbedeletedoutletedition() {
			System.out.println("For this to work, ensure to add a return request for this outlet");
			 String outlet = "MyKey";
		     Boolean itsbeingused = CRiTS.checkOutletAva(outlet, reqlist);
		     assertFalse("If its false, that means an outlet is being used, and it cannot be deleted. This method is used to check for such events", itsbeingused);
		     
		}
		@Test
		public void testnumberregex() {
			String testnum = Helper.readString("Enter a invalid phone number >");
		    assertFalse("Should display false as it does not meet the regex requirements. This pattern is used in my methods for the demo software",testnum.matches("\\+(\\d{1,3}) (\\d{8})"));
			}
			

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
}





		
		
	
	


