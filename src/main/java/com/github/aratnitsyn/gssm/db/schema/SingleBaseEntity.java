package com.github.aratnitsyn.gssm.db.schema;

import javax.persistence.*;

/**
 * Abstract class single implementation {@link BaseEntity}.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class SingleBaseEntity implements BaseEntity {
    /**
     * Id entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
