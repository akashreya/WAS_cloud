package main.java.com.smartans.entity;

import java.util.Date;

/**
 * <pre>
 * <b>Description : </b>
 * Employee.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-13 11:04:31 PM $
 * @author $Author: megha.karadi $ 
 * </pre>
 */
public class Employee {

    /**
     * employeeId.
     */
    private String employeeId;

    /**
     * name.
     */
    private String name;

    /**
     * designation.
     */
    private Designation designation;

    /**
     * lastModifiedDate.
     */
    private Date lastModifiedDate;

    /**
     * <pre>
	 * <b>Description : </b>
	 * Constructs an instance of 'Employee'.
	 * 
	 * </pre>
     */
    public Employee() {
    }

    /**
     * <pre>
	 * <b>Description : </b>
	 * Constructs an instance of 'Employee'.
	 * 
	 * @param employeeIdParam , not null
	 * @param nameParam , not null
	 * @param designationParam , not null
	 * @param lastModifiedDateParam , not null
	 * </pre>
     */
    public Employee(final String employeeIdParam, final String nameParam, final Designation designationParam,
        final Date lastModifiedDateParam) {
        super();
        this.employeeId = employeeIdParam;
        this.name = nameParam;
        this.designation = designationParam;
        this.lastModifiedDate = lastModifiedDateParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'employeeId' attribute value.
     * 
     * @return employeeId , null if not found.
     * </pre>
     */

    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'employeeId' attribute value.
     * 
     * @param employeeIdParam , may be null.
     * </pre>
     */
    public void setEmployeeId(final String employeeIdParam) {
        this.employeeId = employeeIdParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'name' attribute value.
     * 
     * @return name , null if not found.
     * </pre>
     */

    public String getName() {
        return name;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'name' attribute value.
     * 
     * @param nameParam , may be null.
     * </pre>
     */
    public void setName(final String nameParam) {
        this.name = nameParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'designation' attribute value.
     * 
     * @return designation , null if not found.
     * </pre>
     */

    public Designation getDesignation() {
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
    public void setDesignation(final Designation designationParam) {
        this.designation = designationParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'lastModifiedDate' attribute value.
     * 
     * @return lastModifiedDate , null if not found.
     * </pre>
     */

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'lastModifiedDate' attribute value.
     * 
     * @param lastModifiedDateParam , may be null.
     * </pre>
     */
    public void setLastModifiedDate(final Date lastModifiedDateParam) {
        this.lastModifiedDate = lastModifiedDateParam;
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
        result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
        result = prime * result + ((lastModifiedDate == null) ? 0 : lastModifiedDate.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Employee other = (Employee) obj;
        if (designation == null) {
            if (other.designation != null) {
                return false;
            }
        }
        else if (!designation.equals(other.designation)) {
            return false;
        }
        if (employeeId == null) {
            if (other.employeeId != null) {
                return false;
            }
        }
        else if (!employeeId.equals(other.employeeId)) {
            return false;
        }
        if (lastModifiedDate == null) {
            if (other.lastModifiedDate != null) {
                return false;
            }
        }
        else if (!lastModifiedDate.equals(other.lastModifiedDate)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
