import java.util.ArrayList;
import java.util.Scanner;

class Book {
    int id;
    String title;
    String author;
    boolean isAvailable = true;

    Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    void displayDetails() {
        System.out
                .println(id + " | " + title + " by " + author + " | " + (isAvailable ? "Available" : "Not Available"));
    }
}

interface Searchable {
    boolean search(String keyword);
}

abstract class User {
    String name;
    int id;

    User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    void displayInfo() {
        System.out.println("User: " + name + " | ID: " + id);

    }

    abstract void borrowBook(Book book);

    abstract void returnBook(Book book);
}

class Student extends User {
    Student(String name, int id) {
        super(name, id);
    }

    void borrowBook(Book book) {
        if (book.isAvailable) {
            book.isAvailable = false;
            System.out.println(name + " borrowed " + book.title);
        } else {
            System.out.println("Book not available");
        }
    }

    void returnBook(Book book) {
        book.isAvailable = true;
        System.out.println(name + " returned " + book.title);
    }
}

class Admin extends User {
    Admin(String name, int id) {
        super(name, id);
    }

    void borrowBook(Book book) {
        System.out.println("admin cannot borrow books");
    }

    void returnBook(Book book) {
        System.out.println("admin do not return books");
    }

    void addBook(ArrayList<Book> library, Book book) {
        library.add(book);
        System.out.println("admin added book " + book.title);

    }
}

public class libraryProject {
    ArrayList<Book> library = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        libraryProject system = new libraryProject();
        system.start();

    }

    void start() {
        library.add(new Book(1, "Atomic Habits", "James Clear"));
        library.add(new Book(2, "The Alchemist", "Paulo Coelho"));
        library.add(new Book(3, "Clean Code", "Robert C. Martin"));

        Student student = new Student("Gaurav", 101);
        Admin admin = new Admin("AdminUser", 1);

        // Show menu
        menu(student, admin);
    }

    void menu(Student student, Admin admin) {
        while (true) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. List all books");
            System.out.println("2. Search book by title");
            System.out.println("3. Borrow book (Student)");
            System.out.println("4. Return book (Student)");
            System.out.println("5. Add book (Admin)");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    listBooks();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    borrowBook(student);
                    break;
                case 4:
                    returnBook(student);
                    break;
                case 5:
                    addBook(admin);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    void listBooks() {
        System.out.println("\nLibrary Books:");
        for (Book book : library) {
            book.displayDetails();
        }
    }

    void searchBook() {
        System.out.print("Enter keyword to search: ");
        String keyword = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Book book : library) {
            if (book.title.toLowerCase().contains(keyword) || book.author.toLowerCase().contains(keyword)) {
                book.displayDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found matching keyword.");
        }
    }

    void borrowBook(Student student) {
        System.out.print("Enter book ID to borrow: ");
        int id = sc.nextInt();
        sc.nextLine();

        Book book = findBookById(id);
        if (book != null) {
            student.borrowBook(book);
        } else {
            System.out.println("Book not found.");
        }
    }

    void returnBook(Student student) {
        System.out.print("Enter book ID to return: ");
        int id = sc.nextInt();
        sc.nextLine();

        Book book = findBookById(id);
        if (book != null) {
            student.returnBook(book);
        } else {
            System.out.println("Book not found.");
        }
    }

    void addBook(Admin admin) {
        System.out.print("Enter new book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter book title: ");
        String title = sc.nextLine();
        System.out.print("Enter book author: ");
        String author = sc.nextLine();

        Book newBook = new Book(id, title, author);
        admin.addBook(library, newBook);
    }

    Book findBookById(int id) {
        for (Book book : library) {
            if (book.id == id) {
                return book;
            }
        }
        return null;
    }

}
