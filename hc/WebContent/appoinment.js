const user = JSON.parse(localStorage.getItem('user'));
var appos = [];
var nextNumber = 1;
var days = [ 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday',
		'Saturday' ];
var selectedDate = '';
var selectedDoc = '';
$(document).ready(

function() {
	$('#btn_submit').show();
	$('#btn_update').hide();
	$('#btn_cancel').hide();
	getAppos();
	getDoctors();
	// getPatients();
});
function sessionChange() {
	getNextNumber();
}
function getDoctors() {

	$.get('/hc/api/doctors/all', function(data) {
		$('#new_doctor_select').empty();
		$('#new_doctor_select').append(
				' <option value="">Select a doctor</option>');
		for (var i = 0; i < data.length; i++) {
			$('#new_doctor_select').append(
					' <option value="' + data[i].id + '">' + data[i].name
							+ '</option>');
		}

	});
}
var toUpdate = null;
function update(id) {
	for (var i = 0; i < appos.length; i++) {
		if (appos[i].id == id) {
			toUpdate = appos[i];
			$('#update_meta').empty();
			$('#update_meta').append(
					'You gonna edit, ' + toUpdate.sessionDescription
							+ ' session of ' + toUpdate.date);
			$('#btn_submit').hide();
			$('#btn_update').show();
			$('#btn_cancel').show();
		}
	}
}

function doUpdate() {
	debugger
	toUpdate.sessionId = $('#new_session').val()
	toUpdate.date = new Date(selectedDate);
	$.ajax({
		type : "PUT",
		url : '/hc/api/appoinment/update/' + toUpdate.id,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		headers : {
			"Authorization" : "Basic "
					+ btoa(localStorage.getItem('username') + ":"
							+ localStorage.getItem('password'))
		},
		data : JSON.stringify(toUpdate),
		success : function(data) {
			if (data.status) {
				alert('Updated');
				toUpdate = null;
				$('#update_meta').empty();
				$('#btn_submit').show();
				$('#btn_update').hide();
				$('#btn_cancel').hide();
				getAppos();
			}
		}
	});
}

function cancel() {
	$('#update_meta').empty();
	$('#btn_submit').show();
	$('#btn_update').hide();
	$('#btn_cancel').hide();
}
function del(id) {
	$.ajax({
		type : "DELETE",
		url : '/hc/api/appoinment/delete/' + id,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		headers : {
			"Authorization" : "Basic "
					+ btoa(localStorage.getItem('username') + ":"
							+ localStorage.getItem('password'))
		},

		success : function(data) {
			if (data.status) {
				alert('Deleted');
				getAppos();
			}
		}
	});
}
function getNextNumber() {
	var sessionId = $('#new_session').val();
	var d = new Date(selectedDate);
	$.ajax({
		type : "GET",
		url : '/hc/api/doctors/appos/' + selectedDate + '/' + sessionId,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		headers : {
			"Authorization" : "Basic "
					+ btoa(localStorage.getItem('username') + ":"
							+ localStorage.getItem('password'))
		},

		success : function(data) {
			nextNumber = data.length + 1;
		}
	});
}
function create() {
	// var patientId = $('#new_patient').val();
	var sessionId = $('#new_session').val();
	var d = new Date(selectedDate);
	$.ajax({
		type : "POST",
		url : '/hc/api/appoinment/book',
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		headers : {
			"Authorization" : "Basic "
					+ btoa(localStorage.getItem('username') + ":"
							+ localStorage.getItem('password'))
		},
		data : JSON.stringify({
			date : d,
			// patientId : patientId,
			sessionId : sessionId,
			number : nextNumber
		}),
		success : function(data) {
			if (data.status) {
				alert('Appoinment created');
				getAppos();
			}
		}
	});
}
function doctorChange() {
	selectedDoc = $('#new_doctor_select').val();
	getDoctorSessions();
	getNextNumber();
}
function dateChanged() {
	selectedDate = $('#new_date').val();
	getDoctorSessions();
	getNextNumber();
}
function getPatients() {
	$.get('/hc/api/user/patients', function(data) {
		$('#new_patient').empty();
		for (var i = 0; i < data.length; i++) {
			$('#new_patient').append(
					' <option value="' + data[i].id + '">' + data[i].name
							+ '</option>');
		}
	});
}
function getDoctorSessions() {
	var d = new Date(selectedDate);
	var day = days[d.getDay()].toUpperCase();
	$.ajax({
		type : "GET",
		url : '/hc/api/doctors/sessions/' + selectedDoc + '/' + day,
		dataType : 'json',
		headers : {
			"Authorization" : "Basic "
					+ btoa(localStorage.getItem('username') + ":"
							+ localStorage.getItem('password'))
		},
		success : function(data) {
			$('#new_session').empty();
			for (var i = 0; i < data.length; i++) {
				$('#new_session').append(
						' <option value="' + data[i].id + '">'
								+ data[i].description + '</option>');
			}

		}
	});
}
function getAppos() {

	var day = $('#day_select').val();
	$
			.ajax({
				type : "GET",
				url : '/hc/api/appoinment/my/',
				dataType : 'json',
				headers : {
					"Authorization" : "Basic "
							+ btoa(localStorage.getItem('username') + ":"
									+ localStorage.getItem('password'))
				},
				success : function(data) {
					appos = data;
					$('#table_container').empty();
					const tds = '<tr>' + '<td> @id</td>' + '<td> @date </td>'
							+ '<td> @number </td>' + '<td> @paid </td>'
							+ '<td> @patientName </td>'
							+ '<td> @sessionDescription </td>'
							+ '<td> @action</td>' + '</tr>';
					const btnUpdate = '<button type="button" style="margin:5px" class="btn btn-warning" onClick="update( @id )">Update</button>';
					const btnDelete = '<button type="button" style="margin:5px" class="btn btn-danger" onClick="del( @id )">Delete</button>';
					for (var i = 0; i < data.length; i++) {
						var d = data[i];
						var actions = btnUpdate.replace('@id', d.id)
								+ btnDelete.replace('@id', d.id);
						var tr = tds.replace('@id', d.id).replace('@date',
								d.date).replace('@number', d.number).replace(
								'@paid', d.paid == 1 ? 'TRUE' : 'FALSE')
								.replace('@patientName', d.patientName)
								.replace('@sessionDescription',
										d.sessionDescription).replace(
										'@action', actions);

						$('#table_container').append(tr);
					}

				}
			});

}