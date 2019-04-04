package controller;


import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/changePassword")
public class ChangePasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        if(request.getUserPrincipal() != null){
            request.getRequestDispatcher("/WEB-INF/changePassword.jsp").forward(request, response);
        }
        else{
            response.sendError(403);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String inputOldPassword = request.getParameter("inputOldPassword");
        String inputNewPassword = request.getParameter("inputNewPassword");
        System.out.println(inputOldPassword);
        System.out.println(inputNewPassword);
        User user = (User) request.getSession().getAttribute("user");
        UserService userService = new UserService();
        String encryptedPassword = userService.encryptPassword(inputOldPassword);
        if(user.getPassword().equals(encryptedPassword)){
            userService.updatePassword(user,inputNewPassword);
            response.sendRedirect(request.getContextPath() + "/");
        }
        else{
            response.sendRedirect(request.getContextPath() + "/changePassword"); // dodaj komunikat o złym hasle
        }
    }

}