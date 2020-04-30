package com.santd.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MovieModel {

    @NotNull(message = "Id should not be null")
    private String id;
    @NotNull(message = "Name should not be null")
    private String name;
    @Positive(message = "Should be a valid year")
    @Pattern(regexp = "^(19|20)\\d{2}$")
    private int releaseYear;

}
