package it.ispw.unilife.view;
import java.util.Scanner;

public class ExploreCoursesCLIController {

    private final Scanner scanner;
    private final String pageTitle;

    public ExploreCoursesCLIController(){
        this.scanner = new Scanner(System.in);
        this.pageTitle = "Explore Courses";
    }

    public int showSearchMenu() throws NumberFormatException {

        System.out.println("1. Filtered Search");
        System.out.println("2. Interest Search");
        System.out.println("0. Home");
        System.out.print("-Choose an Option-");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public String showSearchBar(){
        return "A";
    }

    public void showPageTitle(){
        System.out.println("\n" + pageTitle);
    }
}
