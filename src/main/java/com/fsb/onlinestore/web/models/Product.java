package com.fsb.onlinestore.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String code;
    private String name;
    private Double price;
    private int quantity;
    private String image;
}
