package org.carlisting.workfloworchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowResult {
    private Long listingId;
    private boolean success;
    private String message;
}
