package com.github.aratnitsyn.gssm.web.rest;

import com.github.aratnitsyn.gssm.service.BaseRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Base controller. Use for implementations controllers.
 *
 * @param <S> type of service
 */
@RequestMapping("/api")
public abstract class BaseRestController<S extends BaseRestService> {
    protected S service;

    @SuppressWarnings("unchecked")
    @Autowired
    private void setService(BaseRestService service) {
        this.service = (S) service;
    }
}
