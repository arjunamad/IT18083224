<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.0.min.js"
	integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ="
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
</head>
<body>
	<section id="new_container">
		<h2>Create New Appoinment</h2>
		<p id="update_meta"></p>
		
		<input type="hidden" id="id">
		<div class="form-group">
			<label>Doctor</label> <select id="new_doctor_select"
				onChange="doctorChange()"></select>
		</div>
		<div class="form-group">
			<label>Doctor</label> <input type="date" id="new_date"
				onChange="dateChanged()">
		</div>
		<div class="form-group">
			<label>Session</label> <select id="new_session" onChange="sessionChange()"></select>
		</div>
		<!--  	<div class="form-group">
			<label>patient</label> <select id="new_patient" ></select>
		</div>-->
		<button type="button" onclick="create()" id="btn_submit"
			class="btn btn-primary">Submit</button>
		<button type="button" onclick="doUpdate()" id="btn_update"
			class="btn btn-primary">Update</button>
		<button type="button" onclick="cancel()" id="btn_cancel"
			class="btn btn-danger">Cancel</button>
	</section>

	<table class="table" style="width: 900px">
		<thead>
			<tr>
				<th>#</th>
				<th>Date</th>
				<th>Number</th>
				<th>Is Paid</th>
				<th>Patient</th>
				<th>Session</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody id="table_container">
		</tbody>
	</table>
	<script src="appoinment.js"></script>
</body>
</html>