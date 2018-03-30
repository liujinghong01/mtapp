/**
 * Created by jiachenpan on 16/11/18.
 */



  export function platform() {
    const u = navigator.userAgent;
    const isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    const isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    return isAndroid?'Android':'iOS'
  }
 export function parseTime(time, cFormat) {
   if (arguments.length === 0) {
     return null;
   }
   const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}';
   let date;
   if (typeof time == 'object') {
     date = time;
   } else {
     if (('' + time).length === 10) time = parseInt(time) * 1000;
     date = new Date(time);
   }
   const formatObj = {
     y: date.getFullYear(),
     m: date.getMonth() + 1,
     d: date.getDate(),
     h: date.getHours(),
     i: date.getMinutes(),
     s: date.getSeconds(),
     a: date.getDay()
   };
   const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
     let value = formatObj[key];
     if (key === 'a') return ['一', '二', '三', '四', '五', '六', '日'][value - 1];
     if (result.length > 0 && value < 10) {
       value = '0' + value;
     }
     return value || 0;
   });
   return time_str;
 }

 export function todayDate() {
  const date = new Date();
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  return formatObj.y+'-'+(formatObj.m < 10 ? '0'+formatObj.m : formatObj.m)+'-'+ (formatObj.d < 10 ? '0'+formatObj.d : formatObj.d)
}
export function currDays(curr_date) {//获取指定日期在当年的第多少天
  const date = new Date();//
  const year=Number(curr_date.split('-')[0])
  const month=Number(curr_date.split('-')[1])
  const day=Number(curr_date.split('-')[2])
  let totalDays = 0
  for(var i=1;i<month;i++){
    var d = new Date(year, i, 0);
    totalDays += d.getDate()
  }
  return totalDays+day
}
export function currDate(yeas,num) {//获取指定第多少天数在当年的日期

  if(num<=31){
    return yeas+"-"+"1-"+num
  }else{
      if(Number(yeas)%4===0){

        if(num<=60){
          return yeas+"-"+"2-"+(num-31)
        }else if(num<=91){
          return yeas+"-"+"3-"+(num-60)
        }else if(num<=122){
          return yeas+"-"+"4-"+(num-91)
        }else if(num<=152){
          return yeas+"-"+"5-"+(num-121)
        }else if(num<=182){
          return yeas+"-"+"6-"+(num-152)
        }else if(num<=213){
          return yeas+"-"+"7-"+(num-182)
        }else if(num<=244){
          return yeas+"-"+"8-"+(num-213)
        }else if(num<=274){
          return yeas+"-"+"9-"+(num-244)
        }else if(num<=305){
          return yeas+"-"+"10-"+(num-274)
        }else if(num<=335){
          return yeas+"-"+"11-"+(num-305)
        }else if(num<=366){
          return yeas+"-"+"12-"+(num-335)
        }
      }else{
        if(num<=59){
          return yeas+"-"+"2-"+(num-31)
        }else if(num<=90){
          return yeas+"-"+"3-"+(num-59)
        }else if(num<=120){
          return yeas+"-"+"4-"+(num-90)
        }else if(num<=151){
          return yeas+"-"+"5-"+(num-120)
        }else if(num<=181){
          return yeas+"-"+"6-"+(num-151)
        }else if(num<=212){
          return yeas+"-"+"7-"+(num-181)
        }else if(num<=243){
          return yeas+"-"+"8-"+(num-212)
        }else if(num<=273){
          return yeas+"-"+"9-"+(num-243)
        }else if(num<=304){
          return yeas+"-"+"10-"+(num-273)
        }else if(num<=334){
          return yeas+"-"+"11-"+(num-304)
        }else if(num<=365){
          return yeas+"-"+"12-"+(num-334)
        }

      }

  }


}

 export function formatTime(time, option) {
   time = +time * 1000;
   const d = new Date(time);
   const now = Date.now();

   const diff = (now - d) / 1000;

   if (diff < 30) {
     return '刚刚'
   } else if (diff < 3600) { // less 1 hour
     return Math.ceil(diff / 60) + '分钟前'
   } else if (diff < 3600 * 24) {
     return Math.ceil(diff / 3600) + '小时前'
   } else if (diff < 3600 * 24 * 2) {
     return '1天前'
   }
   if (option) {
     return parseTime(time, option)
   } else {
     return d.getMonth() + 1 + '月' + d.getDate() + '日' + d.getHours() + '时' + d.getMinutes() + '分'
   }
 }
