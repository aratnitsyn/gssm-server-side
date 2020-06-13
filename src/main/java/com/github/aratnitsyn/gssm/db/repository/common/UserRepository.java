package com.github.aratnitsyn.gssm.db.repository.common;

import com.github.aratnitsyn.gssm.db.repository.BaseRepository;
import com.github.aratnitsyn.gssm.db.schema.common.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository - {@link UserEntity}.
 */
@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {
    /**
     * Find user by username.
     *
     * @param username username
     * @return user
     */
    Optional<UserEntity> findByUsername(String username);
}
