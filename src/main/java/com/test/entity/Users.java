package com.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.util.LinkedHashSet;
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

   // @NotNull
//    @Column(name = "TimeAccountCreated", nullable = false)
//    private Instant timeAccountCreated;

    @NotNull
    @Column(name = "IsDeleted", nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "userID")
    private Set<UserPassword> userPasswords = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userID")
    private Set<UserVsRole> userVsRoles = new LinkedHashSet<>();

    public Users(String fullName, String userName, String email) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
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

//    public Instant getTimeAccountCreated() {
//        return timeAccountCreated;
//    }
//
//    public void setTimeAccountCreated(Instant timeAccountCreated) {
//        this.timeAccountCreated = timeAccountCreated;
//    }

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