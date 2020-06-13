package com.github.aratnitsyn.gssm.service;

import com.github.aratnitsyn.gssm.db.repository.BaseRepository;
import com.github.aratnitsyn.gssm.db.schema.BaseEntity;

import java.io.Serializable;

/**
 * Base rest service for implements services with anything entities.
 *
 * @param <T>  type {@link BaseEntity}
 * @param <ID> type {@link BaseEntity#getId()}
 */
public interface BaseCrudService<T extends BaseEntity, ID extends Serializable> extends BaseRestService {
    /**
     * Get entity by id.
     *
     * @param id id entity
     * @return entity
     */
    T get(ID id);

    /**
     * Delete entity by id.
     *
     * @param id id entity
     * @return entity
     */
    T delete(ID id);

    /**
     * Repository performs with database.
     *
     * @return repository
     */
    BaseRepository<T, ID> getRepository();
}
