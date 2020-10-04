package com.kowalczyk_konrad.portfolio.response;


import com.kowalczyk_konrad.portfolio.enity.Reviews;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ReviewRestModel {

    private final String name;
    private final String comment;
    private final String email;
    private final String company;
    private final String token;


    public ReviewRestModel(String name, String comment, String email, String company) {
        this.name = name;
        this.comment = comment;
        this.email = email;
        this.company = company;
        this.token = null;
    }

    public ReviewRestModel(String name, String comment, String email, String company, String token) {
        this.name = name;
        this.comment = comment;
        this.email = email;
        this.company = company;
        this.token = token;
    }

    public Reviews toEnity()
    {
        return new Reviews(name, comment, company, email);
    }


}
