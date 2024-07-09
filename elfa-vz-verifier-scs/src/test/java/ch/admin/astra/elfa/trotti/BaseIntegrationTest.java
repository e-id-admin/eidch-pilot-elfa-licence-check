package ch.admin.astra.elfa.trotti;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles({"local"})
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    @Configuration
    public static class TestConfig {
        @Bean
        BuildProperties buildProperties() {
            return new BuildProperties(new Properties());
        }
    }

    protected static final String BASE_URL = "/api/v1/verifier/";

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mvc;

    public <T, S> S fetchGet(String action, TypeReference<S> type) throws Exception {
        MvcResult mvcResult = mvc.perform(get(BASE_URL + action))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                type);
    }

    public <T, S> S fetchPost(String action, T request, TypeReference<S> type) throws Exception {
        MvcResult mvcResult = mvc.perform(post(BASE_URL + action)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                type);
    }

}
