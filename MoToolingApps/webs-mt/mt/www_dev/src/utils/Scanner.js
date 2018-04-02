/**
 * Created by zxf on 2017/8/31.
 */
const Scanner = {}
console.log(window.cordova)
let barcodeScanner = {};
try {
  barcodeScanner = window.cordova?window.cordova.plugins.barcodeScanner:''
}catch (err){
  console.log(window.cordova.plugins)
}

Scanner.scanbar = function (success,fail) {
  barcodeScanner.scan(function (result) {
    if(result.cancelled){
      fail({text:'取消操作'});
      return;
    }
    success(result)
  },function (error) {
    console.log(JSON.stringify(error));
    fail(error)
  })
}




export default Scanner;
