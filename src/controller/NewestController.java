package controller;

import model.Discovery;
import service.DiscoveryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/newest")
public class NewestController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        saveDiscoveriesInRequest(request);
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    private void saveDiscoveriesInRequest(HttpServletRequest request) {
        DiscoveryService service = new DiscoveryService();
        List<Discovery> listOfDiscoveriesByTime =
                service.getAllDiscoveriesFromLastDay((d1, d2) -> d2.getTimestamp().compareTo(d1.getTimestamp()));
        request.setAttribute("discoveries", listOfDiscoveriesByTime);
    }

}