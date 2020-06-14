package com.github.aratnitsyn.gssm.db.schema.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.aratnitsyn.gssm.db.schema.SingleAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity - User.
 */
@Entity
@Table(name = "principal", uniqueConstraints = {
    @UniqueConstraint(name = "uq_user_username", columnNames = { "username" })
})
public class UserEntity extends SingleAuditingEntity {
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "username", length = 64)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password", length = 60)
    private String password;

    @NotNull
    @Column(name = "enabled")
    private Boolean enabled = true;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
