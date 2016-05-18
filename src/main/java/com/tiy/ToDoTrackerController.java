package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godfather on 5/17/2016.
 */
@Controller
public class ToDoTrackerController {

    @Autowired
    ToDoRepository todos;

    @Autowired
    UserRepository users;

    @PostConstruct // add a user to the database right when the app starts up
    public void init() {
        if (users.count() == 0) {
            User user = new User();
            user.name = "Godfather";
            user.password = "123";
            users.save(user);
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            user = new User(userName, password);
            users.save(user);
            session.setAttribute("user", user);
        }
        else if (!password.equals(user.getPassword())) {
            throw  new Exception("Incorrect password");
        }
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    public void setSessionAttribute(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {
        setSessionAttribute(model, session);
        List<ToDo> toDoList = new ArrayList<ToDo>();
//        String name = (String) session.getAttribute("name");
//        User user = users.findFirstByName(name);
        User savedUser = (User)session.getAttribute("user");
        if (savedUser != null) {
            toDoList = todos.findByUser(savedUser);
        } else {

//        if (user != null) {
//            model.addAttribute("user", user);
//        }
            Iterable<ToDo> allToDos = todos.findAll();

            for (ToDo toDo : allToDos) {
                toDoList.add(toDo);
            }
        }
        model.addAttribute("todos", toDoList);
        return "home";
    }

    @RequestMapping(path = "/add-todo", method = RequestMethod.POST)
    public String addToDo(HttpSession session, String todoName, boolean is_done) {
//        String name = (String) session.getAttribute("name");
//        User user = users.findFirstByName(name);
        User user = (User) session.getAttribute("user");
        ToDo toDo = new ToDo(todoName, is_done, user);
        todos.save(toDo); // saves to repo
        return "redirect:/";
    }

    @RequestMapping(path = "/searchByName", method = RequestMethod.GET)
    public String queryToDosByName(Model model, String search) {
        System.out.println("Searching by ..." + search);
        List<ToDo> toDoList = todos.findByNameStartsWith(search);
        model.addAttribute("todos", toDoList);
        return "home";
    }

    @RequestMapping(path = "/searchByBoolean", method = RequestMethod.GET)
    public String queryToDosByBoolean(Model model, String search) {
        System.out.println("Searching by ..." + search);
//        List<ToDo> toDoList = todos.findByNameStartsWith(search);
        List<ToDo> toDoList = todos.findByNameStartsWith(search);
        model.addAttribute("todos", toDoList);
        return "home";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteToDo(Model model, Integer toDoID) {
        if (toDoID != null) {
            todos.delete(toDoID);
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/modify", method = RequestMethod.GET)
    public String modify(Model model, Integer toDoID) {
        if (toDoID != null) {
            ToDo toDo = todos.findOne(toDoID);
            toDo.is_done = ! toDo.is_done;
            todos.save(toDo);
        }

        return "redirect:/";
    }
}
