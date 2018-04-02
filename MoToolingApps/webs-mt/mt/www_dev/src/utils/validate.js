

/* 合法uri*/
export function validateURL(textval) {
  const urlregex = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/;
  return urlregex.test(textval);
}

/* 小写字母*/
export function validateLowerCase(str) {
  const reg = /^[a-z]+$/;
  return reg.test(str);
}

/* 大写字母*/
export function validateUpperCase(str) {
  const reg = /^[A-Z]+$/;
  return reg.test(str);
}

/* 大小写字母*/
export function validatAlphabets(str) {
  const reg = /^[A-Za-z]+$/;
  return reg.test(str);
}

export function validateEmail(email){
  const emailReg = /^[A-Za-z0-9_-\u4e00-\u9fa5]+@[a-zA-Z0-9_-\u4e00-\u9fa5]+(\.[a-zA-Z0-9_-\u4e00-\u9fa5]+)+$/;
  return emailReg.test(email);
}

export function validatePhone(phone) {
  const phoneReg = /^1\d{10}$/;
  return phoneReg.test(phone);
}

export function validatePWD(pwd) {
 const pwdReg =  /^[a-zA-Z0-9][a-zA-Z0-9_]{5,19}$/;
 return pwdReg.test(pwd)
}

//6位数字
export function validIsNumber(number){
  const pwdReg =  /^\d{6}/;
  return pwdReg.test(number)
}
