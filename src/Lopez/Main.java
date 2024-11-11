package Lopez;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean exit = true;
        do{
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-11s%-27s%-12s|\n", "", "**BARANGAY BLOTTER SYSTEM**", "");
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-18s%-13s%-19s|\n", "", "Select Option", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "1. Citizen", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "2. Officer", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "3. Incident Type", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "4. Blotter Report", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "5. Record", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "6. Exit", "");
            System.out.println("+--------------------------------------------------+");
            int choices = 6;
            int option = OptionValidation(input, choices);
            System.out.println("+--------------------------------------------------+");
            switch(option){
                case 1:
                    Citizen cit = new Citizen();
                    cit.Citizen_List();
                break;
                case 2:
                    Officer off = new Officer();
                    off.Officer_List();
                break;
                case 3:
                    Incident_Type it = new Incident_Type();
                    it.Incident_List();
                break;
                case 4:
                    Blotter_Report br = new Blotter_Report();
                    br.blotter();
                break;
                case 5:
                    Record rec = new Record();
                    rec.record();
                break;
                default:
                    input.nextLine();
                    String tagain;
                    while(true){
                        System.out.printf("|%-5sDo You Really Want To Exit? (Yes/No): ","");
                        try{
                            tagain = input.next();
                            if(tagain.equalsIgnoreCase("yes")){
                                exit = false;
                                break;
                            }else if(tagain.equalsIgnoreCase("no")){
                                exit = true;
                                break;
                            }
                        }catch(Exception e){
                            System.out.println("Error!");
                        }
                    }
                break;
            }
        }while(exit);
        
    }
    private static int OptionValidation(Scanner input, int choices){
        int option;
        while(true){
            System.out.printf("|%-5sEnter Option: ","");
            try{
                option = input.nextInt();
                if(option>0 && option<=choices){
                    break;
                }
            }catch(Exception e){
                System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                input.nextLine();
            }
        }
        return option;
    }
}
