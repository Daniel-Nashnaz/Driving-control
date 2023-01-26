package com.test.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Nationalized
    @Column(name = "VehicleNumber", nullable = false, length = 10)
    private String vehicleNumber;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "VehicleName", nullable = false, length = 20)
    private String vehicleName;

    @OneToMany(mappedBy = "vehicleID")
    @JsonManagedReference
    private Set<Driver> drivers = new LinkedHashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CarAdministrator", nullable = false)
    private Users carAdministrator;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "TypeOfVehicle", nullable = false, length = 20)
    private String typeOfVehicle;

    @NotNull
    @Column(name = "\"Year\"", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "IsDeleted", nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "vehicleID")
    private Set<Travel> travels = new LinkedHashSet<>();

    public Set<Travel> getTravels() {
        return travels;
    }

    public void setTravels(Set<Travel> travels) {
        this.travels = travels;
    }


    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTypeOfVehicle() {
        return typeOfVehicle;
    }

    public void setTypeOfVehicle(String typeOfVehicle) {
        this.typeOfVehicle = typeOfVehicle;
    }

    public Users getCarAdministrator() {
        return carAdministrator;
    }

    public void setCarAdministrator(Users carAdministrator) {
        this.carAdministrator = carAdministrator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

}