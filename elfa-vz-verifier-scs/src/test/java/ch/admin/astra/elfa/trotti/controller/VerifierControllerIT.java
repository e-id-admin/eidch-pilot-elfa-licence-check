package ch.admin.astra.elfa.trotti.controller;

import ch.admin.astra.elfa.trotti.BaseIntegrationTest;
import ch.admin.astra.elfa.trotti.api.*;
import ch.admin.astra.elfa.trotti.domain.qrcode.QrCodeService;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.TechAdapterClient;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.HolderResponse;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ch.admin.astra.elfa.trotti.api.VerificationStatus.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;

class VerifierControllerIT extends BaseIntegrationTest {

    private static final String EXISTING_USE_CASE = "c2041c31-db6b-4cf1-871d-6a24d400159b";
    public static final UUID ID = UUID.fromString("11111111-2222-3333-4444-555555555555");

    @MockBean
    private TechAdapterClient techAdapterClient;

    @Autowired
    private QrCodeService qrCodeService;

    @Test
    void testGetUseCases_thenSuccess() throws Exception {

        List<UseCaseResponse> response = fetchGet("use-cases", new TypeReference<>(){});

        assertNotNull(response);
        Assertions.assertEquals(1, response.size());

        UseCaseResponse useCaseResponse = response.get(0);
        assertEquals("title", useCaseResponse.getTitle());
        assertEquals("description", useCaseResponse.getDescription());

        List<AttributeGroupResponse> attributeGroups = useCaseResponse.getAttributeGroups();
        assertEquals(1, attributeGroups.size());

        AttributeGroupResponse attributeGroupResponse = attributeGroups.get(0);
        assertEquals("name", attributeGroupResponse.getName());
        assertEquals(1, attributeGroupResponse.getOrder());

        List<AttributeResponse> attributes = attributeGroupResponse.getAttributes();
        AttributeResponse attributeResponse = attributes.get(0);
        assertEquals("name", attributeResponse.getName());
        assertEquals("type", attributeResponse.getType());
        assertEquals(1, attributeResponse.getOrder());
    }

    @Test
    void testStartVerificationProcess_usingExistingUseCase_thenSuccess() throws Exception {
        doReturn(getTestVerificationResponse()).when(techAdapterClient)
                .startVerification(any());

        StartVerificationRequest request = StartVerificationRequest.builder().useCaseId(EXISTING_USE_CASE).build();
        VerificationBeginResponse response = fetchPost("verify", request, new TypeReference<>(){});

        assertNotNull(response);
        assertEquals( ID, response.getId());
        assertEquals(Base64.getEncoder().encodeToString(qrCodeService.create("url", 500).getImageData()), Base64.getEncoder().encodeToString(response.getQrCode()));
        assertEquals("png", response.getQrCodeFormat());
    }

    @Test
    void testGetVerificationProcess_usingMockedProcess_thenSuccess() throws Exception {
        doReturn(getTestVerificationResponse()).when(techAdapterClient)
                .getVerificationStatus(argThat(r -> r.equals(ID)));


        VerificationStatusResponse response = fetchGet("verify/" + ID, new TypeReference<>(){});

        assertNotNull(response);

        assertEquals(ID.toString(), response.getId());
        assertEquals(SUCCESS, response.getStatus());
        assertEquals("url", response.getVerificationUrl());

        assertThat(response.getHolderResponse().getAttributes()).containsExactlyEntriesOf(
                Map.of("attributeKey", "attributeValue"));
    }

    private VerificationResponse getTestVerificationResponse() {
        return VerificationResponse.builder()
                .id(ID)
                .status(VerificationStatus.SUCCESS)
                .verificationUrl("url")
                .holderResponse(HolderResponse.builder()
                    .attribute("attributeKey", "attributeValue")
                    .build())
                .build();
    }
}