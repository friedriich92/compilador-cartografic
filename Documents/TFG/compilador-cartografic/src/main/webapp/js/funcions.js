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
    today = curHour + ":" + curMinute + ":" + curSeconds + curMeridiem + " " + dayOfMonth + "/" + curMonth + "/" + curYear;
}