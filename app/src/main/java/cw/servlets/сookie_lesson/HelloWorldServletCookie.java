package cw.servlets.сookie_lesson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@WebServlet(value = "/cookie")
public class HelloWorldServletCookie extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
//    String contentType = req.getHeader("Content-Type");
  }

  @Override
  protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // =================================================== Cookie
    // response.addCookie(new Cookie("key", "value"));
    // Cookie[] cookies = request.getCookies();

    // response.setHeader("Set-Cookie", "testCookie=testCookieValue");
    // response.setHeader("Set-Cookie", "name=John Doe");

    response.addCookie(new Cookie("name", URLEncoder.encode( "John Doe", StandardCharsets.UTF_8)));
    Cookie ageCooka = new Cookie("age", "45");
    response.addCookie(ageCooka);
//    Cookie[] allCookies = request.getCookies();
//    for (Cookie cookie : allCookies) {
//      System.out.println(cookie.getName() + " = " + cookie.getValue());
//    }
    // =================================================== Cookie end

    // =================================================== Session
    // request.getSession();
    // request.changeSessionId();
    // request.getRequestedSessionId();
    // request.isRequestedSessionIdValid();
    // request.isRequestedSessionIdFromURL();
    // request.isRequestedSessionIdFromCookie();

    HttpSession session = request.getSession(true);
    session.isNew();
    session.getId();

    session.setMaxInactiveInterval(3600);
    session.getMaxInactiveInterval();
    session.getLastAccessedTime();
    session.getCreationTime();

    session.setAttribute("key", "value");
    session.getAttribute("key");
    Enumeration<String> attributeNames = session.getAttributeNames();
    session.removeAttribute("key");

    ServletContext servletContext = session.getServletContext();

    session.invalidate();
    // =================================================== Session end

    response.setContentType("text/html; charset=utf-8");
    response.getWriter().write("<h1>Привіт Servlets!</h1>");
    response.getWriter().write("<h1>Hi from JAVA! ${name} here=)</h1>".replace("${name}", parseName(request)));
    response.getWriter().write(getAllParameters(request));

    String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("Дата: yyyy.MM.dd, Час: HH:mm:ss"));
    response.getWriter().write("<h4>"+currentTime+"</h4>");

    String allHeaders = getAllHeaders(request);
    response.getWriter().write(allHeaders);

    // response.setHeader("Refresh",  "5"); // сторінка оновлюється кожні 5 сек

    Cookie[] allCookies = request.getCookies();
    for (Cookie cookie : allCookies) {
      System.out.println(cookie.getName() + " = " + cookie.getValue());
    }

    response.getWriter().write("<h4>Writer зачинянється!</h4>");
    response.getWriter().close();
  }

  private String parseName(HttpServletRequest req){
    if(req.getParameterMap().containsKey("name")){
      return req.getParameter("name");
    }
    return "[unnamed]";
  }
  private  String getAllParameters(HttpServletRequest req) {
    String contentType = req.getContentType();
    System.out.println("=====>>> "+contentType);
    if("application/json".equals(contentType)){
      return getAllParametersJson(req);
    }else{
      return getAllParametersUrlEncoded(req);
    }
  }
  private String getAllParametersJson(HttpServletRequest req){
    try {
      String body = req.getReader().lines().collect(Collectors.joining("\n"));
      Type type = TypeToken.getParameterized(Map.class, String.class, String.class).getType();
      Map<String, String> params = new Gson().fromJson(body, type);
      return params.entrySet()
        .stream()
        .map(it -> it.getKey() + " = " + it.getValue())
        .collect(Collectors.joining("<br>"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  private String getAllParametersUrlEncoded(HttpServletRequest req){
    StringJoiner result = new StringJoiner("<br>");
    Enumeration<String> parameterNames = req.getParameterNames();
    while(parameterNames.hasMoreElements()){
      String parameterName = parameterNames.nextElement();
      String parameterValues = Arrays.toString(req.getParameterValues(parameterName));
      result.add(parameterName+"="+parameterValues);
    }
    return result.toString();
  }

  private String getAllHeaders(HttpServletRequest req){
    StringJoiner result = new StringJoiner("<br>");
    Enumeration<String> headerNames = req.getHeaderNames();
    while(headerNames.hasMoreElements()){
      String headerName = headerNames.nextElement();
      String headerValue = req.getHeader(headerName);
      result.add(headerName+" "+headerValue);
    }
    return result.toString();
  }
}
