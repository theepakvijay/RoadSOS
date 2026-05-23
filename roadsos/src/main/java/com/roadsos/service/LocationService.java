package com.roadsos.service;

import com.roadsos.model.EmergencyResource;
import com.roadsos.model.ResourceType;
import com.roadsos.repository.EmergencyResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private EmergencyResourceRepository emergencyResourceRepository;

    private static final double EARTH_RADIUS_KM = 6371.0;

    public double calculateDistance(double lat1, double lng1, 
                                    double lat2, double lng2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    public List<EmergencyResource> findNearestResources(
            double userLat, double userLng, 
            ResourceType type, int limit) {

        List<EmergencyResource> resources;

        if (type != null) {
            resources = emergencyResourceRepository
                            .findByTypeAndAvailableTrue(type);
        } else {
            resources = emergencyResourceRepository.findByAvailableTrue();
        }

        return resources.stream()
                .sorted((a, b) -> {
                    double distA = calculateDistance(
                            userLat, userLng, 
                            a.getLatitude(), a.getLongitude());
                    double distB = calculateDistance(
                            userLat, userLng, 
                            b.getLatitude(), b.getLongitude());
                    return Double.compare(distA, distB);
                })
                .limit(limit)
                .collect(Collectors.toList());
    }

    public double getDistanceTo(double userLat, double userLng,
                                 EmergencyResource resource) {
        return calculateDistance(
                userLat, userLng,
                resource.getLatitude(), resource.getLongitude());
    }
}