<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Phone</title>
<link type="text/css" href="css/style.css" rel="stylesheet">
<script type="text/javascript">
function validate(frm)
{
	var msg="";
	var mid=frm.mid.value;
	var empName=frm.empName.value;
	var valid=true;
	if(mid=="")
	{
		msg+="MID field is Mandatory<br/>";
		valid=false;	
	}
	if(empName=="")
	{
		msg+="Employee Name field is Mandatory<br/>";
		valid=false;	
	}
	if(!valid)
	{
		document.getElementById("errDiv").innerHTML=msg;
		return valid;
	}else{
		return valid;
	}
}
</script>
</head>
<div id="errDiv" style="color :red;"></div>
<body>
	<form id="createEmployee" action="createEmployee.action" method="post" onsubmit="return validate(this)">
		<table class="table" >
			<tr>
				<td>MID</td>
				<td><input type="text" id="mid" name="mid" maxlength="8" onkeyup="valid(this)" onblur="valid(this)"/>
				</td>
			</tr>
			<tr>
				<td>Name</td>
				<td><input type="text" id="empName" name="empName" maxlength="20" />
				</td>
			</tr>
			<tr>
				<td>Designation</td>
				<td>
				<select name="designation" id="designation"> 
				<c:forEach items="${designations}" var="d">
					<option value=${d.designationId}> ${d.designationName}</option>
					
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="Create" /></td>
				<td><input type="button" name="cancel" value="Cancel" onclick="window.location='index.jsp'" /></td>
			</tr>
		</table>
	</form>

</body>
</html>