package com.idan.coupons.beans;

import lombok.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<Coupons> coupons;

    //-----------------------------Constructors---------------------------------------

    //Constructor without customer id and purchased coupons.
    public Customer(final String firstName, final String lastName, final String email, final String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
