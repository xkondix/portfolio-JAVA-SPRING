package com.kowalczyk_konrad.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kowalczyk_konrad.portfolio.controller.EmailController;
import com.kowalczyk_konrad.portfolio.controller.ReviewsController;
import com.kowalczyk_konrad.portfolio.email.EmailService;
import com.kowalczyk_konrad.portfolio.response.EmailRestModel;
import com.kowalczyk_konrad.portfolio.response.ReviewRestModel;
import com.kowalczyk_konrad.portfolio.service.FirebaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmailController.class)
public class EmailControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmailService emailService;

    @Test
    void whenValidEmailData_thenReturns200() throws Exception {

        EmailRestModel emailRestModel = new EmailRestModel(
                "Work",
                "Hello ...",
                "konrad.kowalczyk.98@gmail.com");

        mockMvc.perform(post("/api/sendEmail")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(emailRestModel)))
                .andExpect(status().isOk());


    }
}