/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seats.dao.impl;

import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.exception.CouldNotDeleteException;
import main.java.com.smartans.exception.CouldNotSaveException;
import main.java.com.smartans.exception.CouldNotSearchException;
import main.java.com.smartans.exception.CouldNotUpdateException;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * <pre>
 * <b>Description : </b>
 * BaseDao.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 04:54:24 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class BaseDao {
    /**
     * hibernateTemplate.
     */
    private HibernateTemplate hibernateTemplate;
    
    
    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'hibernateTemplate' attribute value.
     * 
     * @param hibernateTemplateParam , may be null.
     * </pre>
     */
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * create.
     * 
     * @param object
     * @return
     * @throws CouldNotSaveException
     * </pre>
     */
    public Object create(Object object) throws CouldNotSaveException {
        try {
            hibernateTemplate.save(object);
        }
        catch (Exception ex) {
            throw new CouldNotSaveException(ex);
        }
        return object;
    }
    
    
    /**
     * <pre>
     * <b>Description : </b>
     * retrieve.
     * 
     * @param class1
     * @param seatNumber
     * </pre>
     */
    public Object retrieve(Class<?> className, String id)  throws CouldNotSearchException {
        Object object = null;
        try {
            object = hibernateTemplate.get(className, id);
        }
        catch (DataAccessException accessException) {
            throw new CouldNotSearchException(WASConstants.COULD_NOT_RETRIEVE_OBJECT);
        }
        catch (Exception ex) {
            throw new CouldNotSearchException(WASConstants.UNEXPECTED_ERROR_OCCURED);
        }
        return object;
    }
    /**
     * <pre>
     * <b>Description : </b>
     * update.
     * 
     * @param object
     * @return
     * @throws CouldNotUpdateException
     * </pre>
     */
    public Object update(Object object) throws CouldNotUpdateException {
        try {
            hibernateTemplate.saveOrUpdate(object);
        }
        catch (Exception ex) {
            throw new CouldNotUpdateException(ex);
        }
        return object;
    }
    
    /**
     * <pre>
     * <b>Description : </b>
     * delete.
     * 
     * @param objectname
     * @param id
     * @return
     * @throws CouldNotDeleteException
     * </pre>
     */
    public void delete(Object object) throws CouldNotDeleteException {
        try {
            hibernateTemplate.delete(object);
        }
        catch (DataAccessException accessException) {
            throw new CouldNotDeleteException(WASConstants.COULD_NOT_DELETE_OBJECT);
        }
        catch (Exception ex) {
            throw new CouldNotDeleteException(WASConstants.UNEXPECTED_ERROR_OCCURED);
        }
    }
    
    /**
     * <pre>
     * <b>Description : </b>
     * getObjects.
     * 
     * @return
     * </pre>
     * @throws CouldNotSearchException 
     */
    @SuppressWarnings("unchecked")
    public List<Object> getObjects(String namedQuery) throws CouldNotSearchException {
        List<Object> objects = null;
        try {
            objects = hibernateTemplate.find(namedQuery);
        }
        catch (DataAccessException accessException) {
            throw new CouldNotSearchException(WASConstants.COULD_NOT_SEARCH_OBJECT);
        }
        catch (Exception ex) {
            throw new CouldNotSearchException(WASConstants.UNEXPECTED_ERROR_OCCURED);
        }
        return objects;
    }

}
