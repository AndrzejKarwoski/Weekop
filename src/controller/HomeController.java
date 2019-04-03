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

@WebServlet("")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        saveDiscoveriesInRequest(request);
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    private void saveDiscoveriesInRequest(HttpServletRequest request) {
        DiscoveryService service = new DiscoveryService();
        List<Discovery> listOfDiscoveries = service.getAllDiscoveries(new Comparator<Discovery>() {
            @Override
            public int compare(Discovery o1, Discovery o2) {
                long d1Vote = o1.getUpVote() - o1.getDownVote();
                long d2Vote = o2.getUpVote() - o2.getDownVote();
                if(d1Vote < d2Vote) {
                    return 1;
                } else if(d1Vote > d2Vote) {
                    return -1;
                }
                return 0;
            }
        });
        request.setAttribute("discoveries", listOfDiscoveries);
    }

}
