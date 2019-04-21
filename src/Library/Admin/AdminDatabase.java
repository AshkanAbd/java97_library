package Library.Admin;

import Library.database.Database;
import Library.database.QueryBuilder;
import Library.model.Book;
import Library.model.User;
import org.json.JSONException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class AdminDatabase extends Database {

    public AdminDatabase(String username, String password, String database) {
        super(username, password, database);
    }

    public void insertBook(Book book) {
        try {
            String query = QueryBuilder.insertBook(book);
            runQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        try {
            String userQuery = QueryBuilder.selectUser(null);
            ResultSet result = runQuery(userQuery);
            while (result.next()) {
                userList.add(getUserFromResult(result));
            }
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<User> getUsers(String username) {
        List<User> userList = new ArrayList<>();
        try {
            String userQuery = QueryBuilder.selectUser(username);
            ResultSet result = runQuery(userQuery);
            while (result.next()) {
                userList.add(getUserFromResult(result));
            }
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void verifyBooks() throws SQLException {
        String selectBookQuery = QueryBuilder.selectBook(null);
        ResultSet result = runQuery(selectBookQuery);
        Date sqlDate;
        java.util.Date today = Calendar.getInstance().getTime();
        while (result.next()) {
            sqlDate = result.getDate("free_date");
            if (sqlDate.before(today)) {
                Book book = getBookFromResult(result);
                freeBook(book);
            }
        }
    }

    public void deleteBook(Book book) {
        try {
            Map<String, String> bookCause = createCause(book);
            String findBookQuery = QueryBuilder.selectBook(bookCause);
            ResultSet bookResult = runQuery(findBookQuery);
            Book book1 = getBookFromResult(bookResult);
            freeBook(book1);
            String deleteBookQuery = QueryBuilder.deleteBook(book1);
            runQuery(deleteBookQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(User user) {
        try {
            String findUserQuery = QueryBuilder.selectUser(user.getUsername());
            ResultSet userResult = runQuery(findUserQuery);
            user.setBooks(userResult.getString("books"));
            Map<String, String> bookCause = new HashMap<>();
            for (String idString : user.getBooks()) {
                bookCause.clear();
                bookCause.put("id", idString);
                String findBookQuery = QueryBuilder.selectBook(bookCause);
                Book book = getBookFromResult(runQuery(findBookQuery));
                freeBook(book);
            }
            String deleteUser = QueryBuilder.deleteUser(user);
            runQuery(deleteUser);
        } catch (SQLException | JSONException e) {

        }
    }
}
