package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godfather on 5/17/2016.
 */
@Controller
public class ToDoTrackerController {

    @Autowired
    ToDoRepository todos;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model) {
        Iterable<ToDo> allToDos = todos.findAll();
        List<ToDo> toDoList = new ArrayList<ToDo>();
        for (ToDo toDo : allToDos) {
            toDoList.add(toDo);
        }
        model.addAttribute("todos", toDoList);
        return "home";
    }

    @RequestMapping(path = "/add-todo", method = RequestMethod.POST)
    public String addToDo(String todoName, boolean is_done) {
        ToDo toDo = new ToDo(todoName, is_done);
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
