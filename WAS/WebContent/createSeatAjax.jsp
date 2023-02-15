<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="css/style.css" rel="stylesheet">
</head>
<body>

<div id="errDiv" style="color :red"></div>
	<form id="createSeat" action="createSeat.action" method="post" onsubmit="return validateSeat(this)">
		<table class="table" >
			<tr>
				<td>Seat number</td>
				<td><input type="text" id="sid" name="sid" onkeyup="valid(this)" onblur="valid(this)" />
				</td>
			</tr>
			<tr>
				<td>Extn number</td>
				<td><input type="text" id="extn" name="extn" maxlength="5"/>
				</td>
			</tr>
			<tr>
				<td>Is manager seat : </td>
				<td><label class="table">Yes</label><input type="radio" name="isManager" value="true" />
				<label class="table">No</label><input type="radio" name="isManager" value="false" checked="checked" />
				</td>

			</tr>
			<tr>
				<td><input type="submit" value="Create" /></td>
				<td><input type="button" name="cancelSeat" value="Cancel" onclick="window.location='index.jsp'" /></td>
			</tr>
		</table>
	</form>

</body>
</html>
