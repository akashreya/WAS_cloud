<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Workspace Allocation System</title>
<script src="js/script.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body style="margin-left: auto; margin-right: auto; width: 100%;min-width: 1280">
	<div style="height: 50px;background-color: skyblue;position:relative ">
	<img  src="images/sixta.jpg">
	</div>
	<div style="height: 10px;position:relative ">
	</div>
	<div style="height: 30px;background-color: skyblue;position:relative ">
		<div style="position:absolute;bottom: 0">
			<label class="counts">Available : ${sessionScope.availableCount}</label>
			<label style="padding-left:4em" class="counts"> Occupied : ${sessionScope.occupiedCount}</label>
			<label style="padding-left:4em" class="counts">Employees to be seated : ${sessionScope.employeesTobeSeated}</label>
		</div>
	</div>
	<div id="totalOuterPanel" style="position: absolute;padding-top: 10px">
	<div style="float:left">
	<div style="height: 100px;padding-top: 30px;padding-left: 40px">
		<a href="#" onMouseover="move('container',10)" onMouseout="clearTimeout(move.to)"><img src="images/arrowleft.png" border=0></a>
	</div>
	<div id="big" style="position:relative;width:100px;height:550px;overflow-y: hidden;left:0;padding-left: 40px">
	<div id="container" style="position:absolute;width:100px;height:900; ">
	<c:set var="empIndex" scope="page" value="1" />
	<c:forEach items="${sessionScope.Employees}" var="emp">
		<div class="wrapper" style="padding-top: 30px;">
			<div class="tooltip">${emp.value.mID}<br>${emp.value.employeeName}</div>
				<c:if test="${empIndex eq 1}">
					<c:if test="${emp.value.isManager}">
						<img id="${emp.value.mID}" src="images/employeered.png" draggable="true" ondragstart="drag(event)">
					</c:if>
					<c:if test="${not emp.value.isManager}">
						<img id="${emp.value.mID}" src="images/employee.PNG" draggable="true" ondragstart="drag(event)">
					</c:if>
				</c:if>
				<c:if test="${empIndex ne 1}">
					<c:if test="${emp.value.isManager}">
						<img id="${emp.value.mID}" src="images/employeered.png" draggable="false">
					</c:if>
					<c:if test="${not emp.value.isManager}">
						<img id="${emp.value.mID}" src="images/employee.PNG" draggable="false">
					</c:if>
				</c:if>
		</div>				
		<c:set var="empIndex" scope="page" value="0" />
	</c:forEach>
	</div>
	</div>
	<div style="height: 100px;padding-left: 40px">
	 	<a href="#" onMouseover="move('container',-10)" onMouseout="clearTimeout(move.to)"><img src="images/arrowright.png" border=0></a>	
	</div>
	</div>
	<div class="scrollable-content" style="height: 810px; width: 850px;padding-top:20px; overflow: auto; float: left; position:relative ; ">
	<div class="table" id="errorMessage"><big>${sessionScope.error }</big></div>
	<div class="table" id="successMessage"><big>${sessionScope.successMessage }</big></div>
	<c:set var="count" scope="page" value="0" />
		<table id="myTable">
		<c:forEach items="${sessionScope.seatMap}" var="seat"  >
		<c:if test="${count mod 7 == 0}">
			<tr>
		</c:if>
		<c:if test="${seat.value.isManagerSeat}">
			<td class="manager">
		</c:if>
		<c:if test="${not seat.value.isManagerSeat}">
			<td class="employee">
		</c:if>
			<div class="wrapper" style="float: left">
			<c:if test="${not empty seat.value.employeeType}">
			<div class="tooltip">${seat.value.extensionNumber}<br>${seat.value.employeeType.mID}<br>${seat.value.employeeType.designation}</div>
			</c:if>
			<c:if test="${empty seat.value.employeeType}">
			<div class="tooltip">${seat.value.seatName}<br>${seat.value.extensionNumber}</div>
			
			</c:if>
				<div id="${seat.value.seatName}" ondrop="drop(event)" ondragover="allowDrop(event)" style="background-color: white;width : 100px;height : 100px" onmouseover="displayCloseButton(this.id)" onmouseout="removeCloseButton(this.id)">
				${seat.value.seatName}
				<c:if test="${not empty seat.value.employeeType}">
					<c:if test="${seat.value.employeeType.isManager}">
						<img id="${seat.value.employeeType.mID}" src="images/employeered.png" draggable="true" ondragstart="drag(event)">
						<div style="overflow-y: hidden; width:100px;height:15px;">${seat.value.employeeType.employeeName}</div>
					</c:if>
					<c:if test="${not seat.value.employeeType.isManager}">
						<img id="${seat.value.employeeType.mID}" src="images/employee.PNG" draggable="true" ondragstart="drag(event)">
						<div style="overflow-y: hidden; width:100px;height:15px;overflow-x: hidden;">${seat.value.employeeType.employeeName}</div>
					</c:if>
				</c:if>
				<c:if test="${empty seat.value.employeeType}">
					<c:set var="closeIconId" scope="page" value="closeIcon" />
					<div style="right:0;position:absolute;bottom:0">
						<img id="${seat.value.seatName }${closeIconId}" src="images/closeicon.png" class="closeButton" onclick="deleteSeat(this.id)">
					</div>
				</c:if>
				</div>
				</div>
			</div>
			</td>
			<c:if test="${count mod 7 == 6}">
			</tr>
			</c:if>		
		<c:set var="count" scope="page" value="${count + 1}" />
		</c:forEach>
		</table>
	</div>
	
	<div style="display: inline-block;padding-top: 10px; ">
	<div style="padding-left: 120px">
	<div style="height: 100px;" ondrop="drop(event)" ondragover="allowDrop(event)">
		<img src="images/deletebig.jpg" id="deletebin" draggable="false">
	</div>
	<div style="height: 100px; width: 110px;">
		<button type="button" id="buttonId" onclick="displayResultSeat()">Seat+</button>
	</div>
	<div style="height: 100px; width: 110px;">
		<button type="button" id="buttonId" onclick="displayResultEmp()">Employee+</button>
	</div> 
	</div>
	<div>
	<div id="errDiv" class="table" style="color :red;"></div>
	<div id="createSeatDiv" class="createSeatDiv" >
	</div>
	</div>
	
	
</div>

</div>
</body>
</html>