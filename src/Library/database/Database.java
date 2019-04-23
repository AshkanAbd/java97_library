package Library.database;

import Library.model.Book;
import Library.model.User;
import org.json.JSONException;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private String url = "jdbc:mysql://localhost:3306/";
    private String username = "root";
    private String password = "Ashkan007";
    private Connection connection;

    public static int EXIST_EMAIL = 1;
    public static int EXIST_USERNAME = 2;
    public static int UNKNOWN = 3;

    public Database(final String username, final String password, String database) {
        loadDriver();
        this.username = username;
        this.password = password;
        url += database;
    }

    public void connectDatabase() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
            System.out.println("Close database connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet runQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        if (statement.execute(query)) {
            return statement.getResultSet();
        } else {
            return null;
        }
    }

    private void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean signIn(String username, String password) {
        try {
            String loginQuery = QueryBuilder.selectUser(username);
            ResultSet userResult = runQuery(loginQuery);
            if (!userResult.first()) return false;
            User user = getUserFromResult(userResult);
            if (user.getPassword().equals(password))
                return true;
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Object[] signUp(User user) {
        try {
            String signUpQuery = QueryBuilder.insertUser(user);
            runQuery(signUpQuery);
            return new Object[]{true};
        } catch (SQLException e) {
            if (e.getMessage().contains("email"))
                return new Object[]{false, EXIST_EMAIL};
            if (e.getMessage().contains("username"))
                return new Object[]{false, EXIST_USERNAME};
            return new Object[]{false, UNKNOWN};
        }
    }

    public List<Book> getBooks() {
        List<Book> bookList = new ArrayList<>();
        try {
            String booksQuery = QueryBuilder.selectBook(null);
            ResultSet result = runQuery(booksQuery);
            while (result.next()) {
                bookList.add(getBookFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public List<Book> getBooks(Map<String, String> cause) {
        List<Book> bookList = new ArrayList<>();
        try {
            String booksQuery = QueryBuilder.selectBook(cause);
            ResultSet result = runQuery(booksQuery);
            while (result.next()) {
                bookList.add(getBookFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public Book getBook(Book book) {
        try {
            Map<String, String> cause = createCause(book);
            String findBookQuery = QueryBuilder.selectBook(cause);
            ResultSet bookResult = runQuery(findBookQuery);
            return getBookFromResult(bookResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Book getBookFromResult(ResultSet resultSet) throws SQLException {
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

    protected User getUserFromResult(ResultSet resultSet) throws SQLException, JSONException {
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String booksArray = resultSet.getString("books");
        User user = new User(username, email, password);
        user.setBooks(booksArray);
        return user;
    }

    protected Map<String, String> createCause(Book book) {
        Map<String, String> cause = new HashMap<>();
        cause.put("book_name", book.getName());
        cause.put("writer", book.getWriter());
        return cause;
    }

    public void freeBook(Book book) {
        try {
            String updateBookQuery = QueryBuilder.updateBook(book, null, null);
            runQuery(updateBookQuery);
            String userInfoQuery = QueryBuilder.selectUser(book.getOwner());
            ResultSet result = runQuery(userInfoQuery);
            User user = new User(book.getOwner());
            result.first();
            user.setBooks(result.getString("books"));
            user.removeBook(Integer.toString(book.getId()));
            String updateUserQuery = QueryBuilder.updateUser(user);
            runQuery(updateUserQuery);
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
    }
}
