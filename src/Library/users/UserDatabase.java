package Library.users;

import Library.database.Database;
import Library.database.QueryBuilder;
import Library.model.Book;
import Library.model.User;
import org.json.JSONException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class UserDatabase extends Database {
    public UserDatabase(String username, String password, String database) {
        super(username, password, database);
    }

    public boolean checkBook(Book book) {
        try {
            Map<String, String> cause = createCause(book);
            String findBookQuery = QueryBuilder.selectBook(cause);
            ResultSet result = runQuery(findBookQuery);
            Date sqlData = result.getDate("free_date");
            java.util.Date today = Calendar.getInstance().getTime();
            if (sqlData.before(today))
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void requestBook(Book book, User user) {
        try {
            Map<String, String> cause = createCause(book);
            Date date = new Date(book.getFreeDate().getTime());
            String requestBookQuery = QueryBuilder.updateBook(book, user.getUsername(), date);
            runQuery(requestBookQuery);
            String findBookQuery = QueryBuilder.selectBook(cause);
            ResultSet bookResult = runQuery(findBookQuery);
            bookResult.first();
            Book book1 = getBookFromResult(bookResult);
            user.addBook(String.valueOf(book1.getId()));
            String updateUserQuery = QueryBuilder.updateUser(user);
            runQuery(updateUserQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> rentedBooks(User user) {
        List<Book> rentedList = new ArrayList<>();
        try {
            String findUserQuery = QueryBuilder.selectUser(user.getUsername());
            ResultSet userResult = runQuery(findUserQuery);
            userResult.first();
            User user1 = getUserFromResult(userResult);
            Map<String, String> cause = new HashMap<>();
            for (String idString : user1.getBooks()) {
                cause.clear();
                cause.put("id", idString);
                String findBookQuery = QueryBuilder.selectBook(cause);
                ResultSet bookResult = runQuery(findBookQuery);
                bookResult.first();
                rentedList.add(getBookFromResult(bookResult));
            }
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        return rentedList;
    }
}
