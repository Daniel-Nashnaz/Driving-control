package com.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private Users userID;
    @Size(max = 100)
    @NotNull
    @Nationalized
    @Column(name = "Address", nullable = false, length = 100)
    private String address;

    @Size(max = 10)
    @NotNull
    @Nationalized
    @Column(name = "ApartmentNumber", nullable = false, length = 10)
    private String apartmentNumber;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = "City", nullable = false, length = 50)
    private String city;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = "Country", nullable = false, length = 50)
    private String country;

    public Address(String address, String apartmentNumber, String city, String country) {
        this.address = address;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

}