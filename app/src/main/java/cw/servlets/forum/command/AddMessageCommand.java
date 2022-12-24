package cw.servlets.forum.command;

import cw.servlets.forum.InMemoryMessageStorage;
import cw.servlets.forum.Message;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class AddMessageCommand implements Command{
  @Override
  public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
    resp.setContentType("text/html; charset=utf-8");

    String author = req.getParameter("author");
    String content = req.getParameter("content");

    // normally need to validate parameters

    Message message = new Message();
    message.setId(UUID.randomUUID().toString());
    message.setAuthor(author);
    message.setContent(content);

    InMemoryMessageStorage.getInstance().add(message);

    resp.sendRedirect("/app/forum");
  }
}
