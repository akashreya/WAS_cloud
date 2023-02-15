package main.java.com.smartans.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.common.util.ErrorCodeMessageUtil;
import main.java.com.smartans.designation.dtos.DesignationType;
import main.java.com.smartans.designation.dtos.ShowDesignationType;
import main.java.com.smartans.designation.interfaces.MaintainDesignations;
import main.java.com.smartans.employees.dtos.CreateEmployeeType;
import main.java.com.smartans.employees.dtos.DeleteEmployeeType;
import main.java.com.smartans.employees.dtos.EmployeeType;
import main.java.com.smartans.employees.dtos.ShowEmployeeType;
import main.java.com.smartans.helper.SearchHelper;
import main.java.com.smartans.seatassignement.dtos.AssignSeatType;
import main.java.com.smartans.seatassignement.dtos.ConfirmSeatType;
import main.java.com.smartans.seatassignement.dtos.ConfirmSwapSeatType;
import main.java.com.smartans.seatassignement.dtos.ConfirmUnassignSeatType;
import main.java.com.smartans.seatassignement.dtos.IdentifierType;
import main.java.com.smartans.seatassignement.dtos.ReassignSeatType;
import main.java.com.smartans.seatassignement.dtos.SeatAssignmentType;
import main.java.com.smartans.seatassignement.dtos.SwapSeatType;
import main.java.com.smartans.seatassignement.dtos.UnassignSeatType;
import main.java.com.smartans.seatassignement.interfaces.SeatAssignment;
import main.java.com.smartans.seats.dtos.CreateSeatType;
import main.java.com.smartans.seats.dtos.DeleteSeatType;
import main.java.com.smartans.seats.dtos.SeatType;
import main.java.com.smartans.seats.dtos.ShowSeatType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <pre>
 * <b>Description : </b>
 * FrontController.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-03 03:46:12 PM $
 * @author $Author: megha.karadi $ 
 * </pre>
 */
@Controller
public class FrontController {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * helper.
     */
    @Autowired
    private SearchHelper helper;

    /**
     * seatAssignment.
     */
    @Autowired
    private SeatAssignment seatAssignment;

