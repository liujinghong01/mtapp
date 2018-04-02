/**
 * Created by zxf on 2017/9/9.
 */
export default {
  get (name) {
    let value = sessionStorage.getItem(name)
    if (/^\{.*\}$/.test(value)) value = JSON.parse(value)
    return value
  },
  set (name, value) {
    if (typeof value === typeof {}) value = JSON.stringify(value)
    return sessionStorage.setItem(name, value)
  },
  remove (name) {
    return sessionStorage.removeItem(name)
  },
  clear(){
    sessionStorage.clear()
  }
}
