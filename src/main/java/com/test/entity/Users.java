package com.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Nationalized
    @Column(name = "FullName", nullable = false, length = 100)
    private String fullName;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = "UserName", nullable = false, length = 50)
    private String userName;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;


    @NotNull
    @Column(name = "IsDeleted", nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "userID", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserPassword> userPasswords = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userID", fetch = FetchType.LAZY)
    private Set<UserVsRole> userVsRoles = new LinkedHashSet<>();


    @OneToMany(mappedBy = "userID", fetch = FetchType.LAZY)
    private Set<RefreshToken> refreshTokens = new LinkedHashSet<>();


    @OneToMany(mappedBy = "userID", fetch = FetchType.LAZY)
    private Set<UserVsAdmin> userVsAdmins = new LinkedHashSet<>();


    @OneToMany(mappedBy = "administratorID", fetch = FetchType.LAZY)
    private Set<UserVsAdmin> adminsVsUser = new LinkedHashSet<>();


    @OneToMany(mappedBy = "userID", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "carAdministrator")
    private Set<Vehicle> vehicles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userID")
    private Set<Driver> drivers = new LinkedHashSet<>();


    public Users(String fullName, String userName, String email, String phone) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<UserVsAdmin> getAdminsVsUser() {
        return adminsVsUser;
    }

    public void setAdminsVsUser(Set<UserVsAdmin> adminsVsUser) {
        this.adminsVsUser = adminsVsUser;
    }

    public Set<UserVsAdmin> getUserVsAdmins() {
        return userVsAdmins;
    }

    public void setUserVsAdmins(Set<UserVsAdmin> userVsAdmins) {
        this.userVsAdmins = userVsAdmins;
    }

    public Set<RefreshToken> getRefreshTokens() {
        return refreshTokens;
    }

    public void setRefreshTokens(Set<RefreshToken> refreshTokens) {
        this.refreshTokens = refreshTokens;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<UserPassword> getUserPasswords() {
        return userPasswords;
    }

    public void setUserPasswords(Set<UserPassword> userPasswords) {
        this.userPasswords = userPasswords;
    }

    public Set<UserVsRole> getUserVsRoles() {
        return userVsRoles;
    }

    public void setUserVsRoles(Set<UserVsRole> userVsRoles) {
        this.userVsRoles = userVsRoles;
    }

}