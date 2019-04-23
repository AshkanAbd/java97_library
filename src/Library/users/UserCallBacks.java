package Library.users;

import Library.controller.BaseCallBack;
import Library.model.Book;
import Library.view.BookPane;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class UserCallBacks extends BaseCallBack {
    private UserController userController;

    UserCallBacks(UserController controller) {
        this.userController = controller;
    }

    void rentedBookFilter(MouseEvent mouseEvent, String list, String name, String writer, String owner, String date, boolean isRent) {
        if (invalidClick(mouseEvent)) return;
        Map<String, String> cause = new HashMap<>();
        if (!name.isEmpty()) cause.put("book_name", name);
        if (!writer.isEmpty()) cause.put("writer", writer);
        if (isRent) {
            cause.put("owner", "");
        } else {
            if (!owner.isEmpty()) cause.put("owner", owner);
            if (!date.isEmpty()) cause.put("free_date", date);
        }
        List<Book> bookList = userController.database.getBooks(cause);
        if (list.equalsIgnoreCase("All books")) {
            userController.refreshAllBookList(bookList);
        } else {
            List<Book> rentedBooks = new ArrayList<>();
            for (Book book : bookList) {
                if (userController.rentedBookList.contains(book)) {
                    rentedBooks.add(book);
                }
            }
            userController.refreshRentedBookList(rentedBooks);
        }
    }

    private void requestRent(MouseEvent mouseEvent, Book book, DatePicker datePicker) {
        if (invalidClick(mouseEvent)) return;
        String dateString = datePicker.getEditor().getText();
        if (dateString.isEmpty()) return;
        Date today = Date.valueOf(LocalDate.now());
        Date rentDate = Date.valueOf(dateString);
        if (!rentDate.after(today)) return;
        book.setFreeDate(rentDate);
        userController.database.requestBook(book, userController.user);
        userController.refreshAllBookList(userController.database.getBooks());
        userController.refreshRentedBookList(userController.database.rentedBooks(userController.user));
        userController.bookInfoPane.setContent(null);
    }

    private void paybackBook(MouseEvent mouseEvent, Book book) {
        if (invalidClick(mouseEvent)) return;
        userController.database.freeBook(book);
        userController.user.removeBook(String.valueOf(book.getId()));
        userController.refreshAllBookList(userController.database.getBooks());
        userController.refreshRentedBookList(userController.database.rentedBooks(userController.user));
        userController.bookInfoPane.setContent(null);
    }

    void onBookClick(MouseEvent mouseEvent, Book book) {
        if (invalidClick(mouseEvent)) return;
        BookPane bookPane = new BookPane(book);
        userController.bookInfoPane.setContent(bookPane.getPane());
        if (book.getOwner() == null || book.getOwner().isEmpty()) {
            bookPane.getButton().setText("Rent");
            bookPane.setOnClick(e -> requestRent(e, book, bookPane.getDatePicker()));
        } else if (book.getOwner().equals(userController.user.getUsername())) {
            bookPane.getButton().setText("Payback");
            bookPane.setOnClick(e -> paybackBook(e, book));
        } else {
            bookPane.getButton().setVisible(false);
        }
    }

    void changeController(MouseEvent mouseEvent) {
        if (invalidClick(mouseEvent)) return;
        userController.booksScrollPane.setVisible(false);
        userController.rentedScrollPane.setVisible(false);
        userController.signOut.setVisible(false);
        userController.searchBox.setVisible(false);
        userController.bookInfoPane.setVisible(false);
        userController.controller.getStartButtonBox().setVisible(true);
        userController.controller.getAppTitle().setVisible(true);
        userController.controller.getMain().getLoader().setController(userController.controller);
        userController.database.close();
    }
}
