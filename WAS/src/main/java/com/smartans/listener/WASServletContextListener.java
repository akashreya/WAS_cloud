/**
 * 
 */
package main.java.com.smartans.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import main.java.com.smartans.helper.SearchHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author M1016932
 *
 */
public class WASServletContextListener implements HttpSessionListener {

    /**
     * helper.
     */
    @Autowired
    private SearchHelper helper;


	

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'helper' attribute value.
     * 
     * @param helperParam , may be null.
     * </pre>
     */
    public void setHelper(SearchHelper helper) {
        this.helper = helper;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * sessionCreated.
     * 
     * @param sessionEvent
     * </pre>
     */
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        
        ApplicationContext ctx = 
              (ApplicationContext) WebApplicationContextUtils.
                getWebApplicationContext(session.getServletContext());
        
        helper = (SearchHelper) ctx.getBean("helper");
        helper.searchSeats(session);
        helper.searchEmployees(session);
		
	}

	/**
	 * <pre>
	 * <b>Description : </b>
	 * sessionDestroyed.
	 * 
	 * @param sessionEvent
	 * </pre>
	 */
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		
	}

}
