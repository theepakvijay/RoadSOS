package com.roadsos.service;

import com.roadsos.model.*;
import com.roadsos.repository.SosRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SosService {

    @Autowired
    private SosRequestRepository sosRequestRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PriorityService priorityService;

    public Map<String, Object> handleSos(Double latitude,
                                          Double longitude,
                                          SeverityLevel severity,
                                          String description) {

        // 1. Save SOS request to DB
        SosRequest sosRequest = new SosRequest();
        sosRequest.setLatitude(latitude);
        sosRequest.setLongitude(longitude);
        sosRequest.setSeverity(severity);
        sosRequest.setDescription(description);
        SosRequest saved = sosRequestRepository.save(sosRequest);

        // 2. Get priority order based on severity
        Map<ResourceType, String> priorityOrder = 
                priorityService.getPriorityOrder(severity);

        // 3. Find nearest resource for each priority type
        Map<String, Object> nearestResources = new HashMap<>();
        for (ResourceType type : priorityOrder.keySet()) {
            List<EmergencyResource> resources = 
                    locationService.findNearestResources(
                            latitude, longitude, type, 1);

            if (!resources.isEmpty()) {
                EmergencyResource nearest = resources.get(0);
                double distance = locationService.getDistanceTo(
                        latitude, longitude, nearest);

                Map<String, Object> resourceInfo = new HashMap<>();
                resourceInfo.put("name", nearest.getName());
                resourceInfo.put("phone", nearest.getPhone());
                resourceInfo.put("address", nearest.getAddress());
                resourceInfo.put("distanceKm", 
                        Math.round(distance * 10.0) / 10.0);
                resourceInfo.put("action", priorityOrder.get(type));
                resourceInfo.put("latitude", nearest.getLatitude());   // ← ADD THIS
                resourceInfo.put("longitude", nearest.getLongitude()); // ← ADD THIS

                nearestResources.put(type.toString(), resourceInfo);
            }
        }

        // 4. Build response
        Map<String, Object> response = new HashMap<>();
        response.put("sosId", saved.getId());
        response.put("status", saved.getStatus());
        response.put("severity", severity);
        response.put("message", 
                priorityService.getActionMessage(severity));
        response.put("resources", nearestResources);

        return response;
    }

    public SosRequest getSosById(Long id) {
        return sosRequestRepository.findById(id)
                .orElseThrow(() -> 
                        new RuntimeException("SOS request not found: " + id));
    }

    public List<SosRequest> getPendingSos() {
        return sosRequestRepository.findByStatus(SosStatus.PENDING);
    }
}