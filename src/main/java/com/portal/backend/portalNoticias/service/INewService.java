package com.portal.backend.portalNoticias.service;

import com.portal.backend.portalNoticias.model.New;
import com.portal.backend.portalNoticias.model.NewResponseRest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface INewService {

    public ResponseEntity<NewResponseRest> save(New news);

    public ResponseEntity<NewResponseRest> deleteById(Long id);

    public Page<New> findAll(Pageable pageable);

    public Page<New> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
