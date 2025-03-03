package project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

@WebServlet("/placeOrder")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Preluare detalii comandă și salvare în baza de date
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double totalPrice = getBookPrice(bookId) * quantity;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_book_store", "root", "")) {
            // Adăugăm comanda
            String orderQuery = "INSERT INTO orders (user_id, total_price) VALUES (?, ?)";
            PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, user.getUserId());
            orderStmt.setDouble(2, totalPrice);
            orderStmt.executeUpdate();
            
            // Obținem ID-ul comenzii
            ResultSet rs = orderStmt.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);
            
            // Adăugăm produsele din comandă
            String orderItemQuery = "INSERT INTO order_items (order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";
            PreparedStatement itemStmt = conn.prepareStatement(orderItemQuery);
            itemStmt.setInt(1, orderId);
            itemStmt.setInt(2, bookId);
            itemStmt.setInt(3, quantity);
            itemStmt.setDouble(4, totalPrice);
            itemStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("orderConfirmation.jsp");
    }
    
    private double getBookPrice(int bookId) {
        // Obține prețul unei cărți din baza de date
        return 25.99;  // Exemplu
    }
}
