webpackJsonp([93],{241:function(e,t,i){i(597);var n=i(0)(i(398),i(704),"data-v-0e9269f8",null);e.exports=n.exports},398:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=i(8),a=i.n(n),s=i(4),o=i(80),c=i(83),p=i.n(c),r=i(86),d=i.n(r);t.default={name:"subDeptDetail",data:function(){return{dep_id:"",dep_name:"",upperDepName:"",showPopup:!1,addDepFlag:!1,depList:[],members:[],newDepName:"",action:"check"}},components:{MwPrompt:d.a,invite:p.a},computed:i.i(s.a)(["chosenCompany","userinfo"]),activated:function(){var e=this.$route.query;console.log(e),this.dep_id=e.dep_id,this.dep_name=e.dep_name,this.upperDepName=e.upperDepName,this.getDepts()},methods:{turnCheck:function(){this.showPopup=!1,this.addDepFlag=!1,this.action="check"},getDepts:function(){var e=this;if(e.chosenCompany.company_id){var t={company_id:e.chosenCompany.company_id,dep_id:e.dep_id};i.i(o.h)(t).then(function(t){console.log(t),e.depList=t.deps,e.members=t.memberInfos}).catch(function(e){console.log(e),$toast.show(e.description)})}},showEditAction:function(){var e=this,t=this,i=new Object;i["批量移动员工"]=function(){if(0===t.members.length)return void $toast.show("当前部门无可移动员工");t.$router.forward({path:"/company/batchSelect",query:{members:a()(t.members),type:"move",dep_id:e.dep_id}})},i["批量删除员工"]=function(){if(0===t.members.length)return void $toast.show("当前部门无可删除员工");t.$router.forward({path:"/company/batchSelect",query:{members:a()(t.members),type:"del",dep_id:e.dep_id}})},$actionSheet.show({buttons:i,title:"批量操作",cancelText:t.$t("cancel")})},addNewDep:function(){var e=this;if(""===this.newDepName.trim())return void $toast.show("部门名称不能为空");var t={company_id:e.chosenCompany.company_id,dep_name:e.newDepName,upper_id:e.dep_id?e.dep_id:"undefined"};i.i(o.i)(t).then(function(t){e.getDepts()}).catch(function(e){$toast.show(e.description)})}}}},496:function(e,t,i){t=e.exports=i(220)(),t.push([e.i,"\n.item-img[data-v-0e9269f8] {\n  width: 30px;\n  height: 30px;\n  float: left;\n}\n.text-btn[data-v-0e9269f8] {\n  border: 1px solid #4a90e2;\n  padding: 3px;\n  border-radius: 2px;\n  margin-right: 10px;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/views/company/subDeptDetail.vue"],names:[],mappings:";AACA;EACE,YAAY;EACZ,aAAa;EACb,YAAY;CACb;AACD;EACE,0BAA0B;EAC1B,aAAa;EACb,mBAAmB;EACnB,mBAAmB;CACpB",file:"subDeptDetail.vue",sourcesContent:["\n.item-img[data-v-0e9269f8] {\n  width: 30px;\n  height: 30px;\n  float: left;\n}\n.text-btn[data-v-0e9269f8] {\n  border: 1px solid #4a90e2;\n  padding: 3px;\n  border-radius: 2px;\n  margin-right: 10px;\n}\n"],sourceRoot:""}])},597:function(e,t,i){var n=i(496);"string"==typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);i(221)("0b045682",n,!0)},704:function(e,t,i){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page company"},[n("div",{staticClass:"mw-page-header"},[n("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(t){e.$router.go(-1)}}}),e._v(" "),n("span",[e._v(e._s(e.dep_name))]),e._v(" "),"check"===e.action?n("i",{staticClass:"right",on:{click:function(t){e.action="edit"}}},[e._v("管理")]):e._e(),e._v(" "),"edit"===e.action?n("i",{staticClass:"right",on:{click:e.turnCheck}},[e._v("取消")]):e._e()]),e._v(" "),n("div",{staticClass:"mw-page-content"},[n("list",[n("item",[n("span",{staticStyle:{opacity:".6"}},[e._v(e._s(e.chosenCompany.company_name))]),e._v(" "),n("i",{staticClass:"icon ion-ios-arrow-right"}),e._v(" "),n("span",{staticStyle:{opacity:".6"}},[e._v(e._s(e.upperDepName))]),e._v(" "),n("i",{staticClass:"icon ion-ios-arrow-right"}),e._v(" "+e._s(e.dep_name)+"\n      ")])],1),e._v(" "),n("list",e._l(e.depList,function(t){return n("item",{key:"item.dep_id",staticClass:"item-icon-right"},[e._v("\n        "+e._s(t.dep_name)+"\n        ")])})),e._v(" "),n("list",[n("div",{staticClass:"item item-divider-ios"},[e._v("\n        成员\n      ")]),e._v(" "),e._l(e.members,function(t){return n("item",{key:"item.members",staticClass:"item-icon-right item-contact",nativeOn:{click:function(i){e.$router.forward("/company/memberDetail/"+t.member_id)}}},[n("img",{staticClass:"item-img",attrs:{src:t.icon?t.icon:"static/images/company/contact2.png"}}),e._v(" "),n("span",{staticStyle:{"line-height":"30px","margin-left":"15px"}},[e._v(e._s(t.member_name))]),t.member_id===e.userinfo.uid?n("span",{staticClass:"label"},[e._v("我自己")]):e._e()])}),e._v(" "),n("item",{staticClass:"item-icon-right",nativeOn:{click:function(t){e.showPopup=!0}}},[n("img",{staticClass:"item-img",attrs:{src:i(81)}}),e._v(" "),n("span",{staticStyle:{"line-height":"30px","margin-left":"15px"}},[e._v("邀请同事加入")])])],2),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:e.showPopup,expression:"showPopup"}]},[n("invite",{attrs:{dep_id:e.dep_id,dep_name:e.dep_name,type:"2"},on:{hideInvite:function(t){e.showPopup=!1}}})],1)],1),e._v(" "),n("mw-prompt",{attrs:{show:e.addDepFlag,okFunction:e.addNewDep,placeholder:"请输入部门名称"},on:{hidePop:function(t){e.addDepFlag=!1}},model:{value:e.newDepName,callback:function(t){e.newDepName=t},expression:"newDepName"}}),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:"edit"===e.action,expression:"action==='edit'"}],staticClass:"mw-page-footer"},[n("div",{staticClass:"btns"},[n("div",{staticClass:"btn",on:{click:function(t){e.addDepFlag=!0}}},[e._v("添加子部门")]),e._v(" "),n("div",{staticClass:"divider"}),e._v(" "),n("div",{staticClass:"btn",on:{click:function(t){e.$router.forward("/company/deptSetting/"+e.dep_id)}}},[e._v("部门设置")]),e._v(" "),n("div",{staticClass:"divider"}),e._v(" "),n("div",{staticClass:"btn",on:{click:e.showEditAction}},[e._v("批量操作")])])])],1)},staticRenderFns:[]}}});
//# sourceMappingURL=93.5cbe46518b8242c4ec2f.js.map