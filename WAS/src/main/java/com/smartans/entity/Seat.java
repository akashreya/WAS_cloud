package main.java.com.smartans.entity;

/**
 * <pre>
 * <b>Description : </b>
 * Seat.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-13 11:04:31 PM $
 * @author $Author: megha.karadi $ 
 * </pre>
 */
public class Seat {
    /**
     * seatNumber.
     */
    private String seatNumber;

    /**
     * extensionNumber.
     */
    private String extensionNumber;

    /**
     * isManagerSeat.
     */
    private Boolean isManagerSeat;

    /**
     * employee.
     */
    private Employee employee;

    /**
     * <pre>
	 * <b>Description : </b>
	 * Constructs an instance of 'Seat'.
	 * 
	 * </pre>
     */
    public Seat() {
    }

    /**
     * <pre>
	 * <b>Description : </b>
	 * Constructs an instance of 'Seat'.
	 * 
	 * @param seatNumberParam , not null
	 * @param extensionNumberParam , not null
	 * @param isManagerSeatParam , not null
	 * @param employeeParam , not null
	 * </pre>
     */
    public Seat(final String seatNumberParam, final String extensionNumberParam, final Boolean isManagerSeatParam,
        final Employee employeeParam) {
        super();
        this.seatNumber = seatNumberParam;
        this.extensionNumber = extensionNumberParam;
        this.isManagerSeat = isManagerSeatParam;
        this.employee = employeeParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'seatNumber' attribute value.
     * 
     * @return seatNumber , null if not found.
     * </pre>
     */

    public String getSeatNumber() {
        return seatNumber;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'seatNumber' attribute value.
     * 
     * @param seatNumberParam , may be null.
     * </pre>
     */
    public void setSeatNumber(final String seatNumberParam) {
        this.seatNumber = seatNumberParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'extensionNumber' attribute value.
     * 
     * @return extensionNumber , null if not found.
     * </pre>
     */

    public String getExtensionNumber() {
        return extensionNumber;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'extensionNumber' attribute value.
     * 
     * @param extensionNumberParam , may be null.
     * </pre>
     */
    public void setExtensionNumber(final String extensionNumberParam) {
        this.extensionNumber = extensionNumberParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'isManagerSeat' attribute value.
     * 
     * @return isManagerSeat , null if not found.
     * </pre>
     */

    public Boolean getIsManagerSeat() {
        return isManagerSeat;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'isManagerSeat' attribute value.
     * 
     * @param isManagerSeatParam , may be null.
     * </pre>
     */
    public void setIsManagerSeat(final Boolean isManagerSeatParam) {
        this.isManagerSeat = isManagerSeatParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'employee' attribute value.
     * 
     * @return employee , null if not found.
     * </pre>
     */

    public Employee getEmployee() {
        return employee;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'employee' attribute value.
     * 
     * @param employeeParam , may be null.
     * </pre>
     */
    public void setEmployee(final Employee employeeParam) {
        this.employee = employeeParam;
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
        result = prime * result + ((employee == null) ? 0 : employee.hashCode());
        result = prime * result + ((extensionNumber == null) ? 0 : extensionNumber.hashCode());
        result = prime * result + ((isManagerSeat == null) ? 0 : isManagerSeat.hashCode());
        result = prime * result + ((seatNumber == null) ? 0 : seatNumber.hashCode());
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
        Seat other = (Seat) obj;
        if (employee == null) {
            if (other.employee != null) {
                return false;
            }
        }
        else if (!employee.equals(other.employee)) {
            return false;
        }
        if (extensionNumber == null) {
            if (other.extensionNumber != null) {
                return false;
            }
        }
        else if (!extensionNumber.equals(other.extensionNumber)) {
            return false;
        }
        if (isManagerSeat == null) {
            if (other.isManagerSeat != null) {
                return false;
            }
        }
        else if (!isManagerSeat.equals(other.isManagerSeat)) {
            return false;
        }
        if (seatNumber == null) {
            if (other.seatNumber != null) {
                return false;
            }
        }
        else if (!seatNumber.equals(other.seatNumber)) {
            return false;
        }
        return true;
    }

}
