package packSocialNetwork;

import packSocialNetworkExceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * SocialNetwork contains people information and the relations between them if exist.
 * This project is being developed on Data Structures and Algorithms subject on UPV/EHU at 2020/2021 academic year.
 *
 * @author Iyán Álvarez
 * @version secondIyan
 */
public class SocialNetwork {

    // Attributes
    private ArrayList<Person> personList;
    private ArrayList<Relation> relationList;
    private static SocialNetwork instance;
    private final Scanner sc = new Scanner(System.in);

    // Constructors
    /**
     * Creates an instance of Social Network.
     */
    private SocialNetwork() {
        this.personList = new ArrayList<>();
        this.relationList = new ArrayList<>();
    }

    // Methods
    /**
     * Gets instance of the Social Network, if it does not exist it creates a new one, that is going to be unique
     * and returns it.
     * This method is based on the Singleton design pattern.
     * @return The instance of the Social Network.
     */
    public static SocialNetwork getInstance() {
        if (instance == null) {
            instance = new SocialNetwork();
        }
        return instance;
    }

    // 1st milestone
    /**
     * Presents an initial menu with the different choices for interacting with the social network.
     */
    public void initialMenu() {
        printInitialMenu();
        selectionInitialMenu();
    }
    /**
     * Prints the choices of the initial menu.
     */
    private void printInitialMenu() {
        System.out.println("\nSocial Network menu:");
        printAddPersonPeople();
        printAddRelations();
        printPrintOut();
        printFind();
        System.out.println("5. Log out \n \n");
    }
    /**
     * Prints the choices of the add person people menu.
     */
    private void printAddPersonPeople() {
        System.out.println(
                        "1. Add person or people: \n" +
                        "    M. Manually \n" +
                        "    F. File ");
    }
    /**
     * Prints the choices of add relations menu.
     */
    private void printAddRelations() {
        System.out.println(
                        "2. Add relation(s): \n" +
                        "    M. Manually \n" +
                        "    F. File ");
    }
    /**
     * Prints the choices of the print out menu.
     */
    private void printPrintOut() {
        System.out.println(
                        "3. Print out people: \n" +
                        "    C. Console \n" +
                        "    F. File ");
    }
    /**
     * Prints the choices of the find menu.
     */
    private void printFind() {
        System.out.println(
                        "4. Find: \n" +
                        "    1. Friends of the person given the surname \n" +
                        "    2. People born in specified city \n" +
                        "    3. People born between two dates \n" +
                        "    4. People born in the city of residential ID set \n" +
                        "    5. People that share favourite movies \n" +
                        "    6. Chain \n" +
                        "    7. Cliques ");
    }
    /**
     * Request user to select an option and do what was specified in the menu, if selected option does not
     * exist, will call the program recursively.
     */
    private void selectionInitialMenu() {
        int option;
        do {
            option = askForSelectionInput();
            System.out.println("You have selected: ");
            switch (option) {
                case 1:
                    addPersonPeopleSelected();
                    break;
                case 2:
                    addRelationsSelected();
                    break;
                case 3:
                    printOutSelected();
                    break;
                case 4:
                    printFindSelected();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    break;
            }
            if (option != 5) printInitialMenu();
        } while (option != 5);
        sc.close();
    }
    /**
     * Request user for an input, it has to be a number and treats InputMismatchException.
     * @return Returns user input int.
     */
    private int askForSelectionInput() {
        boolean correct = false;
        int option = 0;
        while (!correct) {
            try {
                System.out.print("Select one option: ");
                option = sc.nextInt();
                correct = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: You should introduce a number");
                sc.nextLine();
            }
        }
        return option;
    }
    /**
     * Prints Add person option and performs task.
     */
    private void addPersonPeopleSelected() {
        printAddPersonPeople();
        String to;
        do {
            System.out.println("Manually (M) or File (F)");
            System.out.print("\nEnter M or F: ");
            to = sc.next();
        } while (!(to.equals("M") || to.equals("F")));
        if (to.equals("M")) {
            System.out.println("Follow the next format:");
            System.out.println("idperson,name,lastname,birthdate,gender,birthplace,home,studiedat,workplaces,films,groupcode");
            try {
                addPerson(sc.next());
            } catch (PersonAlreadyAtSocialNetwork e) {
                System.out.println("That person is already at the network");
            }
        }
        else {
            System.out.println("The file must be on 'files/' directory.");
            System.out.print("Enter the name of the file: ");
            addPeopleFromFile(sc.next());
            System.out.print("Completed.\n\n");
        }
    }
    /**
     * Prints Add relation option and performs task.
     */
    private void addRelationsSelected() {
        printAddRelations();
        String to;
        do {
            System.out.println("Manually (M) or File (F)");
            System.out.print("\nEnter M or F: ");
            to = sc.next();
        } while (!(to.equals("M") || to.equals("F")));
        if (to.equals("M")) {
            System.out.println("Follow the next format:");
            System.out.println("friend1,friend2");
            try {
                String s = sc.next();
                String[] sa = s.split(",");
                addRelation(sa[0], sa[1]);
            } catch (RelationAlreadyAtSocialNetwork e) {
                System.out.println("Error: That relation is already at the network");
            } catch (PersonNotFoundException e) {
                System.out.println("Error: The realtion couldn't be added; at least one ID was not found on SocialNetwork");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: There was an error with your input");
            }
        }
        else {
            System.out.println("The file must be on 'files/' directory.");
            System.out.print("Enter the name of the file: ");
            addRelationsFromFile(sc.next());
            System.out.print("Completed.\n\n");
        }
    }
    /**
     * Prints Print out option and performs task.
     */
    private void printOutSelected() {
        printPrintOut();
        String to;
        do {
            System.out.println("Console (C) or File (F)");
            System.out.print("\nEnter C or F: ");
            to = sc.next();
        } while (!(to.equals("C") || to.equals("F")));
        if (to.equals("C")) {
            printPeopleToConsole();
        }
        else {
            System.out.println("The file must be on 'files/' directory.");
            System.out.print("Enter the name of the file: ");
            printPeopleToFile(sc.next());
        }
    }
    /**
     * Prints Find option and performs task.
     */
    private void printFindSelected() {
        printFind();
        int to = 0;
        do {
            System.out.println("Select one of the previous options (1-6)");
            System.out.print("\nEnter 1, 2, 3, 4, 5, 6, 7: ");
            try {
                to = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Introduce a number from 1 to 6 (both included)");
            }
        } while (to < 1 || 7 < to);
        switch (to) {
            case 1:
                System.out.println("You have selected: ");
                System.out.println( "1. Friends of the person given the surname \n" +
                                    "    C. Console \n" +
                                    "    F. File ");
                String tos1;
                do {
                    System.out.println("Console (C) or File (F)");
                    System.out.print("\nEnter C or F: ");
                    tos1 = sc.next();
                } while (!(tos1.equals("C") || tos1.equals("F")));
                System.out.print("Write the surname: ");
                String sn1 = sc.next();
                if (tos1.equals("C")) printFriendsBySurnameToConsole(sn1);
                else {
                    System.out.println("The file will be on 'files/' directory.");
                    System.out.print("Enter the name of the file: ");
                    printFriendsBySurnameToFile(sn1, sc.next());
                }
                break;
            case 2:
                System.out.println("You have selected: ");
                System.out.println( "2. People born in specified city \n" +
                        "    C. Console \n" +
                        "    F. File ");
                String tos2;
                do {
                    System.out.println("Console (C) or File (F)");
                    System.out.print("\nEnter C or F: ");
                    tos2 = sc.next();
                } while (!(tos2.equals("C") || tos2.equals("F")));
                System.out.print("Write the city: ");
                String sn2 = sc.next();
                if (tos2.equals("C")) printPersonByCityToConsole(sn2);
                else {
                    System.out.println("The file will be on 'files/' directory.");
                    System.out.print("Enter the name of the file: ");
                    printPersonByCityToFile(sn2, sc.next());
                }
                break;
            case 3:
                System.out.println("You have selected: ");
                System.out.println( "3. People born between two dates \n" +
                        "    C. Console \n" +
                        "    F. File ");
                String tos3;
                do {
                    System.out.println("Console (C) or File (F)");
                    System.out.print("\nEnter C or F: ");
                    tos3 = sc.next();
                } while (!(tos3.equals("C") || tos3.equals("F")));
                System.out.print("Write the initial year: ");
                String sn31 = sc.next();
                System.out.print("Write the limit year: ");
                String sn32 = sc.next();
                if (tos3.equals("C")) printPersonBetweenDatesToConsole(Integer.parseInt(sn31), Integer.parseInt(sn32));
                else {
                    System.out.println("The file will be on 'files/' directory.");
                    System.out.print("Enter the name of the file: ");
                    printPersonBetweenDatesToFile(Integer.parseInt(sn31), Integer.parseInt(sn32), sc.next());
                }
                break;
            case 4:
                System.out.println("You have selected: ");
                System.out.println( "4. People born in the city of residential ID set \n" +
                        "    C. Console \n" +
                        "    F. File \n" +
                        "The file residential.txt must be on folder files/");
                String tos4;
                do {
                    System.out.println("Console (C) or File (F)");
                    System.out.print("\nEnter C or F: ");
                    tos4 = sc.next();
                } while (!(tos4.equals("C") || tos4.equals("F")));
                if (tos4.equals("C")) printPersonByCityResidentialToConsole();
                else {
                    System.out.println("The file will be on 'files/' directory.");
                    System.out.print("Enter the name of the file: ");
                    printPersonByCityResidentialToFile(sc.next());
                }
                break;
            case 5:
                System.out.println("You have selected: ");
                System.out.println( "5. People that share favourite movies \n" +
                        "    C. Console \n" +
                        "    F. File");
                String tos5;
                do {
                    System.out.println("Console (C) or File (F)");
                    System.out.print("\nEnter C or F: ");
                    tos5 = sc.next();
                } while (!(tos5.equals("C") || tos5.equals("F")));
                System.out.print("Write the favourite movie: \n");
                sc.nextLine();
                String sn5 = sc.nextLine();
                if (tos5.equals("C")) printPersonListMoviesToConsole(sn5);
                else {
                    System.out.println("The file will be on 'files/' directory.");
                    System.out.print("Enter the name of the file: ");
                    printPersonListMoviesToFile(sn5, sc.next());
                }
                break;
            case 6:
                break;
            case 7:
                break;

        }
    }
    /**
     * Adds a person to the Social Network in lexicographical order.
     * @param data Data of the person.
     *             Must follow the specified format: idperson,name,lastname,birthdate,gender,birthplace,home,studiedat,workplaces,films,groupcode
     * @throws PersonAlreadyAtSocialNetwork If the person's ID already exists in the Social Network.
     */
    private void addPerson(String data) throws PersonAlreadyAtSocialNetwork {
        String[] d = data.split(",");
        Person np = null;
        try {
            String[] d7 = d[7].split(";");
            String[] d8 = d[8].split(";");
            String[] d9 = d[9].split(";");
            np = new Person(d[0], d[1], d[2], d[3], d[4], d[5], d[6], d7, d8, d9, d[10]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There was an error with your input\n");
        }
        int i = 0;
        boolean inserted = false;
        while (i < personList.size() && !inserted) {
            Person p = personList.get(i);
            if (p.equals(np)) throw new PersonAlreadyAtSocialNetwork();
            else if (p.compareTo(np) < 0) i++;
            else inserted = true;
        }
        personList.add(i,np);
    }
    /**
     * Adds all the people from the file to the Social Network.
     * The file must be on files/ directory.
     * The file must follow the specified format: idperson,name,lastname,birthdate,gender,birthplace,home,studiedat,workplaces,films,groupcode
     * @param filename Name of the file that contains people that is going to be added to the Social Network.
     */
    private void addPeopleFromFile(String filename) {
        int error = 0;
        File f;
        Scanner sf;
        try {
            f = new File("files/" + filename);
            sf = new Scanner(f);
            String sexample = "idperson,name,lastname,birthdate,gender,birthplace,home,studiedat,workplaces,films,groupcode";
            if (sexample.equals(sf.nextLine())) {
                while (sf.hasNextLine()) {
                    try {
                        addPerson(sf.nextLine());
                    } catch (PersonAlreadyAtSocialNetwork e) {
                        error++;
                    }
                }
                sf.close();
            } else {
                System.out.println("Error: File data is not in required format");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File can not be found");
        }
        if (error > 0) {
            System.out.println("Error: " + error + " users couldn't be added to the Social Network because the user ID already exists\n");
        }
    }
    /**
     * Prints all the people at the Social Network to the console.
     */
    private void printPeopleToConsole() {
        for (Person p: personList) {
            System.out.println(p.toString());
        }
    }
    /**
     * Prints all the people at the Social Network to a file.
     * The file will be located on files/ directory with the specified name.
     * @param filename Name of the file that is going to contain all the people in the Social Network.
     */
    private void printPeopleToFile(String filename) {
        File f = new File("files/" + filename);
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            String sexample = "idperson,name,lastname,birthdate,gender,birthplace,home,studiedat,workplaces,films,groupcode\n";
            fw.write(sexample);
            for (Person p: personList) {
                fw.write(p.toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.err.println("Error: File was not found");
        }
    }
    /**
     * Adds a relation of 2 people.
     * @param p1 User ID of one person.
     * @param p2 User ID of the other person.
     */
    private void addRelation(String p1, String p2) throws RelationAlreadyAtSocialNetwork, PersonNotFoundException {
        if (existsInSocialNetwork(p1) && existsInSocialNetwork(p2)) {
            Relation r = new Relation(p1, p2);
            if (relationList.contains(r)) throw new RelationAlreadyAtSocialNetwork();
            else relationList.add(r);
        }
        else throw new PersonNotFoundException();
    }
    /**
     * Adds all the relations that the specified file contains.
     * The file must be on files/ directory.
     * The file must follow the specified format: friend1,friend2
     * @param filename Name of the file that contains relations that are going to be added to the Social Network.
     */
    private void addRelationsFromFile(String filename) {
        File f;
        Scanner sf;
        int al = 0;
        int nf = 0;
        try {
            f = new File("files/" + filename);
            sf = new Scanner(f);
            String sexample = "friend1,friend2";
            if (sexample.equals(sf.nextLine())) {
                String[] arr;
                while (sf.hasNextLine()) {
                    try {
                        arr = sf.nextLine().split(",");
                        addRelation(arr[0], arr[1]);
                    } catch (RelationAlreadyAtSocialNetwork e) {
                        al++;
                    } catch (PersonNotFoundException e) {
                        nf++;
                    }
                }
                sf.close();
            }
            else {
                System.out.println("Error: File data is not in required format");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File can not be found");
        }
        if (al > 0) System.out.println("Error: " + al + " realtionship(s) already at the network");
        if (nf > 0) System.out.println("Error: " + nf + " realtionship(s) couldn't be added; at least one ID was not found on SocialNetwork");
    }
    /**
     * Checks if one person exists in the social network given the ID (identifier).
     * @param ID ID of the person you wan to check if exits in the social network.
     * @return true  iif specified ID is associated to a person of the social network; otherwise false.
     */
    private boolean existsInSocialNetwork(String ID) {
        try {
            if (binarySearchPersonID(ID) != null) return true;
        }
        catch (PersonNotFoundException ignored) {}
        return false;
    }
    /**
     * Makes a binary search in the ArrayList ordered by the ID
     * Frontend method
     * @param id ID of the Person to search in the SocialNetwork
     * @return The person instance if found
     * @throws PersonNotFoundException if the Person does not exist in the SocialNetwork
     */
    private Person binarySearchPersonID(String id) throws PersonNotFoundException {
        return binarySearchPersonIDBack(id, 0, personList.size());
    }
    /**
     * Makes a binary search in the ArrayList ordered by the ID
     * Backend method
     * @param id ID of the Person to search in the SocialNetwork
     * @param left Left limit of the ArrayList
     * @param right Right limit of the ArrayList
     * @return The person instance if found
     * @throws PersonNotFoundException if the Person does not exist in the SocialNetwork
     */
    private Person binarySearchPersonIDBack(String id, int left, int right) throws PersonNotFoundException {
        if (right >= left) {
            int mid = left + (right - left) / 2;
            String actualid = personList.get(mid).getIdentifier();
            if (actualid.equals(id)) return personList.get(mid);
            if (actualid.compareTo(id) > 0) return binarySearchPersonIDBack(id , left, mid - 1);
            return binarySearchPersonIDBack(id, mid + 1, right);
        }
        throw new PersonNotFoundException();
    }

    // 2nd milestone
    /**
     * Given a surname, finds the Person(s) in the SocialNetwork with that surname and returns them in an
     * ArrayList of Person.
     * @param surname Surname of the person(s) that we want to find.
     * @return ArrayList of Persons in the SocialNetwork with that surname.
     * @throws PersonNotFoundException If no one in the SocialNetwork has that surname.
     */
    private ArrayList<Person> findPersonBySurname(String surname) throws PersonNotFoundException {
        ArrayList<Person> arr = new ArrayList<>();
        for (Person p: personList) {
            if (p.getSurname().equals(surname)) {
                arr.add(p);
            }
        }
        if (arr.isEmpty()) {
            throw new PersonNotFoundException();
        }
        return arr;
    }
    /**
     * Given a surname, returns a String with the friends of the user(s) with that surname.
     * @param surname Surname of the person(s) that we want to know his/her friends.
     * @return A String with the friends of the user(s) with that surname.
     * @throws PersonNotFoundException If no one in the SocialNetwork has that surname.
     */
    private String findFriendsBySurname(String surname) throws PersonNotFoundException {
        String s = "";
        ArrayList<Person> arr = findPersonBySurname(surname);
        String id;
        for (Person p: arr) {
            id = p.getIdentifier();
            s += "The user " + id + " surname is " + surname + "\nList of the friends: \n";
            for (Relation r: relationList) {
                if (r.getPerson1().equals(id)) {
                    s += binarySearchPersonID(r.getPerson2()).getBasicInfo() + "\n";
                }
                else if (r.getPerson2().equals(id)) {
                    s += binarySearchPersonID(r.getPerson1()).getBasicInfo() + "\n";
                }
            }
            s += "\n";
        }
        return s;
    }
    /**
     * Given a surname, prints the friends of the user(s) with that surname in the console.
     * @param surname Surname of the person(s) that we want to know his/her friends.
     */
    private void printFriendsBySurnameToConsole(String surname) {
        try {
            System.out.println(findFriendsBySurname(surname));
        } catch (PersonNotFoundException e) {
            System.out.println("Error: Does not exist no one with that surname in the SocialNetwork");
        }
    }
    /**
     * Given a surname, prints the friends of the user(s) with that surname in the specified file.
     * @param surname Surname of the person(s) that we want to know his/her friends.
     * @param filename File where we want to save the information.
     */
    private void printFriendsBySurnameToFile(String surname, String filename) {
        File f;
        FileWriter fw;
        String s =  "";
        try {
            f = new File("files/" + filename);
            fw = new FileWriter(f);
            s += findFriendsBySurname(surname);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: File was not found");
        } catch (PersonNotFoundException e) {
            System.out.println("Error: Does not exist no one with that surname in the SocialNetwork");
        }
    }
    /**
     * Given a city, finds the Person(s) in the SocialNetwork that have born in that city and returns them
     * in an ArrayList of Person.
     * @param city City of the person(s) that we want to find.
     * @return ArrayList of Persons in the SocialNetwork that have born in that city.
     * @throws PersonNotFoundException If no one in the SocialNetwork has born in the given city.
     */
    private ArrayList<Person> findPersonByCity(String city) throws PersonNotFoundException {
        ArrayList<Person> arr = new ArrayList<>();
        for (Person p: personList) {
            if (p.getBirthplace().equals(city)) {
                arr.add(p);
            }
        }
        if (arr.isEmpty()) {
            throw new PersonNotFoundException();
        }
        return arr;
    }
    /**
     * Given a city, returns a String with the basic info of the user(s) born in that city.
     * @param city City of the person(s) that we want to know.
     * @return A String with the basic info of the user(s) born in the given city.
     * @throws PersonNotFoundException If no one in the SocialNetwork has born in the given city.
     */
    private String findPersonByCityStringBasic(String city) throws PersonNotFoundException {
        String s = "The user(s) born in " + city + " is/are:\n";
        ArrayList<Person> arr = findPersonByCity(city);
        String id;
        for (Person p: arr) {
            s += p.getBasicInfo() + "\n";
        }
        return s;
    }
    /**
     * Given a city, prints the user(s) basic info that has born in the given city in the console.
     * @param city City of the person(s) that we want to know.
     */
    private void printPersonByCityToConsole(String city) {
        try {
            System.out.println(findPersonByCityStringBasic(city));
        } catch (PersonNotFoundException e) {
            System.out.println("Error: Does not exist no one that has born there in the SocialNetwork");
        }
    }
    /**
     * Given a city, prints the user(s) basic info that has born in the given city in the specified file.
     * @param city City of the person(s) that we want to know.
     * @param filename File where we want to save the information.
     */
    private void printPersonByCityToFile(String city, String filename) {
        File f;
        FileWriter fw;
        String s =  "";
        try {
            f = new File("files/" + filename);
            fw = new FileWriter(f);
            s += findPersonByCityStringBasic(city);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: File was not found");
        } catch (PersonNotFoundException e) {
            System.out.println("Error: Does not exist no one with that surname in the SocialNetwork");
        }
    }
    /**
     * Given two dates, finds the Person(s) in the SocialNetwork born between the given years an return them in a
     * sorted ArrayList of Person.
     * Pre: Year 1 <= Year2.
     * @param year1 Start year (included).
     * @param year2 Limit year (included).
     * @return ArrayList of Persons in the SocialNetwork born between the given years sorted.
     * @throws PersonNotFoundException If no one in the SocialNetwork has born between that years.
     */
    private ArrayList<Person> findPersonBetweenDates(int year1, int year2) throws PersonNotFoundException {
        ArrayList<Person> arr = new ArrayList<>();
        for (Person p: personList) {
            int bdy = p.getBirthdateYear();
            if (year1 <= bdy && bdy <= year2) {
                arr.add(p);
            }
        }
        if (arr.isEmpty()) {
            throw new PersonNotFoundException();
        }
        // TODO Sort the array HOW??
        return arr;
    }
    /**
     * Given two dates, finds the Person(s) in the SocialNetwork born between the given years an return them in a
     * sorted ArrayList of Person.
     * Pre: Year 1 <= Year2.
     * @param year1 Start year (included).
     * @param year2 Limit year (included).
     * @return A String with the basic info of the user(s) born in the given city.
     * @throws PersonNotFoundException If no one in the SocialNetwork has born in the given city.
     */
    private String findPersonBetweenDatesString(int year1, int year2) throws PersonNotFoundException {
        String s = "The user(s) born between " + year1 + " and " + year2 + " is/are:\n";
        ArrayList<Person> arr = findPersonBetweenDates(year1, year2);
        String id;
        for (Person p: arr) {
            s += p.getBasicInfo() + "\n";
        }
        return s;
    }
    /**
     * Given two dates, prints the user(s) basic info that has born between the given years.
     * @param year1 Start year (included).
     * @param year2 Limit year (included).
     */
    private void printPersonBetweenDatesToConsole(int year1, int year2) {
        try {
            System.out.println(findPersonBetweenDatesString(year1, year2));
        } catch (PersonNotFoundException e) {
            System.out.println("Error: Does not exist no one that has born between the given dates");
        }
    }
    /**
     * Given two dates, prints the user(s) basic info that has born between the given years in the specified file.
     * @param year1 Start year (included).
     * @param year2 Limit year (included).
     * @param filename File where we want to save the information.
     */
    private void printPersonBetweenDatesToFile(int year1, int year2, String filename) {
        File f;
        FileWriter fw;
        String s =  "";
        try {
            f = new File("files/" + filename);
            fw = new FileWriter(f);
            s += findPersonBetweenDatesString(year1, year2);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: File was not found");
        } catch (PersonNotFoundException e) {
            System.out.println("Error: Does not exist no one with that has born between the given dates");
        }
    }
    /**
     * Given a city, returns a String with more info of the user(s) born in that city.
     * @param city City of the person(s) that we want to know.
     * @return A String with more info of the user(s) born in the given city.
     * @throws PersonNotFoundException If no one in the SocialNetwork has born in the given city.
     */
    private String findPersonByCityStringMore(String city) throws PersonNotFoundException {
        String s = "";
        ArrayList<Person> arr = findPersonByCity(city);
        String id;
        for (Person p: arr) {
            s += p.getMoreInfo() + "\n";
        }
        return s;
    }
    /**
     * Finds the Person(s) in the SocialNetwork that have born in the same city as the ID's given in the file
     * residential.txt in an String.
     * Pre: The file residential.txt must be on folder files/
     * @return String with Persons more info that have born in the city of each ID given in residential.txt.
     */
    private String findPersonByCityResidentialString() {
        File f = null;
        Scanner fr = null;
        String s = "";
        try {
            f = new File("files/residential.txt");
            fr = new Scanner(f);
            while (fr.hasNext()) {
                Person p = binarySearchPersonID(fr.nextLine());
                s += p.getIdentifier() + " was born in " + p.getBirthplace() + ", and this users too:\n";
                try {
                    s += findPersonByCityStringMore(p.getBirthplace()) + "\n";
                } catch (PersonNotFoundException e) {
                    System.out.println("Error: Does not exist no one that has born in " + p.getBirthplace() + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File was not found");
        }
        return s;
    }
    /**
     * Prints the user(s) more info of the ones that have born in the given ID's birthplace in the console.
     */
    private void printPersonByCityResidentialToConsole() {
        System.out.println(findPersonByCityResidentialString());
    }
    /**
     * Prints the user(s) more info of the ones that have born in the given ID's birthplace in the specified file.
     * @param filename File where we want to save the information.
     */
    private void printPersonByCityResidentialToFile(String filename) {
        File f;
        FileWriter fw;
        String s =  "";
        try {
            f = new File("files/" + filename);
            fw = new FileWriter(f);
            s += findPersonByCityResidentialString();
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: File was not found");
        }
    }
    /**
     * Splits Person(s) by individual favourite movies in a HashMap, where the keys are the favourite movies, and the values
     * an ArrayList of Person that have that favourite movie in common.
     * @return A HashMap, where the keys are the favourite movies, and the values an ArrayList of Person that have that
     * favourite movie in common.
     */
    private HashMap<String, ArrayList<Person>> splitPersonByMovies() {
        HashMap<String, ArrayList<Person>> hm = new HashMap<String, ArrayList<Person>>();
        for (Person p: personList) {
            String fm = p.getMovies();
            String[] fma  = fm.split(";");
            for (String fmai : fma) {
                ArrayList<Person> al;
                if (hm.containsKey(fmai)) {
                    al = hm.get(fmai);
                } else {
                    al = new ArrayList<Person>();
                }
                al.add(p);
                hm.put(fmai, al);
            }
        }
        return hm;
    }
    /**
     * Obtains the list of Person(s) that share that favourite movie in common, if exist.
     * @param movie The title of the favourite movie.
     * @return An ArrayList of Person(s) that have in common the given movie as favourite.
     * @throws PersonNotFoundException If no one in the SocialNetwork has the movie given as favourite.
     */
    private ArrayList<Person> getPersonListMovies(String movie) throws PersonNotFoundException {
        HashMap<String, ArrayList<Person>> hm = splitPersonByMovies();
        if (hm.containsKey(movie)) {
            return hm.get(movie);
        }
        throw new PersonNotFoundException();
    }
    /**
     * Obtains a String of Person(s) that share that favourite movie in common, if exist.
     * @param movie The title of the favourite movie.
     * @return A String of Person(s) basic info that have in common the given movie as favourite.
     */
    private String getPersonListMoviesString(String movie) {
        String s = "";
        try {
            ArrayList<Person> al = getPersonListMovies(movie);
            s = "The user(s) with favourite movie " + movie + " is/are:\n";
            for (Person p : al) {
                s += p.getBasicInfo() + "\n";
            }
        } catch (PersonNotFoundException e) {
            System.out.println("Error: Does not exist no one with the favourite movie " + movie + "\n");
        }
        return s;
    }
    /**
     * Prints the user(s) that share the specified favourite movies basic info in the console.
     * @param movies Favourite movies of the users we want to find.
     */
    private void printPersonListMoviesToConsole(String movies) {
        System.out.println(getPersonListMoviesString(movies));
    }
    /**
     * Prints the user(s) that share the specified favourite movies basic infoin the specified file.
     * @param movies Favourite movies of the users we want to find.
     * @param filename File where we want to save the information.
     */
    private void printPersonListMoviesToFile(String movies, String filename) {
        File f;
        FileWriter fw;
        String s =  "";
        try {
            f = new File("files/" + filename);
            fw = new FileWriter(f);
            s += getPersonListMoviesString(movies);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: File was not found");
        }
    }




    // 3rd milestone


}