    /**
     * maintainDesignation.
     */
    @Autowired
    private MaintainDesignations maintainDesignation;

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'helper' attribute value.
     * 
     * @param helperParam , may be null.
     * </pre>
     */
    public final void setHelper(final SearchHelper helperParam) {
        this.helper = helperParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'maintainDesignation' attribute value.
     * 
     * @param maintainDesignationParam , may be null.
     * </pre>
     */
    public final void setMaintainDesignation(final MaintainDesignations maintainDesignationParam) {
        this.maintainDesignation = maintainDesignationParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'seatAssignment' attribute value.
     * 
     * @param seatAssignmentParam , may be null.
     * </pre>
     */
    public final void setSeatAssignment(final SeatAssignment seatAssignmentParam) {
        this.seatAssignment = seatAssignmentParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * assignOrReassign.
     * 
     * @param model , not null
     * @param request , not null
     * @return target , never null
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/assignreassign.action", method = RequestMethod.GET)
    public final String assignOrReassign(final Model model, final HttpServletRequest request) {
        String target = "index";
        ConfirmSeatType confirmSeatType = null;
        Map<String, ShowEmployeeType> employees = (Map<String, ShowEmployeeType>) request.getSession().getAttribute(
            "Employees");
        Map<String, ShowSeatType> seatMap = (Map<String, ShowSeatType>) request.getSession().getAttribute("seatMap");
        String employeeId = request.getParameter(WASConstants.MID);
        String seatId = request.getParameter(WASConstants.SEAT_ID);
        if (employeeId != null && !employeeId.isEmpty() && seatId != null && !seatId.isEmpty()) {
            EmployeeType employeeType = new EmployeeType();
            employeeType.setmID(employeeId);
            SeatType seatType = new SeatType();
            seatType.setSeatName(seatId);
            if (employees != null && employees.containsKey(employeeId)) {
                if (seatMap != null && seatMap.containsKey(seatId)) {
                    AssignSeatType assignSeatType = new AssignSeatType();
                    assignSeatType.setEmployeeType(employeeType);
                    assignSeatType.setSeatType(seatType);
                    confirmSeatType = seatAssignment.assignSeat(assignSeatType);
                }
                else {
                    confirmSeatType = new ConfirmSeatType();
                    confirmSeatType.setSuccess(false);
                    List<String> errors = new ArrayList<String>();
                    errors.add(ErrorCodeMessageUtil
                        .getMessageForErrorCode(WASConstants.EMPLOYEE_CANNOT_BE_ASSIGNED_TO_OCCUPIED_SEAT_FROM_QUEUE));
                    confirmSeatType.setErrorMessage(errors);
                }

            }
            else if (employees != null && !employees.containsKey(employeeId) && seatMap != null
                && seatMap.containsKey(seatId)) {
                ReassignSeatType reassignSeatType = new ReassignSeatType();
                reassignSeatType.setEmployeeType(employeeType);
                reassignSeatType.setSeatType(seatType);
                confirmSeatType = seatAssignment.reassignSeat(reassignSeatType);
            }
            else if (employees != null && !employees.containsKey(employeeId) && !employees.containsKey(seatId)) {
                SwapSeatType swapSeatType = new SwapSeatType();

                SeatAssignmentType employee = new SeatAssignmentType();
                employee.setEmployeeType(employeeType);
                swapSeatType.setEmployee(employee);

                SeatAssignmentType swappedEmployee = new SeatAssignmentType();
                EmployeeType swappedEmployeeType = new EmployeeType();
                swappedEmployeeType.setmID(seatId);
                swappedEmployee.setEmployeeType(swappedEmployeeType);
                swapSeatType.setSwappedEmployee(swappedEmployee);

                ConfirmSwapSeatType confirmSwapSeatType = seatAssignment.swapSeat(swapSeatType);
                if (confirmSwapSeatType != null && !confirmSwapSeatType.isSuccess()) {
                    request.getSession().setAttribute("error", confirmSwapSeatType.getErrorMessage().get(0));
                    request.getSession().setAttribute("successMessage", "");
                }
                else {
                    request.getSession().setAttribute("error", "");
                    request.getSession().setAttribute("successMessage",
                        ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.SEAT_REASSIGNED_SUCCESSFULLY));
                    searchSeatsEmployees(request);
                }
            }
            if (confirmSeatType != null) {
                if (!confirmSeatType.isSuccess()) {
                    request.getSession().setAttribute("error", confirmSeatType.getErrorMessage().get(0));
                    request.getSession().setAttribute("successMessage", "");
                }
                else {
                    request.getSession().setAttribute("error", "");
                    request.getSession().setAttribute("successMessage",
                        ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.SEAT_ASSIGNED_SUCCESSFULLY));
                    searchSeatsEmployees(request);
                }
            }
        }
        else {
            searchSeatsEmployees(request);
        }
        return target;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * addSeat.
     * 
     * @param model , not null
     * @param request , not null
     * @param response , not null
     * @return target , never null
     * </pre>
     */
    @RequestMapping(value = "/createSeat.action", method = RequestMethod.POST)
    public final String addSeat(final Model model, final HttpServletRequest request, 
        final HttpServletResponse response) {
        String seatNumber = request.getParameter(WASConstants.SEAT_ID);
        String extensionNumber = request.getParameter("extn");
        String isManager = request.getParameter("isManager");
        if (seatNumber != null && extensionNumber != null && isManager != null && !seatNumber.isEmpty()
            && !extensionNumber.isEmpty() && !isManager.isEmpty()) {

            if (Boolean.valueOf(isManager) && request.getSession().getAttribute("managerSeatCount") != null
                && (Integer) request.getSession().getAttribute("managerSeatCount") == WASConstants.FIVE) {
                request.getSession().setAttribute("error",
                    ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.ALREADY_5_MANAGER_SEATS_PRESENT));
                request.getSession().setAttribute("successMessage", "");
            }
            else {
                if (request.getSession().getAttribute("seatMapCount") != null
                    && (Integer) request.getSession().getAttribute("seatMapCount") == WASConstants.HUNDRED) {
                    request.getSession().setAttribute("error",
                        ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.ALREADY_100_SEATS_PRESENT));
                    request.getSession().setAttribute("successMessage", "");
                }
                else {
                    ShowSeatType showSeatType = helper.getMaintainSeats().createSeats(
                        new CreateSeatType(seatNumber, extensionNumber, Boolean.valueOf(isManager)));
                    if (!showSeatType.isSuccess()) {
                        request.getSession().setAttribute("error", showSeatType.getErrorMessage().get(0));
                        request.getSession().setAttribute("successMessage", "");
                    }
                    else {
                        request.getSession().setAttribute("error", "");
                        request.getSession().setAttribute("successMessage",
                            ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.SEAT_ADDED_SUCCESSFULLY));
                    }
                }
            }
        }
        searchSeatsEmployees(request);
        return "index";
    }

    /**
     * <pre>
     * <b>Description : </b>
     * deleteEmployee.
     * 
     * @param model , not null
     * @param request , not null
     * @return target , never null
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/deleteEmployee.action", method = RequestMethod.GET)
    public final String deleteEmployee(final Model model, final HttpServletRequest request) {
        String employeeId = request.getParameter(WASConstants.MID);

        if (employeeId != null && !employeeId.isEmpty()) {
            Map<String, ShowEmployeeType> employees = (Map<String, ShowEmployeeType>) request.getSession()
                .getAttribute("Employees");
            if (employees != null && !employees.containsKey(employeeId)) {
                UnassignSeatType unassignSeatType = new UnassignSeatType();
                List<IdentifierType> employeeIds = new ArrayList<IdentifierType>();
                // forloop if unassign multiple employees
                IdentifierType identifierType = new IdentifierType();
                identifierType.setEmployeeId(employeeId);
                employeeIds.add(identifierType);

                unassignSeatType.setEmployeeIds(employeeIds);
                ConfirmUnassignSeatType confirmUnassignSeatType = seatAssignment.unAssignSeat(unassignSeatType);
                if (!confirmUnassignSeatType.isSuccess()) {
                    request.getSession().setAttribute("error", confirmUnassignSeatType.getErrorMessage().get(0));
                    request.getSession().setAttribute("successMessage", "");
                }
                else {
                    request.getSession().setAttribute("error", "");
                    request.getSession().setAttribute("successMessage",
                        ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.EMPLOYEE_REMOVED_SUCCESSFULLY));
                    searchSeatsEmployees(request);
                }
            }
            else if (employees != null && employees.containsKey(employeeId)) {
                DeleteEmployeeType deleteEmployeeType = new DeleteEmployeeType();
                deleteEmployeeType.setmID(employeeId);
                ShowEmployeeType showEmployeeType = helper.getMaintainEmployees().deleteEmployee(deleteEmployeeType);
                if (!showEmployeeType.isSuccess()) {
                    request.getSession().setAttribute("error", showEmployeeType.getErrorMessage().get(0));
                    request.getSession().setAttribute("successMessage", "");
                }
                else {
                    request.getSession().setAttribute("error", "");
                    request.getSession().setAttribute("successMessage",
                        ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.EMPLOYEE_REMOVED_SUCCESSFULLY));
                }
            }

        }
        searchSeatsEmployees(request);
        return "index";
    }

    /**
     * <pre>
     * <b>Description : </b>
     * searchSeatsEmployees.
     * 
     * @param request , not null
     * </pre>
     */
    private void searchSeatsEmployees(final HttpServletRequest request) {
        // Search Seats
        helper.searchSeats(request.getSession());
        // Search employees who have not got a seat.
        helper.searchEmployees(request.getSession());
    }

    /**
     * <pre>
     * <b>Description : </b>
     * addSeatAjax.
     * 
     * @param model , not null
     * @param request , not null
     * @param response , not null
     * @return target , never null
     * </pre>
     */
    @RequestMapping(value = "/createSeatAjax.action", method = RequestMethod.GET)
    public final String addSeatAjax(final Model model, final HttpServletRequest request,
        final HttpServletResponse response) {
        request.getSession().setAttribute("error", "");
        request.getSession().setAttribute("successMessage", "");
        return "createSeatAjax";

    }

    /**
     * <pre>
     * <b>Description : </b>
     * addEmployee.
     * 
     * @param model , not null
     * @param request , not null
     * @param response , not null
     * @return target , never null
     * </pre>
     */
    @RequestMapping(value = "/createEmployee.action", method = RequestMethod.POST)
    public final String addEmployee(final Model model, final HttpServletRequest request,
        final HttpServletResponse response) {
        String[] designations = request.getParameterValues("designation");
        String employeeId = request.getParameter(WASConstants.MID);
        String employeeName = request.getParameter("empName");
        if (designations != null && designations.length > 0 && employeeId != null && !employeeId.isEmpty()
            && employeeName != null && !employeeName.isEmpty()) {
            ShowEmployeeType showEmployeeType = helper.getMaintainEmployees().createEmployee(
                new CreateEmployeeType(employeeId, employeeName, designations[0]));
            if (!showEmployeeType.isSuccess()) {
                request.getSession().setAttribute("error", showEmployeeType.getErrorMessage().get(0));
                request.getSession().setAttribute("successMessage", "");
            }
            else {
                request.getSession().setAttribute("error", "");
                request.getSession().setAttribute("successMessage",
                    ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.EMPLOYEE_ADDED_SUCCESSFULLY));
            }
        }
        searchSeatsEmployees(request);
        return "index";
    }

    /**
     * <pre>
     * <b>Description : </b>
     * addEmployeeAjax.
     * 
     * @param model , not null
     * @param request , not null
     * @param response , not null
     * @return target , never null
     * </pre>
     */
    @RequestMapping(value = "/createEmpAjax.action", method = RequestMethod.GET)
    public final String addEmployeeAjax(final Model model, final HttpServletRequest request,
        final HttpServletResponse response) {
        request.getSession().setAttribute("error", "");
        request.getSession().setAttribute("successMessage", "");
        // getDesignationList
        List<DesignationType> designations = null;
        ShowDesignationType showDesignationType = maintainDesignation.searchDesignation();
        if (showDesignationType != null && showDesignationType.isSuccess()) {
            designations = showDesignationType.getDesignations();
        }
        request.setAttribute("designations", designations);
        return "createEmployeeAjax";
    }

    /**
     * <pre>
     * <b>Description : </b>
     * deleteSeat.
     * 
     * @param model , not null
     * @param request , not null
     * @return target , never null
     * </pre>
     */
    @RequestMapping(value = "/deleteSeat.action", method = RequestMethod.POST)
    public final String deleteSeat(final Model model, final HttpServletRequest request) {
        String seatNumber = request.getParameter(WASConstants.SEAT_ID);
        if (seatNumber != null && !seatNumber.isEmpty()) {
            DeleteSeatType deleteSeatType = new DeleteSeatType();
            deleteSeatType.setSeatName(seatNumber);
            ShowSeatType showSeatType = helper.getMaintainSeats().deleteSeat(deleteSeatType);
            if (!showSeatType.isSuccess()) {
                request.getSession().setAttribute("error", showSeatType.getErrorMessage().get(0));
                request.getSession().setAttribute("successMessage", "");
            }
            else {
                request.getSession().setAttribute("error", "");
                request.getSession().setAttribute("successMessage",
                    ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.SEAT_DELETED_SUCCESSFULLY));
                searchSeatsEmployees(request);
            }
        }
        searchSeatsEmployees(request);
        return "index";
    }

}
