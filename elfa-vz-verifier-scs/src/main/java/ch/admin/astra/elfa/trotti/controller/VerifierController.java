package ch.admin.astra.elfa.trotti.controller;

import ch.admin.astra.elfa.trotti.api.StartVerificationRequest;
import ch.admin.astra.elfa.trotti.api.UseCaseResponse;
import ch.admin.astra.elfa.trotti.api.VerificationBeginResponse;
import ch.admin.astra.elfa.trotti.api.VerificationStatusResponse;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;
import ch.admin.astra.elfa.trotti.domain.verifier.VerifierService;
import ch.admin.astra.elfa.trotti.domain.verifier.model.VerificationStartData;
import ch.admin.astra.elfa.trotti.mapper.UseCaseMapper;
import ch.admin.astra.elfa.trotti.mapper.VerificationMapper;
import ch.admin.astra.elfa.trotti.util.LoggingUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/verifier")
@Tag(name = "Verifier", description = "Handles verification of electronic learner drivers licenses")
class VerifierController {

    private final VerifierService verifierService;
    private final UseCaseMapper useCaseMapper;
    private final VerificationMapper verificationMapper;

    @Operation(summary = "Return a list of possible use-cases", description = "Retrieves a list of possible use-cases.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    @GetMapping(value = "/use-cases")
    public ResponseEntity<List<UseCaseResponse>> getUseCases() {
        LoggingUtil.operation("Get use-cases");

        List<UseCaseResponse> response = useCaseMapper.map(verifierService.getUseCases());
        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(24, TimeUnit.HOURS))
            .body(response);
    }

    @Operation(summary = "Start a new verification process", description = "This endpoint starts a new verification process for a given use-case.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    @PostMapping(value = "/verify")
    public ResponseEntity<VerificationBeginResponse> startVerificationProcess(@Parameter(in = ParameterIn.DEFAULT, required = true, schema = @Schema(implementation = StartVerificationRequest.class)) @Valid @RequestBody() StartVerificationRequest request) {
        LoggingUtil.operation("Start verification process");
        LoggingUtil.useCaseId(request.getUseCaseId());

        VerificationStartData verifyResponse = verifierService.startVerification(UUID.fromString(request.getUseCaseId()));
        VerificationBeginResponse response = verificationMapper.map(verifyResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get the current status of a previously initiated verification process.", description = "This endpoint can be used to poll the status and result of a previously initiated verification process.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    @GetMapping(value = "/verify/{verificationId}")
    public ResponseEntity<VerificationStatusResponse> getVerificationProcess(@Parameter(in = ParameterIn.DEFAULT, required = true, schema = @Schema(implementation = String.class)) @Valid @PathVariable UUID verificationId) {
        LoggingUtil.operation("Get verification process status");
        LoggingUtil.verificationId(verificationId);

        VerificationResponse verificationResultResponse = verifierService.getVerificationStatus(verificationId);
        return ResponseEntity.ok(verificationMapper.map(verificationResultResponse));
    }
}
