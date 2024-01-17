package com.example.e.development.controller;

import com.example.e.development.models.Module;
import com.example.e.development.service.CourseService;
import com.example.e.development.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {
    private final ModuleService moduleService;

    private final CourseService courseService;

    public ModuleController(ModuleService moduleService, CourseService courseService) {
        this.moduleService = moduleService;
        this.courseService = courseService;
    }

    @PostMapping("/create/{courseId}")
    public ResponseEntity<Module> createModule(@PathVariable Long courseId , @RequestBody Module module){

        Module createdModule = moduleService.createModule(courseId, module);
        return new ResponseEntity<>(createdModule,HttpStatus.CREATED);
    }

}
