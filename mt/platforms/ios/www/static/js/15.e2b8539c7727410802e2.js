webpackJsonp([15],{279:function(e,t,i){i(672);var n=i(0)(i(436),i(782),"data-v-9dac6f92",null);e.exports=n.exports},328:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});t.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(e){var t=e.target.value;this.$emit("input",t)}}}},329:function(e,t,i){t=e.exports=i(220)(),t.push([e.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},330:function(e,t,i){var n=i(329);"string"==typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);i(221)("6ea14c28",n,!0)},331:function(e,t,i){i(330);var n=i(0)(i(328),i(332),"data-v-19336c8e",null);e.exports=n.exports},332:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"compute-item"},[i("span",{staticClass:"label"},[e._v(e._s(e.label))]),e._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==e.editType,expression:"editType!=='color'"}],staticClass:"value"},[e.canEdit&&"check"!==e.action&&"approve"!==e.action?"check"!==e.action||"approve"!==e.action?i("input",{ref:"input",attrs:{type:e.editType,placeholder:e.placeholder},domProps:{value:e.value},on:{input:e.input}}):e._e():i("span",[e._v(e._s(e.value))])]),e._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:"color"===e.editType,expression:"editType==='color'"}],staticClass:"value"},[i("span",{staticClass:"color",style:{background:e.color}})]),e._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:e.hasArrow,expression:"hasArrow"}],staticClass:"note"},[i("span",{directives:[{name:"show",rawName:"v-show",value:e.hasArrow&&e.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:e.noteClick}},[e._v(e._s(e.arrowText))]),e._v(" "),i("i",{directives:[{name:"show",rawName:"v-show",value:e.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),e._v(" "),i("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},356:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={components:{},name:"timePicker",data:function(){return{currIndex:0,scrollTop:"",interval:"",width:"100%",result:[],selectList:[[],[]]}},computed:{},props:{showPicker:{type:Boolean,default:!1,required:!0},pickerType:{type:String,default:"time",required:!0},pickerValue:{},pickerList:{}},methods:{ok:function(){switch(this.pickerType){case"time":this.$emit("chosen",this.result[0]+":"+this.result[1]);break;case"shift":case"radio":this.$emit("chosen",this.result[0])}this.$emit("hideMask")},cancel:function(){this.$emit("hideMask")},touchend:function(){var e=this;e.scrollTop=event.currentTarget.scrollTop;var t=event.currentTarget;clearInterval(e.interval),e.interval=null,e.interval=setInterval(function(){e.scroll(t)},100)},scroll:function(e){if(e.scrollTop==this.scrollTop){clearInterval(this.interval),this.interval=null;var t=Math.round(e.scrollTop/32);e.scrollTop=32*t;var i="";i="radio"===this.pickerType?this.pickerList[t]:e.firstChild.children[t+2].innerText,console.log("取值"+t);for(var n=e.parentNode.children,a=this,o=0;o<n.length;o++)e===n[o]&&(a.result[o]=i)}else this.scrollTop=e.scrollTop},isscroll:function(){for(var e=Math.round(event.currentTarget.scrollTop/32),t=event.currentTarget.firstChild.children,i=0;i<t.length;i++)t[i].classList.contains("active")&&t[i].classList.remove("active"),t[i].classList.contains("activePrev")&&t[i].classList.remove("activePrev");t[e+1].classList.add("activePrev"),t[e+2].classList.add("active")},refresh:function(){var e=this;switch(this.pickerType){case"time":for(var t=0;t<24;t++)this.selectList[0][t]=t<10?"0"+t:t;for(var t=0;t<60;t++)this.selectList[1][t]=t<10?"0"+t:t;var i=document.querySelectorAll(".slideList");if(""!==this.pickerValue&&"请选择时间"!==this.pickerValue){var n=Number(this.pickerValue.split(":")[0]),a=Number(this.pickerValue.split(":")[1]);n<10&&(n="0"+n),a<10&&(a="0"+a),this.result[0]=n,this.result[1]=a,setTimeout(function(){i[0].scrollTop=32*n,i[1].scrollTop=32*a},100)}else this.result[0]="12",this.result[1]="00",setTimeout(function(){i[0].scrollTop=384,i[1].scrollTop=0},100);break;case"shift":this.selectList=[[]],this.pickerList.forEach(function(t,i){e.selectList[0].push(t.shift_name+"(工作时长:"+Math.floor(t.day_mins/60)+":"+t.day_mins%60+")")});var o=document.querySelectorAll(".slideList");if(""!==this.pickerValue&&"请选择班次"!==this.pickerValue){var s=this.pickerValue;this.result[0]=s;var r=0;this.pickerList.forEach(function(e,t){e.shift_name==s&&(r=t)}),setTimeout(function(){o[0].scrollTop=32*r},100)}else this.result[0]=this.selectList[0][0],setTimeout(function(){o[0].scrollTop=0},100);break;case"radio":this.selectList=[[]],this.pickerList.forEach(function(t,i){e.selectList[0].push(t.val)});var c=document.querySelectorAll(".slideList");if(""!==this.pickerValue.id){var p=this.pickerValue;this.result[0]=p;var l=0;this.pickerList.forEach(function(e,t){console.log(e.id+":"+p.id+"index:"+t),e.id===p.id&&(l=t)}),setTimeout(function(){c[0].scrollTop=32*l},100)}else this.result[0]=this.selectList[0][0],setTimeout(function(){c[0].scrollTop=0},100)}this.width=100/this.selectList.length+"%"}},mounted:function(){},watch:{showPicker:function(e,t){clearInterval(this.interval),this.interval=null,e&&(this.filterStr="",this.refresh())}}}},360:function(e,t,i){t=e.exports=i(220)(),t.push([e.i,"\n.mask[data-v-58a8a9ae] {\n  position: absolute;\n  width: 100%;\n  height: 100%;\n  top: 0;\n  left: 0;\n  z-index: 16;\n  background-color: #2e2e2d;\n  opacity: .4;\n}\n.pickerBox[data-v-58a8a9ae] {\n  width: 100%;\n  height: 205px;\n  background: #f5f5f5;\n  z-index: 100;\n  position: absolute;\n  bottom: -205px;\n  left: 0;\n  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);\n  transition: all .5s;\n}\n.pickerBox.active[data-v-58a8a9ae] {\n    bottom: 0px;\n}\n.pickerBox .pkHeader[data-v-58a8a9ae] {\n    width: 100%;\n    height: 45px;\n    background: #fff;\n    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);\n}\n.pickerBox .pkHeader span[data-v-58a8a9ae] {\n      width: 50px;\n      height: 45px;\n      text-align: center;\n      float: left;\n      line-height: 45px;\n}\n.pickerBox .pkHeader span.right[data-v-58a8a9ae] {\n        float: right;\n        color: #4c0;\n}\n.pickerBox .pkBody[data-v-58a8a9ae] {\n    width: 100%;\n    height: 160px;\n}\n.pickerBox .pkBody .slideList[data-v-58a8a9ae] {\n      width: 100%;\n      height: 100%;\n      float: left;\n      overflow-y: scroll;\n      -webkit-overflow-scrolling: touch;\n}\n.pickerBox .pkBody .slideList[data-v-58a8a9ae]::-webkit-scrollbar {\n        width: 0;\n        height: 0;\n}\n.pickerBox .pkBody .slideList div[data-v-58a8a9ae] {\n        width: 100%;\n}\n.pickerBox .pkBody .slideList div p[data-v-58a8a9ae] {\n          height: 32px;\n          line-height: 32px;\n          font-size: 17px;\n          text-align: center;\n          margin: 0;\n          color: #000;\n          opacity: 0.1;\n}\n.pickerBox .pkBody .slideList div p.active[data-v-58a8a9ae] {\n            opacity: 1;\n}\n.pickerBox .pkBody .slideList div p.active[data-v-58a8a9ae] {\n            opacity: 1;\n}\n.pickerBox .pkBody .slideList div p.activePrev[data-v-58a8a9ae] {\n            opacity: .4;\n}\n.pickerBox .pkBody .slideList div p.active + p[data-v-58a8a9ae] {\n            opacity: 0.4;\n}\n.pickerBox .pkBody .slideList div p.active + p + p[data-v-58a8a9ae] {\n            opacity: 0.1;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/components/timePicker.vue"],names:[],mappings:";AACA;EACE,mBAAmB;EACnB,YAAY;EACZ,aAAa;EACb,OAAO;EACP,QAAQ;EACR,YAAY;EACZ,0BAA0B;EAC1B,YAAY;CACb;AACD;EACE,YAAY;EACZ,cAAc;EACd,oBAAoB;EACpB,aAAa;EACb,mBAAmB;EACnB,eAAe;EACf,QAAQ;EACR,uCAAuC;EACvC,oBAAoB;CACrB;AACD;IACI,YAAY;CACf;AACD;IACI,YAAY;IACZ,aAAa;IACb,iBAAiB;IACjB,uCAAuC;CAC1C;AACD;MACM,YAAY;MACZ,aAAa;MACb,mBAAmB;MACnB,YAAY;MACZ,kBAAkB;CACvB;AACD;QACQ,aAAa;QACb,YAAY;CACnB;AACD;IACI,YAAY;IACZ,cAAc;CACjB;AACD;MACM,YAAY;MACZ,aAAa;MACb,YAAY;MACZ,mBAAmB;MACnB,kCAAkC;CACvC;AACD;QACQ,SAAS;QACT,UAAU;CACjB;AACD;QACQ,YAAY;CACnB;AACD;UACU,aAAa;UACb,kBAAkB;UAClB,gBAAgB;UAChB,mBAAmB;UACnB,UAAU;UACV,YAAY;UACZ,aAAa;CACtB;AACD;YACY,WAAW;CACtB;AACD;YACY,WAAW;CACtB;AACD;YACY,YAAY;CACvB;AACD;YACY,aAAa;CACxB;AACD;YACY,aAAa;CACxB",file:"timePicker.vue",sourcesContent:["\n.mask[data-v-58a8a9ae] {\n  position: absolute;\n  width: 100%;\n  height: 100%;\n  top: 0;\n  left: 0;\n  z-index: 16;\n  background-color: #2e2e2d;\n  opacity: .4;\n}\n.pickerBox[data-v-58a8a9ae] {\n  width: 100%;\n  height: 205px;\n  background: #f5f5f5;\n  z-index: 100;\n  position: absolute;\n  bottom: -205px;\n  left: 0;\n  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);\n  transition: all .5s;\n}\n.pickerBox.active[data-v-58a8a9ae] {\n    bottom: 0px;\n}\n.pickerBox .pkHeader[data-v-58a8a9ae] {\n    width: 100%;\n    height: 45px;\n    background: #fff;\n    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);\n}\n.pickerBox .pkHeader span[data-v-58a8a9ae] {\n      width: 50px;\n      height: 45px;\n      text-align: center;\n      float: left;\n      line-height: 45px;\n}\n.pickerBox .pkHeader span.right[data-v-58a8a9ae] {\n        float: right;\n        color: #4c0;\n}\n.pickerBox .pkBody[data-v-58a8a9ae] {\n    width: 100%;\n    height: 160px;\n}\n.pickerBox .pkBody .slideList[data-v-58a8a9ae] {\n      width: 100%;\n      height: 100%;\n      float: left;\n      overflow-y: scroll;\n      -webkit-overflow-scrolling: touch;\n}\n.pickerBox .pkBody .slideList[data-v-58a8a9ae]::-webkit-scrollbar {\n        width: 0;\n        height: 0;\n}\n.pickerBox .pkBody .slideList div[data-v-58a8a9ae] {\n        width: 100%;\n}\n.pickerBox .pkBody .slideList div p[data-v-58a8a9ae] {\n          height: 32px;\n          line-height: 32px;\n          font-size: 17px;\n          text-align: center;\n          margin: 0;\n          color: #000;\n          opacity: 0.1;\n}\n.pickerBox .pkBody .slideList div p.active[data-v-58a8a9ae] {\n            opacity: 1;\n}\n.pickerBox .pkBody .slideList div p.active[data-v-58a8a9ae] {\n            opacity: 1;\n}\n.pickerBox .pkBody .slideList div p.activePrev[data-v-58a8a9ae] {\n            opacity: .4;\n}\n.pickerBox .pkBody .slideList div p.active + p[data-v-58a8a9ae] {\n            opacity: 0.4;\n}\n.pickerBox .pkBody .slideList div p.active + p + p[data-v-58a8a9ae] {\n            opacity: 0.1;\n}\n"],sourceRoot:""}])},361:function(e,t,i){var n=i(360);"string"==typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);i(221)("18b13ac2",n,!0)},363:function(e,t,i){i(361);var n=i(0)(i(356),i(364),"data-v-58a8a9ae",null);e.exports=n.exports},364:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("section",[i("div",{directives:[{name:"show",rawName:"v-show",value:e.showPicker,expression:"showPicker"}],staticClass:"mask",on:{click:function(t){e.$emit("hideMask")}}}),e._v(" "),i("div",{staticClass:"pickerBox",class:e.showPicker?"active":""},[i("div",{staticClass:"pkHeader"},[i("span",{on:{click:e.cancel}},[e._v("取消")]),e._v(" "),i("span",{staticClass:"right",on:{click:e.ok}},[e._v("确认")])]),e._v(" "),i("div",{staticClass:"pkBody"},e._l(e.selectList,function(t,n){return i("div",{staticClass:"slideList",style:{width:e.width},on:{touchend:e.touchend,scroll:e.isscroll}},[i("div",[i("p"),e._v(" "),i("p"),e._v(" "),e._l(t,function(t,n){return i("p",{staticClass:"active"},[e._v(e._s(t))])}),e._v(" "),i("p"),e._v(" "),i("p")],2)])}))])])},staticRenderFns:[]}},436:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=i(485),a=i(363),o=i.n(a),s=i(331),r=i.n(s);t.default={name:"equipment",components:{timePicker:o.a,CstItem:r.a},data:function(){return{action:"action",currSelect:"",showPicker:!1,pickerType:"",pickerValue:"",pickerList:"",num:10,dep_id:"",dep_name:"",upperDepName:"",depList:[{val:"黄杰",id:1},{val:"向辉",id:2},{val:"刘镜宏",id:3},{val:"林家纬",id:4},{val:"黄杰",id:5}],depType:[{val:"人员",id:"1"},{val:"设备",id:"2"}],departInfo:{dep_name:"CNC",dep_admin:"黄杰",dep_admin_id:1,parent_dep_name:"伟博思科技有限公司生产部",dep_type:"人工",dep_type_id:"1"}}},computed:{},activated:function(){},mounted:function(){var e=this.$route.query;this.dep_id=e.dep_id,this.getPgDetail()},methods:{selectAdmin:function(){this.currSelect="Admin",this.showPicker=!0,this.pickerType="radio",this.pickerValue={val:this.departInfo.dep_admin,id:this.departInfo.dep_admin_id},this.pickerList=this.depList},selectDepType:function(){this.currSelect="DepType",this.showPicker=!0,this.pickerType="radio",this.pickerValue={val:this.departInfo.dep_type,id:this.departInfo.dep_type_id},this.pickerList=this.depType},chosen:function(e){"Admin"===this.currSelect?(this.departInfo.dep_admin=e.val,this.departInfo.dep_admin_id=e.id,this.currSelect=""):(this.currSelect="DepType")&&(this.departInfo.dep_type=e.val,this.departInfo.dep_type_id=e.id,this.currSelect="")},getPgDetail:function(){var e={query:{pg_id:this.dep_id}};i.i(n.a)(e).then(function(e){}).catch(function(e){$toast.show(e.description)})}}}},485:function(e,t,i){"use strict";function n(e){return i.i(a.a)({url:"/api/pm/pg/detail",method:"post",data:e})}t.a=n;var a=i(7)},571:function(e,t,i){t=e.exports=i(220)(),t.push([e.i,"","",{version:3,sources:[],names:[],mappings:"",file:"index.vue",sourceRoot:""}])},672:function(e,t,i){var n=i(571);"string"==typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);i(221)("366269c5",n,!0)},782:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page storage"},[i("div",{staticClass:"mw-page-header"},[i("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(t){e.$router.go(-1)}}}),e._v(" "),i("span",[e._v("CNC")]),e._v(" "),i("i",{staticClass:"right"})]),e._v(" "),i("div",{staticClass:"mw-page-content "},[i("cst-item",{attrs:{label:"部门名称",action:e.action},model:{value:e.departInfo.dep_name,callback:function(t){e.$set(e.departInfo,"dep_name",t)},expression:"departInfo.dep_name"}}),e._v(" "),i("cst-item",{attrs:{label:"部门主管",action:"check",canEdit:!1,hasArrow:"action"===e.action},nativeOn:{click:function(t){e.selectAdmin(t)}},model:{value:e.departInfo.dep_admin,callback:function(t){e.$set(e.departInfo,"dep_admin",t)},expression:"departInfo.dep_admin"}}),e._v(" "),i("cst-item",{attrs:{label:"上级部门",action:e.action},model:{value:e.departInfo.parent_dep_name,callback:function(t){e.$set(e.departInfo,"parent_dep_name",t)},expression:"departInfo.parent_dep_name"}}),e._v(" "),i("cst-item",{attrs:{label:"部门类型",action:"check",canEdit:!1,hasArrow:"action"===e.action},nativeOn:{click:function(t){e.selectDepType(t)}},model:{value:e.departInfo.dep_type,callback:function(t){e.$set(e.departInfo,"dep_type",t)},expression:"departInfo.dep_type"}}),e._v(" "),i("cst-item",{attrs:{label:"设备列表",action:"check",hasArrow:!0},nativeOn:{click:function(t){e.$router.forward("productionDeptSub/equipmentList")}}}),e._v(" "),i("cst-item",{attrs:{label:"设备点检分配",action:"check",hasArrow:!0},nativeOn:{click:function(t){e.$router.forward("productionDeptSub/checklist")}},model:{value:e.num,callback:function(t){e.num=t},expression:"num"}}),e._v(" "),i("cst-item",{attrs:{label:"部门日历设置",action:"check",hasArrow:!0}})],1),e._v(" "),i("time-picker",{attrs:{showPicker:e.showPicker,pickerType:e.pickerType,pickerValue:e.pickerValue,pickerList:e.pickerList},on:{chosen:e.chosen,hideMask:function(t){e.showPicker=!1}}})],1)},staticRenderFns:[]}}});
//# sourceMappingURL=15.e2b8539c7727410802e2.js.map