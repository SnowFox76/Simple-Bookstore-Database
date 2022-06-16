import java.util.*;      
import java.sql.*;

public class eBookstoreApp {
	/*
	 * The eBookstore Application. The server must first be launched and then run through the terminal.
	 */
	private static void endCont() {
		//Holds the application till the user wishes to return to the menu
		System.out.println("Press ENTER to Return to Menu");
		Scanner input = new Scanner(System.in);
		String end = input.nextLine();
		System.out.println(end);
		input.close();
		
	}
	
	public static void menu() {
		//The main UI the user decides what to do. 
		System.out.println("=======================");
		System.out.println("         MENU");
		System.out.println("=======================");
		System.out.println(" ");
		System.out.println(" [*]  1.  Search Book");
		System.out.println(" [*]  2.  Enter/Add Book");
		System.out.println(" [*]  3.  Update Book");
		System.out.println(" [*]  4.  Delete Book");
		System.out.println(" [*]  5.  Print Book Database");
		System.out.println(" [*]  0.  Exit");
		System.out.println(" ");
	}
	
	public static void search() {
		//The main search function for searching the databse
		Scanner input = new Scanner(System.in);
		String userInput;
		int userInputInt;
		String sqlSelect = null;
		//The UI used for the search function
		System.out.println(" ");
		System.out.println("=====SEARCH=====");
		System.out.println(" ");
		System.out.println(" Search for: ");
		System.out.println(" 1.  Book ID");
		System.out.println(" 2.  Book Title");
		System.out.println(" 3.  Book Author");
		System.out.println(" 4.  Book QTY");
		System.out.println(" 0.  Exit");
		System.out.println(" ");
		
		while (true)  {
			//The user will choose which option they wish to do
			System.out.println("Enter Number of Choice: ");
			userInput = input.nextLine();
			try {
				userInputInt = Integer.parseInt(userInput);
				//Use if statement to filter through user input and execute sql query
				if (userInputInt == 1) {
					//Search the database using the unique id
					System.out.println("Enter ID: "); String id = input.next();
					//Select the book based on the id from the database
					sqlSelect = "select * from books where id = '" + id + "'";
					break;
				} else if (userInputInt == 2) {
					System.out.println("Enter Title: "); String Title = input.next();
					//Select the book based on the author from the database
					sqlSelect = "select * from books where Author = '" + Title + "'";
					break;
				} else if (userInputInt == 3) {
					System.out.println("Enter Author: "); String Author = input.next();
					sqlSelect = "select * from books where id = " + Author;
					break;
				} else if (userInputInt == 4) {
					System.out.println("Enter QTY: "); String Qty = input.next();
					sqlSelect = "select * from books where id = " + Qty;
					break;
				} else if (userInputInt == 0) {
					break;
				} else if (userInputInt > 4) {
					System.out.println("Wrong Input\nPlease Try Again\n");
				} else if (userInputInt < 0) {
					System.out.println("Wrong Input\nPlease Try Again\n");
				}
			} catch (NumberFormatException e) {
				System.out.println("Wrong Input\nPlease Try Again\n");
			}
		} 
		
		if (Integer.parseInt(userInput) != 0) {
			try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?allowPublicKeyRetrieval=true&useSSL=false", "myuser", "1234");
				Statement stmt = conn.createStatement();
			) {
				ResultSet rset = stmt.executeQuery(sqlSelect);
				System.out.println(" ");
				System.out.println("SQL query: " + sqlSelect + "\n");
				System.out.println("Books Matching Your Search:");
				int rowCount = 0;
				while (rset.next()) {
					++rowCount;
					String id = rset.getString("id");
					String Title = rset.getString("Title");
					String Author = rset.getString("Author");				
					String Qty = rset.getString("Qty");
					row();
					titleRow();
					row();
					System.out.printf("| "+"%-5s"+"| "+"%-60s"+"| "+"%-30s"+"| "+"%5s"+" |\n", id, Title, Author, Qty);
					row();
				}
				System.out.println("Total Number of Records: " + rowCount);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}	
		}
		input.close();
	}
	
