package com.kowalczyk_konrad.portfolio.representationModel;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ReviewsModel {

    @Getter
    private final String name;
    @Getter
    private final String comment;
    @Getter
    private final String company;
    @Getter
    private final String data;


    public ReviewsModel(String name, String comment, String company, String data) {
        this.name = name;
        this.comment = comment;
        this.company = company;
        this.data = data;
    }
}
