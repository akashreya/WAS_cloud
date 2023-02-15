package main.java.com.smartans.common.util;

/**
 * <pre>
 * <b>Description : </b>
 * StringUtil.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-12 12:14:31 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class StringUtil {

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SIAMStringUtil'.
     * 
     * </pre>
     */
    protected StringUtil() {
    }

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * appends strings.
     * 
     * @param objs , not null.
     * @return , never null.
     * </pre>
     */
    public static String addString(final Object... objs) {
        final StringBuffer sb = new StringBuffer();
        for (final Object obj : objs) {
            sb.append(String.valueOf(obj));
        }
        return sb.toString();
    }

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * This method checks if a string is null or empty.
     * 
     * @param str string value to be checked, may be null
     * @return true if blank else false, never null
     * </pre>
     */
    public static boolean isEmpty(final String str) {
        return ((str == null) || (str.length() == 0));
    }

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * Checks whether a string is non empty.
     * 
     * @param str string value to be checked, may be null
     * @return true if not blank else false, never null
     * </pre>
     */
    public static boolean isNotEmpty(final String str) {
        return !((str == null) || (str.length() == 0));
    }

}
