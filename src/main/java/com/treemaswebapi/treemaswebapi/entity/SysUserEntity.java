package com.treemaswebapi.treemaswebapi.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@EntityListeners(AuditingEntityListener.class)
public class SysUserEntity implements UserDetails {
    @Id
    @Column(name = "userid")
    @NotBlank(message = "User ID tidak boleh kosong")
    private String userid;

    @Column(name = "branchid", length = 20)
    private String branchid;

    @Column(name = "full_name", length = 150)
    private String fullName;

    @Column(name = "active", length = 1)
    private String active;

    @Column(name = "sql_password", length = 70)
    private String sqlPassword;

    @Column(name = "usrcrt", length = 150)
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;

    @Column(name = "usrupd", length = 150)
    private String usrupd;

    @Column(name = "dtmupd")
    private Timestamp dtmupd;

    @NotBlank(message = "Is Login tidak boleh kosong")
    @Column(name = "is_login", length = 1)
    private String isLogin;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "is_pass_chg", length = 1)
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

    @Column(name = "is_locked", length = 1)
    private String isLocked;

    @Column(name = "times_locked")
    private Integer timesLocked;

    @Column(name = "locked_time")
    private Timestamp lockedTime;

    @Column(name = "is_approved", length = 101)
    private String isApproved;

    @Column(name = "token_jwt", columnDefinition = "text")
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
}
