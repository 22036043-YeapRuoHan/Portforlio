import java.util.ArrayList;

/*
 * I declare that this code was written by me. 
 * I do not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: 
 * Student ID:
 * Class:
 * Date/Time Last modified:
 */

public class WardManagement {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Advance visitorList
		ArrayList<Visitor> visitorList = new ArrayList<Visitor>();

		// initialise Ward array with ward objects
		Ward[] wardArr = new Ward[4];

		// -------------------
		// Complete code here
		// -------------------
		Ward ward1 = new Ward("A", "1 Bed, attached bath/toilet", 10, 535.00);
		Ward ward2 = new Ward("B1", "4 Bed, attached bath/toilet", 20, 266.43);
		Ward ward3 = new Ward("B2", "6 Bed, common bath/toilet", 20, 83.00);
		Ward ward4 = new Ward("C", "8 Bed, common bath/toilet", 50, 37.00);
		wardArr[0] = ward1;
		wardArr[1] = ward2;
		wardArr[2] = ward3;
		wardArr[3] = ward4;

		// initialise Patient arraylist with patient objects
		ArrayList<Patient> patientList = new ArrayList<Patient>();

		// -------------------
		// Complete code here
		// -------------------
		patientList.add(new Patient("111A", "John Lee", "A", 2, "01/12/2022"));
		patientList.add(new Patient("222B", "Mary Jane", "B1", 11, "02/12/2022"));
		patientList.add(new Patient("333C", "Abdul Musri", "B1", 12, "03/12/2022"));
		patientList.add(new Patient("444D", "Jane Tan", "B2", 2, "12/12/2022", "", 3));
		patientList.add(new Patient("444D", "Paul Tan", "C", 2, "02/11/2022", "", 4));
		patientList.add(new Patient("666F", "Paul Ng", "C", 3, "03/11/2022", "09/11/2022", 0));
		patientList.add(new Patient("777G", "Wong Kuan", "C", 4, "02/12/2022"));

		// display standard menu and ask for option
		int option = -99;
		publicMenu();

