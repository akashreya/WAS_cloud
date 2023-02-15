package main.java.com.smartans.entity;

/**
 * <pre>
 * <b>Description : </b>
 * Designation.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-13 11:04:31 PM $
 * @author $Author: megha.karadi $ 
 * </pre>
 */
public class Designation {

    /**
     * designationId.
     */
    private int designationId;

    /**
     * designation.
     */
    private String designation;

    /**
     * isManager.
     */
    private Boolean isManager;

    /**
     * <pre>
	 * <b>Description : </b>
	 * Constructs an instance of 'Designation'.
	 * 
	 * </pre>
     */
    public Designation() {
    }

    /**
     * <pre>
	 * <b>Description : </b>
	 * Constructs an instance of 'Designation'.
	 * 
	 * @param designationIdParam , not null
	 * @param designationParam , not null
	 * @param isManagerParam , not null
	 * </pre>
     */
    public Designation(final int designationIdParam, final String designationParam, final Boolean isManagerParam) {
        super();
        this.designationId = designationIdParam;
        this.designation = designationParam;
        this.isManager = isManagerParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'designationId' attribute value.
     * 
     * @return designationId , null if not found.
     * </pre>
     */

    public int getDesignationId() {
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
    public void setDesignationId(final int designationIdParam) {
        this.designationId = designationIdParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'designation' attribute value.
     * 
     * @return designation , null if not found.
     * </pre>
     */

    public String getDesignation() {
        return designation;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'designation' attribute value.
     * 
     * @param designationParam , may be null.
     * </pre>
     */
    public void setDesignation(final String designationParam) {
        this.designation = designationParam;
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

    /**
     * <pre>
     * <b>Description : </b>
     * hashCode.
     * 
     * @return result , never null
     * </pre>
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((designation == null) ? 0 : designation.hashCode());
        result = prime * result + designationId;
        result = prime * result + ((isManager == null) ? 0 : isManager.hashCode());
        return result;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * equals.
     * 
     * @param obj , may be null
     * @return boolean , never null
     * </pre>
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Designation other = (Designation) obj;
        if (designation == null) {
            if (other.designation != null) {
                return false;
            }
        }
        else if (!designation.equals(other.designation)) {
            return false;
        }
        if (designationId != other.designationId) {
            return false;
        }
        if (isManager == null) {
            if (other.isManager != null) {
                return false;
            }
        }
        else if (!isManager.equals(other.isManager)) {
            return false;
        }
        return true;
    }

}
