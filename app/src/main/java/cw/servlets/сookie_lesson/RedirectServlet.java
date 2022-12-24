package cw.servlets.сookie_lesson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/search")
public class RedirectServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // https://www.google.com/search?q=JWT
    String googleSearchURL = "https://www.google.com/search";
    if(req.getParameterMap().containsKey("q")){
      String q = req.getParameterValues("q")[0];
      googleSearchURL += "?q=" + q;
    }
    resp.sendRedirect(googleSearchURL);
  }

  // CLIENT -> POST -> REDIRECT -> CLIENT -> GET
  // POST -> REDIRECT -> GET
  // PRG pattern
}
