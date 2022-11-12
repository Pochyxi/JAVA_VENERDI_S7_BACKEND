package com.example.java_venerdi_s7.repository;

import com.example.java_venerdi_s7.entities.Misurazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MisurazioneRepository extends JpaRepository<Misurazione, Long> {

    @Query(
            value = "select m from Misurazione m where m.sonda.id = :id"
    )
    public List<Misurazione> getMisurazioneBySondaId( @Param ( "id" ) Long id );

}
