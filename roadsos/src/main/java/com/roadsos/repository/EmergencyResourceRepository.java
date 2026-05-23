package com.roadsos.repository;

import com.roadsos.model.EmergencyResource;
import com.roadsos.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyResourceRepository 
        extends JpaRepository<EmergencyResource, Long> {

    List<EmergencyResource> findByType(ResourceType type);

    List<EmergencyResource> findByAvailableTrue();

    List<EmergencyResource> findByTypeAndAvailableTrue(ResourceType type);
}