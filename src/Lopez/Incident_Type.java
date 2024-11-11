package Lopez;

import java.util.Scanner;

public class Incident_Type {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    
    public void Incident_List(){
        boolean exit = true;
        do{
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-12s%-25s%-13s|\n", "", "**MANAGE INCIDENT TYPES**", "");
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-18s%-13s%-19s|\n", "", "Select Option", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "1. List An Incident", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "2. Edit Incident Type", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "3. Delete Incident Type", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "4. View Incident Type List", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "5. Back", "");
            System.out.println("+--------------------------------------------------+");
            int choices = 5;
            int option = OptionValidation(input, choices);
            System.out.println("+--------------------------------------------------+");
            switch(option){
                case 1:
                    Add();
                break;
                case 2:
                    Edit();
                break;
                case 3:
                    Delete();
                break;
                case 4:
                    view();
                break;
                default:
                    exit = false;
                break;
            }
        }while(exit);
    }
    private void Add(){
        boolean exit = true;
        boolean exit2 = true;
        String iname, idescription;
        do{
            System.out.printf("|%-50s|\n", "How Many Incident Types Will You Add? (0 to exit)!");
            int num = IntegerValidation(input);
            if(num==0){
                exit = false;
                exit2 = false;
            }
            while(exit2){
                for(int x = 0; x < num; x++){
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-13s%-18s%-5d%-14s|\n", "", "Enter Information ",x+1, "");
                    System.out.println("+--------------------------------------------------+");
                    input.nextLine();
                    System.out.printf("|%-5sIncident Name: ","");
                    iname = input.nextLine();
                    System.out.printf("|%-5sIncident Description: ","");
                    idescription = input.nextLine();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-24s%-2s%-24s|\n","","||","");
                    String SQL = "INSERT INTO Incident_Types (i_name, i_description) Values (?,?)";
                    conf.addRecord(SQL, iname, idescription);
                    System.out.printf("|%-24s%-2s%-24s|\n","","||","");
                }
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-10s%-30s%-10s|\n", "", "Add Another Incident? (Yes/No)", "");
                String again;
                while(true){
                    System.out.printf("|%-5sEnter: ","");
                    again = input.nextLine();
                    if(again.equalsIgnoreCase("Yes")){
                        exit = true;
                        exit2 = false;
                        break;
                    }else if(again.equalsIgnoreCase("No")){
                        exit = false;
                        exit2 = false;
                        break;
                    }
                }
            }
        }while(exit);
    }
    private void Edit(){
        boolean exit = true;
        int id;
        String iname, idescription;
        do{
            boolean exit2 = true;
            view();
            while(true){
                System.out.printf("|%-5sEnter ID: ","");
                try{
                    id = input.nextInt();
                    String SQL = "SELECT COUNT (*) FROM Incident_Types Where i_id = ?";
                    if(conf.recordExists(SQL, id)){
                        break;
                    }else if(id == 0){
                        exit = false;
                        exit2 = false;
                        break;
                    }else{
                        System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Incident Types!!", "");
                    }
                }catch(Exception e){
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                    input.nextLine();
                }
            }
            while(exit2){
                input.nextLine();
                System.out.printf("|%-5sIncident Name: ","");
                iname = input.nextLine();
                System.out.printf("|%-5sIncident Description: ","");
                idescription = input.nextLine();
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-24s%-2s%-24s|\n","","||","");
                String SQL = "UPDATE Incident_Types SET i_name = ?, i_description = ? Where i_id = ?";
                conf.updateRecord(SQL, iname, idescription, id);
                System.out.printf("|%-24s%-2s%-24s|\n","","||","");
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-6s%-37s%-7s|\n", "", "Edit Another Incident Types? (Yes/No)", "");
                String again;
                while(true){
                    System.out.printf("|%-5sEnter: ","");
                    again = input.nextLine();
                    if(again.equalsIgnoreCase("Yes")){
                        exit2 = false;
                        exit = true;
                        break;
                    }else if(again.equalsIgnoreCase("No")){
                        exit2 = false;
                        exit = false;
                        break;
                    }
                }
            }
        }while(exit);
    }
    private void Delete(){
        boolean exit = true;
        do{
            boolean exit2 = true;
            int id;
            view();
            while(true){
                System.out.printf("|%-5sEnter ID: ","");
                try{
                    id = input.nextInt();
                    String SQL = "SELECT COUNT (*) FROM Incident_Types Where i_id = ?";
                    if(conf.recordExists(SQL, id)){
                        break;
                    }else if(id == 0){
                        exit = false;
                        exit2 = false;
                        break;
                    }else{
                        System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Incident Types!!", "");
                    }
                }catch(Exception e){
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                    input.nextLine();
                }
            }
            while(exit2){
                String SQL = "DELETE FROM Incident_Types Where i_id = ?";
                conf.deleteRecord(SQL, id);
                input.nextLine();
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-6s%-38s%-6s|\n", "", "Delete Another Incident Type? (Yes/No)", "");
                String again;
                while(true){
                    System.out.printf("|%-5sEnter: ","");
                    again = input.nextLine();
                    if(again.equalsIgnoreCase("Yes")){
                        exit2 = false;
                        exit = true;
                        break;
                    }else if(again.equalsIgnoreCase("No")){
                        exit2 = false;
                        exit = false;
                        break;
                    }
                }
            }
        }while(exit);
        
    }
    public void view(){
        String tbl_view = "SELECT * FROM Incident_Types";
        String[] tbl_Headers = {"ID", "Incident Name", "Description"};
        String[] tbl_Columns = {"i_id", "i_name", "i_description"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
    }
    private static int IntegerValidation(Scanner input){
        int num;
        System.out.printf("|%-5sEnter: ","");
        while(true){
            try{
                num = input.nextInt();
                if(num>=0){
                    break;
                }else{
                    System.out.printf("|%-8s%-34s%-8s|\n", "", "!!Negative Value Is Not Accepted!!", "");
                    System.out.printf("|%-5sEnter Again: ","");
                }
            }catch(Exception e){
                System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                input.nextLine();
                System.out.printf("|%-5sEnter Again: ","");
            }
        }
        return num;
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
