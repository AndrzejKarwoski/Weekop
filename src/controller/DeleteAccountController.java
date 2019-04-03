package controller;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteAccount")
public class DeleteAccountController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        if(request.getUserPrincipal() != null){
            request.getRequestDispatcher("/WEB-INF/deleteAccount.jsp").forward(request, response);
        }
        else{
            response.sendError(403);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String inputPassword = request.getParameter("inputPassword");
        User user = (User) request.getSession().getAttribute("user");
        UserService userService = new UserService();
        String encryptedPassword = userService.encryptPassword(inputPassword);
        System.out.println(user.getPassword());
        System.out.println(encryptedPassword);
        if(user.getPassword().equals(encryptedPassword)){
            userService.deleteUser(user);
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/");
        }
        else{
            response.sendRedirect(request.getContextPath() + "/deleteAccount"); // dodaj komunikat o złym hasle
        }
    }
}