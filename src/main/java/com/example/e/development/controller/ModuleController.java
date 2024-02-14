package com.example.e.development.controller;

import com.example.e.development.course.CourseService;
import com.example.e.development.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping("/edit/{moduleId}")
    public ResponseEntity<Module> editModule(@PathVariable Long moduleId, @RequestBody Module module){
        Module updatedModule = moduleService.editModule(moduleId, module);
        return new ResponseEntity<>(updatedModule, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<Module>> getAllModules(){
        List<Module> modules = moduleService.getAllModules();
        return ResponseEntity.ok(modules);
    }
    @GetMapping("/{moduleId}")
    public ResponseEntity<Module> getModuleById(@PathVariable Long moduleId){
        Module module = moduleService.getModuleById(moduleId);
        return ResponseEntity.ok(module);
    }


    @DeleteMapping("delete/{moduleId}")
    public ResponseEntity<String> deleteModule(@PathVariable Long moduleId){

        boolean isDeleted = moduleService.deleteModule(moduleId);

        if (isDeleted){
            return ResponseEntity.ok("successfully deleted!");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
