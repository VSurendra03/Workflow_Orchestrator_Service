package org.carlisting.workfloworchestrator.service;

import org.carlisting.workfloworchestrator.dto.CarListingRequest;
import org.carlisting.workfloworchestrator.dto.NotificationRequest;
import org.carlisting.workfloworchestrator.dto.UserVerificationResult;
import org.carlisting.workfloworchestrator.dto.VehicleListingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WorkflowService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtService jwtService;

    private static final String USER_SERVICE_URL = "http://user-service/api/users/verify";
    private static final String VEHICLE_SERVICE_URL = "http://vehicle-service/api/vehicles";
    private static final String NOTIFICATION_SERVICE_URL = "http://notification-service/api/notifications";

    public ResponseEntity<String> processCarListing(CarListingRequest request) {
        // Step 1: Verify user account
        UserVerificationResult userVerification = verifyUserAccount(request.getUserId());
        if (!userVerification.isVerified()) {
            return ResponseEntity.badRequest().body("User verification failed");
        }

        // Step 2: Create vehicle listing
        VehicleListingResult vehicleListing = createVehicleListing(request);
        if (vehicleListing == null || vehicleListing.getId() == null) {
            return ResponseEntity.badRequest().body("Failed to create vehicle listing");
        }

        // Step 3: Send confirmation notification
        boolean notificationSent = sendConfirmationNotification(request.getUserId(), vehicleListing.getId());
        if (!notificationSent) {
            // Log the failure but continue with the process
            System.out.println("Failed to send notification for listing " + vehicleListing.getId());
        }

        return ResponseEntity.ok("Car listing workflow completed successfully. Listing ID: " + vehicleListing.getId());
    }

    private UserVerificationResult verifyUserAccount(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken());
        HttpEntity<Long> request = new HttpEntity<>(userId, headers);
        return restTemplate.postForObject(USER_SERVICE_URL, request, UserVerificationResult.class);
    }

    private VehicleListingResult createVehicleListing(CarListingRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken());
        HttpEntity<CarListingRequest> httpEntity = new HttpEntity<>(request, headers);
        return restTemplate.postForObject(VEHICLE_SERVICE_URL, httpEntity, VehicleListingResult.class);
    }

    private boolean sendConfirmationNotification(Long userId, Long listingId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken());
        HttpEntity<NotificationRequest> request = new HttpEntity<>(new NotificationRequest(userId, "Your car listing " + listingId + " has been created successfully."), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(NOTIFICATION_SERVICE_URL, request, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }
}
