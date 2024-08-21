package org.carlisting.workfloworchestrator.controller;

import org.carlisting.workfloworchestrator.dto.CarListingRequest;
import org.carlisting.workfloworchestrator.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @PostMapping("/car-listing")
    public ResponseEntity<String> initiateCarListingWorkflow(@RequestBody CarListingRequest request) {
        return workflowService.processCarListing(request);
    }
}
