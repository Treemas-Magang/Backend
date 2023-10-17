package com.treemaswebapi.treemaswebapi.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserEntityListener {

    @PrePersist
    public void onPrePersist(Object entity) {
        if (entity instanceof UserEntity) {
            UserEntity userEntity = (UserEntity) entity;
            // Logic to be executed before the entity is persisted (created)
            if (userEntity.getPassword() == null) {
                userEntity.setPassword("123456"); // Set default password if not set
            }
        }
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        if (entity instanceof UserEntity) {
            UserEntity userEntity = (UserEntity) entity;
            // Logic to be executed before the entity is updated
            if (userEntity.getPassword() == null) {
                userEntity.setPassword("123456"); // Set default password if not set
            }
        }
    }
}
