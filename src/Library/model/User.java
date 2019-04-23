package Library.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private String password;
    private List<String> books;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.books = new ArrayList<>();
    }

    public User(String username) {
        this.username = username;
        this.books = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(String booksArray) throws JSONException {
        books = new ArrayList<>();
        if (booksArray == null || booksArray.isEmpty()) {
            return;
        }
        JSONArray jsonArray = new JSONArray(booksArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            books.add(jsonArray.getString(i));
        }
    }

    public void addBook(String bookID) {
        books.add(bookID);
    }

    public void removeBook(String bookID) {
        books.remove(bookID);
    }

    public String getBooksJSON() {
        JSONArray jsonArray = new JSONArray();
        for (String str : books)
            jsonArray.put(str);
        return jsonArray.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ username: ").append(username);
        if (email != null) {
            builder.append(", email: ").append(email);
        }
        if (password != null) {
            builder.append(", password: ").append(password);
        }
        if (books != null) {
            builder.append(", books: ").append(books.toString());
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof User)) return false;
        User user = (User) o;
        return user.username.equals(username);
    }

    @Override
    public int hashCode() {
        return username.hashCode() * 29;
    }
}
