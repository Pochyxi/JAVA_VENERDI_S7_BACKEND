package com.example.java_venerdi_s7.controller;


import com.example.java_venerdi_s7.entities.RoleType;
import com.example.java_venerdi_s7.entities.Sonda;
import com.example.java_venerdi_s7.services.RoleService;
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
@RequestMapping("/api/sonde")
@CrossOrigin(origins = "*")
public class SondaController {
    @Autowired
    private SondaService ss;

    @Autowired
    private RoleService rs;

    @GetMapping("")
    @CrossOrigin
    public List<Sonda> getAllSonde() {
        return ss.getAll();
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Sonda>> getAllPageable( Pageable p) {
        Page<Sonda> findAll = ss.getAllPaginate(p);

        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Sonda> getById(@PathVariable Long id) throws Exception {

        return new ResponseEntity<>( ss.getById(id), HttpStatus.OK);

    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Sonda> getByUsername(@PathVariable String username) throws Exception {
        return new ResponseEntity<>( ss.findByUsername(username), HttpStatus.OK);

    }

    @PostMapping("/new")
    public void create(@RequestBody Sonda sonda) {
        try {
            ss.save(sonda);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @PostMapping("/new/{nomeSonda}/{username}/{password}/{latitudine}/{longitudine}")
    public Sonda create(
            @PathVariable("nomeSonda") String nomeSonda,
            @PathVariable("username") String username,
            @PathVariable("password") String password,
            @PathVariable("latitudine") double latitudine,
            @PathVariable("longitudine") double longitudine
    ) {
        try {
            Sonda sonda = new Sonda();
            sonda.setNomeSonda(nomeSonda);
            sonda.setUsername(username);
            sonda.setPassword(password);
            sonda.setLatitudine(latitudine);
            sonda.setLongitudine(longitudine);
            ss.save(sonda);

            return sonda;
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;

    }

    @PutMapping("")
    public void update(@RequestBody Sonda sonda) {
        try {
            ss.save(sonda);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @PutMapping("/{id}/add-role/{roleType}")
    public void addRole(@PathVariable("id") Long id, @PathVariable("roleType") RoleType roleType) throws Exception {
        Sonda u = ss.getById(id);
        u.addRole(rs.getByRole(roleType));

        ss.update(u);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        try {
            ss.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
