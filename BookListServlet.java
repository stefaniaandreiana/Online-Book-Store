package project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_book_store", "root", "")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books WHERE availability = TRUE");
            while (rs.next()) {
                Book book = new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getString("category"),
                    rs.getBoolean("availability"),
                    rs.getString("description")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("books", books);
        request.getRequestDispatcher("book_list.jsp").forward(request, response);
    }
}
