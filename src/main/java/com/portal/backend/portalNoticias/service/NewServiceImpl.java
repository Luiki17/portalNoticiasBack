package com.portal.backend.portalNoticias.service;

import com.portal.backend.portalNoticias.dao.INewDao;
import com.portal.backend.portalNoticias.model.New;
import com.portal.backend.portalNoticias.model.NewResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewServiceImpl implements INewService{

    @Autowired
    private INewDao newDao;

    @Override
    @Transactional
    public ResponseEntity<NewResponseRest> save(New news) {

        NewResponseRest response = new NewResponseRest();
        List<New> list = new ArrayList<>();

        try {
            New newsSaved = newDao.save(news);

            if (newsSaved.getId() != null) {
                list.add(newsSaved);
                response.getNewResponse().setNews(list);
                response.setMetadata("Response ok", "00", "New saved successfully");
            } else {
                response.setMetadata("Response error", "-1", "Error saving the new");
                return new ResponseEntity<NewResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            response.setMetadata("Response error", "-1", "Error saving the new");
            return new ResponseEntity<NewResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<NewResponseRest>(response, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<NewResponseRest> deleteById(Long id) {

        NewResponseRest response = new NewResponseRest();

        try {
            newDao.deleteById(id);
            response.setMetadata("Response ok", "00", "New deleted successfully");
        } catch (Exception e) {
            response.setMetadata("Response error", "-1", "Error deleting the new");
            return new ResponseEntity<NewResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<NewResponseRest>(response, HttpStatus.OK);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<New> findAll(Pageable pageable) {
        return newDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<New> findByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return newDao.findByTitleContainingIgnoreCase(title, pageable);
    }

}
