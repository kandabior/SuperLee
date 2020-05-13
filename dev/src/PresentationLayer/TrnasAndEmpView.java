package PresentationLayer;

import java.util.Scanner;

public class TrnasAndEmpView {


    public static void main(String[] args) {

        while (true) {
            System.out.println("Welcome to the \"Super-Lee\" system!\n" +
                    "Which system would you like to enter to?:\n" +
                    "1. Transport management\n" +
                    "2.  Employee managemen\n" +
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
        }

    }
}

