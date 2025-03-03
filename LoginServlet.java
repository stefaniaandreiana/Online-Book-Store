package project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_book_store", "root", "")) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", rs);
                    response.sendRedirect("home.jsp");
                } else {
                    request.setAttribute("error", "Parola incorectă!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Utilizatorul nu există!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



