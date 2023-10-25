package com.treemaswebapi.treemaswebapi.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.treemaswebapi.treemaswebapi.entity.UserRole.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_user", schema = "public")
@EntityListeners(UserEntityListener.class)
public class SysUserEntity implements UserDetails {
    @Id
    @Column(name = "userid")
    @NotBlank(message = "User ID tidak boleh kosong")
    private String userid;

    @Column(name = "branchid")
    private String branchid;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "active")
    private String active;

    @Column(name = "sql_password", nullable = false)
    private String sqlPassword;

    @Column(name = "usrcrt")
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;

    @Column(name = "usrupd")
    private String usrupd;

    @Column(name = "dtmupd")
    private Timestamp dtmupd;

    @NotBlank(message = "Is Login tidak boleh kosong")
    @Column(name = "is_login")
    private String isLogin;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "email")
    private String email;

    @Column(name = "is_pass_chg")
    private String isPassChg;

    @Column(name = "last_passchg")
    private Timestamp lastPasschg;

    @Column(name = "user_ed")
    private Timestamp userEd;

    @Column(name = "password_ed")
    private Timestamp passwordEd;

    @Column(name = "wrong_pass_count")
    private Short wrongPassCount;

    @Column(name = "wrong_pass_time")
    private Timestamp wrongPassTime;

    @Column(name = "is_locked")
    private String isLocked;

    @Column(name = "times_locked")
    private Integer timesLocked;

    @Column(name = "locked_time")
    private Timestamp lockedTime;

    @Column(name = "is_approved")
    private String isApproved;

    @Column(name = "token_jwt")
    private String tokenJwt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return sqlPassword;
    }

    @Override
    public String getUsername() {
        return userid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Join Column
    @OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "nik")
    private KaryawanEntity karyawan;
}
