/**
 * 
 */

function DateTime() {
	var objToday = new Date(),
    weekday = new Array('Diumenge', 'Dilluns', 'Dimarts', 'Dimecres', 'Dijous', 'Divendres', 'Dissabre'),
    dayOfWeek = weekday[objToday.getDay()],
    domEnder = new Array( '', '', '', '', '', '', '', '', '', '' ),
    dayOfMonth = today + (objToday.getDate() < 10) ? '0' + objToday.getDate() + domEnder[objToday.getDate()] : objToday.getDate() + domEnder[parseFloat(("" + objToday.getDate()).substr(("" + objToday.getDate()).length - 1))],
    months = new Array('1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'),
    curMonth = months[objToday.getMonth()],
    curYear = objToday.getFullYear(),
    curHour = objToday.getHours() > 12 ? objToday.getHours() - 12 : (objToday.getHours() < 10 ? "0" + objToday.getHours() : objToday.getHours()),
    curMinute = objToday.getMinutes() < 10 ? "0" + objToday.getMinutes() : objToday.getMinutes(),
    curSeconds = objToday.getSeconds() < 10 ? "0" + objToday.getSeconds() : objToday.getSeconds(),
    curMeridiem = objToday.getHours() > 12 ? "PM" : "AM";
    today = /*curHour + ":" + curMinute + ":" + curSeconds + curMeridiem + " " +*/ curYear + "-" + curMonth + "-" + dayOfMonth;
}

function deleteRow(rowNumber) {
	for (var i = 0; i < filesUploaded; i++) {
		if (i == rowNumber) {
			if ((i + 1) <= filesUploaded) {
				var nextFile2UploadName, nextToday, nextRowInformation;
				nextFile2UploadName = $("#file-name" + (i + 1)).text();
				nextToday = $("#file-hour" + (i + 1)).text();
				nextRowInformation = $("#file-filter" + (i + 1)).text();
				// Row i
				$("#file-name" + i).text(nextFile2UploadName);
				$("#file-hour" + i).text(nextToday);
				$("#file-filter" + i).text(nextRowInformation);
				$("#checkbox-file" + i).attr('checked', false);
				// Row i+1
				$("#file-name" + (i + 1)).text("");
				$("#file-hour" + (i + 1)).text("");
				$("#file-filter" + (i + 1)).text("");
			}
			else {
				$("#file-name" + i).text("");
				$("#file-hour" + i).text("");
				$("#file-filter" + i).text("");
				$("#checkbox-file" + i).attr('checked', false);
			}
		}
		else if (i > rowNumber) {
			if ((i + 1) <= filesUploaded) {
				var nextFile2UploadName, nextToday, nextRowInformation;
				nextFile2UploadName = $("#file-name" + (i + 1)).text();
				nextToday = $("#file-hour" + (i + 1)).text();
				nextRowInformation = $("#file-filter" + (i + 1)).text();
				// Row i
				$("#file-name" + i).text(nextFile2UploadName);
				$("#file-hour" + i).text(nextToday);
				$("#file-filter" + i).text(nextRowInformation);
				// Row i+1
				$("#file-name" + (i + 1)).text("");
				$("#file-hour" + (i + 1)).text("");
				$("#file-filter" + (i + 1)).text("");
			}
			else {
				$("#file-name" + i).text("");
				$("#file-hour" + i).text("");
				$("#file-filter" + i).text("");
			}
		}
	}
}