package cw.servlets.forum.command;

import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandService implements Command{
  private Map<String, Command> commands;
  public CommandService(){
    commands = new HashMap<>();
    commands.put("GET /app/forum", new GetMessagesCommand());
    commands.put("POST /app/forum/delete", new DeleteMessageCommand());
    commands.put("POST /app/forum", new AddMessageCommand());
  }
  @Override
  public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException{
    String requestURI = req.getRequestURI();
    String commandKey = req.getMethod() + " " + requestURI;
    commands.get(commandKey).process(req, resp, engine);
  }
}
