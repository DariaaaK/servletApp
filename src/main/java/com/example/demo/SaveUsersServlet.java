package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/saveUsersServlet")
public class SaveUsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User users = new User();

        users.setLogin(login);
        users.setPassword(password);


        int status = UsersRepository.save(users);


        if (status > 0) {
            out.print("User saved successfully!");
        } else {
            out.println("Sorry! unable to save user");
        }
        out.close();
    }
}