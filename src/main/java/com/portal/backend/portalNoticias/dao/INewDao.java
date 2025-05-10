package com.portal.backend.portalNoticias.dao;

import com.portal.backend.portalNoticias.model.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INewDao extends JpaRepository<New, Long> {

    Page<New> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
