import com.LibraryManagementSystem.View.AdminView;

public class Main {
    public static void main(String [] args) {

        System.out.println("-------------- Welcome to the Library -------------");
        // going to adminView
        AdminView adminUserInterface = new AdminView();
        adminUserInterface.login();
    }
}