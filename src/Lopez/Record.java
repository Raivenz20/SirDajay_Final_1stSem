package Lopez;

import java.util.Scanner;

public class Record {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    Blotter_Report br = new Blotter_Report();
    Citizen cit = new Citizen();
    
    public void record(){
        boolean exit = true;
        do{
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-15s%-20s%-15s|\n", "", "**BARANGAY RECORDS**", "");
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-18s%-13s%-19s|\n", "", "Select Option", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "1. General", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "2. Spicific Report", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "3. Back", "");
            System.out.println("+--------------------------------------------------+");
            int choices = 3;
            int option = OptionValidation(input, choices);
            System.out.println("+--------------------------------------------------+");
            switch(option){
                case 1:
                    generalReport();
                break;
                case 2:
                    Specific();
                break;
                default:
                    exit = false;
                break;
            }
        }while(exit);
    }
    private void generalReport(){
        System.out.println("+----------------------------------------------------------------------------------------------------+");
        System.out.printf("|%-39s%-22s%-39s|\n", "", "==--General Report--==", "");
        br.View();
    }
    private void Specific(){
        int id, id2;
        boolean exit = true;
        while(exit){
            boolean exit2 = true;
            cit.view();
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-8s%-34s%-8s|\n", "", "**Select Citizen ID to View Info**", "");
            System.out.println("+--------------------------------------------------+");
            while(true){
                System.out.printf("|%-5sEnter ID: ","");
                try{
                    id = input.nextInt();
                    String SQL = "SELECT COUNT (*) FROM Citizen_List Where c_id = ?";
                    if(conf.recordExists(SQL, id)){
                        break;
                    }else if(id == 0){
                        exit = false;
                        exit2 = false;
                        break;
                    }else{
                        System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Id Does Not Exist In Citizen List!!", "");
                    }
                }catch(Exception e){
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                    input.nextLine();
                }
            }
            while(exit2){
                String c_fname = "SELECT c_fname FROM Citizen_List Where c_id = ?";
                String fname = conf.reportVariables(c_fname, id);
                String c_mname = "SELECT c_mname FROM Citizen_List Where c_id = ?";
                String mname = conf.reportVariables(c_mname, id);
                String c_lname = "SELECT c_lname FROM Citizen_List Where c_id = ?";
                String lname = conf.reportVariables(c_lname, id);
                String c_contact = "SELECT c_contact FROM Citizen_List Where c_id = ?";
                String contact = conf.reportVariables(c_contact, id);
                String c_bdate = "SELECT c_bdate FROM Citizen_List Where c_id = ?";
                String bdate = conf.reportVariables(c_bdate, id);
                String c_gender = "SELECT c_gender FROM Citizen_List Where c_id = ?";
                String gender = conf.reportVariables(c_gender, id);
                String c_address = "SELECT c_address FROM Citizen_List Where c_id = ?";
                String address = conf.reportVariables(c_address, id);
                String c_rdate = "SELECT c_rdate FROM Citizen_List Where c_id = ?";
                String rdate = conf.reportVariables(c_rdate, id);
                
                System.out.println("+----------------------------------------------------------------------+");
                System.out.printf("|%-19s%-32s%-19s|\n", "", "==--Citizen Full Information--==", "");
                System.out.println("+----------------------------------------------------------------------+");
                System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "First Name", ":", "", fname, "");
                System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Middle Name", ":", "", mname, "");
                System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Last Name", ":", "", lname, "");
                System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Contact", ":", "", contact, "");
                System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Gender", ":", "", gender, "");
                System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Address", ":", "", address, "");
                System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Birth Date", ":", "", bdate, "");
                System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Registered Date", ":", "", rdate, "");
                
                System.out.println("+----------------------------------------------------------------------+");
                System.out.printf("|%-22s%-25s%-23s|\n", "", "==--Violation History--==", "");
                /*String tbl_view = "SELECT Incident_Types.i_name, Incident_Types.i_description, "
                        + "Blotter_Report.r_fname, Blotter_Report.r_lname, Blotter_Report.r_status, Blotter_Report.r_location, "
                        + "(Blotter_Report.r_fname ||' '|| Blotter_Report.r_lname) AS RepFullname, "
                        + "Blotter_Report.r_incident_date, Blotter_Report.r_reported_date, Blotter_Report.r_solution, Blotter_Report.r_id, "
                        + "Officer_List.o_fname, Officer_List.o_lname, Officer_List.o_position, Officer_List.o_contact, Officer_List.o_gender, "
                        + "(Officer_List.o_fname ||' '|| Officer_List.o_lname) AS OffFullname "
                        + "FROM Blotter_Report "
                        + "INNER JOIN Citizen_List ON Blotter_Report.c_id = Citizen_List.c_id "
                        + "INNER JOIN Incident_Types ON Blotter_Report.i_id = Incident_Types.i_id "
                        + "INNER JOIN Officer_List ON Blotter_Report.o_id = Officer_List.o_id "
                        + "Where Citizen_List.c_id = "+id;
                        
                String[] tbl_Headers = {"Report ID", "Reporter Name", "Officer Name", "Status", "Report Name", "Incident Location", "Incident Date", "Reported Date", "Solution"};
                String[] tbl_Columns = {"r_id", "RepFullname", "OffFullname", "r_status", "i_name", "r_location", "r_incident_date", "r_reported_date", "r_solution"};
                conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
                */
                String tbl_view = "SELECT Incident_Types.i_name,"
                        + " Blotter_Report.r_status, Blotter_Report.r_id,"
                        + " (Officer_List.o_fname ||' '|| Officer_List.o_lname) AS OffFullname"
                        + " From Blotter_Report "
                        + " INNER JOIN Citizen_List ON Blotter_Report.c_id = Citizen_List.c_id"
                        + " INNER JOIN Incident_Types ON Blotter_Report.i_id = Incident_Types.i_id"
                        + " INNER JOIN Officer_List ON Blotter_Report.o_id = Officer_List.o_id"
                        + " Where Citizen_List.c_id = "+id;
                String[] tbl_Headers = {"Report ID", "Incident Name", "Status", "Officer Name"};
                String[] tbl_Columns = {"r_id", "i_name", "r_status", "OffFullname"};
                conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
                boolean exit3 = true;
                while(true){
                    System.out.printf("|%-5sEnter ID of Specific Information (0 to exit): ","");
                    try{
                        id2 = input.nextInt();
                        String SQL = "SELECT COUNT (*) FROM Blotter_Report Where r_id = ? And c_id = "+id;
                        if(conf.recordExists(SQL, id2)){
                            break;
                        }else if(id2 == 0){
                            exit3 = false;
                            break;
                        }else{
                            System.out.printf("|%-2s%-45s%-3s|\n", "", "!!Id Does Not Match to Existing Information!!", "");
                        }
                    }catch(Exception e){
                        System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                        input.nextLine();
                    }
                }
                while(exit3){
                    String r_fname = "SELECT r_fname FROM Blotter_Report Where r_id = ?";
                    String r_fname2 = conf.reportVariables(r_fname, id2);
                    String r_lname = "SELECT r_lname FROM Blotter_Report Where r_id = ?";
                    String r_lname2 = conf.reportVariables(r_lname, id2);
                    String r_location = "SELECT r_location FROM Blotter_Report Where r_id = ?";
                    String r_location2 = conf.reportVariables(r_location, id2);
                    String r_status = "SELECT r_status FROM Blotter_Report Where r_id = ?";
                    String r_status2 = conf.reportVariables(r_status, id2);
                    String r_incident_date = "SELECT r_incident_date FROM Blotter_Report Where r_id = ?";
                    String r_incident_date2 = conf.reportVariables(r_incident_date, id2);
                    String r_reported_date = "SELECT r_reported_date FROM Blotter_Report Where r_id = ?";
                    String r_reported_date2 = conf.reportVariables(r_reported_date, id2);
                    System.out.println("+----------------------------------------------------------------------+");
                    System.out.printf("|%-18s%-34s%-18s|\n", "", "==--Full Information Of Report--==", "");
                    System.out.println("+----------------------------------------------------------------------+");
                    System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Reporter First Name", ":", "", r_fname2, "");
                    System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Reporter Last Name", ":", "", r_lname2, "");
                    System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Location", ":", "", r_location2, "");
                    System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Status", ":", "", r_status2, "");
                    System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Incident Date", ":", "", r_incident_date2, "");
                    System.out.printf("|%-5s%-20s%-1s%-2s%-34s%-8s|\n", "", "Reported Date", ":", "", r_reported_date2, "");
                    System.out.println("+----------------------------------------------------------------------+");
                    exit3 = false;
                }
                exit2 = false;
                exit = false;
            }
        }
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
