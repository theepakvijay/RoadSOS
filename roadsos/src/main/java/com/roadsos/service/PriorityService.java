package com.roadsos.service;

import com.roadsos.model.EmergencyResource;
import com.roadsos.model.ResourceType;
import com.roadsos.model.SeverityLevel;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PriorityService {

    public Map<ResourceType, String> getPriorityOrder(SeverityLevel severity) {

        Map<ResourceType, String> priority = new LinkedHashMap<>();

        switch (severity) {

            case HIGH:
                // unconscious / multiple injured
                // contact all three simultaneously
                priority.put(ResourceType.AMBULANCE, 
                        "Call immediately — unconscious victim");
                priority.put(ResourceType.TRAUMA_CENTRE, 
                        "Alert trauma centre to prepare");
                priority.put(ResourceType.POLICE, 
                        "Secure the accident site");
                break;

            case MEDIUM:
                // injured but conscious
                priority.put(ResourceType.AMBULANCE, 
                        "Injured person needs medical attention");
                priority.put(ResourceType.HOSPITAL, 
                        "Nearest hospital for treatment");
                priority.put(ResourceType.POLICE, 
                        "For traffic management");
                break;

            case LOW:
                // minor accident, no injuries
                priority.put(ResourceType.POLICE, 
                        "File accident report");
                priority.put(ResourceType.HOSPITAL, 
                        "Optional — for precautionary checkup");
                break;
        }

        return priority;
    }

    public String getActionMessage(SeverityLevel severity) {
        return switch (severity) {
            case HIGH -> "CRITICAL: Multiple services alerted. " +
                         "Stay with the victim. Do not move them.";
            case MEDIUM -> "Help is on the way. " +
                           "Keep the victim calm and still.";
            case LOW -> "Police notified. " +
                        "Exchange details with other party.";
        };
    }
}
