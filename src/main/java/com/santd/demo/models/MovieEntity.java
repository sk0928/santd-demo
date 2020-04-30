package com.santd.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MovieEntity {

    @Id
    @NotBlank(message = "Id should not be null")
    private String id;
    @NotBlank(message = "Name should not be null")
    private String name;
    @Positive(message = "Should be a valid year")
    private int releaseYear;

}
