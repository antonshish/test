package com.simple_rest.demo.dao;

import com.simple_rest.demo.entity.SimpleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleEntityDao extends JpaRepository<SimpleEntity, Integer> {
}
