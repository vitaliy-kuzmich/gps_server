package vitaliy.kuzmich.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vitaliy.kuzmich.model.PositionModel;
import vitaliy.kuzmich.model.User;
import vitaliy.kuzmich.services.PositionModelService;
import vitaliy.kuzmich.services.UserService;

import java.util.List;

@RestController
public class Positions {
    @Autowired
    PositionModelService service;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/add-position")
    public void add(PositionModel pos, Long userId) throws Exception {
        User u = userService.getByID(userId);

        service.addOrUpdatePos(pos, userService.getByID(userId));

    }

    @RequestMapping(value = "/get-position")
    public List<PositionModel> get(long userId, long fromPeriod, long toPeriod) throws Exception {
        User u = userService.getByID(userId);
        return service.getByPeriod(u, fromPeriod, toPeriod);

    }
}
