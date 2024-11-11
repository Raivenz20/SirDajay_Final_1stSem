package Lopez;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.sql.*;

public class Citizen {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    LocalDate cdate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    
    
    public void Citizen_List(){
        boolean exit = true;
        do{
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-15s%-20s%-15s|\n", "", "**MANAGE CITIZEN'S**", "");
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-18s%-13s%-19s|\n", "", "Select Option", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "1. Add Citizen", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "2. Edit Citizen", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "3. Delete Citizen", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "4. View Citizen List", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "5. Back", "");
            System.out.println("+--------------------------------------------------+");
            int choices = 5;
            int option = OptionValidation(input, choices);
            System.out.println("+--------------------------------------------------+");
            switch(option){
                case 1:
                    AddCitizen();
                break;
                case 2:
                    EditCitizen();
                break;
                case 3:
                    DeleteCitizen();
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
    private void AddCitizen(){
        boolean exit = true;
        boolean exit2 = true;
        String fname , mname, lname, Cnum, gender, Sbdate;
        LocalDate bdate;
        Long number;
        do{
            System.out.printf("|%-3s%-43s%-4s|\n", "", "How Many Citizen Will You Add? (0 to exit)!", "");
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
                        System.out.printf("|%-5sMiddle Name: ","");
                        mname = input.nextLine();
                        if(CheckValidName(mname)){
                            break;
                        }else{
                            System.out.printf("|%-10s%-29s%-11s|\n", "", "!!Enter A Valid Middle Name!!", "");
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
                    while(true){
                        System.out.printf("|%-5sContact #: +63 ","");
                        try{
                            number = input.nextLong();
                            if(number>9000000000L && number<=9999999999L){
                                Cnum = "+63 "+number;
                                break;
                            }else{
                                System.out.printf("|%-9s%-32s%-9s|\n", "", "!!Enter A Valid Contact Number!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-9s%-32s%-9s|\n", "", "!!Enter A Valid Contact Number!!", "");
                            input.next();
                        }
                    }
                    input.nextLine();
                    while(true){
                        System.out.printf("|%-5sGender (Male/Female): ","");
                        try{
                            gender = input.nextLine();
                            if(gender.equalsIgnoreCase("Male")||gender.equalsIgnoreCase("Female")){
                                break;
                            }else{
                                System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Make Sure You Input (Male/Female)!!", "");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Make Sure You Input (Male/Female)!!", "");
                        }
                    }
                    while(true){
                        System.out.printf("|%-5sBirth Date (yyyy-mm-dd): ","");
                        try{
                            Sbdate = input.next();
                            bdate = LocalDate.parse(Sbdate,dateFormat);
                            if(bdate.isBefore(cdate.minusYears(2))&&bdate.isAfter(cdate.minusYears(130))){
                                break;
                            }else{
                                System.out.println("+--------------------------------------------------+");
                                System.out.printf("|%-8s%-34s%-8s|\n", "", "!!Citizen Age Must Be 2 Or Older!!", "");
                                System.out.printf("|%-7s%-36s%-8s|\n", "", "!!Citizen Age Must Not Be Over 130!!", "");
                                System.out.printf("|%-5s%-40s%-5s|\n", "", "!!In Order For The Citizen To Register!!", "");
                                System.out.println("+--------------------------------------------------+");
                            }
                        }catch(Exception e){
                            System.out.printf("|%-3s%-44s%-3s|\n","","**Follow (YYYY-MM-DD) example (2003-01-05)**","");
                        }
                    }
                    input.nextLine();
                    System.out.printf("|%-5sAddress: ","");
                    String address = input.nextLine();
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-24s%-2s%-24s|\n","","||","");
                    String SQL = "INSERT INTO Citizen_List (c_fname, c_mname, c_lname, c_contact, c_gender, c_bdate, c_address, c_rdate) Values (?,?,?,?,?,?,?,?)";
                    conf.addRecord(SQL, fname, mname, lname, Cnum, gender, bdate, address, cdate);
                    System.out.printf("|%-24s%-2s%-24s|\n","","||","");
                }
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-10s%-29s%-11s|\n", "", "Add Another Citizen? (Yes/No)", "");
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
    private void EditCitizen(){
        boolean exit = true;
        int id;
        String fname , mname, lname, Cnum, gender, Sbdate;
        LocalDate bdate;
        Long number;
        String choice;
        do{
            boolean exit2 = true;
            view();
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
                        System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                    }
                }catch(Exception e){
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                    input.nextLine();
                }
            }
            while(exit2){
                input.nextLine();
                System.out.printf("|%-4s%-42s%-4s|\n", "", "Do You Want To Change Everything? (Yes/No)", "");
                while(true){
                    System.out.printf("|%-5sEnter: ","");
                    choice = input.nextLine();
                    if(choice.equalsIgnoreCase("Yes")||choice.equalsIgnoreCase("No")){
                        break;
                    }
                }
                int option;
                if(choice.equalsIgnoreCase("Yes")){
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-10s%-30s%-10s|\n", "", "Enter Citizen Full Information", "");
                    System.out.println("+--------------------------------------------------+");
                    option = 7;
                }else{
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-18s%-13s%-19s|\n", "", "Select Option", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "1. Change Name", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "2. Change Contact", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "3. Change Gender", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "4. Change Birth Date", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "5. Change Address", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "6. Back", "");
                    System.out.println("+--------------------------------------------------+");
                    int choices = 6;
                    option = OptionValidation(input, choices);
                    System.out.println("+--------------------------------------------------+");
                }
                switch(option){
                    case 1:
                        EditNames(id);
                    break;
                    case 2:
                        EditContact(id);
                    break;
                    case 3:
                        EditGender(id);
                    break;
                    case 4:
                        EditBirthDate(id);
                    break;
                    case 5:
                        EditAddress(id);
                    break;
                    case 7:
                        EditAll(id);
                    break;
                    default:
                        exit2 = false;
                        exit = false;
                    break;
                }
                if(option!=6){
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-10s%-20s%-10s|\n", "", "Edit Another Citizen? (Yes/No)", "");
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
        }while(exit);
    }
    private void EditAll(int id){
        String fname , mname, lname, Cnum, gender, Sbdate;
        Long number;
        LocalDate bdate;
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
            System.out.printf("|%-5sMiddle Name: ","");
            mname = input.nextLine();
            if(CheckValidName(mname)){
                break;
            }else{
                System.out.printf("|%-10s%-29s%-11s|\n", "", "!!Enter A Valid Middle Name!!", "");
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
        while(true){
            System.out.printf("|%-5sContact #: +63 ","");
            try{
                number = input.nextLong();
                if(number>9000000000L && number<=9999999999L){
                    Cnum = "+63 "+number;
                    break;
                }else{
                    System.out.printf("|%-9s%-32s%-9s|\n", "", "!!Enter A Valid Contact Number!!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-9s%-32s%-9s|\n", "", "!!Enter A Valid Contact Number!!", "");
                input.next();
            }
        }
        input.nextLine();
        while(true){
            System.out.printf("|%-5sGender (Male/Female): ","");
            try{
                gender = input.nextLine();
                if(gender.equalsIgnoreCase("Male")||gender.equalsIgnoreCase("Female")){
                    break;
                }else{
                    System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Make Sure You Input (Male/Female)!!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Make Sure You Input (Male/Female)!!", "");
            }
        }
        while(true){
            System.out.printf("|%-5sBirth Date (yyyy-mm-dd): ","");
            try{
                Sbdate = input.next();
                bdate = LocalDate.parse(Sbdate,dateFormat);
                if(bdate.isBefore(cdate.minusYears(2))&&bdate.isAfter(cdate.minusYears(130))){
                    break;
                }else{
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-8s%-34s%-8s|\n", "", "!!Citizen Age Must Be 2 Or Older!!", "");
                    System.out.printf("|%-7s%-36s%-8s|\n", "", "!!Citizen Age Must Not Be Over 130!!", "");
                    System.out.printf("|%-5s%-40s%-5s|\n", "", "!!In Order For The Citizen To Register!!", "");
                    System.out.println("+--------------------------------------------------+");
                }
            }catch(Exception e){
                System.out.printf("|%-3s%-44s%-3s|\n","","**Follow (YYYY-MM-DD) example (2003-01-05)**","");
            }
        }
        input.nextLine();
        System.out.printf("|%-5sAddress: ","");
        String address = input.nextLine();
        System.out.println("+--------------------------------------------------+");
        String SQL = "UPDATE Citizen_List SET c_fname = ?, c_mname = ?, c_lname = ?, c_contact = ?, c_gender = ?, c_bdate = ?, c_address = ? Where c_Id = ?";
        conf.updateRecord(SQL, fname, mname, lname, Cnum, gender, bdate, address, id);
    }
    private void EditNames(int id){
        String fname, mname, lname;
        input.nextLine();
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
            System.out.printf("|%-5sMiddle Name: ","");
            mname = input.nextLine();
            if(CheckValidName(mname)){
                break;
            }else{
                System.out.printf("|%-10s%-29s%-11s|\n", "", "!!Enter A Valid Middle Name!!", "");
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
        String SQL = "UPDATE Citizen_List SET c_fname = ?, c_mname = ?, c_lname = ? Where c_Id = ?";
        conf.updateRecord(SQL, fname, mname, lname, id);
    }
    private void EditContact(int id){
        String Cnum;
        Long number;
        while(true){
            System.out.printf("|%-5sContact #: +63 ","");
            try{
                number = input.nextLong();
                if(number>9000000000L && number<=9999999999L){
                    Cnum = "+63 "+number;
                    break;
                }else{
                    System.out.printf("|%-9s%-32s%-9s|\n", "", "!!Enter A Valid Contact Number!!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-9s%-32s%-9s|\n", "", "!!Enter A Valid Contact Number!!", "");
                input.next();
            }
        }
        input.nextLine();
        String SQL = "UPDATE Citizen_List SET c_contact = ? Where c_Id = ?";
        conf.updateRecord(SQL, Cnum, id);
    }
    private void EditGender(int id){
        String gender;
        input.nextLine();
        while(true){
            System.out.printf("|%-5sGender (Male/Female): ","");
            try{
                gender = input.nextLine();
                if(gender.equalsIgnoreCase("Male")||gender.equalsIgnoreCase("Female")){
                    break;
                }else{
                    System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Make Sure You Input (Male/Female)!!", "");
                }
            }catch(Exception e){
                System.out.printf("|%-6s%-37s%-7s|\n", "", "!!Make Sure You Input (Male/Female)!!", "");
            }
        }
        String SQL = "UPDATE Citizen_List SET c_gender = ? Where c_Id = ?";
        conf.updateRecord(SQL, gender, id);
    }
    private void EditBirthDate(int id){
        String Sbdate;
        LocalDate bdate=null;
        while(true){
            System.out.printf("|%-5sBirth Date (yyyy-mm-dd): ","");
            try{
                Sbdate = input.next();
                bdate = LocalDate.parse(Sbdate,dateFormat);
                if(bdate.isBefore(cdate.minusYears(2))&&bdate.isAfter(cdate.minusYears(130))){
                    break;
                }else{
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-8s%-34s%-8s|\n", "", "!!Citizen Age Must Be 2 Or Older!!", "");
                    System.out.printf("|%-7s%-36s%-8s|\n", "", "!!Citizen Age Must Not Be Over 130!!", "");
                    System.out.printf("|%-5s%-40s%-5s|\n", "", "!!In Order For The Citizen To Register!!", "");
                    System.out.println("+--------------------------------------------------+");
                }
            }catch(Exception e){
                System.out.printf("|%-3s%-44s%-3s|\n","","**Follow (YYYY-MM-DD) example (2003-01-05)**","");
            }
        }
        input.nextLine();
        String SQL = "UPDATE Citizen_List SET c_bdate = ? Where c_Id = ?";
        conf.updateRecord(SQL, bdate, id);
    }
    private void EditAddress(int id){
        input.nextLine();
        System.out.printf("|%-5sAddress: ","");
        String address = input.nextLine();
        String SQL = "UPDATE Citizen_List SET c_address = ? Where c_Id = ?";
        conf.updateRecord(SQL, address, id);
    }
    public void DeleteCitizen(){
        boolean exit = true;
        do{
            boolean exit2 = true;
            int id;
            view();
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
                        System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Citizen's List!!", "");
                    }
                }catch(Exception e){
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                    input.nextLine();
                }
            }
            while(exit2){
                String SQL = "DELETE FROM Citizen_List Where c_id = ?";
                conf.deleteRecord(SQL, id);
                input.nextLine();
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-9s%-32s%-9s|\n", "", "Delete Another Citizen? (Yes/No)", "");
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
        String tbl_view = "SELECT * FROM Citizen_List";
        String[] tbl_Headers = {"ID", "First Name", "Last Name"};
        String[] tbl_Columns = {"c_id", "c_fname", "c_lname"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
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
