package com.simple_rest.demo.api;


import com.simple_rest.demo.dao.SimpleEntityDao;
import com.simple_rest.demo.service.SimpleService;
import com.simple_rest.demo.service.dto.SimpleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("api/simple")
public class SimpleApi {

    @Autowired
    private SimpleService service;

    @GetMapping("/{id}")
    public ResponseEntity<SimpleDto> getEntityById(@PathVariable(name = "id", required = true) int id) {
        SimpleDto dto = service.findById(id);
        return dto == null ? ResponseEntity.notFound().build() : new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity saveEntity(@RequestBody SimpleDto simpleEntity) {
        if(!StringUtils.hasLength(simpleEntity.getName()) || simpleEntity.getId() > 0)
            return ResponseEntity.badRequest().build();
        service.save(simpleEntity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntity(@PathVariable(name = "id", required = true) int id) {
        service.deleteById(id);
    }

    @PutMapping("/update")
    public ResponseEntity updateEntity(@RequestBody SimpleDto entity) {
        if(entity.getId() < 1)
            return ResponseEntity.badRequest().build();
        service.save(entity);
        return ResponseEntity.ok().build();
    }
}
