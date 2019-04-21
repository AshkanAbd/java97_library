package Library.Admin;

import Library.database.DataBase;
import Library.database.QueryBuilder;
import Library.model.Book;
import Library.model.User;
import org.json.JSONException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdminDataBase extends DataBase {

    public AdminDataBase(String username, String password, String database) {
        super(username, password, database);
    }

    public boolean insertBook(Book book) {
        try {
            String query = QueryBuilder.insertBook(book);
            runQuery(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void verifyBooks() throws SQLException {
        String query = QueryBuilder.selectBook(null);
        ResultSet result = runQuery(query);
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
            Map<String, String> bookCause = new HashMap<>();
            bookCause.put("book_name", book.getName());
            bookCause.put("writer", book.getWriter());
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

    private Book getBookFromResult(ResultSet resultSet) throws SQLException {
        String bookName = resultSet.getString("book_name");
        String writer = resultSet.getString("writer");
        String owner = resultSet.getString("owner");
        int id = resultSet.getInt("id");
        Date date = resultSet.getDate("free_date");
        Book book = new Book(bookName, writer);
        book.setOwner(owner);
        book.setId(id);
        book.setFreeDate(date);
        return book;
    }

    public void freeBook(Book book) {
        try {
            String updateBookQuery = QueryBuilder.updateBook(book, null, null);
            runQuery(updateBookQuery);
            String userInfoQuery = QueryBuilder.selectUser(book.getOwner());
            ResultSet result = runQuery(userInfoQuery);
            User user = new User(book.getOwner());
            user.setBooks(result.getString("books"));
            user.removeBook(Integer.toString(book.getId()));
            String updateUserQuery = QueryBuilder.updateUser(user);
            runQuery(updateUserQuery);
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
    }

}
