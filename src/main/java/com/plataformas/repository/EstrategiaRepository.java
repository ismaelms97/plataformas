package com.plataformas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plataformas.model.Estrategia;

@Repository
public interface EstrategiaRepository  extends JpaRepository <Estrategia, Integer>{

}
