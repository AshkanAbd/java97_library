package Library.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Book {
    private int id;
    private String name;
    private String writer;
    private Date freeDate;
    private String owner;

    public Book(String name, String writer) {
        this.name = name;
        this.writer = writer;
    }

    public Book(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        this.name = jsonObj.getString("name");
        this.name = jsonObj.getString("writer");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getFreeDate() {
        return freeDate;
    }

    public void setFreeDate(Date freeDate) {
        this.freeDate = freeDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toJSON(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("name",name);
            jsonObj.put("writer",writer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ name: ").append(name);
        builder.append(", write: ").append(writer);
        if (owner != null && !owner.isEmpty()) {
            builder.append(", owner: ").append(owner);
            builder.append(", free date: ").append(freeDate.toString());
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return name.hashCode() * writer.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return book.name.equals(name) && book.writer.equals(writer);
    }
}
