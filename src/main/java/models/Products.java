package models;

public class Products {
    private String title;
    private String body;
    private int userId;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Products() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Products(String title, String body, int userId, int id) {
        setTitle(title);
        setBody(body);
        setUserId(userId);
        setId(id);
    }

}
