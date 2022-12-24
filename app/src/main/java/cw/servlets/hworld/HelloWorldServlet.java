package cw.servlets.hworld;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@WebServlet(value = "/hello")
public class HelloWorldServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
//    String contentType = req.getHeader("Content-Type");
  }

  @Override
  protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html; charset=utf-8");
    response.getWriter().write("<h1>Привіт Servlets!</h1>");
    response.getWriter().write("<h1>Hi from JAVA! ${name} here=)</h1>".replace("${name}", parseName(request)));
    response.getWriter().write(getAllParameters(request));

    String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("Дата: yyyy.MM.dd, Час: HH:mm:ss"));
    response.getWriter().write("<h4>"+currentTime+"</h4>");

    String allHeaders = getAllHeaders(request);
    response.getWriter().write(allHeaders);

    // response.setHeader("Refresh",  "5"); // сторінка оновлюється кожні 5 сек

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
