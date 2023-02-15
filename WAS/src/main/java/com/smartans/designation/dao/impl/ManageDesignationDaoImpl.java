package main.java.com.smartans.designation.dao.impl;

import java.util.ArrayList;
import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.designation.dao.ManageDesignationDao;
import main.java.com.smartans.entity.Designation;
import main.java.com.smartans.exception.CouldNotSearchException;
import main.java.com.smartans.exception.DesignationException;
import main.java.com.smartans.seats.dao.impl.BaseDao;

/**
 * <pre>
 * <b>Description : </b>
 * ManageDesignationDaoImpl.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-03 09:54:23 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ManageDesignationDaoImpl extends BaseDao implements ManageDesignationDao {

    /**
     * designations.
     */
    private List<Designation> designations;

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'designations' attribute value.
     * 
     * @return designations , null if not found.
     * </pre>
     */

    public List<Designation> getDesignations() {
        return designations;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'designations' attribute value.
     * 
     * @param designationsParam , may be null.
     * </pre>
     */
    public void setDesignations(final List<Designation> designationsParam) {
        this.designations = designationsParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * searchDesignation.
     * 
     * @param namedQuery , not null
     * @return designations , null if not found
     * @throws DesignationException
     * </pre>
     */
    @Override
    public List<Designation> searchDesignation(final String namedQuery) throws DesignationException {
        if (designations == null || designations.size() <= 0) {
            try {
                List<Object> objects = getObjects(namedQuery);
                if (objects != null && !objects.isEmpty()) {
                    designations = new ArrayList<Designation>();
                    for (Object object : objects) {
                        designations.add((Designation) object);
                    }
                }
            }
            catch (CouldNotSearchException ex) {
                throw new DesignationException(WASConstants.COULD_NOT_SEARCH_DESIGNATIONS);
            }
            catch (Exception ex) {
                throw new DesignationException(WASConstants.DB_DOWN);
            }
        }
        return designations;
    }

}
