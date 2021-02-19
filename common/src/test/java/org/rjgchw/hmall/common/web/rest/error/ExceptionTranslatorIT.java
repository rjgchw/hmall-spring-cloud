package org.rjgchw.hmall.common.web.rest.error;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests controller advice.
 */
@WithMockUser
@AutoConfigureMockMvc
@SpringBootTest(classes = ExceptionTranslatorApplicationMock.class)
public class ExceptionTranslatorIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_409_status_if_concurrency_failure() throws Exception {
        mockMvc.perform(get("/api/exception-translator-test/concurrency-failure"))
            .andExpect(status().isConflict())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.title").value("Concurrency Failure"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void should_return_422_status_if_method_argument_not_valid() throws Exception {
         mockMvc.perform(post("/api/exception-translator-test/method-argument").content("{}").contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isUnprocessableEntity())
             .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
             .andExpect(jsonPath("$.title").value("Validation Failed"))
             .andExpect(jsonPath("$.errors.[0].resource").value("test"))
             .andExpect(jsonPath("$.errors.[0].field").value("test"))
             .andExpect(jsonPath("$.errors.[0].code").value("missing_field"));
    }

    @Test
    public void should_return_400_status_if_missing_servlet_request_part() throws Exception {
        mockMvc.perform(get("/api/exception-translator-test/missing-servlet-request-part"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.title").value("Bad Request"));
    }

    @Test
    public void should_return_400_status_if_missing_servlet_request_parameter() throws Exception {
        mockMvc.perform(get("/api/exception-translator-test/missing-servlet-request-parameter"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.title").value("Bad Request"));
    }

    @Test
    public void should_return_403_status_if_access_denied() throws Exception {
        mockMvc.perform(get("/api/exception-translator-test/access-denied"))
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andDo(print())
            .andExpect(jsonPath("$.title").value("Access Forbidden"));
    }

    @Test
    public void should_return_401_status_if_unauthorized() throws Exception {
        mockMvc.perform(get("/api/exception-translator-test/unauthorized"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.title").value("Unauthorized"))
            .andExpect(jsonPath("$.message").value("test authentication failed!"));
    }

    @Test
    public void should_return_405_status_if_method_not_supported() throws Exception {
        mockMvc.perform(post("/api/exception-translator-test/access-denied"))
            .andExpect(status().isMethodNotAllowed())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.title").value("Method Not Allowed"));
    }

    @Test
    public void should_return_404_status_if_resource_not_found() throws Exception {
        mockMvc.perform(get("/api/exception-translator-test/resource-not-found"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.title").value("resource not found"));
    }

    @Test
    public void should_return_400_status_if_exception_with_response_status() throws Exception {
        mockMvc.perform(get("/api/exception-translator-test/response-status"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.title").value("test response status"));
    }

    @Test
    public void should_return_500_status_if_internal_server_error() throws Exception {
        mockMvc.perform(get("/api/exception-translator-test/internal-server-error"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.title").value("Internal Server Error"));
    }

}
