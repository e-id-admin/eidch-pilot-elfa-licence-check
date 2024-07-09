package ch.admin.astra.elfa.trotti.controller;

import ch.admin.astra.elfa.trotti.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HealthControllerIT extends BaseIntegrationTest {

    private static final String PRIVATE_BASE_URL = "/health/liveness";

    @Test
    void testGetHealthLiveness_thenSuccess() throws Exception {
        mvc.perform(get(PRIVATE_BASE_URL))
                .andExpect(status().isOk());
    }
}