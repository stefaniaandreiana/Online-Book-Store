package project;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private double price;
    private String category;
    private boolean availability;
    private String description;
    public Book(int bookId, String title, String author, double price, String category, boolean availability, String description) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.availability = availability;
        this.description = description;
    }
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
