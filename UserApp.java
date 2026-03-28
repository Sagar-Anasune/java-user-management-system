import java.sql.*;
import java.util.Scanner;

public class UserApp {

    static final String URL = "jdbc:mysql://localhost:3306/userdb";
    static final String USER = "root";
    static final String PASSWORD = "Sagar@123"; // apna password

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            while (true) {
                System.out.println("\n===== MENU =====");
                System.out.println("1. Add User");
                System.out.println("2. View Users");
                System.out.println("3. Delete User");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();

                if (choice == 1) {
                    sc.nextLine(); // clear buffer

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    String insertQuery = "INSERT INTO users (name, email) VALUES (?, ?)";
                    PreparedStatement ps = con.prepareStatement(insertQuery);
                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.executeUpdate();

                    System.out.println("User added successfully");

                } else if (choice == 2) {

                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM users");

                    System.out.println("\n--- Users ---");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getString("email"));
                    }

                } else if (choice == 3) {

                    System.out.print("Enter user ID to delete: ");
                    int id = sc.nextInt();

                    String deleteQuery = "DELETE FROM users WHERE id=?";
                    PreparedStatement ps = con.prepareStatement(deleteQuery);
                    ps.setInt(1, id);
                    ps.executeUpdate();

                    System.out.println("User deleted");

                } else if (choice == 4) {
                    System.out.println("Exiting...");
                    break;

                } else {
                    System.out.println("Invalid choice");
                }
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}