package src.PresentationLayer;

import src.BusinessLayer.Suppliers.Items;
import src.InterfaceLayer.Suppliers.FacadeController;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Integer DayOfTheWeek = getCurrentDay();
    public static int plusDay = 0;


    public static void main(String[] args) {
        mainLoop();
    }

    private static void mainLoop() {


            Printer.Print("Welcome To EOEHRES !\n");
            //printShape();
        int choose =0;
            do {
                try {

                   /* LocalDate systemDate = LocalDate.now().plusDays(Main.plusDay);
                    //Calendar cal = Calendar.getInstance();
                    System.out.println(systemDate.getDayOfWeek().toString());
                    int i =0;
                    while (i<10 && getCurrentDay(systemDate.getDayOfWeek().toString()) != 1){
                        systemDate = systemDate.plusDays(1);
                        System.out.println(systemDate.getDayOfWeek().toString());
                        i++;
                    }
                    //Date date = cal.getTime();
                    Calendar cal = Calendar.getInstance();
                    cal.set(systemDate.getYear(), systemDate.getMonthValue(), systemDate.getDayOfMonth());
                    Date date = cal.getTime();
                    System.out.println(date.toString());*/
                Printer.Print("\nDay: " + (LocalDate.now().plusDays(plusDay).toString()));
                Printer.Print("\nPlease choose an action:\n" +
                        "1. Enter Inventory & Reports Menu\n" +
                        "2. Enter Suppliers Menu\n" +
                        "3. Enter Transports Menu\n" +
                        "4. Enter Personal manager Menu\n" +
                        "5. Promote Day\n" +
                        "6. Quit\n");


                    choose = scanner.nextInt();
                    switch (choose) {
                        case 1:
                            InventoryMenu.mainLoop();
                            break;
                        case 2:
                            SupplierMenu.displaySupplierMenu();
                            break;
                        case 3:
                            TransportsMenu.main();
                            break;
                        case 4:
                            EmployeesMenu.main();
                            break;
                        case 5:
                            plusDay++;
                            SupplierMenu.PromoteDay(LocalDate.now().plusDays(plusDay));
                            InventoryMenu.PromoteDay((DayOfTheWeek + plusDay) % 7);
                        case 6:
                            break;
                    }
                } catch (Exception e) {
                    Printer.Print("Can't execute the action.");
                }
            }
            while (choose != 6);
            System.out.println("Shutting down the system...");
    }

    private static void printShape() {
        System.out.println(",--,             \n" +
                "                            ,-.----.                                   ,---.'|             \n" +
                "  .--.--.                   \\    /  \\       ,---,. ,-.----.            |   | :       ,---, \n" +
                " /  /    '.           ,--,  |   :    \\    ,'  .' | \\    /  \\           :   : |    ,`--.' | \n" +
                "|  :  /`. /         ,'_ /|  |   |  .\\ : ,---.'   | ;   :    \\          |   ' :    |   :  : \n" +
                ";  |  |--`     .--. |  | :  .   :  |: | |   |   .' |   | .\\ :          ;   ; '    :   |  ' \n" +
                "|  :  ;_     ,'_ /| :  . |  |   |   \\ : :   :  |-, .   : |: |          '   | |__  |   :  | \n" +
                " \\  \\    `.  |  ' | |  . .  |   : .   / :   |  ;/| |   |  \\ :          |   | :.'| '   '  ; \n" +
                "  `----.   \\ |  | ' |  | |  ;   | |`-'  |   :   .' |   : .  /          '   :    ; |   |  | \n" +
                "  __ \\  \\  | :  | | :  ' ;  |   | ;     |   |  |-, ;   | |  \\          |   |  ./  '   :  ; \n" +
                " /  /`--'  / |  ; ' |  | '  :   ' |     '   :  ;/| |   | ;\\  \\         ;   : ;    |   |  ' \n" +
                "'--'.     /  :  | : ;  ; |  :   : :     |   |    \\ :   ' | \\.'         |   ,/     '   :  | \n" +
                "  `--'---'   '  :  `--'   \\ |   | :     |   :   .' :   : :-'           '---'      ;   |.'  \n" +
                "             :  ,      .-./ `---'.|     |   | ,'   |   |.'                        '---'    \n" +
                "              `--`----'       `---`     `----'     `---'");

        System.out.println("                                                                                                                                                                                                     \n" +
                "                     .,##*          .*##*.                                              ,((.     .....    .*#/.                                             ./%(.          ./%(,                        \n" +
                "                .#.           #@%,          .#,                                    *(                 .          .#.                                   //         (/               /(.                  \n" +
                "             #      ...,#@# %@@@@@&.@@%.        .#.                             #      #@@@@@@.,, .  .*@@@@@#        ,/                            ,*       .%@#.(@@@@@@@@@*           ,/               \n" +
                "          #      *#@@@@@@@@#,@(*,   .&@@@@#         #                        (       (@@@@@@@*,,,    ..   ,@@@@*        ,.                      .*        *@@@@@&    /@,*@@@@@@@&         ,,            \n" +
                "        (       %@@@@@@@@@@@@@@@@@@@@@@@@@@@*         /                    #       &@@@@@@@,   (#&.  ,@@,#@@@@@@&.        .*                  *.        .&@@@@@@@@@@@@@@@@@@@@@@@@&          (          \n" +
                "      %       *@@@@@@@@*..      .(@@,.    ,./%          %                (       *@@@@@@%(%@@@@@@@@@@@@@@@@@@@@@@@,         *                ,         *@@@@@@@@@@@%.       ..&@@@@&          .,        \n" +
                "     ,        (@@@@@&.                      &@/          ,.             # .      %@@@@@@(( /((#@#(.,@@@*      (@@@#           /            #           %@@@#&@@@*                ,@#,           &       \n" +
                "   ..        .&@@@@@.                       ,@@(          .,           (        ,@@@@/            /@@@,       #@@@#            #          %           ,@@ #@.                    @@%             %      \n" +
                "   ,          %@@@@@(                       /@@#           .          &          %@@@/          *(%,          (@@@#             ,        #            #@,  /( ./,    &@@&#      .@@@@@(           &     \n" +
                "  @           (@@@&.     .,                 %@@%.           &         ..         ,@@@@               .,,,,,,   *@@#             &        ,            (@@@@@@@@@@* #@@@@@@@@@#    @@@@,            .    \n" +
                "  /            ,@@@    /@@@@@@@. /@@@@@@@&. .@%             ,        #.           %@@#  .&@@@@/   *@@@@@@@@@@@/ *&,             .       (.            ,& #@@@@@@(  *@@@@@@@@@@     @@@%            %    \n" +
                "  ..            (@@.   ../@@@%.  .&@@@@@%@*  @%                      &            *@@@   *&@@@@@@*  .@@@@@@@(.                          &              /  &@@@@@,    ,(&#         (@@#             @.   \n" +
                "  *              %@*               ...      /@&             ,        (.          ,@@@@   ..,@@@@@%    %@@@&       ..            ,       #.                   @@       /#,         @@@              %    \n" +
                "  @.             .@(      /,        ,.                      &         ,          ,@@@@      ..,@@#.     #@%,..                  @        .                 (@@@@@@@@@  (@@@%     .@(               ,    \n" +
                "   ,             *@%   (@@* ./.  ..*@@@@/                  ..         #.          #@@@/             .%&@@@@@%%@%               ,         %.               @@@@@@@@@@&/, (@@@%     @.              & .   \n" +
                "   *.             #@/  (@@(*(@@@@@@%#@@@# ..               /           %.         .%@@@    (&@@&########&@@@/ &@#              .          #               #@@@,        &@@@@. / .@#              (      \n" +
                "    ,.             /@@%        .,. ,&#. %@@*              *             (.             .   ,@@@&      *&&*.   @@@(           *             #            %@@@&%#& .      *@& #@@@@@.             #       \n" +
                "      (             /@@@/             &@@%.             /                .*            ,                   .%.@@@#          #               /.          .&@@*  ,@@@@,      @@@@@%.             (        \n" +
                "       ,*            .(@@%.  .&@@@# ,@@&*             ,,                   ,,                /            /@@@@/          (                   (.          ,@@@ *%&@*     #@@@&*              #          \n" +
                "         ./             *&(          #%.            *.                       ./           .%@@@&,       /@@@%.         ./                       /.         .#@@/ .,. *%@@@@&,              (            \n" +
                "            **            *%/.     *#            **                             ,(           /&@@@@@@@@@&#,         .(                             (.          *@@@@@@&*(*              /.              \n" +
                "               .(*                           ,(.                                    *(,                         *(.                                   *(.                           /*                  \n" +
                "                    ./(/,             ,*(/                                               ./#(*,.      .,*/((,                                              ,#/*.            .,/(,     ");
    }

    private static void AddNewBranch() {


    }


    private static Integer getCurrentDay() {
        String day = LocalDate.now().getDayOfWeek().toString();
        switch (day) {
            case "SUNDAY":
                return 1;
            case "MONDAY":
                return 2;
            case "TUESDAY":
                return 3;
            case "WEDNESDAY":
                return 4;
            case "THURSDAY":
                return 5;
            case "FRIDAY":
                return 6;
            case "SATURDAY":
                return 0;
        }
        return 1;
    }


    private static Integer getCurrentDay(String day) {

        switch (day) {
            case "SUNDAY":
                return 1;
            case "MONDAY":
                return 2;
            case "TUESDAY":
                return 3;
            case "WEDNESDAY":
                return 4;
            case "THURSDAY":
                return 5;
            case "FRIDAY":
                return 6;
            case "SATURDAY":
                return 0;
        }
        return 1;
    }

}