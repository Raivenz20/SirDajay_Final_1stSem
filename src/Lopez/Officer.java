package Lopez;

import java.util.Scanner;

public class Officer {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    
    public void Officer_List(){
        boolean exit = true;
        do{
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-19s%-12s%-19s|\n", "", "**OFFICERS**", "");
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-18s%-13s%-19s|\n", "", "Select Option", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "1. Add Officer", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "2. Edit Information", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "3. Remove Officer", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "4. View Officer List", "");
            System.out.printf("|%-5s%-20s%-25s|\n", "", "5. Back", "");
            System.out.println("+--------------------------------------------------+");
            int choices = 5;
            int option = OptionValidation(input, choices);
            System.out.println("+--------------------------------------------------+");
            switch(option){
                case 1:
                    AddOfficer();
                break;
                case 2:
                    EditInformation();
                break;
                case 3:
                    DeleteOfficer();
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
    private void AddOfficer(){
        boolean exit = true;
        String fname, lname, position, Cnum, gender;
        long number;
        while(exit){
            boolean exit2 = true;
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-13s%-23s%-14s|\n", "", "Select Officer Position", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "1. Barangay Captain", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "2. Barangay Councilor", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "3. Barangay Secretary", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "4. Barangay Treasurer", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "5. Barangay Tanod", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "6. Desk Officer", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "7. Investigator", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "8. Community Relations Officer", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "9. Exit", "");
            System.out.println("+--------------------------------------------------+");
            int choices = 9;
            int option = OptionValidation(input, choices);
            if(option == 9){
                exit2 = false;
                exit = false;
            }
            while(exit2){
                switch(option){
                    case 1:
                        position = "Barangay Captain";
                    break;
                    case 2:
                        position = "Barangay Councilor";
                    break;
                    case 3:
                        position = "Barangay Secretary";
                    break;
                    case 4:
                        position = "Barangay Treasurer";
                    break;
                    case 5:
                        position = "Barangay Tanod";
                    break;
                    case 6:
                        position = "Desk Officer";
                    break;
                    case 7:
                        position = "Investigator";
                    break;
                    default:
                        position = "Community Relations Officer";
                    break;
                }
                if(!conf.isPositionOccupied(position)) {
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
                    String SQL = "INSERT INTO Officer_List (o_fname, o_lname, o_position, o_contact, o_gender) Values (?,?,?,?,?)";
                    conf.addRecord(SQL, fname, lname, position, Cnum, gender);
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-10s%-29s%-11s|\n", "", "Add Another Officer? (Yes/No)", "");
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
                }else{
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!That Position is Full!!", "");
                    exit2 = false;
                }
            }
        }
    }
    private void EditInformation(){
        boolean exit = true;
        String choice;
        int id;
        do{
            boolean exit2 = true;
            view();
            while(true){
                System.out.printf("|%-5sEnter ID: ","");
                try{
                    id = input.nextInt();
                    String SQL = "SELECT COUNT (*) FROM Officer_List Where o_id = ?";
                    if(conf.recordExists(SQL, id)){
                        break;
                    }else if(id == 0){
                        exit = false;
                        exit2 = false;
                        break;
                    }else{
                        System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Officer's List!!", "");
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
                if(choice.equalsIgnoreCase("yes")){
                    option = 6;
                }else{
                    System.out.println("+--------------------------------------------------+");
                    System.out.printf("|%-18s%-13s%-19s|\n", "", "Select Option", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "1. Change Name", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "2. Change Contact", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "3. Change Position", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "4. Change Gender", "");
                    System.out.printf("|%-5s%-20s%-25s|\n", "", "5. Back", "");
                    System.out.println("+--------------------------------------------------+");
                    int choices = 5;
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
                        EditPosition(id);
                    break;
                    case 4:
                        EditGender(id);
                    break;
                    case 6:
                        EditAll(id);
                    break;
                    default:
                        exit2 = false;
                        exit = false;
                    break;
                }
                if(option!=5){
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
        boolean exit = true;
        String fname, lname, position, Cnum, gender;
        long number;
        while(exit){
            boolean exit2 = true;
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-13s%-23s%-14s|\n", "", "Select Officer Position", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "1. Barangay Captain", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "2. Barangay Councilor", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "3. Barangay Secretary", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "4. Barangay Treasurer", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "5. Barangay Tanod", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "6. Desk Officer", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "7. Investigator", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "8. Community Relations Officer", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "9. Exit", "");
            System.out.println("+--------------------------------------------------+");
            int choices = 9;
            int option = OptionValidation(input, choices);
            if(option == 9){
                input.nextLine();
                exit2 = false;
                exit = false;
            }
            while(exit2){
                switch(option){
                    case 1:
                        position = "Barangay Captain";
                    break;
                    case 2:
                        position = "Barangay Councilor";
                    break;
                    case 3:
                        position = "Barangay Secretary";
                    break;
                    case 4:
                        position = "Barangay Treasurer";
                    break;
                    case 5:
                        position = "Barangay Tanod";
                    break;
                    case 6:
                        position = "Desk Officer";
                    break;
                    case 7:
                        position = "Investigator";
                    break;
                    default:
                        position = "Community Relations Officer";
                    break;
                }
                if(!conf.isPositionOccupied(position)) {
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
                    String SQL = "UPDATE Officer_List SET o_fname = ?, o_lname = ?, o_position = ?, o_contact = ?, o_gender = ? Where o_Id = ?";
                    conf.updateRecord(SQL, fname, lname, position, Cnum, gender, id);
                    exit = false;
                }else{
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!That Position is Full!!", "");
                    exit2 = false;
                }
            }
        }
    }
    private void EditNames(int id){
        String fname, lname;
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
            System.out.printf("|%-5sLast Name: ","");
            lname = input.nextLine();
            if(CheckValidName(lname)){
                break;
            }else{
                System.out.printf("|%-11s%-27s%-12s|\n", "", "!!Enter A Valid Last Name!!", "");
            }
        }
        String SQL = "UPDATE Officer_List SET o_fname = ?, o_lname = ? Where o_Id = ?";
        conf.updateRecord(SQL, fname, lname, id);
    }
    private void EditContact(int id){
        long number;
        String Cnum;
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
        String SQL = "UPDATE Officer_List SET o_contact = ? Where o_Id = ?";
        conf.updateRecord(SQL, Cnum, id);
    }
    private void EditPosition(int id){
        boolean exit = true;
        String position;
        do{
            boolean exit2 = true;
            System.out.println("+--------------------------------------------------+");
            System.out.printf("|%-13s%-23s%-14s|\n", "", "Select Officer Position", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "1. Barangay Captain", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "2. Barangay Councilor", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "3. Barangay Secretary", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "4. Barangay Treasurer", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "5. Barangay Tanod", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "6. Desk Officer", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "7. Investigator", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "8. Community Relations Officer", "");
            System.out.printf("|%-5s%-30s%-15s|\n", "", "9. Exit", "");
            System.out.println("+--------------------------------------------------+");
            int choices = 9;
            int option = OptionValidation(input, choices);
            if(option == 9){
                input.nextLine();
                exit2 = false;
                exit = false;
            }
            while(exit2){
                switch(option){
                    case 1:
                        position = "Barangay Captain";
                    break;
                    case 2:
                        position = "Barangay Councilor";
                    break;
                    case 3:
                        position = "Barangay Secretary";
                    break;
                    case 4:
                        position = "Barangay Treasurer";
                    break;
                    case 5:
                        position = "Barangay Tanod";
                    break;
                    case 6:
                        position = "Desk Officer";
                    break;
                    case 7:
                        position = "Investigator";
                    break;
                    default:
                        position = "Community Relations Officer";
                    break;
                }
                if(!conf.isPositionOccupied(position)){
                    String SQL = "UPDATE Officer_List SET o_position = ? Where o_Id = ?";
                    conf.updateRecord(SQL, position, id);
                    input.nextLine();
                    exit2 = false;
                    exit = false;
                }else{
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!That Position is Full!!", "");
                    exit2 = false;
                }
            }
        }while(exit);
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
        String SQL = "UPDATE Officer_List SET o_gender = ? Where o_Id = ?";
        conf.updateRecord(SQL, gender, id);
    }
    public void DeleteOfficer(){
        boolean exit = true;
        do{
            boolean exit2 = true;
            int id;
            view();
            while(true){
                System.out.printf("|%-5sEnter ID: ","");
                try{
                    id = input.nextInt();
                    String SQL = "SELECT COUNT (*) FROM Officer_List Where o_id = ?";
                    if(conf.recordExists(SQL, id)){
                        break;
                    }else if(id == 0){
                        exit = false;
                        exit2 = false;
                        break;
                    }else{
                        System.out.printf("|%-5s%-39s%-6s|\n", "", "!!Id Does Not Exist In Officer's List!!", "");
                    }
                }catch(Exception e){
                    System.out.printf("|%-12s%-25s%-13s|\n", "", "!!Enter A Valid Integer!!", "");
                    input.nextLine();
                }
            }
            while(exit2){
                String SQL = "DELETE FROM Officer_List Where o_id = ?";
                conf.deleteRecord(SQL, id);
                input.nextLine();
                System.out.println("+--------------------------------------------------+");
                System.out.printf("|%-9s%-32s%-9s|\n", "", "Delete Another Officer? (Yes/No)", "");
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
        String tbl_view = "SELECT * FROM Officer_List";
        String[] tbl_Headers = {"ID", "First Name", "Last Name", "Position", "Contact #", "Gender"};
        String[] tbl_Columns = {"o_id", "o_fname", "o_lname", "o_position", "o_contact", "o_gender"};
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
