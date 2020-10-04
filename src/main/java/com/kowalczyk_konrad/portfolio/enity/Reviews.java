package com.kowalczyk_konrad.portfolio.enity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Reviews {


    private String token;
    private  boolean available;
    @NotBlank(message="Name is required")
    private  String name;
    @NotBlank(message="Comment is required")
    private  String comment;
    private  String company;
    @Email
    private  String email;
    private  String data;


    public Reviews(@NotBlank String name, @NotBlank String comment, @NotBlank String company,  @Email String email) {
        this.name = name;
        this.comment = comment;
        this.company = company;
        this.email = email;
        this.data = LocalDate.now().toString();
        this.available=false;
        this.token = UUID.randomUUID().toString();

    }

    public Reviews(@NotBlank String name, @NotBlank String comment, @NotBlank String company,  @Email String email, String token) {
        this.name = name;
        this.comment = comment;
        this.company = company;
        this.email = email;
        this.data = LocalDate.now().toString();
        this.available=false;
        this.token = token;

    }

    public boolean isAvailable()
    {
        return this.available;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name.replaceAll("\\s","___"));
        stringBuilder.append("/");

        stringBuilder.append(comment.replaceAll("\\s","___"));
        stringBuilder.append("/");


        stringBuilder.append(company.replaceAll("\\s","___"));
        stringBuilder.append("/");


        stringBuilder.append(email);
        stringBuilder.append("/");

        stringBuilder.append(token);

      return stringBuilder.toString();
    }


}
