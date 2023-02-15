package main.java.com.smartans.designation.dtos;

import java.io.Serializable;

/**
 * <pre>
 * <b>Description : </b>
 * DesignationType.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 12:05:50 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class DesignationType implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 2294180737192748072L;
    /**
     * designationId.
     */
    private Integer designationId;
    /**
     * designationName.
     */
    private String designationName;
    /**
     * isManager.
     */
    private Boolean isManager;

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'designationId' attribute value.
     * 
     * @return designationId , null if not found.
     * </pre>
     */

    public Integer getDesignationId() {
        return designationId;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'designationId' attribute value.
     * 
     * @param designationIdParam , may be null.
     * </pre>
     */
    public void setDesignationId(final Integer designationIdParam) {
        this.designationId = designationIdParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'designationName' attribute value.
     * 
     * @return designationName , null if not found.
     * </pre>
     */

    public String getDesignationName() {
        return designationName;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'designationName' attribute value.
     * 
     * @param designationNameParam , may be null.
     * </pre>
     */
    public void setDesignationName(final String designationNameParam) {
        this.designationName = designationNameParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'isManager' attribute value.
     * 
     * @return isManager , null if not found.
     * </pre>
     */

    public Boolean getIsManager() {
        return isManager;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'isManager' attribute value.
     * 
     * @param isManagerParam , may be null.
     * </pre>
     */
    public void setIsManager(final Boolean isManagerParam) {
        this.isManager = isManagerParam;
    }

}
