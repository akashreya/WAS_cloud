package main.java.com.smartans.designation.dao;

import java.util.List;

import main.java.com.smartans.entity.Designation;
import main.java.com.smartans.exception.DesignationException;

/**
 * <pre>
 * <b>Description : </b>
 * ManageDesignationDao.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-13 09:51:43 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public interface ManageDesignationDao {
	
	/**
	 * <pre>
	 * <b>Description : </b>
	 * searchDesignation.
	 * 
	 * @param namedQuery , not null
	 * @return designationList , null if not found
	 * @throws DesignationException
	 * </pre>
	 */
	List<Designation> searchDesignation(String namedQuery) throws DesignationException;

}
