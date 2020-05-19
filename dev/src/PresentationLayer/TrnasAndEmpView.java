package PresentationLayer;

import java.util.Scanner;

public class TrnasAndEmpView {


    public static void main(String[] args) {

        while (true) {
                System.out.println("Welcome to the \"Super-Lee\" system!\n" +
                        "Which system would you like to enter to?:\n" +
                        "1. Transport manager\n" +
                        "2. Personal manager\n" +
                        "3. Exit");
                int role;
                while (true) {
                    try {
                        Scanner ans = new Scanner(System.in);
                        role = ans.nextInt();
                        if (role >= 1 & role <= 3)
                            break;
                        else
                            System.out.println("Expected to get a number in range 1-3");
                    } catch (Exception e) {
                        System.out.println("Expected to get a number in range 1-3");
                    }
                }
                switch (role) {
                    case 1:
                        TransportsMenu.main();
                        break;
                    case 2:
                        EmployeesMenu.main();
                        break;
                    case 3:
                        return;
                    default:
                        break;
                }
        }

    }
}

