package com.kowalczyk_konrad.portfolio.controller;


import com.kowalczyk_konrad.portfolio.email.EmailService;
import com.kowalczyk_konrad.portfolio.response.EmailRestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://konrad-kowalczyk.herokuapp.com", "https://konrad-kowalczyk.herokuapp.com/#contact"})
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> saveReview(@RequestBody EmailRestModel emailRestModel) throws ExecutionException, InterruptedException {
        try {
            emailService.sendEmail(
                    "konrad.kowalczyk.98@gmail.com", emailRestModel.getTop(), emailRestModel.getMess());
            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
