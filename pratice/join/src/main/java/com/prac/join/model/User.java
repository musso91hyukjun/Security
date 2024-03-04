package com.prac.join.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "customer")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    @Column(name = "customer_id")
    private int id;


    private String name;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    private String role;

    @Column(name = "create_dt")
    private String createDt;
}
