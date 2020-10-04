package com.kowalczyk_konrad.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kowalczyk_konrad.portfolio.controller.ReviewsController;
import com.kowalczyk_konrad.portfolio.enity.Reviews;
import com.kowalczyk_konrad.portfolio.response.ReviewRestModel;
import com.kowalczyk_konrad.portfolio.service.FirebaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ReviewsController.class)
public class ReviewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FirebaseService firebaseService;

    @Test
    void whenValidReviewsData_thenReturns200() throws Exception {

        ReviewRestModel reviewRestModel = new ReviewRestModel(
                "Konrad",
                "Comment",
                "konrad.kowalczyk.98@gmail.com",
                "");

        mockMvc.perform(post("/api/data")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reviewRestModel)))
                .andExpect(status().isOk());


    }

    @Test
    void whenValidReviewsData_thenAddToDatabase() throws Exception {

        ReviewRestModel reviewRestModel = new ReviewRestModel(
                "Konrad",
                "Comment",
                "konrad.kowalczyk.98@gmail.com",
                "");

        mockMvc.perform(post("/api/data")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reviewRestModel)));

        ArgumentCaptor<ReviewRestModel> reviewsCaptor = ArgumentCaptor.forClass(ReviewRestModel.class);
        verify(firebaseService, times(1)).saveReviews(reviewsCaptor.capture());
        assertThat(reviewsCaptor.getValue().getName()).isEqualTo("Konrad");
        assertThat(reviewsCaptor.getValue().getEmail()).isEqualTo("konrad.kowalczyk.98@gmail.com");
        assertThat(reviewsCaptor.getValue().getComment()).isEqualTo("Comment");
        assertThat(reviewsCaptor.getValue().getCompany()).isEqualTo("");


    }


}

