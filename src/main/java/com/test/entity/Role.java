package com.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.NaturalId;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

//    @Size(max = 10)
//    @NotNull
//    @Nationalized
//    @Column(name = "RuleName", nullable = false, length = 10)
//    private String ruleName;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "RuleName", nullable = false,length = 60)
    private RoleName ruleName;

    @OneToMany(mappedBy = "roleID")
    private Set<UserVsRole> userVsRoles = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleName getRuleName() {
        return ruleName;
    }

    public void setRuleName(RoleName ruleName) {
        this.ruleName = ruleName;
    }

    public Set<UserVsRole> getUserVsRoles() {
        return userVsRoles;
    }

    public void setUserVsRoles(Set<UserVsRole> userVsRoles) {
        this.userVsRoles = userVsRoles;
    }

}