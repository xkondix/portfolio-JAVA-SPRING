package com.kowalczyk_konrad.portfolio.controller;

import com.kowalczyk_konrad.portfolio.representationModel.ReviewsModel;
import com.kowalczyk_konrad.portfolio.response.ReviewRestModel;
import com.kowalczyk_konrad.portfolio.service.FirebaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class ReviewsController {


    private final FirebaseService firebaseService;


    public ReviewsController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @CrossOrigin(origins = {"https://konrad-kowalczyk.herokuapp.com", "https://konrad-kowalczyk.herokuapp.com/#yourOpinion"})
    @GetMapping("/data")
    public ResponseEntity<List<ReviewsModel>> getReviews() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(firebaseService.getAll());
    }

    @CrossOrigin(origins = {"https://konrad-kowalczyk.herokuapp.com", "https://konrad-kowalczyk.herokuapp.com/#yourOpinion"})
    @PostMapping("/data")
    public ResponseEntity<String> saveReview(@RequestBody ReviewRestModel review) throws ExecutionException, InterruptedException {

        return  ResponseEntity.ok(firebaseService.saveReviews(review));


    }


    @GetMapping("/confirmComment/{token}/conf/{email}")
    public ResponseEntity<String> confirm(@PathVariable(value = "token") String token
            , @PathVariable(value = "email") String email) throws ExecutionException, InterruptedException {

        if(firebaseService.availableComment(email,token))
        {
            return new ResponseEntity<String>("Thanks for your confirmation.",HttpStatus.CREATED) ;
        }
        else
        {
            return new ResponseEntity<String>("Something went wrong. Please try again.",HttpStatus.FORBIDDEN) ;
        }

    }

    @GetMapping("/confirmChange/{name}/{comment}/{company}/{email}/{token}")
    public ResponseEntity<String> change(
            @PathVariable(value = "token") String token,
            @PathVariable(value = "email") String email,
            @PathVariable(value = "company") String company,
            @PathVariable(value = "comment") String comment,
            @PathVariable(value = "name") String name) throws ExecutionException, InterruptedException {

        if(firebaseService.changeReview
                (new ReviewRestModel(name,comment,email,company,token)))
        {
            return new ResponseEntity<String>("Change accepted.",HttpStatus.ACCEPTED) ;
        }
        else
        {
            return new ResponseEntity<String>("Something went wrong. Please try again.",HttpStatus.FORBIDDEN) ;
        }
    }


}
