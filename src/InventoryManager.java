
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class InventoryManager {

    public static void main(String[] args) {
        // Entry point for the program
        // TODO: Implement menu-driven program for inventory management
        Scanner scanner = new Scanner(System.in);
        String fileName = "inventory.txt";
        while (true) {
            System.out.println("1. Read Inventory");
            System.out.println("2. Add Item");
            System.out.println("3. Update Item");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    readInventory(fileName);
                    break;
                case 2:
                    System.out.print("Enter item name: ");
                    String itemName = scanner.nextLine();
                    System.out.print("Enter item count: ");
                    int itemCount = scanner.nextInt();
                    addItem(fileName, itemName, itemCount);
                    break;
                case 3:
                    System.out.print("Enter item name: ");
                    itemName = scanner.nextLine();
                    System.out.print("Enter new item count: ");
                    itemCount = scanner.nextInt();
                    updateItem(fileName, itemName, itemCount);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void readInventory(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory: " + e.getMessage());
        }
    }

    public static void addItem(String fileName, String itemName, int itemCount) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(itemName + "," + itemCount);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }

    public static void updateItem(String fileName, String itemName, int itemCount) {
        try {
            File inputFile = new File(fileName);
            File tempFile = new File("tempInventory.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean itemFound = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(itemName)) {
                    writer.write(itemName + "," + itemCount);
                    itemFound = true;
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
            reader.close();
            writer.close();

            if (!inputFile.delete()) {
                System.out.println("Could not delete original file");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename temp file");
            }
            if (!itemFound) {
                System.out.println("Item not found in inventory");
            }
        } catch (IOException e) {
            System.out.println("Error updating item: " + e.getMessage());
        }
    }
}
