export const calcDate = (date1, date2) => {
  var date3 = date2 - date1

  var days = Math.floor(date3 / (24 * 3600 * 1000))

  var leave1 = date3 % (24 * 3600 * 1000) // 计算天数后剩余的毫秒数
  var hours = Math.floor(leave1 / (3600 * 1000))

  var leave2 = leave1 % (3600 * 1000) // 计算小时数后剩余的毫秒数
  var minutes = Math.floor(leave2 / (60 * 1000))

  var leave3 = leave2 % (60 * 1000) // 计算分钟数后剩余的毫秒数
  var seconds = Math.round(date3 / 1000)
  return {
    leave1,
    leave2,
    leave3,
    days: days,
    hours: hours,
    minutes: minutes,
    seconds: seconds
  }
}

/**
 * 日期格式化
 */
export function dateFormat(date) {
  let format = 'yyyy-MM-dd hh:mm:ss'
  if (date !== 'Invalid Date') {
    var o = {
      'M+': date.getMonth() + 1, // month
      'd+': date.getDate(), // day
      'h+': date.getHours(), // hour
      'm+': date.getMinutes(), // minute
      's+': date.getSeconds(), // second
      'q+': Math.floor((date.getMonth() + 3) / 3), // quarter
      'S': date.getMilliseconds() // millisecond
    }
    if (/(y+)/.test(format)) {
      format = format.replace(RegExp.$1,
        (date.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(format)) {
        format = format.replace(RegExp.$1,
          RegExp.$1.length === 1 ? o[k]
            : ('00' + o[k]).substr(('' + o[k]).length))
      }
    }
    return format
  }
  return ''
}
export function dateToString(date) {
	var year = date.getFullYear();
	var month = (date.getMonth() + 1).toString();
	var day = (date.getDate()).toString();
	var hour = (date.getHours()).toString().padStart(2, "0");
	var minute = (date.getMinutes()).toString().padStart(2, "0");
	var second = (date.getSeconds()).toString().padStart(2, "0");

	if(month.length == 1) {
		month = "0" + month;
	}
	if(day.length == 1) {
		day = "0" + day;
	}
	var dateTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
	return dateTime;
}

export function bjDate(date, date1) {
	var date = new Date(date.replace(/-/g,'/'));
	var date1 = new Date(date1.replace(/-/g,'/'));
	if(date.getTime() - date1.getTime() < 0) {
		return '2';
	} else {
		return '1';
	}
}
