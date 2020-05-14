package PresentationLayer;

import java.util.Scanner;

public class TrnasAndEmpView {


    public static void main(String[] args) {

        while (true) {
            try {
                System.out.println("Welcome to the \"Super-Lee\" system!\n" +
                        "Which system would you like to enter to?:\n" +
                        "1. Transport management\n" +
                        "2. Personal manager\n" +
                        "3. Exit");

                Scanner ans = new Scanner(System.in);
                int role = ans.nextInt();
                switch (role) {
                    case 1:
                        UserMenu.main();
                        break;
                    case 2:
                        view.main();
                        break;
                    case 3:
                        return;
                    default:
                        break;
                }

            } catch (Exception e) {
                System.out.println("\nInvalid selection\n");
            }
        }

    }
}

