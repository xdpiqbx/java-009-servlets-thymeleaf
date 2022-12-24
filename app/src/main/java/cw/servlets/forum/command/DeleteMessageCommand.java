package cw.servlets.forum.command;

import cw.servlets.forum.InMemoryMessageStorage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class DeleteMessageCommand implements Command {
  @Override
  public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
    resp.setContentType("text/html; charset=utf-8");
    String id = req.getParameter("id");
    InMemoryMessageStorage.getInstance().deleteById(id);
    resp.sendRedirect("/app/forum");
  }
}