//##########################################################
	
	public static void insert () {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println(" ");
		System.out.println("===ENTER NEW BOOK===");
		System.out.println("Press ENTER to Start");
		String iDislikeJava = input.nextLine();
		input.useDelimiter("\n");
		System.out.println("Book ID: "); String id = input.next();
		System.out.println("Book Title: "); String Title = input.next();
		System.out.println("Book Author: "); String Author = input.next();
		System.out.println("Book Qty: "); String Qty = input.next();
		id = id.replaceAll("[\n\r]", "");
		Title = Title.replaceAll("[\n\r]", "");
		Author = Author.replaceAll("[\n\r]", "");
		Qty = Qty.replaceAll("[\n\r]", "");
		
		String sqlInsert = "insert into books " + "values("+id+", '"+Title+"', '"+ Author+"', "+ Qty+")";
		System.out.println(iDislikeJava);
		System.out.println("SQL query: " + sqlInsert);
		System.out.println(" ");
		
		try (
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?allowPublicKeyRetrieval=true&useSSL=false", "myuser", "1234");
			Statement stmt = conn.createStatement();
		) {
			int countInserted = stmt.executeUpdate(sqlInsert);
			if (countInserted == 1) {
				System.out.println("1 Record Inserted");
			} else {
				System.out.println(countInserted + " Records Inserted");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		input.close();
	}
	
//##########################################################
	
	public static void update() {
		
		Scanner input = new Scanner(System.in);
		String userInput;
		int userInputInt;
		String sqlUpdate = null;
		
		System.out.println(" ");
		System.out.println("=====UPDATE=====");
		System.out.println(" ");
		System.out.println(" Update a: ");
		System.out.println(" 1.  Book Title");
		System.out.println(" 2.  Book Author");
		System.out.println(" 3.  Book Qty");
		System.out.println(" 0.  Exit");
		System.out.println(" ");
		
		while (true)  {
			System.out.println("Enter Number of Choice: ");
			userInput = input.nextLine();
			try {
				userInputInt = Integer.parseInt(userInput);
				input.useDelimiter("\n");
				if (userInputInt == 1) {
					System.out.println("The BOOK ID of the book you want UPDATED");
					System.out.println("Enter ID: "); String id = input.next();
					System.out.println("Enter the NEW BOOK TITLE: "); String Title = input.next();
					Title = Title.replaceAll("[\n\r]", ""); id = id.replaceAll("[\n\r]", "");
					sqlUpdate = "update books set Title = '" + Title + "' where id = " + id;
					break;
				} else if (userInputInt == 2) {
					System.out.println("The BOOK ID of the book you want UPDATED");
					System.out.println("Enter ID: "); String id = input.next();
					System.out.println("Enter the NEW BOOK AUTHOR: "); String Author = input.next();
					Author = Author.replaceAll("[\n\r]", ""); id = id.replaceAll("[\n\r]", "");
					sqlUpdate = "update books set Author = '" + Author + "' where id = " + id;
					break;
				} else if (userInputInt == 3) {
					System.out.println("The BOOK ID of the book you want UPDATED");
					System.out.println("Enter ID: "); String id = input.next();
					System.out.println("Enter the NEW BOOK QTY: "); String Qty = input.next();
					Qty = Qty.replaceAll("[\n\r]", ""); id = id.replaceAll("[\n\r]", "");
					sqlUpdate = "update books set Qty = " + Qty + " where id = " + id;
					break;
				} else if (userInputInt == 0) {
					break;
				} else if (userInputInt > 3) {
					System.out.println("Wrong Input\nPlease Try Again\n");
				} else if (userInputInt < 0) {
					System.out.println("Wrong Input\nPlease Try Again\n");
				}
			} catch (NumberFormatException e) {
				System.out.println("Wrong Input\nPlease Try Again\n");
			}
		}
		
		if (Integer.parseInt(userInput) != 0) {
			try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?allowPublicKeyRetrieval=true&useSSL=false", "myuser", "1234");
				Statement stmt = conn.createStatement();
			) {
				System.out.println(" ");
				System.out.println("SQL query: " + sqlUpdate + "\n");
				int countUpdated = stmt.executeUpdate(sqlUpdate);
				if (countUpdated == 1) {
					System.out.println("1 Record Updated");
				} else {
					System.out.println(countUpdated + " Records Updated");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		input.close();
	}
	
//##########################################################
	
	public static void delete() {
		
		Scanner input = new Scanner(System.in);
		String sqlDelete;
		
		System.out.println(" ");
		System.out.println("====DELETE====");
		System.out.println(" ");
		System.out.println("Enter Book ID (0 to Exit): "); String id = input.next();
		if (Integer.parseInt(id) != 0) {
			sqlDelete = "delete from books where id = " + id;
			System.out.println(" ");
			System.out.println("SQL query: " + sqlDelete + "\n");
			try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?allowPublicKeyRetrieval=true&useSSL=false", "myuser", "1234");
				Statement stmt = conn.createStatement();
			) {
				int countDeleted = stmt.executeUpdate(sqlDelete);
				if (countDeleted == 1) {
					System.out.println("1 Record Deleted");
				} else {
					System.out.println(countDeleted + " Records Deleted");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		input.close();
	}

//##########################################################
	
	public static void row() {
		System.out.print("+");
		for (int i = 0; i <= 5; i++) {
			System.out.print("-");
		} System.out.print("+");
		for (int i = 0; i <= 60; i++) {
			System.out.print("-");
		} System.out.print("+");
		for (int i = 0; i <= 30; i++) {
			System.out.print("-");
		} System.out.print("+");
		for (int i = 0; i <= 6; i++) {
			System.out.print("-");
		} System.out.print("+\n");
	}
	
	public static void titleRow() {
		System.out.print("| ID");
		for (int i = 0; i <= 2; i++) {
			System.out.print(" ");
		} System.out.print("|");
		System.out.print(" Title");
		for (int i = 0; i <= 54; i++) {
			System.out.print(" ");
		} System.out.print("|");
		System.out.print(" Author");
		for (int i = 0; i <= 23; i++) {
			System.out.print(" ");
		} System.out.print("|");
		System.out.print("   QTY");
		for (int i = 0; i <= 0; i++) {
			System.out.print(" ");
		} System.out.print("|\n");
	}
	
	public static void print() {
		
		String sqlSelect = "select * from books";
		System.out.println(" ");
		System.out.println("SQL query: " + sqlSelect + "\n");
		System.out.println(" ");
		System.out.println("====THE DATABASE====");
		System.out.println(" ");
		try (
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?allowPublicKeyRetrieval=true&useSSL=false", "myuser", "1234");
			Statement stmt = conn.createStatement();
		) {
			ResultSet rset = stmt.executeQuery(sqlSelect);
			row();
			titleRow();
			row();
			while (rset.next()) {
				System.out.printf("| "+"%-5s"+"| "+"%-60s"+"| "+"%-30s"+"| "+"%5s"+" |\n", rset.getInt("id"), 
						rset.getString("Title"), rset.getString("Author"), rset.getInt("Qty"));
			}
			row();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
//##########################################################
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		while (true)  {
			menu();
			System.out.println("Enter Number of Choice: ");
			String userInput = input.next();
			try {
				int userInputInt = Integer.parseInt(userInput);
				if (userInputInt == 1) {
					search();
					endCont();
					continue;
				} else if (userInputInt == 2) {
					insert();
				} else if (userInputInt == 3) {
					update();
				} else if (userInputInt == 4) {
					delete();
				} else if (userInputInt == 5) {
					print();
					endCont();
					continue;
				} else if (userInputInt == 0) {
					break;
				} else if (userInputInt > 5) {
					System.out.println("Wrong Input\nPlease Try Again\n");
				} else if (userInputInt < 0) {
					System.out.println("Wrong Input\nPlease Try Again\n");
				}
			} catch (NumberFormatException e) {
				System.out.println("Wrong Input\nPlease Try Again\n");
			}	
		}
		input.close();
	}
}
