webpackJsonp([72],{254:function(t,e,r){r(738);var o=r(0)(r(452),r(883),"data-v-286f999e",null);t.exports=o.exports},370:function(t,e,r){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(t){var e=t.target.value;this.$emit("input",e)}}}},371:function(t,e,r){e=t.exports=r(225)(),e.push([t.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},372:function(t,e,r){var o=r(371);"string"==typeof o&&(o=[[t.i,o,""]]),o.locals&&(t.exports=o.locals);r(226)("6ea14c28",o,!0)},373:function(t,e,r){r(372);var o=r(0)(r(370),r(374),"data-v-19336c8e",null);t.exports=o.exports},374:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"compute-item"},[r("span",{staticClass:"label"},[t._v(t._s(t.label))]),t._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==t.editType,expression:"editType!=='color'"}],staticClass:"value"},[t.canEdit&&"check"!==t.action&&"approve"!==t.action&&"checkOnly"!==t.action?r("input",{ref:"input",attrs:{type:t.editType,placeholder:t.placeholder},domProps:{value:t.value},on:{input:t.input}}):r("span",[t._v(t._s(t.value))])]),t._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:"color"===t.editType,expression:"editType==='color'"}],staticClass:"value"},[r("span",{staticClass:"color",style:{background:t.color}})]),t._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"note"},[r("span",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow&&t.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:t.noteClick}},[t._v(t._s(t.arrowText))]),t._v(" "),r("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),t._v(" "),r("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},452:function(t,e,r){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=r(89),s=r(83),a=r(373),n=r.n(a);e.default={name:"manageStart",components:{CstItem:n.a},data:function(){return{order_line_id:"",order_startup:{sup_type:1,up_not_tax:"",order_line_id:"",process_cost:"",process_ratio:"",process_ratio_percent:"",design_cost:"",design_ratio:"",design_ratio_percent:"",is_batch_process:1,is_crt_bom:0,stuff_cost:"",stuff_ratio:"",stuff_ratio_percent:"",count:"",cst_mold_no:"",price:0}}},computed:{up_not_tax:function(){return 1==this.order_startup.is_batch_process?this.order_startup.up_not_tax=this.order_startup.count*this.order_startup.price:this.order_startup.up_not_tax=this.order_startup.price,this.order_startup.up_not_tax}},methods:{startup:function(){var t=this;if(Number(this.order_startup.design_ratio_percent)+Number(this.order_startup.stuff_ratio_percent)+Number(this.order_startup.process_ratio_percent)>100)$dialog.confirm({theme:"ios",title:"比例已超出100%,确认下达?",cancelText:"取消",okText:"确定"}).then(function(e){if(console.log("confirm result: ",e),e){var s={order_startup:t.order_startup};r.i(o.a)(s).then(function(e){t.$router.go(-1)}).catch(function(t){$toast.show(t.description)})}});else{var e={order_startup:this.order_startup};r.i(o.a)(e).then(function(e){t.$router.go(-1)}).catch(function(t){$toast.show(t.description)})}},checkPercent:function(t){switch(t){case"design_ratio":this.order_startup.design_ratio=this.order_startup.design_ratio_percent/100,this.order_startup.design_cost=Number(this.order_startup.up_not_tax*this.order_startup.design_ratio).toFixed(2);break;case"process_ratio":this.order_startup.process_ratio=this.order_startup.process_ratio_percent/100,this.order_startup.process_cost=Number(this.order_startup.up_not_tax*this.order_startup.process_ratio).toFixed(2);break;case"stuff_ratio":this.order_startup.stuff_ratio=this.order_startup.stuff_ratio_percent/100,this.order_startup.stuff_cost=Number(this.order_startup.up_not_tax*this.order_startup.stuff_ratio).toFixed(2)}},checkValue:function(t){switch(t){case"design_cost":this.order_startup.design_ratio=this.order_startup.design_cost/this.order_startup.up_not_tax,console.log(this.order_startup.design_ratio),this.order_startup.design_ratio_percent=Number(100*this.order_startup.design_ratio).toFixed(2);break;case"process_cost":this.order_startup.process_ratio=this.order_startup.process_cost/this.order_startup.up_not_tax,console.log(this.order_startup.design_ratio),this.order_startup.process_ratio_percent=Number(100*this.order_startup.process_ratio).toFixed(2);break;case"stuff_cost":this.order_startup.stuff_ratio=this.order_startup.stuff_cost/this.order_startup.up_not_tax,console.log(this.order_startup.stuff_ratio),this.order_startup.stuff_ratio_percent=Number(100*this.order_startup.stuff_ratio).toFixed(2)}},resetPrice:function(t){var e=parseFloat(this.order_startup.design_ratio_percent),r=parseFloat(this.order_startup.process_ratio_percent),o=parseFloat(this.order_startup.stuff_ratio_percent);switch(console.log(e+"-"+o+"-"+r),t){case"design_ratio":e+o+r>100&&(o=Math.floor((100-e)/2)),r=100-e-o;break;case"stuff_ratio":console.log(o),e+o+r>100&&(r=Math.floor((100-o)/2)),e=100-o-r;break;case"process_ratio":e+o+r>100&&(e=Math.floor((100-r)/2)),o=100-e-r}console.log(e+"-"+o+"-"+r),this.order_startup.design_ratio_percent=Number(e).toFixed(2),this.order_startup.stuff_ratio_percent=Number(o).toFixed(2),this.order_startup.process_ratio_percent=Number(r).toFixed(2),this.order_startup.design_ratio=e/100,this.order_startup.design_cost=Number(this.order_startup.up_not_tax*this.order_startup.design_ratio).toFixed(2),this.order_startup.stuff_ratio=o/100,this.order_startup.stuff_cost=Number(this.order_startup.up_not_tax*this.order_startup.stuff_ratio).toFixed(2),this.order_startup.process_ratio=r/100,this.order_startup.process_cost=Number(this.order_startup.up_not_tax*this.order_startup.process_ratio).toFixed(2)}},mounted:function(){var t=this;this.order_line_id=this.$route.params.order_line_id,this.order_startup.order_line_id=this.$route.params.order_line_id,r.i(s.a)({order_line_id:this.order_line_id}).then(function(e){t.order_startup.price=e.up,t.order_startup.count=e.count,t.order_startup.up_not_tax=e.up*e.count}).catch(function(t){})},watch:{"order_startup.sup_type":function(t,e){console.log(e),1==e&&(this.order_startup.is_crt_bom=0)}}}},599:function(t,e,r){e=t.exports=r(225)(),e.push([t.i,"\n.divider-title[data-v-286f999e] {\n  padding: 15px;\n}\n.input-percent[data-v-286f999e], .input-value[data-v-286f999e] {\n  width: 50px;\n  display: inline;\n  border-bottom: 1px solid;\n  text-align: right;\n}\n.input-value[data-v-286f999e] {\n  width: 40%;\n  text-align: left;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/views/customerOrder/management/start.vue"],names:[],mappings:";AACA;EACE,cAAc;CACf;AACD;EACE,YAAY;EACZ,gBAAgB;EAChB,yBAAyB;EACzB,kBAAkB;CACnB;AACD;EACE,WAAW;EACX,iBAAiB;CAClB",file:"start.vue",sourcesContent:["\n.divider-title[data-v-286f999e] {\n  padding: 15px;\n}\n.input-percent[data-v-286f999e], .input-value[data-v-286f999e] {\n  width: 50px;\n  display: inline;\n  border-bottom: 1px solid;\n  text-align: right;\n}\n.input-value[data-v-286f999e] {\n  width: 40%;\n  text-align: left;\n}\n"],sourceRoot:""}])},738:function(t,e,r){var o=r(599);"string"==typeof o&&(o=[[t.i,o,""]]),o.locals&&(t.exports=o.locals);r(226)("392f1221",o,!0)},883:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page"},[r("div",{staticClass:"mw-page-header"},[r("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),r("span",[t._v("定单下达")]),t._v(" "),r("i",{staticClass:"right"})]),t._v(" "),r("div",{staticClass:"mw-page-content"},[r("item",[r("span",[t._v("供货类型")]),t._v(" "),r("label",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.sup_type,expression:"order_startup.sup_type"}],attrs:{name:"sup_type",type:"radio",value:"1"},domProps:{checked:t._q(t.order_startup.sup_type,"1")},on:{change:function(e){t.$set(t.order_startup,"sup_type","1")}}}),t._v(" 自制\n      ")]),t._v(" "),r("label",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.sup_type,expression:"order_startup.sup_type"}],attrs:{name:"sup_type",type:"radio",value:"2"},domProps:{checked:t._q(t.order_startup.sup_type,"2")},on:{change:function(e){t.$set(t.order_startup,"sup_type","2")}}}),t._v(" 外购\n      ")])]),t._v(" "),r("cst-item",{attrs:{label:"数量",editType:"number",canEdit:!1},model:{value:t.order_startup.count,callback:function(e){t.$set(t.order_startup,"count",e)},expression:"order_startup.count"}}),t._v(" "),r("cst-item",{attrs:{label:"未税单价",editType:"number",canEdit:!1},model:{value:t.up_not_tax,callback:function(e){t.up_not_tax=e},expression:"up_not_tax"}}),t._v(" "),r("item",[r("span",[t._v("是否制定BOM")]),t._v(" "),1==t.order_startup.sup_type?r("label",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.is_crt_bom,expression:"order_startup.is_crt_bom"}],attrs:{name:"is_crt_bom",type:"radio",value:"1"},domProps:{checked:t._q(t.order_startup.is_crt_bom,"1")},on:{change:function(e){t.$set(t.order_startup,"is_crt_bom","1")}}}),t._v(" 是\n      ")]):r("label",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.is_crt_bom,expression:"order_startup.is_crt_bom"}],attrs:{name:"is_crt_bom",type:"radio",value:"1",disabled:"disabled"},domProps:{checked:t._q(t.order_startup.is_crt_bom,"1")},on:{change:function(e){t.$set(t.order_startup,"is_crt_bom","1")}}}),t._v(" 是\n      ")]),t._v(" "),r("label",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.is_crt_bom,expression:"order_startup.is_crt_bom"}],attrs:{name:"is_crt_bom",type:"radio"},domProps:{value:0,checked:t._q(t.order_startup.is_crt_bom,0)},on:{change:function(e){t.$set(t.order_startup,"is_crt_bom",0)}}}),t._v(" 否\n      ")])]),t._v(" "),r("item",[r("span",[t._v("是否批量生产")]),t._v(" "),r("label",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.is_batch_process,expression:"order_startup.is_batch_process"}],attrs:{name:"is_batch_process",type:"radio",value:"1"},domProps:{checked:t._q(t.order_startup.is_batch_process,"1")},on:{change:function(e){t.$set(t.order_startup,"is_batch_process","1")}}}),t._v("是")]),t._v(" "),r("label",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.is_batch_process,expression:"order_startup.is_batch_process"}],attrs:{name:"is_batch_process",type:"radio",value:"0"},domProps:{checked:t._q(t.order_startup.is_batch_process,"0")},on:{change:function(e){t.$set(t.order_startup,"is_batch_process","0")}}}),t._v(" 否\n      ")])]),t._v(" "),t._m(0,!1,!1),t._v(" "),r("item",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.design_ratio_percent,expression:"order_startup.design_ratio_percent"}],staticClass:"input-percent",attrs:{placeholder:"0",type:"number",maxlength:"2"},domProps:{value:t.order_startup.design_ratio_percent},on:{input:[function(e){e.target.composing||t.$set(t.order_startup,"design_ratio_percent",e.target.value)},function(e){t.checkPercent("design_ratio")}]}}),t._v("% 设计费\n      "),r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.design_cost,expression:"order_startup.design_cost"}],staticClass:"input-value",attrs:{placeholder:"0",type:"number"},domProps:{value:t.order_startup.design_cost},on:{input:[function(e){e.target.composing||t.$set(t.order_startup,"design_cost",e.target.value)},function(e){t.checkValue("design_cost")}]}})]),t._v(" "),r("item",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.stuff_ratio_percent,expression:"order_startup.stuff_ratio_percent"}],staticClass:"input-percent",attrs:{placeholder:"0",type:"number"},domProps:{value:t.order_startup.stuff_ratio_percent},on:{input:[function(e){e.target.composing||t.$set(t.order_startup,"stuff_ratio_percent",e.target.value)},function(e){t.checkPercent("stuff_ratio")}]}}),t._v("% 材料费\n      "),r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.stuff_cost,expression:"order_startup.stuff_cost"}],staticClass:"input-value",attrs:{placeholder:"0",type:"number"},domProps:{value:t.order_startup.stuff_cost},on:{input:[function(e){e.target.composing||t.$set(t.order_startup,"stuff_cost",e.target.value)},function(e){t.checkValue("stuff_cost")}]}})]),t._v(" "),r("item",[r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.process_ratio_percent,expression:"order_startup.process_ratio_percent"}],staticClass:"input-percent",attrs:{placeholder:"0",type:"number"},domProps:{value:t.order_startup.process_ratio_percent},on:{input:[function(e){e.target.composing||t.$set(t.order_startup,"process_ratio_percent",e.target.value)},function(e){t.checkPercent("process_ratio")}]}}),t._v("% 加工费\n      "),r("input",{directives:[{name:"model",rawName:"v-model",value:t.order_startup.process_cost,expression:"order_startup.process_cost"}],staticClass:"input-value",attrs:{placeholder:"0",type:"number"},domProps:{value:t.order_startup.process_cost},on:{input:[function(e){e.target.composing||t.$set(t.order_startup,"process_cost",e.target.value)},function(e){t.checkValue("process_cost")}]}})])],1),t._v(" "),r("div",{staticClass:"mw-page-footer"},[r("div",{staticClass:"btns"},[r("div",{staticClass:"btn",on:{click:function(e){e.preventDefault(),t.startup(e)}}},[t._v("\n        确定下达\n      ")])])])])},staticRenderFns:[function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"item-divider"},[r("span",{staticClass:"divider-title"},[t._v("目标成本")])])}]}}});
//# sourceMappingURL=72.357291ca1a2027c35fbd.js.map