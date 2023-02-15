package main.java.com.smartans.designation.interfaces;

import main.java.com.smartans.designation.dtos.ShowDesignationType;

/**
 * <pre>
 * <b>Description : </b>
 * MaintainDesignations.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-13 10:45:12 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public interface MaintainDesignations {
    /**
     * <pre>
	 * <b>Description : </b>
	 * searchDesignation.
	 * 
	 * @return showDesignationType , null if not found
	 * </pre>
     */
    ShowDesignationType searchDesignation();

}
