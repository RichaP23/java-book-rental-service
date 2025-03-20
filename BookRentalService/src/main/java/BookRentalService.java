/**
 * Project Title: Book Rental Service
 * Author: Vivek Arya
 * Student ID: 12186235
 * Date: 30th May 2023
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents a Book Rental Service system.
 * It allows users to rent books, record rental entries,
 * calculate total money collected, and display statistics.
 */
public class BookRentalService {
    private static final double BOOK_RENTAL_PRICE = 4; // Price per book rental
    private static final double DISCOUNT_THRESHOLD_A = 10; // Number of books to qualify for a discount
    private static final double DISCOUNT_THRESHOLD_B = 7; // Number of books to qualify for a discount
    private static final double DISCOUNT_THRESHOLD= 5; // Number of books to qualify for a discount
    private static final double DISCOUNT_PERCENT_B = 10; // 10% discount rate for renting more than DISCOUNT_THRESHOLD books
    private static final double DISCOUNT_PERCENT_A = 20; // 20% discount rate for renting more than DISCOUNT_THRESHOLD books
    private static final double DISCOUNT_PERCENT_C = 5; // 5% discount rate for renting more than DISCOUNT_THRESHOLD books
    private static final int STUDENT_ID = 12186235; // My student ID
    private static final double ZERO = 0.0;

