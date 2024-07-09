package ch.admin.astra.elfa.trotti.domain.techadapter.client;

import ch.admin.astra.elfa.trotti.domain.techadapter.client.impl.MockTechAdapterClientImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(locations="classpath:application.yml")
@ActiveProfiles(value = "local")
@SpringBootTest(properties="spring.main.lazy-initialization=true")
class TechAdapterClientConfigurationMockIT {

    @Autowired
    private TechAdapterClient techAdapterClient;

    @Test
    void assertTechAdapterClient() {
        assertTrue(techAdapterClient instanceof MockTechAdapterClientImpl);
    }
}