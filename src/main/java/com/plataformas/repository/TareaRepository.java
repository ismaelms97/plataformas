package com.plataformas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plataformas.model.Tarea;

@Repository
public interface TareaRepository  extends JpaRepository <Tarea, Integer>{

}
