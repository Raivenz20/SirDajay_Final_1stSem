package Lopez;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Blotter_Report {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    Citizen cit = new Citizen();
    Incident_Type it = new Incident_Type();
    Officer off = new Officer();
    LocalDate cdate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate repdate;
    
    
    
    public void blotter(){
        boolean exit = true;
        do{
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-16s%-17s%-17s|\n", "", "**MAKE A REPORT**", "");
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-18s%-13s%-19s|\n", "", "Select Option", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "1. Add Report", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "2. Edit Report", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "3. Confirm Pending Report", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "4. Mark Report as Solved", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "5. View All Report List", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "6. View Pending Report", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "7. View Ongoing Report", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "8. View Cleared Report", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "9. Back", "");
            System.out.println("+--------------------------------------------------+");
            int choices = 9;
            int option = OptionValidation(input, choices);
            System.out.println("+--------------------------------------------------+");
            switch(option){
                case 1:
                    Add();
                break;
                case 2:
                    EditInformation();
                break;
                case 3:
                    ConfirmReport();
                break;
                case 4:
                    SolvedReport();
                break;
                case 5:
                    View();
                break;
                case 6:
                    ViewPending();
                break;
                case 7:
                    ViewOngoing();
                break;
                case 8:
                    ViewCleared();
                break;
                default:
                    exit = false;
                break;
            }
        }while(exit);
    }
    private void Add(){
        boolean exit = true;
        do{
            input.nextLine();
            System.out.println("+--------------------------------------------------+----------------+");
            System.out.printf("|%-62s|\n", "The Person Making The Report Is A Registered Citizen? (Yes/No/Back)");
            String CitOrNot;
            int CitOrNot2;
            while(true){
                System.out.printf("|%-5sEnter: ","");
                CitOrNot = input.nextLine();
                if(CitOrNot.equalsIgnoreCase("Yes")){
                    CitOrNot2 = 1;
                    break;
                }else if(CitOrNot.equalsIgnoreCase("No")){
                    CitOrNot2 = 2;
                    break;
                }else if(CitOrNot.equalsIgnoreCase("Back")){
                    CitOrNot2 = 3;
                    break;
                }
            }
            System.out.println("+--------------------------------------------------+----------------+");
            int rcid, cid, oid, iid;
            String SQL, date, location, stat;
            String fname, lname;
            switch(CitOrNot2){
                case 1:
                    cit.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-10s%-29s%-11s|\n", "", "**Select ID of The Reporter**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            rcid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Citizen_List Where c_id = ?";
                            if(conf.recordExists(SQL, rcid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    cit.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-6s%-37s%-7s|\n", "", "**Select ID of The Reported Citizen**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            cid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Citizen_List Where c_id = ?";
                            if(cid == rcid){
                                System.out.printf("|%-7s%-35s%-8s|\n", "", "**Warning, Cant Report One's Self**", "");
                            }else if(conf.recordExists(SQL, cid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    while(true){
                        System.out.printf("%-5sIncident Incident Date(YYYY-MM-DD): ","");
                        try{
                            date = input.next();
                            repdate = LocalDate.parse(date,dateFormat);
                            if(repdate.isBefore(cdate)||repdate.isEqual(cdate)){
                                break;
                            }else{
                                System.out.printf("|%-3s%-44s%-3s|\n", "", "Date did not Occur Yet! Enter Today or Past!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-17s%-16s%-17s|\n", "", "!!Invalid Date!!", "");
                        }
                    }
                    input.nextLine();
                    System.out.printf("%-5sIncident Location: ","");
                    location = input.nextLine();
                    it.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-9s%-32s%-9s|\n", "", "**Select ID of The Incident Type**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            iid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Incident_Types Where i_id = ?";
                            if(conf.recordExists(SQL, iid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Incident Types!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    off.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-5s%-40s%-5s|\n", "", "**Select ID of The Officer That Handle**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            oid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Officer_List Where o_id = ?";
                            if(conf.recordExists(SQL, oid)){
                                break;
                            }else{
                                System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Id Does Not Exist In Officer List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    input.nextLine();
                    fname = conf.getFName(rcid);
                    lname = conf.getLName(rcid);
                    stat = "Pending";
                    SQL = "INSERT INTO Blotter_Report (c_id, o_id, i_id, r_fname, r_lname, r_location, r_status, r_incident_date, r_reported_date) Values (?,?,?,?,?,?,?,?,?)";
                    conf.addRecord(SQL, cid, oid, iid, fname, lname, location, stat, repdate, cdate);
                break;
                case 2:
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-13s%-24s%-13s|\n", "", "**Reporter Information**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sFirst Name: ","");
                        fname = input.nextLine();
                        if(CheckValidName(fname)){
                            break;
                        }else{
                            System.out.printf("|%-11s%-28s%-11s|\n", "", "!!Enter A Valid First Name!!", "");
                        }
                    }
                    while(true){
                        System.out.printf("|%-5sLast Name: ","");
                        lname = input.nextLine();
                        if(CheckValidName(lname)){
                            break;
                        }else{
                            System.out.printf("|%-11s%-27s%-12s|\n", "", "!!Enter A Valid Last Name!!", "");
                        }
                    }
                    cit.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-7s%-35s%-8s|\n", "", "**Select ID of The Reported Citizen**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            cid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Citizen_List Where c_id = ?";
                            if(conf.recordExists(SQL, cid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    while(true){
                        System.out.printf("|%-5sIncident Incident Date(YYYY-MM-DD): ","");
                        try{
                            date = input.next();
                            repdate = LocalDate.parse(date,dateFormat);
                            if(repdate.isBefore(cdate)||repdate.isEqual(cdate)){
                                break;
                            }else{
                                System.out.printf("|%-3s%-44s%-3s|\n", "", "Date did not Occur Yet! Enter Today or Past!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-17s%-16s%-17s|\n", "", "!!Invalid Date!!", "");
                        }
                    }
                    input.nextLine();
                    System.out.printf("|%-5sIncident Location: ","");
                    location = input.nextLine();
                    it.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-9s%-32s%-9s|\n", "", "**Select ID of The Incident Type**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            iid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Incident_Types Where i_id = ?";
                            if(conf.recordExists(SQL, iid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Incident Types!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    off.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-5s%-40s%-5s|\n", "", "**Select ID of The Officer That Handle**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            oid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Officer_List Where o_id = ?";
                            if(conf.recordExists(SQL, oid)){
                                break;
                            }else{
                                System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Id Does Not Exist In Officer List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    input.nextLine();
                    stat = "Pending";
                    SQL = "INSERT INTO Blotter_Report (c_id, o_id, i_id, r_fname, r_lname, r_location, r_status, r_incident_date, r_reported_date) Values (?,?,?,?,?,?,?,?,?)";
                    conf.addRecord(SQL, cid, oid, iid, fname, lname, location, stat, repdate, cdate);
                break;
                default:
                    exit = false;
                break;
            }
            if(CitOrNot2 != 3){
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-11s%-28s%-11s|\n", "", "Add Another Report? (Yes/No)", "");
                String again;
                while(true){
                    System.out.printf("|%-5sEnter: ","");
                    again = input.nextLine();
                    if(again.equalsIgnoreCase("Yes")){
                        exit = true;
                        break;
                    }else if(again.equalsIgnoreCase("No")){
                        exit = false;
                        break;
                    }
                }
            }
        }while(exit);
    }
    
    private void EditInformation(){
        boolean exit = true;
        String choice;
        int id;
        do{
            boolean exit2 = true;
            View();
            while(true){
                System.out.printf("|%-5sEnter ID: ","");
                try{
                    id = input.nextInt();
                    String SQL = "SELECT COUNT (*) FROM Blotter_Report Where r_id = ?";
                    if(conf.recordExists(SQL, id)){
                        break;
                    }else if(id == 0){
                        exit = false;
                        exit2 = false;
                        break;
                    }else{
                        System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Blotter Report!!", "");
                    }
                }catch(Exception e){
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                    input.nextLine();
                }
            }
            while(exit2){
                String stat = conf.getStatus(id);
                if(stat.equalsIgnoreCase("Cleared")){
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-1s%-47s%-2s|\n", "", "!!This Report Is Already Cleared, Cannot Edit!!", "");
                    System.out.println("+--------------------------------------------------+");
                    exit2 = false;
                }else{
                    input.nextLine();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-4s%-42s%-4s|\n", "", "Do You Want To Change Everything? (Yes/No)", "");
                    while(true){
                        System.out.printf("|%-5sEnter: ","");
                        choice = input.nextLine();
                        if(choice.equalsIgnoreCase("Yes")||choice.equalsIgnoreCase("No")){
                            break;
                        }
                    }
                    int option;
                    if(choice.equalsIgnoreCase("yes")){
                        option = 8;
                    }else{
                        System.out.println("+--------------------------------------------------+");
                        System.out.printf("|%-18s%-13s%-19s|\n", "", "Select Option", "");
                        System.out.printf("|%-5s%-30s%-15s|\n", "", "1. Change Reported Citizen", "");
                        System.out.printf("|%-5s%-30s%-15s|\n", "", "2. Change Reporter Info", "");
                        System.out.printf("|%-5s%-30s%-15s|\n", "", "3. Change Location", "");
                        System.out.printf("|%-5s%-30s%-15s|\n", "", "4. Change Incident Date", "");
                        System.out.printf("|%-5s%-30s%-15s|\n", "", "5. Change Type of Incident", "");
                        System.out.printf("|%-5s%-30s%-15s|\n", "", "6. Change Officer", "");
                        System.out.printf("|%-5s%-30s%-15s|\n", "", "7. Back", "");
                        System.out.println("+--------------------------------------------------+");
                        int choices = 7;
                        option = OptionValidation(input, choices);
                        System.out.println("+--------------------------------------------------+");
                        input.nextLine();
                    }
                    switch(option){
                        case 1:
                            EditReportedCit(id);
                        break;
                        case 2:
                            EditReporterInfo(id);
                        break;
                        case 3:
                            EditLocation(id);
                        break;
                        case 4:
                            EditDate(id);
                        break;
                        case 5:
                            EditType(id);
                        break;
                        case 6:
                            EditOfficer(id);
                        break;
                        case 8:
                            EditAll(id);
                        break;
                        default:
                            exit2 = false;
                            exit = false;
                        break;
                    }
                    if(option!=7){
                        System.out.println("+--------------------------------------------------+");
                        System.out.printf("|%-10s%-19s%-11s|\n", "", "Edit Another Report? (Yes/No)", "");
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
                }
            }
        }while(exit);
    }
    private void EditAll(int id){
        boolean exit = true;
        do{
            System.out.println("+--------------------------------------------------+----------------+");
            System.out.printf("|%-62s|\n", "The Person Making The Report Is A Registered Citizen? (Yes/No/Back)");
            String CitOrNot;
            int CitOrNot2;
            while(true){
                System.out.printf("|%-5sEnter: ","");
                CitOrNot = input.nextLine();
                if(CitOrNot.equalsIgnoreCase("Yes")){
                    CitOrNot2 = 1;
                    break;
                }else if(CitOrNot.equalsIgnoreCase("No")){
                    CitOrNot2 = 2;
                    break;
                }else if(CitOrNot.equalsIgnoreCase("Back")){
                    CitOrNot2 = 3;
                    break;
                }
            }
            System.out.println("+--------------------------------------------------+----------------+");
            int rcid, cid, oid, iid;
            String SQL, date, location;
            String fname, lname;
            switch(CitOrNot2){
                case 1:
                    cit.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-10s%-29s%-11s|\n", "", "**Select ID of The Reporter**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            rcid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Citizen_List Where c_id = ?";
                            if(conf.recordExists(SQL, rcid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    cit.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-6s%-37s%-7s|\n", "", "**Select ID of The Reported Citizen**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            cid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Citizen_List Where c_id = ?";
                            if(rcid == cid){
                                System.out.printf("|%-7s%-35s%-8s|\n", "", "**Warning, Cant Report One's Self**", "");
                            }else if(conf.recordExists(SQL, cid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    while(true){
                        System.out.printf("|%-5sIncident Incident Date(YYYY-MM-DD): ","");
                        try{
                            date = input.next();
                            repdate = LocalDate.parse(date,dateFormat);
                            if(repdate.isBefore(cdate)||repdate.isEqual(cdate)){
                                break;
                            }else{
                                System.out.printf("|%-3s%-44s%-3s|\n", "", "Date did not Occur Yet! Enter Today or Past!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-17s%-16s%-17s|\n", "", "!!Invalid Date!!", "");
                        }
                    }
                    input.nextLine();
                    System.out.printf("|%-5sIncident Location: ","");
                    location = input.nextLine();
                    it.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-8s%-34s%-8s|\n", "", "**Select ID of The Incident Type**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            iid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Incident_Types Where i_id = ?";
                            if(conf.recordExists(SQL, iid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Incident Types!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    off.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-5s%-40s%-5s|\n", "", "**Select ID of The Officer That Handle**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            oid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Officer_List Where o_id = ?";
                            if(conf.recordExists(SQL, oid)){
                                break;
                            }else{
                                System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Id Does Not Exist In Officer List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    input.nextLine();
                    fname = conf.getFName(rcid);
                    lname = conf.getLName(rcid);
                    SQL = "UPDATE Blotter_Report SET c_id = ?, rc_id = ?, o_id = ?, i_id = ?, r_fname = ?, r_lname = ?, r_location = ?, r_incident_date = ? Where r_id = ?";
                    conf.updateRecord(SQL, cid, rcid, oid, iid, fname, lname, location, repdate, id);
                    exit = false;
                break;
                case 2:
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-13s%-24s%-13s|\n", "", "**Reporter Information**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sFirst Name: ","");
                        fname = input.nextLine();
                        if(CheckValidName(fname)){
                            break;
                        }else{
                            System.out.printf("|%-11s%-28s%-11s|\n", "", "!!Enter A Valid First Name!!", "");
                        }
                    }
                    while(true){
                        System.out.printf("|%-5sLast Name: ","");
                        lname = input.nextLine();
                        if(CheckValidName(lname)){
                            break;
                        }else{
                            System.out.printf("|%-11s%-27s%-12s|\n", "", "!!Enter A Valid Last Name!!", "");
                        }
                    }
                    cit.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-7s%-35s%-8s|\n", "", "**Select ID of The Reported Citizen**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            cid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Citizen_List Where c_id = ?";
                            if(conf.recordExists(SQL, cid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    while(true){
                        System.out.printf("%-5sIncident Incident Date(YYYY-MM-DD): ","");
                        try{
                            date = input.next();
                            repdate = LocalDate.parse(date,dateFormat);
                            if(repdate.isBefore(cdate)||repdate.isEqual(cdate)){
                                break;
                            }else{
                                System.out.printf("|%-3s%-44s%-3s|\n", "", "Date did not Occur Yet! Enter Today or Past!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-17s%-16s%-17s|\n", "", "!!Invalid Date!!", "");
                        }
                    }
                    input.nextLine();
                    System.out.printf("%-5sIncident Location: ","");
                    location = input.nextLine();
                    it.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-9s%-32s%-9s|\n", "", "**Select ID of The Incident Type**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            iid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Incident_Types Where i_id = ?";
                            if(conf.recordExists(SQL, iid)){
                                break;
                            }else{
                                System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Incident Types!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    off.view();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-5s%-40s%-5s|\n", "", "**Select ID of The Officer That Handle**", "");
                    System.out.println("+--------------------------------------------------+");
                    while(true){
                        System.out.printf("|%-5sEnter ID: ","");
                        try{
                            oid = input.nextInt();
                            SQL = "SELECT COUNT (*) FROM Officer_List Where o_id = ?";
                            if(conf.recordExists(SQL, oid)){
                                break;
                            }else{
                                System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Id Does Not Exist In Officer List!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                            input.nextLine();
                        }
                    }
                    input.nextLine();
                    SQL = "UPDATE Blotter_Report SET c_id = ?, rc_id = ? o_id = ?, i_id = ?, r_fname = ?, r_lname = ?, r_location = ?, r_incident_date = ? Where r_id = ?";
                    conf.updateRecord(SQL, cid, 0, oid, iid, fname, lname, location, repdate, id);
                    exit = false;
                break;
                default:
                    exit = false;
                break;
            }
        }while(exit);
    }
    private void EditReportedCit(int id){
        boolean exit = true;
        int rcid = conf.ReturnID1(id);
        int cid;
        cit.view();
        System.out.println("+--------------------------------------------------+");
        System.out.printf("|%-7s%-35s%-8s|\n", "", "**Select ID of The Reported Citizen**", "");
        System.out.println("+--------------------------------------------------+");
        while(true){
            System.out.printf("|%-5sEnter ID: ","");
            try{
                cid = input.nextInt();
                String SQL = "SELECT COUNT (*) FROM Citizen_List Where c_id = ?";
                if(rcid == cid){
                    System.out.printf("|%-7s%-35s%-8s|\n", "", "**Warning, Cant Report One's Self**", "");
                }else if(conf.recordExists(SQL, cid)){
                    break;
                }else if(cid == 0){
                    exit = false;
                    break;
                }else{
                    System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                input.nextLine();
            }
        }
        input.nextLine();
        while(exit){
            String SQL = "UPDATE Blotter_Report SET rc_id = ?, c_id = ? Where r_id = ?";
            conf.updateRecord(SQL, rcid, cid, id);
            exit = false;
        }
    }
    private void EditReporterInfo(int id){
        System.out.println("+--------------------------------------------------+----------------+");
        System.out.printf("|%-67s|\n", "The Person Making The Report Is A Registered Citizen? (Yes/No)");
        String CitOrNot;
        int CitOrNot2;
        int rcid,cid;
        String SQL;
        String fname = null, lname = null;
        while(true){
            System.out.printf("|%-5sEnter: ","");
            CitOrNot = input.nextLine();
            if(CitOrNot.equalsIgnoreCase("Yes")){
                CitOrNot2 = 1;
                break;
            }else if(CitOrNot.equalsIgnoreCase("No")){
                CitOrNot2 = 2;
                break;
            }else{
                System.out.printf("|%-13s%-24s%-13s|\n", "", "!!Enter Correct Choice!!", "");
            }
        }
        System.out.println("+--------------------------------------------------+----------------+");
        cid = conf.ReturnID2(id);
        switch(CitOrNot2){
            case 1:
                cit.view();
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-10s%-29s%-11s|\n", "", "**Select ID of The Reporter**", "");
                System.out.println("+--------------------------------------------------+");
                
                while(true){
                    System.out.printf("|%-5sEnter ID: ","");
                    try{
                        rcid = input.nextInt();
                        SQL = "SELECT COUNT (*) FROM Citizen_List Where c_id = ?";
                        if(cid == rcid){
                            System.out.printf("|%-7s%-35s%-8s|\n", "", "**Warning, Cant Report One's Self**", "");
                        }else if(conf.recordExists(SQL, rcid)){
                            break;
                        }else{
                            System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                        }
                    }catch(Exception e){
                        System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                        input.nextLine();
                    }
                }
                input.nextLine();
                fname = conf.getFName(rcid);
                lname = conf.getLName(rcid);
                SQL = "UPDATE Blotter_Report SET c_id = ?, rc_id = ?, r_fname = ?, r_lname = ? Where r_id = ?";
                conf.updateRecord(SQL, cid, rcid, fname, lname, id);
            break;
            case 2:
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-13s%-24s%-13s|\n", "", "**Reporter Information**", "");
                System.out.println("+--------------------------------------------------+");
                while(true){
                    System.out.printf("|%-5sFirst Name: ","");
                    fname = input.nextLine();
                    if(CheckValidName(fname)){
                        break;
                    }else{
                        System.out.printf("|%-11s%-28s%-11s|\n", "", "!!Enter A Valid First Name!!", "");
                    }
                }
                while(true){
                    System.out.printf("|%-5sLast Name: ","");
                    lname = input.nextLine();
                    if(CheckValidName(lname)){
                        break;
                    }else{
                        System.out.printf("|%-11s%-27s%-12s|\n", "", "!!Enter A Valid Last Name!!", "");
                    }
                }
                SQL = "UPDATE Blotter_Report SET rc_id = ?, r_fname = ?, r_lname = ? Where r_id = ?";
                conf.updateRecord(SQL, 0, fname, lname, id);
            break;
            default:
            break;
        }
    }
    private void EditLocation(int id){
        String location;
        System.out.printf("%-5sIncident Location: ","");
        location = input.nextLine();
        String SQL = "UPDATE Blotter_Report SET r_location = ? Where r_id = ?";
        conf.updateRecord(SQL, location, id);
    }
    private void EditDate(int id){
        String date;
        while(true){
            System.out.printf("%-5sIncident Incident Date(YYYY-MM-DD): ","");
            try{
                date = input.next();
                repdate = LocalDate.parse(date,dateFormat);
                if(repdate.isBefore(cdate)||repdate.isEqual(cdate)){
                    break;
                }else{
                    System.out.printf("|%-3s%-44s%-3s|\n", "", "Date did not Occur Yet! Enter Today or Past!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-17s%-16s%-17s|\n", "", "!!Invalid Date!!", "");
            }
        }
        input.nextLine();
        String SQL = "UPDATE Blotter_Report SET r_incident_date = ? Where r_id = ?";
        conf.updateRecord(SQL, repdate, id);
    }
    private void EditType(int id){
        boolean exit = true;
        int iid;
        it.view();
        System.out.println("+--------------------------------------------------+");
        System.out.printf("|%-9s%-32s%-9s|\n", "", "**Select ID of The Incident Type**", "");
        System.out.println("+--------------------------------------------------+");
        while(true){
            System.out.printf("|%-5sEnter ID: ","");
            try{
                iid = input.nextInt();
                String SQL = "SELECT COUNT (*) FROM Incident_Types Where i_id = ?";
                if(conf.recordExists(SQL, iid)){
                    break;
                }else if(iid == 0){
                    exit = false;
                    break;
                }else{
                    System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Incident Types!!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                input.nextLine();
            }
        }
        while(exit){
            input.nextLine();
            String SQL = "UPDATE Blotter_Report SET i_id = ? Where r_id = ?";
            conf.updateRecord(SQL, iid, id);
            exit = false;
        }
    }
    private void EditOfficer(int id){
        int oid;
        boolean exit = true;
        off.view();
        System.out.println("+--------------------------------------------------+");
        System.out.printf("|%-5s%-40s%-5s|\n", "", "**Select ID of The Officer That Handle**", "");
        System.out.println("+--------------------------------------------------+");
        while(true){
            System.out.printf("|%-5sEnter ID: ","");
            try{
                oid = input.nextInt();
                String SQL = "SELECT COUNT (*) FROM Officer_List Where o_id = ?";
                if(conf.recordExists(SQL, oid)){
                    break;
                }else if(oid == 0){
                    exit = false;
                    break;
                }else{
                    System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Id Does Not Exist In Officer List!!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                input.nextLine();
            }
        }
        while(exit){
            input.nextLine();
            String SQL = "UPDATE Blotter_Report SET o_id = ? Where r_id = ?";
            conf.updateRecord(SQL, oid, id);
            exit = false;
        }
    }
    
    private void ConfirmReport(){
        boolean exit = true;
        int id;
        String stat = "Ongoing";
        ViewPending();
        System.out.println("+--------------------------------------------------+");
        System.out.printf("|%-14s%-22s%-14s|\n", "", "**Select ID To Confirm**", "");
        System.out.println("+--------------------------------------------------+");
        while(true){
            System.out.printf("|%-5sEnter ID: ","");
            try{
                id = input.nextInt();
                String SQL = "SELECT COUNT (*) FROM Blotter_Report Where r_id = ? And r_status = 'Pending'";
                if(conf.recordExists(SQL, id)){
                    break;
                }else if(id == 0){
                    exit = false;
                    break;
                }else{
                    System.out.printf("|%-50s|\n", "", "!!Id Does Not Exist In Blotter Pending Report!!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                input.nextLine();
            }
        }
        while(exit){
            String SQL = "UPDATE Blotter_Report SET r_status = ? Where r_id = ?";
            conf.updateRecord(SQL, stat, id);
            exit = false;
        }
    }
    private void SolvedReport(){
        boolean exit = true;
        int id;
        String stat = "Cleared";
        ViewOngoing();
        System.out.println("+--------------------------------------------------+");
        System.out.printf("|%-14s%-22s%-14s|\n", "", "**Select ID To Clear**", "");
        System.out.println("+--------------------------------------------------+");
        while(true){
            System.out.printf("|%-5sEnter ID: ","");
            try{
                id = input.nextInt();
                String SQL = "SELECT COUNT (*) FROM Blotter_Report Where r_id = ? And r_status = 'Ongoing'";
                if(conf.recordExists(SQL, id)){
                    break;
                }else if(id == 0){
                    exit = false;
                    break;
                }else{
                    System.out.printf("|%-50s|\n", "", "!!Id Does Not Exist In Blotter Ongoing Report!!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                input.nextLine();
            }
        }
        while(exit){
            input.nextLine();
            System.out.printf("|%-5sSolution: ","");
            String solution = input.nextLine();
            String SQL = "UPDATE Blotter_Report SET r_status = ?, r_solution = ? Where r_id = ?";
            conf.updateRecord(SQL, stat, solution, id);
            exit = false;
        }
    }
    private void ViewPending(){
        String tbl_view = "SELECT Citizen_List.c_fname, Citizen_List.c_lname, "
                + "Incident_Types.i_name, "
                + "Blotter_Report.r_status, Blotter_Report.r_id "
                + "FROM Blotter_Report "
                + "INNER JOIN Citizen_List ON Blotter_Report.c_id = Citizen_List.c_id "
                + "INNER JOIN Incident_Types ON Blotter_Report.i_id = Incident_Types.i_id "
                + "Where Blotter_Report.r_status = 'Pending'";
        String[] tbl_Headers = {"ID", "First Name", "Last Name", "Status", "Report Name"};
        String[] tbl_Columns = {"r_id", "c_fname", "c_lname", "r_status", "i_name"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
    }
    private void ViewOngoing(){
        String tbl_view = "SELECT Citizen_List.c_fname, Citizen_List.c_lname, "
                + "Incident_Types.i_name, "
                + "Blotter_Report.r_status, Blotter_Report.r_id "
                + "FROM Blotter_Report "
                + "INNER JOIN Citizen_List ON Blotter_Report.c_id = Citizen_List.c_id "
                + "INNER JOIN Incident_Types ON Blotter_Report.i_id = Incident_Types.i_id "
                + "Where Blotter_Report.r_status = 'Ongoing'";
        String[] tbl_Headers = {"ID", "First Name", "Last Name", "Status", "Report Name"};
        String[] tbl_Columns = {"r_id", "c_fname", "c_lname", "r_status", "i_name"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
    }
    private void ViewCleared(){
        String tbl_view = "SELECT Citizen_List.c_fname, Citizen_List.c_lname, "
                + "Incident_Types.i_name, "
                + "Blotter_Report.r_status, Blotter_Report.r_id "
                + "FROM Blotter_Report "
                + "INNER JOIN Citizen_List ON Blotter_Report.c_id = Citizen_List.c_id "
                + "INNER JOIN Incident_Types ON Blotter_Report.i_id = Incident_Types.i_id "
                + "Where Blotter_Report.r_status = 'Cleared'";
        String[] tbl_Headers = {"ID", "First Name", "Last Name", "Status", "Report Name"};
        String[] tbl_Columns = {"r_id", "c_fname", "c_lname", "r_status", "i_name"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
    }
    
    
    
    public void View(){
        String tbl_view = "SELECT Citizen_List.c_fname, Citizen_List.c_lname, "
                + "Incident_Types.i_name, "
                + "Blotter_Report.r_fname, Blotter_Report.r_lname, Blotter_Report.r_status, Blotter_Report.r_id "
                + "FROM Blotter_Report "
                + "INNER JOIN Citizen_List ON Blotter_Report.c_id = Citizen_List.c_id "
                + "INNER JOIN Incident_Types ON Blotter_Report.i_id = Incident_Types.i_id";
        String[] tbl_Headers = {"ID", "First Name", "Last Name", "Status", "Report Name"};
        String[] tbl_Columns = {"r_id", "c_fname", "c_lname", "r_status", "i_name"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
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
    private boolean CheckValidName(String name){
        if(name.isEmpty()) {
            return false;
        }
        if(Character.isUpperCase(name.charAt(0))) {
            return name.matches("[a-zA-Z]+");
        }
        return false;
    }
}

