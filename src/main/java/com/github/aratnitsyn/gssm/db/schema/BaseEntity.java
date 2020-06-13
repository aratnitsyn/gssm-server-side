package com.github.aratnitsyn.gssm.db.schema;

import java.io.Serializable;

/**
 * Interface of base entity. Use for implementation entities.
 */
public interface BaseEntity extends Serializable {
    /**
     * Id entity.
     *
     * @return id entity.
     */
    Long getId();

    /**
     * Set id entity.
     *
     * @param id entity
     */
    void setId(Long id);
}

