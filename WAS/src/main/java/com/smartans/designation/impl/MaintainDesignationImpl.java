package main.java.com.smartans.designation.impl;

import java.util.ArrayList;
import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.designation.dao.ManageDesignationDao;
import main.java.com.smartans.designation.dtos.DesignationType;
import main.java.com.smartans.designation.dtos.ShowDesignationType;
import main.java.com.smartans.designation.interfaces.MaintainDesignations;
import main.java.com.smartans.entity.Designation;
import main.java.com.smartans.exception.DesignationException;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * <b>Description : </b>
 * MaintainDesignationImpl.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-13 10:45:12 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class MaintainDesignationImpl implements MaintainDesignations {
    /**
     * manageDesignationDao.
     */
    @Autowired
    private ManageDesignationDao manageDesignationDao;

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'manageDesignationDao' attribute value.
     * 
     * @param manageDesignationDaoParam , may be null.
     * </pre>
     */
    public void setManageDesignationDao(final ManageDesignationDao manageDesignationDaoParam) {
        this.manageDesignationDao = manageDesignationDaoParam;
    }

    /**
     * <pre>
	 * <b>Description : </b>
	 * searchDesignation.
	 * 
	 * @return
	 * </pre>
     */
    @Override
    public ShowDesignationType searchDesignation() {
        ShowDesignationType showDesignationType = new ShowDesignationType();
        try {
            List<Designation> searchDesignation = manageDesignationDao
                .searchDesignation(WASConstants.QUERY_DESIGNATION);
            List<DesignationType> designations = null;
            if (searchDesignation != null && !searchDesignation.isEmpty()) {
                showDesignationType.setSuccess(true);
                designations = new ArrayList<DesignationType>();
                for (Designation designation : searchDesignation) {
                    DesignationType designationType = new DesignationType();
                    designationType.setDesignationId(designation.getDesignationId());
                    designationType.setIsManager(designation.getIsManager());
                    designationType.setDesignationName(designation.getDesignation());
                    designations.add(designationType);
                }
            }
            showDesignationType.setDesignations(designations);

        }
        catch (DesignationException ex) {
            showDesignationType.setSuccess(false);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            showDesignationType.setErrorMessages(errors);
        }
        return showDesignationType;
    }

}
