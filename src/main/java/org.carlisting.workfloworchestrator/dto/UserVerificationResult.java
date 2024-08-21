package org.carlisting.workfloworchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVerificationResult {
    private Long userId;
    private boolean verified;
}
