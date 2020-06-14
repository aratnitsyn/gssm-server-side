package com.github.aratnitsyn.gssm.db.repository;

import com.github.aratnitsyn.gssm.db.schema.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Interface of base entity repository. Use for implementation of repositories.
 *
 * @param <T>  type {@link BaseEntity}
 * @param <ID> type {@link BaseEntity#getId()}
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {
}
