webpackJsonp([66],{272:function(e,t,n){n(718);var a=n(0)(n(470),n(863),"data-v-0e0a6c22",null);e.exports=a.exports},370:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});t.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(e){var t=e.target.value;this.$emit("input",t)}}}},371:function(e,t,n){t=e.exports=n(225)(),t.push([e.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},372:function(e,t,n){var a=n(371);"string"==typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);n(226)("6ea14c28",a,!0)},373:function(e,t,n){n(372);var a=n(0)(n(370),n(374),"data-v-19336c8e",null);e.exports=a.exports},374:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"compute-item"},[n("span",{staticClass:"label"},[e._v(e._s(e.label))]),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==e.editType,expression:"editType!=='color'"}],staticClass:"value"},[e.canEdit&&"check"!==e.action&&"approve"!==e.action&&"checkOnly"!==e.action?n("input",{ref:"input",attrs:{type:e.editType,placeholder:e.placeholder},domProps:{value:e.value},on:{input:e.input}}):n("span",[e._v(e._s(e.value))])]),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:"color"===e.editType,expression:"editType==='color'"}],staticClass:"value"},[n("span",{staticClass:"color",style:{background:e.color}})]),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:e.hasArrow,expression:"hasArrow"}],staticClass:"note"},[n("span",{directives:[{name:"show",rawName:"v-show",value:e.hasArrow&&e.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:e.noteClick}},[e._v(e._s(e.arrowText))]),e._v(" "),n("i",{directives:[{name:"show",rawName:"v-show",value:e.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),e._v(" "),n("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},470:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=n(6),o=n.n(a),i=n(373),s=n.n(i),r=n(1);n(80);t.default={name:"quote_line_prods",components:{cstItem:s.a},data:function(){return{cst_prods:[],action:""}},mounted:function(){this.action=this.$route.params.action,"new"!==this.action&&(this.cst_prods=JSON.parse(this.$route.params.cst_prod_list))},methods:{closeMask:function(){this.currItem.swipe=!1,this.currItem={}},delItem:function(e,t){var n=this;$dialog.confirm({theme:"ios",title:"确定要删除"+e.cst_prod_name+"吗?",cancelText:"取消",okText:"确定"}).then(function(e){console.log("confirm result: ",e),e&&n.delProd(t)})},onItemClick:function(e){if(this.currItem.swipe)return void(this.currItem.swipe=!1);$router.forward("/customerOrder/compute/cst_prod_detail/"+this.action+"/"+o()(e))},onSwipeLeft:function(e){this.$set(e,"swipe",!0),this.currItem=e},addOneProd:function(){var e={cst_prod_name:"",cavity_total:""};this.cst_prods.push(e)},delProd:function(e){console.log(e);for(var t in this.cst_prods)console.log(t),t==e&&this.cst_prods.splice(e,1)},saveData:function(){r.a.set("prods",o()(this.cst_prods)),history.go(-1)}}}},579:function(e,t,n){t=e.exports=n(225)(),t.push([e.i,"\n.prd-header span[data-v-0e0a6c22] {\n  color: #416eb6;\n  transform: scale(0.8, 8);\n  -webkit-transform: scale(0.8, 0.8);\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/views/customerOrder/quotes/quotes/quote_line_prods.vue"],names:[],mappings:";AACA;EACE,eAAe;EACf,yBAAyB;EACzB,mCAAmC;CACpC",file:"quote_line_prods.vue",sourcesContent:["\n.prd-header span[data-v-0e0a6c22] {\n  color: #416eb6;\n  transform: scale(0.8, 8);\n  -webkit-transform: scale(0.8, 0.8);\n}\n"],sourceRoot:""}])},718:function(e,t,n){var a=n(579);"string"==typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);n(226)("fa17a290",a,!0)},863:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"page has-navbar"},[n("div",{staticClass:"navbar top"},[n("von-header",{staticStyle:{position:"fixed",visibility:"visible"}},[n("button",{staticClass:"button button-icon ion-ios-arrow-back",attrs:{slot:"left"},on:{click:function(t){e.$router.go(-1)}},slot:"left"}),e._v(" "),n("span",{attrs:{slot:"title"},slot:"title"},[e._v("制品列表")])])],1),e._v(" "),n("div",{staticClass:"page-content"},[n("list",[e._l(e.cst_prods,function(t,a){return n("div",[n("div",{staticClass:"item-divider prd-header"},[n("span",{staticStyle:{"padding-left":"30px",float:"left"}},[e._v("制品"+e._s(a+1))]),e._v(" "),n("span",{staticStyle:{"padding-right":"30px",float:"right"},on:{click:function(n){e.delItem(t,a)}}},[e._v("删除")])]),e._v(" "),n("cst-item",{attrs:{label:"名称",action:e.action,placeholder:"名称"},model:{value:t.cst_prod_name,callback:function(n){e.$set(t,"cst_prod_name",n)},expression:"item.cst_prod_name"}}),e._v(" "),n("cst-item",{attrs:{label:"腔数",action:e.action,placeholder:"0"},model:{value:t.cavity_total,callback:function(n){e.$set(t,"cavity_total",n)},expression:"item.cavity_total"}})],1)}),e._v(" "),n("button",{directives:[{name:"show",rawName:"v-show",value:"check"!=e.action,expression:"action!='check'"}],staticClass:"button button-calm button-block button-outline",on:{click:e.addOneProd}},[e._v("添加制品信息")])],2)],1),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:"check"!=e.action,expression:"action!='check'"}],staticClass:"btns"},[n("div",{staticClass:"btn",on:{click:e.saveData}},[e._v("\n      保存\n    ")])])])},staticRenderFns:[]}}});
//# sourceMappingURL=66.72b85829e1b2ad2c59b9.js.map