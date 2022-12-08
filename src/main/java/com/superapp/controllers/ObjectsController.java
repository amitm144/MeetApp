package com.superapp.controllers;

import com.superapp.boundaries.object.ObjectBoundary;
import com.superapp.boundaries.object.ObjectIdBoundary;
import com.superapp.boundaries.user.UserIdBoundary;
import com.superapp.logic.ObjectsService;
import com.superapp.util.wrappers.UserIdWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ObjectsController {

    private ObjectsService objService;

    @Autowired
    public void setObjectService(ObjectsService objService) {
        this.objService = objService;
    }

    @RequestMapping(
            path = {"/superapp/objects"},
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ObjectBoundary createObject(@RequestBody ObjectBoundary objectBoundary) {
        return this.objService.createObject(objectBoundary);
    }

    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{InternalObjectId}"},
            method = {RequestMethod.PUT},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public void updateObject(@RequestBody ObjectBoundary objectBoundary,
                             @PathVariable String superapp,
                             @PathVariable String InternalObjectId) {
        this.objService.updateObject(superapp, InternalObjectId, objectBoundary);
    }

    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{InternalObjectId}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ObjectBoundary retrieveObject(@PathVariable String superapp, @PathVariable String InternalObjectId) {
        return this.objService.getSpecificObject(superapp,InternalObjectId);
    }

    @RequestMapping(
            path = {"/superapp/objects"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ObjectBoundary[] getAllObjects() {
        List<ObjectBoundary> l = this.objService.getAllObjects();
        return l.toArray(new ObjectBoundary[0]);
    }
    private ArrayList<ObjectBoundary> getNRandomObjects(int n) {
        ArrayList<ObjectBoundary> userBoundaries = new ArrayList<ObjectBoundary>();
        Map<String, Object> map= new HashMap<String, Object>();
        map.put("key", "value for example");
        for (int i = 0; i < n; i++) {
            userBoundaries.add(
                new ObjectBoundary(
                        new ObjectIdBoundary(
                                "2023a.noam.levy",
                                String.format("id%d", i)),
                        "example-type",
                        "example-alias",
                        map,
                        new UserIdWrapper(new UserIdBoundary(String.format("temp%d@gmail.com", i)))
                )
            );
        }
        return userBoundaries;
    }
}
