package com.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @OneToMany(mappedBy = "userID")
    private Set<Travel> travels = new LinkedHashSet<>();



    public Users(String fullName, String userName, String email, String phone) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

}