package com.idan.coupons.beans;

import lombok.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class
Company {
    private Long id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupons> coupons;


    //Constructor without company id.
    public Company(final String name, final String email, final String password, final ArrayList<Coupons> coupons) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }
}
