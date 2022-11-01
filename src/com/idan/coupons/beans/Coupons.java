package com.idan.coupons.beans;

import com.idan.coupons.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Coupons {
    private Long id;
    private Long companyId;
    private CategoryType category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    //Constructor without coupon id.
    public Coupons(final Long companyId, final CategoryType category, final String title, final String description,
                   final Date startDate, final Date endDate, final int amount, final double price, final String image) {
        this.companyId = companyId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

}
