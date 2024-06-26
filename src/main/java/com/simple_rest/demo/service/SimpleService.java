package com.simple_rest.demo.service;

import com.simple_rest.demo.dao.SimpleEntityDao;
import com.simple_rest.demo.entity.SimpleEntity;
import com.simple_rest.demo.service.dto.SimpleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleService {
    @Autowired
    private SimpleEntityDao simpleEntityDao;

    public void save(SimpleDto dto) {
        if(dto == null)
            return;
        simpleEntityDao.save(toEntity(dto));
    }

    public SimpleDto findById(int id) {
        return toDto(simpleEntityDao.findById(id).get());
    }

    public void deleteById(int id) {
        simpleEntityDao.deleteById(id);
    }

    private SimpleEntity toEntity(SimpleDto dto) {
        SimpleEntity entity = new SimpleEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    private SimpleDto toDto(SimpleEntity entity) {
        if(entity == null)
            return null;
        SimpleDto dto = new SimpleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
