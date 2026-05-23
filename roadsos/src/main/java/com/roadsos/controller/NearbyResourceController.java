package com.roadsos.controller;

import com.roadsos.model.EmergencyResource;
import com.roadsos.model.ResourceType;
import com.roadsos.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NearbyResourceController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/nearby")
    public ResponseEntity<Map<String, Object>> getNearbyResources(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(required = false) ResourceType type,
            @RequestParam(defaultValue = "5") int limit) {

        List<EmergencyResource> resources = 
                locationService.findNearestResources(lat, lng, type, limit);

        Map<String, Object> response = new HashMap<>();
        response.put("userLocation", Map.of("lat", lat, "lng", lng));
        response.put("totalFound", resources.size());
        response.put("resources", resources.stream().map(resource -> {
            Map<String, Object> r = new HashMap<>();
            r.put("id", resource.getId());
            r.put("name", resource.getName());
            r.put("type", resource.getType());
            r.put("phone", resource.getPhone());
            r.put("address", resource.getAddress());
            r.put("distanceKm",
                    Math.round(locationService.getDistanceTo(
                            lat, lng, resource) * 10.0) / 10.0);
            r.put("latitude", resource.getLatitude());    // ← ADD THIS
            r.put("longitude", resource.getLongitude());
            r.put("city", resource.getCity());        // ← add
            r.put("country", resource.getCountry());  // ← add
            return r;
        }).toList());

        return ResponseEntity.ok(response);
    }
}