package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;


@Entity
@Table(name = "Customers")
@Slf4j
@NoArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NonNull
    @Size(min = 4, max = 30, message = "Please enter a name with length between 4-30 characters")
    String fullName;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dob;

    @NonNull
    String gender;

    @NonNull
    @Email(message = "Please provide a valid email address", regexp = ".+@.+\\..+")
    String email;

    @NonNull
    @Size(min = 6, max = 10, message = "Please enter a length between 6-10 numbers")
    String phoneNumber;

    @NonNull
    int ssn;

    @Setter(AccessLevel.NONE)
    String password;

    public String setPassword(String password) {
       return this.password = new BCryptPasswordEncoder().encode(password);
    }

    public Customer(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public Customer(@NonNull String fullName, @NonNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob, @NonNull String gender, @NonNull String email, @NonNull String phoneNumber, @NonNull int ssn, String password){
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;
        this.password = setPassword(password);
    }


}
