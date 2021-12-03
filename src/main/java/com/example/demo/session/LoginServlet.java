package com.example.demo.session;
import com.example.demo.UsersRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String user = req.getParameter("user");
        String pwd = req.getParameter("pwd");
//        String userID = "admin";
//        String password = "password";


        try {
            if (checkerLogAndPass(user, pwd)) {
                HttpSession session = req.getSession();
                session.setAttribute("user", "user");
                session.setMaxInactiveInterval(30 * 60);
                Cookie userName = new Cookie("user", user);
                userName.setMaxAge(30 * 60);
                resp.addCookie(userName);
                PrintWriter out = resp.getWriter();
                out.println("Welcome back to the team, " + user + "!");
            } else {
                PrintWriter out = resp.getWriter();
                out.println("Either user name or password is wrong!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkerLogAndPass(String login, String password) throws SQLException {

        ResultSet rs = UsersRepository.getAllUsersRs();

        Map<String, String> usersPasAndLog = new HashMap<>();

        while (rs.next()) {

            usersPasAndLog.put(rs.getString("login"), rs.getString("password"));
        }
        if (usersPasAndLog.containsKey(login) && usersPasAndLog.get(login).equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
