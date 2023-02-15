package main.java.com.smartans.designation.dtos;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * <b>Description : </b>
 * ShowDesignationType.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 12:05:50 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ShowDesignationType implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1609289098788972713L;

    /**
     * designations.
     */
    private List<DesignationType> designations;
    /**
     * isSuccess.
     */
    private boolean isSuccess;
    /**
     * errorMessages.
     */
    private List<String> errorMessages;

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'designations' attribute value.
     * 
     * @return designations , null if not found.
     * </pre>
     */

    public List<DesignationType> getDesignations() {
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
    public void setDesignations(final List<DesignationType> designationsParam) {
        this.designations = designationsParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'isSuccess' attribute value.
     * 
     * @return isSuccess , null if not found.
     * </pre>
     */

    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'isSuccess' attribute value.
     * 
     * @param isSuccessParam , may be null.
     * </pre>
     */
    public void setSuccess(final boolean isSuccessParam) {
        this.isSuccess = isSuccessParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'errorMessages' attribute value.
     * 
     * @return errorMessages , null if not found.
     * </pre>
     */

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'errorMessages' attribute value.
     * 
     * @param errorMessagesParam , may be null.
     * </pre>
     */
    public void setErrorMessages(final List<String> errorMessagesParam) {
        this.errorMessages = errorMessagesParam;
    }

}
