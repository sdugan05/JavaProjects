import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static User[] users = new User[10];
    public static Account[] accounts = new Account[10];

    static void readUsers() throws IOException, ClassNotFoundException {
        userReader.readFromFile();

    }
    static void readAccounts() throws IOException, ClassNotFoundException {
        accountReader.readFromFile();
    }


    public static void Clear() throws IOException {
        Runtime.getRuntime().exec("clear");
    }
    static void Register() throws IOException {
        System.out.println("What is your first name?");
        String firstName = scanner.nextLine();
        System.out.println("What is your last name?");
        String lastName = scanner.nextLine();
        System.out.println("What is your age?");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("What is your gender? (M / F / O)");
        char gender = scanner.nextLine().charAt(0);
        for(int i = 0; i < users.length; i++) {
            if(users[i] == null) {
                users[i] = new User(firstName, lastName, age, gender);
                System.out.println("User " + firstName + " successfully registered.");
                break;
            }

        }
        for(int i = 0; i < accounts.length; i++) {
            if(accounts[i] == null) {
                accounts[i] = new Account(users[i]);
                System.out.println("Account successfully created.");
                break;
            }
        }
        System.out.println("Login or Create an account. (L / R)");
        String in = scanner.nextLine();
        if("R".equals(in) || "r".equals(in)) {
            Register();
        } else if("L".equals(in) || "l".equals(in)) {
            Login();
        }
    }

    static void AccountActions(int i) {
        label:
        while(true) {
            // TODO: Add delete account option
            System.out.println("Deposit, withdrawal or exit? (D / W / E)");
            switch (scanner.nextLine()) {
                case "E":
                case "e":
                    break label;
                case "D":
                case "d":
                    System.out.println("Amount to deposit: ");
                    accounts[i].Deposit(Double.parseDouble(scanner.nextLine()));
                    break;
                case "W":
                case "w":
                    System.out.println("Amount to withdrawal: ");
                    accounts[i].Withdrawal(Double.parseDouble(scanner.nextLine()));
                    break;
            }

            System.out.println("Balance: " + accounts[i].getBalance());
        }

    }
    static void Login() throws IOException {
        System.out.println("Enter account name: ");
        String name = scanner.nextLine();
        for(int i = 0; i < users.length; i++) {
            if(users[i] != null) {
                if(users[i].getFirstName().equals(name)) {
                    System.out.println("Successfully logged in as " + name + ".");
                    System.out.println("Account details:");
                    System.out.println("Balance: " + accounts[i].getBalance());
                    System.out.println("Date created: " + accounts[i].getDateCreated());
                    AccountActions(i);
                    Start();
                    userWriter uw = new userWriter();
                    userWriter.writeToFile("Users.txt", users);
                    accountWriter.writeToFile("Accounts.txt", accounts);
                } else System.out.println("User not found.");
            }
        }
    }
    public static void Start() throws IOException {
        Clear();
        System.out.println("Login or Register an account. (L / R)");
        String in = scanner.nextLine();
        if("R".equals(in) || "r".equals(in)) {
            Register();
        } else if("L".equals(in) || "l".equals(in)) {
            Login();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            readUsers();
            readAccounts();
        } catch (IOException ignored) {}
        Start();
    }
}
class Account implements java.io.Serializable {
    int id;
    User user;

    public Date getDateCreated() {
        return dateCreated;
    }

    Date dateCreated;
    public User getUser() {
        return user;
    }

    public double getBalance() {
        return balance;
    }

    double balance;
    boolean locked;

    public void Deposit(double deposit) {
        balance += deposit;
    }

    public void Withdrawal(double withdrawal) {
        balance -= withdrawal;
    }

    public Account(User user) {
        this.user = user;
        Random r = new Random();
        id = r.nextInt();
        balance = 1000;
        locked = false;
        dateCreated = new Date();
    }
}

class User implements java.io.Serializable {
    public String getFirstName() {
        return firstName;
    }

    String firstName;
    String lastName;
    int age;
    char gender;

    public User(String firstName, String lastName, int age, char gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }
}

class userWriter {
    static void writeToFile(String fileName, User[] users) throws IOException {
        File f = new File(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(users);
        oos.flush();
        oos.close();
    }
}

class userReader {

    static void readFromFile() throws IOException, ClassNotFoundException {
        File f = new File("Users.txt");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        Main.users = (User[]) ois.readObject();
        ois.close();
    }
}

class accountWriter {
    static void writeToFile(String fileName, Account[] accounts) throws IOException {
        File f = new File(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(accounts);
        oos.flush();
        oos.close();
    }
}

class accountReader {
    static void readFromFile() throws IOException, ClassNotFoundException {
        File f = new File("Accounts.txt");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        Main.accounts = (Account[]) ois.readObject();
        ois.close();
    }
}