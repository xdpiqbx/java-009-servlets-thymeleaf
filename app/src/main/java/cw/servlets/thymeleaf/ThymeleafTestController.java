package cw.servlets.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/thym")
public class ThymeleafTestController extends HttpServlet {
  private TemplateEngine engine;

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
//    resolver.setOrder(engine.getTemplateResolvers().size());
    engine.addTemplateResolver(resolver);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html; charset=utf-8");

    Map<String, String[]> parameterMap = req.getParameterMap();

    Map<String, Object> params = new LinkedHashMap<>();

    for (Map.Entry<String, String[]> keyValue: parameterMap.entrySet()) {
      params.put(keyValue.getKey(), keyValue.getValue()[0]);
    }

    Context context = new Context(
      req.getLocale(),
      Map.of("queryParams", params)
    );

//    engine.process("назва шаблону", вхідні дані (локаль та Map змінних), записати результат у відповідь);
    engine.process("test", context, resp.getWriter());
    resp.getWriter().close();
  }
}
