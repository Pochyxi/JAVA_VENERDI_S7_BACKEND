package com.example.java_venerdi_s7.services;

import com.example.java_venerdi_s7.entities.Misurazione;
import com.example.java_venerdi_s7.repository.MisurazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MisurazioneService {

    @Autowired
    MisurazioneRepository mr;

    public Misurazione save( Misurazione x) {
        return mr.save(x);
    }

    public List<Misurazione> getAll() {
        return mr.findAll();
    }

    public Misurazione getById(Long id) throws Exception {
        Optional<Misurazione> ba = mr.findById(id);
        if ( ba.isEmpty() )
            throw new Exception("Payment not available");
        return ba.get();
    }

    public void deleteById(Long id) {
        mr.deleteById(id);
    }

    public Page<Misurazione> getAllPaginate( Pageable p) {
        return mr.findAll(p);
    }

    public List<Misurazione> getMisurazioneBySondaId(long sondaId) {
        return mr.getMisurazioneBySondaId( sondaId );
    }
}
