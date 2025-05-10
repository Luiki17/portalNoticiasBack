package com.portal.backend.portalNoticias.controller;

import com.portal.backend.portalNoticias.model.New;
import com.portal.backend.portalNoticias.model.NewResponseRest;
import com.portal.backend.portalNoticias.response.ErrorResponse;
import com.portal.backend.portalNoticias.service.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/v1")
public class NewController {

    @Autowired
    private INewService newService;

    @PostMapping("/news")
    public ResponseEntity<?> save(@RequestBody New news) {

        try {
            ResponseEntity<NewResponseRest> response = newService.save(news);

            if(response.getBody().getNewResponse().getNews().isEmpty()){
                return new ResponseEntity<>(new ErrorResponse(404, "No news found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        try {
            ResponseEntity<NewResponseRest> response = newService.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/news/page/{page}")
    public Page<New> getNews(@PathVariable Integer page) {
        return newService.findAll(PageRequest.of(page, 10));
    }

    @GetMapping("/news/filter/{title}/page/{page}")
    public Page<New> getNewsByTitle(@PathVariable String title, @PathVariable Integer page) {
        return newService.findByTitleContainingIgnoreCase(title, PageRequest.of(page, 10));
    }

}