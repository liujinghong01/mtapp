
const localStorage = {};

localStorage.get = function (key) {
  return window.localStorage.getItem(key)
}

localStorage.set = function (key,val) {
  return window.localStorage.setItem(key,val)
}

localStorage.remove = function (key) {
  window.localStorage.removeItem(key);
  return '';
}

localStorage.clear = function () {
  return window.localStorage.clear()
}

export default localStorage;
