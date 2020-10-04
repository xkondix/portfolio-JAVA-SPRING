package com.kowalczyk_konrad.portfolio.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.kowalczyk_konrad.portfolio.email.EmailService;
import com.kowalczyk_konrad.portfolio.enity.Reviews;
import com.kowalczyk_konrad.portfolio.representationModel.ReviewsModel;
import com.kowalczyk_konrad.portfolio.response.ReviewRestModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FirebaseService {

    private final EmailService emailService;


    public FirebaseService(EmailService emailService) {
        this.emailService = emailService;
    }

    public boolean changeReview(ReviewRestModel reviewRestModel)
    {
        Firestore fireStore = FirestoreClient.getFirestore();
        Reviews rev;
        try {
            rev =  fireStore.collection("reviews")
                    .document(reviewRestModel.getEmail()).get().get().toObject(Reviews.class);

            if(rev.getToken().equals(reviewRestModel.getToken())) {
                rev.setComment(reviewRestModel.getComment()
                        .replaceAll("___"," "));
                rev.setCompany(reviewRestModel.getCompany()
                        .replaceAll("___"," "));
                rev.setName(reviewRestModel.getName()
                        .replaceAll("___"," "));
                rev.setData(LocalDate.now().toString());

                ApiFuture<WriteResult> collections = fireStore.collection("reviews")
                        .document(rev.getEmail()).set(rev);
                return true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return false;
    }

    public boolean availableComment(String email, String token)
    {
        Firestore fireStore = FirestoreClient.getFirestore();
        Reviews rev;

        try {

                rev = fireStore.collection("reviews")
                        .document(email).get().get().toObject(Reviews.class);
            if(rev.getToken().equals(token)) {

                rev.setAvailable(true);
                ApiFuture<WriteResult> collections = fireStore.collection("reviews")
                        .document(email).set(rev);
                return true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String saveReviews(ReviewRestModel reviewRestModel) throws ExecutionException, InterruptedException {

        Firestore fireStore = FirestoreClient.getFirestore();
        Reviews reviews = reviewRestModel.toEnity();

        if(ifExist(reviews))
        {
            reviews.setToken(getToken(reviews));
            sendNextMail(reviews);
        }
        else {
            ApiFuture<WriteResult> collections = fireStore.collection("reviews")
                    .document(reviews.getEmail()).set(reviews);
            sendNewMail(reviews.getEmail(),reviews.getToken());
            return collections.get().getUpdateTime().toString();
        }

        return null;
    }

    public List<ReviewsModel> getAll() throws ExecutionException, InterruptedException {
        Firestore fireStore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> query = fireStore.collection("reviews").listDocuments();
        return StreamSupport.stream(query.spliterator(), false)
                .map(element -> {
                    try {
                        return element.get().get().toObject(Reviews.class);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).filter(rev -> rev.isAvailable())
                .map(rev ->  new ReviewsModel(rev.getName(),rev.getComment(),rev.getCompany(),rev.getData()))
                .collect(Collectors.toList());
    }

    private void sendNewMail(String email,String token)
    {
        emailService.sendEmail(email,"Confirm your comment",("To confirm your comment, please click here : "
                +"https://portfolio98kk.herokuapp.com/api/confirmComment/"+token+"/conf/"+email));

    }

    private void sendNextMail(Reviews reviews)
    {
        emailService.sendEmail(reviews.getEmail(),"Confirm your change",("To change your comment, please click here : "
                +"https://portfolio98kk.herokuapp.com/api/confirmChange/"+reviews.toString()));

    }



    private boolean ifExist(Reviews reviews)
    {
        Firestore fireStore = FirestoreClient.getFirestore();
        DocumentReference docRef = fireStore.collection("reviews").document(reviews.getEmail());

        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = null;
        try {
            document = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (document.exists()) {
            return true;
        } else {
            return false;
        }


    }

    private String getToken(Reviews reviews)
    {
        Firestore fireStore = FirestoreClient.getFirestore();
        DocumentReference docRef = fireStore.collection("reviews").document(reviews.getEmail());

        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = null;
        try {
            document = future.get();
            return document.toObject(Reviews.class).getToken();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  null;

    }

}
