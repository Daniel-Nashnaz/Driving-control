package com.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class UserVsAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    private Users userID;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AdministratorID", nullable = false)
    private Users administratorID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public Users getAdministratorID() {
        return administratorID;
    }

    public void setAdministratorID(Users administratorID) {
        this.administratorID = administratorID;
    }

}