    // An ArrayList declaration to store rental records
    private static ArrayList<RentalRecord> rentalRecords = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("WELCOME TO BOOK RENTAL SERVICE SYSTEM\n\n\n");
        int choice;
        // Menu-driven console
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    rentBooks(scanner);
                    break;
                case 2:
                    displayAllEntries();
                    break;
                case 3:
                    displayAllEntries();
                    System.out.println();
                    double totalMoneyCollected = calculateTotalMoneyCollected();
                    System.out.println("Total money collected: $" + totalMoneyCollected);
                    break;
                case 4:
                    displayAllEntries();
                    System.out.println();
                    double averageCharge = calculateAverageCharge();
                    //to keep answer till 2 points of decimal 
                    //display avergae charge only if atleast 1 element is present: 
                    if(averageCharge!=0){
                    String avg=String.format("%.2f",(averageCharge));
                    System.out.println("Average charge per entry: $" + avg);
                    }
                    break;
                case 5:
                    System.out.println("\n\nThank you for using the Book Rental Service System");
                    System.out.println("Program written by " + STUDENT_ID);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        } while (choice != 5);

        scanner.close();
    }

    /**
     * Displays the main menu options.
     */
    private static void displayMenu() {
        System.out.println("----- Book Rental Service Menu -----");
        System.out.println("1. Rent Books");
        System.out.println("2. Display All Entries");
        System.out.println("3. Calculate Total Money Collected");
        System.out.println("4. Calculate Average Charge per Entry");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Allows the user to rent books by recording rental entries.
     * Takes one entry at a time.
     *
     * @param scanner the Scanner object to read user input
     */
    private static void rentBooks(Scanner scanner) {

        // Takes one entry at a time
        System.out.println("--- Entry " + (rentalRecords.size() + 1) + " ---");

        // Input customer name
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        // Input number of rented books
        System.out.print("Enter number of books rented: ");
        int numBooks = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // A new ArrayList to store book names
        List<String> bookNames = new ArrayList<>();
        // Entering book names in the ArrayList
        for (int j = 0; j < numBooks; j++) {
            System.out.print("Enter book name: ");
            String bookName = scanner.nextLine();
            bookNames.add(bookName);
        }

        // Adding discount to the total charge
        double discountPercent = ZERO;
        // Applying appropriate discount if number of books > DISCOUNT_THRESHOLD
        if(numBooks >= DISCOUNT_THRESHOLD){
            if (numBooks >= DISCOUNT_THRESHOLD_A) {
                discountPercent = DISCOUNT_PERCENT_A;
            } else if (numBooks >= DISCOUNT_THRESHOLD_B) {
                discountPercent = DISCOUNT_PERCENT_B;
            } else if (numBooks >= DISCOUNT_THRESHOLD) {
                discountPercent = DISCOUNT_PERCENT_C;
            }
        }

        // Calculating total charge of entered books
        double totalCharge = calculateTotalCharge(numBooks, discountPercent);

        // Adding new record to the existing ArrayList of rental records
        RentalRecord record = new RentalRecord(customerName, numBooks, totalCharge, bookNames, discountPercent);
        rentalRecords.add(record);

        // Printing the new record
        System.out.println("\n--- New Record ---");
        displayHeading();
        System.out.printf("\n%-25s%-17s$%-17s%-17s%-30s\n", record.getCustomerName(), record.getNumBooks(), record.getTotalCharge(), record.getDiscount(), record.getBookNames());
        System.out.println("----------------------\n");
        System.out.println("Entry recorded.\n");
    }

    /**
     * Displays all the rental entries.
     */
    private static void displayAllEntries() {
        // If there are no entries, an error message is printed
        int numEntries = rentalRecords.size();
        if (numEntries == 0) {
            System.out.println("Error: Please add at least 1 entry for display.");
            return;
        }
        // If there is at least 1 entry, records are printed
        System.out.println("\n--- Rental Records ---");
        displayHeading();
        for (RentalRecord record : rentalRecords) {
            System.out.printf("\n%-25s%-17s$%-17s%-17s%-30s\n", record.getCustomerName(), record.getNumBooks(), record.getTotalCharge(), record.getDiscount(), record.getBookNames());
        }
        System.out.println("----------------------\n");
    }

    /**
     * Displays the heading for the rental records.
     */
    private static void displayHeading() {
        System.out.printf("\n%-25s%-17s%-17s%-17s%-30s\n", "Customer Name", "No. of Books", "Total Charge", "Discount%", "Book Names");
    }

    /**
     * Calculates the total charge for renting a given number of books.
     *
     * @param numBooks the number of books rented
     * @param discount the discount percentage
     * @return the total charge for renting the books
     */
    private static double calculateTotalCharge(int numBooks, double discountPercent) {
        // To store the total charge
        double totalCharge;

        // If number of books is less than or equal to discount applicable number
        if (numBooks < DISCOUNT_THRESHOLD) {
            totalCharge = numBooks * BOOK_RENTAL_PRICE;
        } else {
            // Adding extra discount to the total charge
            double nonDiscountedCharge = DISCOUNT_THRESHOLD * BOOK_RENTAL_PRICE;
            double discountedCharge = (numBooks - DISCOUNT_THRESHOLD) * (1 - (discountPercent/100)) * BOOK_RENTAL_PRICE;
            totalCharge = nonDiscountedCharge + discountedCharge;
        }

        return totalCharge;
    }
    /**
     * Calculates the total money collected from all rental records.
     *
     * @return the total money collected
     */
    private static double calculateTotalMoneyCollected() {
        double totalMoney = ZERO;

        for (RentalRecord record : rentalRecords) {
            totalMoney += record.getTotalCharge();
        }

        return totalMoney;
    }
     /**
     * Calculates the average charge per entry in the rental records.
     *
     * @return the average charge per entry
     */
    private static double calculateAverageCharge() {
        
        //calculation of average cost collected: 
        int numEntries = rentalRecords.size();
        if(numEntries==0){
            return ZERO;
        }
        double totalCharge = calculateTotalMoneyCollected();
        return totalCharge / numEntries;
    }
}

/**
 * Represents a rental record for a customer.
 */
class RentalRecord {
    private String customerName;
    private int numBooks;
    private double totalCharge;
    private List<String> bookNames;
    private double discount;

    // Constructor method to initialize the rental record
    public RentalRecord(String customerName, int numBooks, double totalCharge, List<String> bookNames, double discount) {
        this.customerName = customerName;
        this.numBooks = numBooks;
        this.totalCharge = totalCharge;
        this.bookNames = bookNames;
        this.discount = discount;
    }

    // Accessor for total charge
    public double getTotalCharge() {
        return totalCharge;
    }

    // Getter for customer name
    public String getCustomerName() {
        return customerName;
    }

    // Getter for number of books
    public int getNumBooks() {
        return numBooks;
    }

    // Getter for book names
    public List<String> getBookNames() {
        return bookNames;
    }

    // Getter for discount
    public double getDiscount() {
        return discount;
    }
}
