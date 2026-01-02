package com.enesderin.FixTrackBackend.repository;

import com.enesderin.FixTrackBackend.model.ServiceForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceFormRepository extends JpaRepository<ServiceForm, Long> {
    List<ServiceForm> findAllByCustomerId(long id);
}
