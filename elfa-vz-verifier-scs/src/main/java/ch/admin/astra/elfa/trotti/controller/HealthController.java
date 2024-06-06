package ch.admin.astra.elfa.trotti.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Tag(name = "Health", description = "Access the health status of the vz verifier")
class HealthController {

    @Operation(summary = "Returns health status", description = "Returns HTTP status code 200 if alive", tags = {"Health"})
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json"))
        @GetMapping(value = "/liveness", produces = {"application/json"})
    public ResponseEntity<Void> isAlive() {
        return ResponseEntity.ok().build();
    }
}