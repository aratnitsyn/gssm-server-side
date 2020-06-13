package com.github.aratnitsyn.gssm.web.rest;

import com.github.aratnitsyn.gssm.service.BaseRestService;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Base controller. Use for implementations controllers.
 *
 * @param <S> type of service
 */
@RequestMapping("/api")
public abstract class BaseRestController<S extends BaseRestService> {
    /**
     * Service that performs business logic.
     *
     * @return service
     */
    public abstract S getService();
}
