package Library.users;

import Library.controller.Controller;
import Library.model.Book;
import Library.model.User;

import java.util.List;

public class UserController {
    private User user;
    private Controller controller;
    private int screenWidth, screenHeight;
    private UserDatabase database;

    public UserController(int screenWidth, int screenHeight, User user, Controller preController) {
        this.user = user;
        this.controller = preController;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        database = new UserDatabase("root", "Ashkan007", "java_library");
        database.connectDatabase();
        removePreView();
        setupRentedBooks();
    }


    private void setupRentedBooks() {
        List<Book> bookList = database.getBooks();
    }

    private void removePreView() {
        controller.getStartButtonBox().setVisible(false);
        controller.getAppTitle().setVisible(false);
    }
}