		// indefinite while loop
		while (option != 11) {
			boolean patientfound = true;
			option = Helper.readInt("\nEnter option or 0 for main menu > ");

			// check for options
			if (option == 0) {
				// display main menu
				publicMenu();
			} else if (option == 1) {
				// list ward info
				displayWardInfo(wardArr);
			} else if (option == 2) {
				// display patient in ward
				displayPatientList(patientList);
			} else if (option == 3) {
				// admit patient
				admitPatient(patientList);
			} else if (option == 4) {
				// discharged patient
				patientfound = dischargePatient(patientList);
			} else if (option == 5) {
				// Remove patient visit
				patientfound = removePatient(patientList);
			} else if (option == 6) {
				// register visit
				patientfound = registerVisit(patientList, visitorList);
			} else if (option == 7) {
				// End visit
				patientfound = endVisit(patientList);
			} else if (option == 8) {
				// End visit
				displayWardOverview(patientList, wardArr);
			} else if (option == 9) {
				// search visitor by patient name
				searchByPatientName(visitorList);
			} else if (option == 10) {
				// search visitor by date
				searchByDate(visitorList);
			} else if (option == 11) {
				// log out
				System.out.println("\nGood bye!");
			} else {
				// invalid option chosen
				System.out.println("\n*** Invalid option selected ***\n");
			}

			// if patient does not exist based on return boolean
			if (!patientfound) {
				System.out.println("\n*** No such patient in ward ***\n");
			}

		}

	} // end of main

	// -------------------------------------------------------------------------------------------------------
	// static method to print the standard menu
	// -------------------------------------------------------------------------------------------------------
	public static void publicMenu() {
		System.out.println();
		Helper.line(45, "*");
		System.out.println("*****     PATIENT  MANAGEMENT  MENU     *****");
		Helper.line(45, "*");

		// -------------------
		// Complete code here
		// -------------------
		System.out.println("1. View all Ward Info");
		System.out.println("2. Display Patient List");
		System.out.println("3. Admit Patient");
		System.out.println("4. Discharge Patient");
		System.out.println("5. Remove Patient");
		System.out.println("6. Register Visit");
		System.out.println("7. End Visit");
		System.out.println("8. Display Ward Overview");
		System.out.println("9. Search Visitor By Patient Name");
		System.out.println("10. Search Visitor By Visit Date");
		System.out.println("11. Logout");
		Helper.line(45, "*");
	}

	// -------------------------------------------------------------------------------------------------------
	// static method takes in a ward array and list out ward details in a tabular
	// list
	// -------------------------------------------------------------------------------------------------------
	public static void displayWardInfo(Ward[] wardArr) {

		// -------------------
		// Complete code here
		// -------------------
		Helper.line(40, "*");
		System.out.println("VIEW ALL WARD INFO");
		Helper.line(40, "*");
		String output = String.format("%-10s %-30s %-20s %-20s\n", "Ward", "Description", "BedCount", "BedCharge");
		for (int i = 0; i < wardArr.length; i++) {

			output += String.format("%-10s %-30s %-20s %-20s\n", wardArr[i].getWard(), wardArr[i].getDescription(),
					wardArr[i].getBedCount(), wardArr[i].getBedCharge());

		}
		System.out.println(output);
		Helper.line(75, "*");
	}

	// -------------------------------------------------------------------------------------------------------
	// static method takes in a patient arraylist and display all the patient
	// information in a tabular list
	// -------------------------------------------------------------------------------------------------------
	public static void displayPatientList(ArrayList<Patient> patientList) {

		// -------------------
		// Complete code here
		// -------------------
		Helper.line(40, "*");
		System.out.println("DISPLAY PATIENT LIST");
		Helper.line(40, "*");

		for (int i = 0; i < patientList.size(); i++) {
			String output = String.format(" %-20s : %-15s ", "NRIC4", patientList.get(i).getNric4());
			System.out.println(output);

			patientList.get(i).display();

		}

	}

	// -------------------------------------------------------------------------------------------------------
	// static method takes in a patient arraylist and performs the admit patient
	// functionality
	// -------------------------------------------------------------------------------------------------------
	public static void admitPatient(ArrayList<Patient> patientList) {

		// -------------------
		// Complete code here
		// -------------------
		Helper.line(40, "=");
		System.out.println("ADMIT PATIENT");
		Helper.line(40, "=");

		String nric4 = Helper.readString("Enter patient 4 digit NRIC > ");
		String name = Helper.readString("Enter patient name > ");
		String ward = Helper.readString("Enter ward > ");
		int bed = Helper.readInt("Enter bed > ");
		String dateWarded = Helper.readString("Enter date warded > ");
		int duplicate = 0;
		for (int i = 0; i < patientList.size(); i++) {
			if (patientList.get(i).getName().equalsIgnoreCase(name)
					&& patientList.get(i).getNric4().equalsIgnoreCase(nric4)
					&& patientList.get(i).getDateDischarged().equals("")) {
				duplicate = 1;

			} 

		}if (duplicate == 0){
			patientList.add(new Patient(nric4, name, ward, bed, dateWarded));
			patientList.get(patientList.size() - 1).display();

			System.out.println("*** Patient has been added ***");
		}
		if (duplicate == 1) {
			System.out.println("*** Patient duplicate ***");
		}

	}

	// -------------------------------------------------------------------------------------------------------
	// static method takes in a patient arraylist and performs the discharge patient
	// functionality
	// It will return 'true' if the patient record exist
	// -------------------------------------------------------------------------------------------------------
	public static boolean dischargePatient(ArrayList<Patient> patientList) {

		boolean patientfound = false;

		// -------------------
		// Complete code here
		// -------------------
		Helper.line(40, "*");
		System.out.println("DISCHARGE PATIENT");
		Helper.line(40, "*");
		String name = Helper.readString("Enter patient name > ");
		int discharge = 1;
		for (int i = 0; i < patientList.size(); i++) {
			if (patientList.get(i).getName().equalsIgnoreCase(name)) {
				patientfound = true;

				if (patientList.get(i).getDateDischarged().equals("")) {
					discharge = 0;

					patientList.get(i).display();
					String discharged = Helper.readString("Enter date discharged > ");
					patientList.get(i).setVisitorCount(0);
					patientList.get(i).setDateDischarged(discharged);
					System.out.println("*** Patient is discharged ***");

				}

			}

		}
		if (discharge == 1 && patientfound == true) {
			System.out.println("*** Patient has already been discharged ***");

		}

		return patientfound;
	}

	// -------------------------------------------------------------------------------------------------------
	// static method takes in a patient arraylist and performs the remove patient
	// functionality
	// It will return 'true' if the patient record exist
	// -------------------------------------------------------------------------------------------------------
	public static boolean removePatient(ArrayList<Patient> patientList) {

		boolean patientfound = false;

		// -------------------
		// Complete code here
		// -------------------
		Helper.line(40, "*");
		System.out.println("REMOVE PATIENT");
		Helper.line(40, "*");
		String name = Helper.readString("Enter patient name > ");
		for (int i = 0; i < patientList.size(); i++) {
			if (patientList.get(i).getName().equalsIgnoreCase(name)) {
				patientList.get(i).display();
				char remove = Helper.readChar("Confirm deletion (y/n) > ");
				if (remove == 'y') {
					patientList.remove(i);
					System.out.println("");
					System.out.println("*** Patient has been deleted ***");
					patientfound = true;

				}

			}

		}

		return patientfound;
	}

	// -------------------------------------------------------------------------------------------------------
	// static method takes in a patient arraylist and performs the register visit
	// functionality
	// It will return 'true' if the patient record exist
	// -------------------------------------------------------------------------------------------------------
	public static boolean registerVisit(ArrayList<Patient> patientList, ArrayList<Visitor> visitorList) {

		boolean patientfound = false;

		// -------------------
		// Complete code here
		// -------------------
		Helper.line(40, "*");
		System.out.println("REGISTER VISIT");
		Helper.line(40, "*");
		String name = Helper.readString("Enter patient name > ");
		for (int i = 0; i < patientList.size(); i++) {
			if (patientList.get(i).getName().equalsIgnoreCase(name)) {
				patientList.get(i).display();
				if (patientList.get(i).getDateDischarged().equals("")) {

					int visitorAllow = 4 - patientList.get(i).getVisitorCount();
					if (visitorAllow > 0) {

						System.out.println("*** Only " + visitorAllow + " visitor(s) allowed ***");
						int visitor = Helper.readInt("Enter number of new visitors > ");
						if (visitor > 0) {
							int totalVisitor = visitor + patientList.get(i).getVisitorCount();

							if (totalVisitor <= 4) {
								// Advance start
								for (int x = 0; x < visitor; x++) {
									String visitorNric4 = Helper.readString("Enter Visitor Nric 4 > ");
									String visitorName = Helper.readString("Enter Visitor Name > ");

									int contactNo = Helper.readInt("Enter Contact Number > ");
									String dateVisit = Helper.readString("Enter date of visit > ");
									String relationship = Helper.readString("Enter your relationship with patient>");
									String patientName = name;
									visitorList.add(new Visitor(visitorNric4, visitorName, contactNo, dateVisit,
											relationship, patientName));
									System.out.println("***Visitor is successfully registered***");

								} // Advance End
								patientList.get(i).setVisitorCount(totalVisitor);

								System.out.println("*** Please proceed to ward ***");
							} else if (totalVisitor > 4) {
								System.out.println("*** Visitors exceeded ***");
							}
						} else {
							System.out.println("***Invalid integer***");
						}
					} else {
						System.out.println("*** No addtional visitor allowed ***");
					}
				} else {
					System.out.println("*** Patient has been discharged ***");
				}
				patientfound = true;

			}
		}

		return patientfound;
	}

	// -------------------------------------------------------------------------------------------------------
	// static method takes in a patient arraylist and performs the end visit
	// functionality
	// It will return 'true' if the patient record exist
	// -------------------------------------------------------------------------------------------------------
	public static boolean endVisit(ArrayList<Patient> patientList) {

		boolean patientfound = false;

		// -------------------
		// Complete code here
		// -------------------
		Helper.line(40, "*");
		System.out.println("END VISIT");
		Helper.line(40, "*");
		String name = Helper.readString("Enter patient name > ");
		for (int i = 0; i < patientList.size(); i++) {
			if (patientList.get(i).getName().equalsIgnoreCase(name)) {
				patientList.get(i).display();
				if (patientList.get(i).getDateDischarged().equals("")) {

					if (patientList.get(i).getVisitorCount() > 0) {

						int leavingVisitor = Helper.readInt("Enter number of visitor(s) leaving > ");
						if (leavingVisitor > 0) {
							if (leavingVisitor < patientList.get(i).getVisitorCount()) {
								int remainVisitor = patientList.get(i).getVisitorCount() - leavingVisitor;
								patientList.get(i).setVisitorCount(remainVisitor);
								System.out.println("*** No of visitor(s) still at ward : " + remainVisitor + " ***");
							} else {
								System.out.println("*** Visitor(s) leaving is more than visited ***");
							}

						} else {
							System.out.println("***Invalid integer***");
						}
					} else {
						System.out.println("*** No visitor in ward ***");
					}
				} else {
					System.out.println("*** Patient has been discharged ***");
				}
				patientfound = true;

			}
		}

		return patientfound;
	}

	// ------------------------------------------------------------------------------------------------------------
	// static method that takes in a patient arraylist, a ward array and display an
	// overview of the ward information
	// ------------------------------------------------------------------------------------------------------------
	public static void displayWardOverview(ArrayList<Patient> patientList, Ward[] WardArr) {

		// -------------------
		// Complete code here
		// -------------------
		Helper.line(40, "*");
		System.out.println("DiSPLAY WARD Overview");
		Helper.line(40, "*");

		int[] totalPatientArr = new int[4];
		int[] totalVisitorArr = new int[4];

		for (int i = 0; i < patientList.size(); i++) {
			if (patientList.get(i).getDateDischarged().equals("")) {
				if (patientList.get(i).getWard().equals("A")) {
					totalPatientArr[0] = totalPatientArr[0] + 1;
					totalVisitorArr[0] = patientList.get(i).getVisitorCount() + totalVisitorArr[0];
				}
				if (patientList.get(i).getWard().equals("B1")) {
					totalPatientArr[1] = totalPatientArr[1] + 1;
					totalVisitorArr[1] = patientList.get(i).getVisitorCount() + totalVisitorArr[1];
				}
				if (patientList.get(i).getWard().equals("B2")) {
					totalPatientArr[2] = totalPatientArr[2] + 1;
					totalVisitorArr[2] = patientList.get(i).getVisitorCount() + totalVisitorArr[2];
				}
				if (patientList.get(i).getWard().equals("C")) {
					totalPatientArr[3] = totalPatientArr[3] + 1;

					totalVisitorArr[3] = patientList.get(i).getVisitorCount() + totalVisitorArr[3];
				}
			}

		}
		int totalBedCount = 0;
		int totalPatient = 0;
		int totalVisitor = 0;
		String output = String.format("%-10s %-30s %-20s %-30s %-20s \n", "Ward", "Description", "BedCount",
				"Total Patient In Ward", "Total Visitor In Ward");
		for (int i = 0; i < WardArr.length; i++) {
			totalBedCount = totalBedCount + WardArr[i].getBedCount();
			totalPatient = totalPatient + totalPatientArr[i];
			totalVisitor = totalVisitor + totalVisitorArr[i];

			output += String.format("%-10s %-30s %-20d %-30d %-20d \n", WardArr[i].getWard(),
					WardArr[i].getDescription(), WardArr[i].getBedCount(), totalPatientArr[i], totalVisitorArr[i]);

		}
		output += String.format("%-30s %-30s %-30s\n", "Total Bed Count In All Ward", "Total Patient In All Ward",
				"Total Visitor In All Ward");
		output += String.format("%-30d %-30d %-30d\n", totalBedCount, totalPatient, totalVisitor);

		System.out.println(output);
		Helper.line(120, "*");

	}

	// Search visitor by patient name
	public static void searchByPatientName(ArrayList<Visitor> visitorList) {
		System.out.println();
		Helper.line(40, "*");
		System.out.println("SEARCH VISITOR BY PATIENT NAME");
		Helper.line(40, "*");
		int visitorfound = 0;

		String patient = Helper.readString("Enter Patient Name > ");
		Helper.line(40, "*");
		for (int i = 0; i < visitorList.size(); i++) {
			if (visitorList.get(i).getPatientName().equalsIgnoreCase(patient)) {
				visitorList.get(i).displayVisitorInfo();
				visitorfound = 1;

			}

		}
		if (visitorfound == 0) {
			System.out.println(" ***No visitor found ***");
		}
	}

	// Search visitor by Date
	public static void searchByDate(ArrayList<Visitor> visitorList) {
		System.out.println();
		Helper.line(40, "*");
		System.out.println("SEARCH VISITOR BY VISIT DATE");
		Helper.line(40, "*");
		int visitorfound = 0;

		String date = Helper.readString("Enter date of visit > ");
		Helper.line(40, "*");
		for (int i = 0; i < visitorList.size(); i++) {
			if (visitorList.get(i).getDateVisit().equalsIgnoreCase(date)) {
				visitorList.get(i).displayVisitorInfo();
				visitorfound = 1;

			}

		}
		if (visitorfound == 0) {
			System.out.println(" ***No visitor found ***");
		}
	}

}