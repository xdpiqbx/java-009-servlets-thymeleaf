package cw.servlets.forum;

import cw.servlets.forum.command.CommandService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/forum/*")
public class ForumController extends HttpServlet {
  private TemplateEngine engine;
//  private MessageStorage messageStorage;
  private CommandService commandService;

  @Override
  public void init() throws ServletException {
    ServletContext servletContext = getServletContext();
    JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
    WebApplicationTemplateResolver resolver = new WebApplicationTemplateResolver(application);

    resolver.setPrefix("/WEB-INF/templates/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode("HTML5");
    resolver.setCacheable(false);

    engine = new TemplateEngine();
    resolver.setOrder(engine.getTemplateResolvers().size());
    engine.addTemplateResolver(resolver);

//    messageStorage = new InMemoryMessageStorage();
    commandService = new CommandService();
  }

  @Override
  public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    commandService.process(req, res, engine);
  }

//  @Override
//  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    if(req.getRequestURI().contains("delete")){
////      String id = req.getParameter("id");
////      messageStorage.deleteById(id);
//    } else {
////      String author = req.getParameter("author");
////      String content = req.getParameter("content");
////
////      // normally need to validate parameters
////
////      Message message = new Message();
////      message.setId(UUID.randomUUID().toString());
////      message.setAuthor(author);
////      message.setContent(content);
////
////      messageStorage.add(message);
//    }
//    resp.sendRedirect("/app/forum");
//  }
//
//  @Override
//  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////    resp.setContentType("text/html; charset=utf-8");
////
/////*
////    Map<String, String[]> parameterMap = req.getParameterMap();
////
////    Map<String, Object> params = new LinkedHashMap<>();
////
////    for (Map.Entry<String, String[]> keyValue: parameterMap.entrySet()) {
////      params.put(keyValue.getKey(), keyValue.getValue()[0]);
////    }
////
////    Context context = new Context(
////      req.getLocale(),
////      Map.of("queryParams", params)
////    );
////*/
////    Context context = new Context(
////      req.getLocale(),
////      Map.of("messages", messageStorage.getAllMessages())
////    );
////
//////    engine.process("назва шаблону", вхідні дані (локаль та Map змінних), записати результат у відповідь);
////    engine.process("forum", context, resp.getWriter());
////    resp.getWriter().close();
//  }
}
