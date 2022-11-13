package com.example.java_venerdi_s7.controller;

import com.example.java_venerdi_s7.entities.Misurazione;
import com.example.java_venerdi_s7.entities.Sonda;
import com.example.java_venerdi_s7.services.MisurazioneService;
import com.example.java_venerdi_s7.services.SondaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/misurazioni")
@CrossOrigin(origins = "*")
public class MisurazioneController {

    @Autowired
    MisurazioneService ms;

    @Autowired
    SondaService ss;

    @GetMapping("")
    public List<Misurazione> getAllMisurazioni() {
        return ms.getAll();
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Misurazione>> getAllPageable( Pageable p ) {
        Page<Misurazione> findAll = ms.getAllPaginate( p );

        if( findAll.hasContent() ) {
            return new ResponseEntity<>( findAll, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND );
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Misurazione> getById( @PathVariable Long id ) throws Exception {

        return new ResponseEntity<>( ms.getById( id ), HttpStatus.OK );

    }

    @GetMapping("/sonda/{id}")
    public List<Misurazione> getMisurazioneBySondaId(@PathVariable Long id) {
        return ms.getMisurazioneBySondaId( id );
    }

    //QUESTO FUNZIONA
    @PostMapping("/new/{sondaId}/{smokeLevel}")
    public Misurazione create(
            @PathVariable("sondaId") int sonda_id,
            @PathVariable("smokeLevel") int smoke_level ) throws Exception {

        Sonda so = ss.getById( ( long ) sonda_id );

        if( so != null ) {
            Misurazione m = new Misurazione();
            m.setSmokeLevel( smoke_level );
            m.setSonda( so );
            ms.save( m );
            return m;
        } else {
            throw new Exception( "l'utente non ha i permessi per effettuare questa transazione" );
        }
    }

    // QUESTO NON FUNZIONA : "Cannot invoke \"java.lang.Integer.intValue()\" because \"sondaId\" is null"
    // APPROFONDIRE IL PERCHE'
//    @PostMapping("/new")
//    public void create(
//            @RequestParam(value="sondaId") Integer sondaId,
//            @RequestParam(value="smokeLevel") Integer smokeLevel ) throws Exception {
//
//        Sonda so = ss.getById( ( long ) sondaId );
//
//        if( so != null ) {
//            Misurazione m = new Misurazione();
//            m.setSmokeLevel( smokeLevel );
//            m.setSonda( so );
//            ms.save( m );
//        } else {
//            throw new Exception( "la sonda non ha i permessi per effettuare questa operazione" );
//        }
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById( @PathVariable Long id ) {
        try {
            ms.deleteById( id );
        } catch( Exception e ) {
            log.error( e.getMessage() );
        }
    }
}
