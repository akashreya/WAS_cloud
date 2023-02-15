function validate(frm) {
	var msg = "";
	var mid = frm.mid.value;
	var empName = frm.empName.value;
	var valid = true;
	if (mid == "") {
		msg += "MID is mandatory<br/>";
		valid = false;
	}
	else if (!(/^M[0-9]{7}$/).test(mid)) {
		msg+="Please enter valid format for employee ID M(#######)<br/>";
		valid=false;
	}
	if (empName == "") {
		msg += "Employee name is mandatory<br/>";
		valid = false;
	}
	else if (!(/^[a-zA-Z ]*$/).test(empName)) {
		msg+="Please enter valid format for employee name<br/>";
		valid=false;
	}
	if (!valid) {
		document.getElementById("errDiv").innerHTML = msg;
		return valid;
	} else {
		return valid;
	}
}
function validateSeat(frm) {
	var msg = "";
	var sid = frm.sid.value;
	var extn = frm.extn.value;
	var valid = true;
	if (sid == "") {
		msg += "Seat number is mandatory<br/>";
		valid = false;
	}
	else if (!(/^WCP[0-9]\-[0-9]F\-[0-9]{3}$/).test(sid)) {
		msg+="Please enter valid format for seat WCP(#)-(#)F-(###)<br/>";
		valid=false;
	}
	if (extn == "") {
		msg += "Extension number is mandatory<br/>";
		valid = false;
	}
	else if (isNaN(extn)) {
		msg += "Extension must be a number<br/>";
		valid = false;
	}
	else if (extn.length < 5) {
		msg += "Extension must be 5 Digits<br/>";
		valid = false;
	}
	if (!valid) {
		document.getElementById("errDiv").innerHTML = msg;
		return valid;
	} else {
		return valid;
	}
}
function valid(f) {
	f.value = f.value.toUpperCase();
}

function move(id, spd) {
	var obj = document.getElementById(id), max = -obj.offsetHeight
			+ obj.parentNode.offsetHeight, top = parseInt(obj.style.top);
	if ((spd > 0 && top <= 0) || (spd < 0 && top >= max)) {
		obj.style.top = top + spd + "px";
		move.to = setTimeout(function() {
			move(id, spd);
		}, 20);
	} else {
		obj.style.top = (spd > 0 ? 0 : max) + "px";
	}
}

function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	ev.dataTransfer.setData("Text", ev.target.id);

}
function drop(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("Text");
	ev.target.appendChild(document.getElementById(data));

	var employeeId = document.getElementById(data).id;
	var seatId = ev.target.id;

	var xmlhttp = null;
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			window.location.href = "index.jsp";
		}
	};
	if (seatId == ("deletebin")) {
		xmlhttp.open("GET", "deleteEmployee.action?mid=" + employeeId, true);
	} else {
		xmlhttp.open("GET", "assignreassign.action?mid=" + employeeId + "&sid="
				+ seatId, true);
	}
	xmlhttp.send();

}

function getProperDivElement(rows, coloumn) {
	return "<div id="
			+ rows
			+ coloumn
			+ " ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\" style=\"background-color: white;width : 100px;height : 100px\">"
			+ "WCP3-5F-" + rows + coloumn + "</div>";
}

function displayResultSeat() {
	clearMessagesFromScreen();
	var xmlhttp = null;
	xmlhttp = new XMLHttpRequest();

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			var response = xmlhttp.responseText;
			document.getElementById("createSeatDiv").innerHTML = response;
		}
	};
	xmlhttp.open("GET", "createSeatAjax.action", true);
	xmlhttp.send(null);

}

function displayResultEmp() {
	clearMessagesFromScreen();
	var xmlhttp = null;
	xmlhttp = new XMLHttpRequest();

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			var response = xmlhttp.responseText;
			document.getElementById("createSeatDiv").innerHTML = response;
		}
	};
	xmlhttp.open("GET", "createEmpAjax.action", true);
	xmlhttp.send(null);

}

function clearMessagesFromScreen() {
	document.getElementById("successMessage").innerHTML = "";
	document.getElementById("errorMessage").innerHTML = "";
	document.getElementById("errDiv").innerHTML = "";
}

function displayCloseButton(divId) {
	var imageid = divId + "closeIcon";
	var element = document.getElementById(imageid);
	element.style.opacity = 10;
}

function removeCloseButton(divId) {
	var imageid = divId + "closeIcon";
	var element = document.getElementById(imageid);
	element.style.opacity = 0;
}

function deleteSeat(seatId){
	var n = seatId.split("c");
	var xmlhttp = null;
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			window.location.href = "index.jsp";
		}
	};
	xmlhttp.open("POST", "deleteSeat.action?&sid=" + n[0], true);
	xmlhttp.send();
}
