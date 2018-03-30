/**
 * Created by zxf on 2017/8/31.
 */
const cameraPlugin = {}

cameraPlugin.getPicture = function(type,onSuccess, onFail){
  navigator.camera.getPicture(function (imgUri) {
    console.log('success'+imgUri);
    onSuccess(imgUri)
  }, function (error) {
    console.log('fail '+ error);
    onFail(error)
  }, {
    allowEdit: false,//选择图片前是否允许编辑
    encodingType: 0,
    targetWidth: 100,//缩放图像的宽度（像素）
    targetHeight: 100,//缩放图像的高度（像素）
    saveToPhotoAlbum: true,//是否保存到相册
    correctOrientation: true,//设置摄像机拍摄的图像是否为正确的方向
    quality: 50,
    sourceType: type==='take'?1:2,
    destinationType: Camera.DestinationType.DATA_URL });
}

export default cameraPlugin
