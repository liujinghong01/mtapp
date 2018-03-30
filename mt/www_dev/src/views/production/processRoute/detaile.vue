<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="goBack"></i>
      <span>工艺路线</span>
      <i class="right" @click="selectProc">参考工艺</i>
    </div>
    <div class="mw-page-content ">
      <div class="content">
        <cst-item  label="物料名称" v-model="prod_info.mat_name"  action="check" ></cst-item>
        <cst-item  label="物料编号" v-model="prod_info.mat_no"  action="check" ></cst-item>
        <cst-item  label="规格型号" v-model="prod_info.mat_model"  action="check" ></cst-item>
        <cst-item  label="所属模号" v-model="prod_info.mold_no"  action="check" ></cst-item>
        <cst-item  label="试模日期" v-model="prod_info.first_try_date"  action="check" ></cst-item>
        <cst-item  label="材质" v-model="prod_info.rawnature_name"  action="check" ></cst-item>
        <cst-item  label="未处理量" v-model="prod_info.undeal_qty"  action="check" ></cst-item>
        <cst-item  label="当前排产数量" v-model="prod_info.prod_qty"  action="action" ></cst-item>

        <div class="proc_list" v-show="prod_info.prod_detail_list.length>0">
          <div>
            <div class="item-divider" style="padding-left: 15px;">工序列表</div>
          </div>
          <!--<swipe-item v-for="(item,index) in prod_info.prod_detail_list" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="procDelete(item,index)">-->
            <!--<p style="margin-bottom: 5px">工序名称：{{ item.proc_name }}</p>-->
            <!--<p>工序类型：{{ item.proc_type==1?'生产':'外协' }}</p>-->
          <!--</swipe-item>-->
          <transition-group name="flip-list" tag="div">
            <div @click="onItemClick(item)"  v-bind:key="item.proc_seq" class="drag" :class="item.drag===true?'active':''" v-for="(item,index) in prod_info.prod_detail_list" @touchstart="touchstart($event,item,index)" @touchmove="touchmove($event,item)" @touchend="touchend($event,item,index)" >
              <p >工序名称：{{ item.proc_name }}</p>
              <p>工序类型：{{ item.proc_type==1?'生产':'外协' }}</p>
              <i v-show="action!=='check'" @click="procDelete(item,index)" class="ion-ios-close"></i>
            </div>
          </transition-group>
        </div>

        <p  @click="$router.forward({path:'addProcedure',query:{action:'new'}})" style="margin-top:10px;text-align: center;color: #416eb6;border-bottom: 1px solid #416eb6;border-top: 1px solid #416eb6;line-height: 40px;background:#fff">+添加工序</p>
      </div>

    </div>
    <div class="mw-page-footer" >
      <div class="btns">
        <div  class="btn" @click="prodOrderModify()">
          保存
        </div>
        <span  v-show="prod_info.po_status==0">|</span>
        <div v-show="prod_info.po_status==0" class="btn" @click="prodOrderModify(1)">
          制定完毕
        </div>
        <span  v-show="Number(prod_info.po_status)===1">|</span>
        <div v-show="Number(prod_info.po_status)===1" class="btn"  @click="submit()">
          提交排产
        </div>
      </div>
    </div>

    <div class="select-box" :class="selectInfo.switch?'active':''">
      <div class="select-click" @click="selectInfo.switch=false"></div>
      <div class="select-main">
        <div class="main-header" style="height: 40px">
          <p >请选择工艺模板</p>
        </div>
        <div class="main-list" style="height: 100%">
          <scroll  :on-infinite="onInfinite"  style="-webkit-overflow-scrolling: touch;top: 40px;">
            <p style="margin: 0" v-for="(item,index) in selectInfo.list" @click="itemClick(item)" >{{ item.tmpl_name }}</p>
            <div v-if="this.curr_page >= this.total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
      </div>
    </div>

    <div v-show="drag" class="model" style="width: 96%;height: 40px;background: #F5F5F5;opacity:.9;position: fixed;left: 2%;padding-left: 5px;box-sizing: border-box" :style="{top:this.top+'px'}">
      <p style="margin: 0;">工序名称：{{ currItem.proc_name }}</p>
      <p style="margin: 0">工序类型：{{ currItem.proc_type==1?'生产':'外协' }}</p>
    </div>
  </div>
</template>

<script>
  import { prod_req_info,tmplList,tmplDetail,prodOrderModify,prodOrderDetail } from '@/api/production/processRoute'
  import SwipeItem from "../../../components/swipeItem"
  import CstItem from "../../../components/cstItem"
  import sess from '@/utils/sess'
  export default{
    name:'processRouteDetaile',
    components: {SwipeItem,CstItem},
    data(){
      return {
        action:'',
        mat_no:'',
        prod_id:'',
        isEdit:false,
        selectInfo:{
          switch:false,
          list:[]
        },
        curr_page:0,
        total_page:'',
        times:'',
        dragx:'',
        dragy:'',
        dragX:0,
        dragY:0,
        top:0,
        topList:[],
        drag:false,
        dragTime:'',
        Timeout:'',
        currItem:'',
        prod_info:{//prod_info.prod_detail_list
          req_id:'',
          bom_qty:'',//	BOM输出数量	number
          bom_type:'',//	Bom类型	string	(1主BOM,2 电极BOM）@mock=测试内容94rk
          prod_qty:'',//	确认数量（完工确认数量）
          delivery_date:'',//	交货日期	string
          mat_id:'',//	物料Id	number	@mock=88080
          mat_name:'',//	物料名称	string
          mat_no:'',//	物料编码	string	@mock=测试内容q6kp
          mat_type_id:'',//	物料类型Id	number	@mock=73237
          mat_type_name:'',//	物料类型名称	string	@mock=
          mold_no:'',//	模号	string	@mock=测试内容55e4
          node_pos:'',//	物料在BOM中唯一位置	number	@mock=55862
          plan_end_time:'',//	计划结束时间
          plan_start_time:'',//	计划开始时间
          pri:'',//	定单优先级 (1.正常,2.急,3.加急)
          po_status:'',
          prod_detail_list:[]
        },



      }
    },
    computed:{

    },
    activated(){
      this.getProcInfo()
    },
    mounted(){
      const param=this.$route.query
      this.action=param.action
      if(this.action==='new'){
        this.mat_no=param.mat_no
        this.prod_info.req_id=param.req_id
        this.getDemand()
      }else if(this.action!=='new'){
        this.prod_id=param.prod_id
        this.prodOrderDetail()
      }
      this.tmplList()
    },
    methods: {
      selectProc(){
        this.selectInfo.switch=true
      },
      //返回上一页面
      goBack(){
        if(this.isEdit){
          $dialog.confirm({
            theme: 'ios',
            title: '是否放弃修改并返回？',
            cancelText: '取消',
            okText: '确定'
          }).then(res=>{
            if(res){
              $router.go(-1)
            }
          })
        }else{
          $router.go(-1)
        }

      },
      //参考工艺模板
      itemClick(item){
        if(this.prod_info.prod_detail_list.length>0){
          $dialog.confirm({
            theme: 'ios',
            title: '确定要覆盖之前的工艺路线么？',
            cancelText: '取消',
            okText: '确定'
          }).then(res=>{
            if(res){
              this.tmplDetail(item)
            }
          })
        }else{
          this.tmplDetail(item)
        }


      },
      //未创建工艺路线需求
      getDemand(){
        const  requestData={
          query:{
            req_id:this.prod_info.req_id
          }
        }
        prod_req_info(requestData).then(res => {
          this.prod_info.bom_qty=res.node_req_info.req_qty
          this.prod_info.mat_id=res.node_req_info.mat_id
          this.prod_info.mat_model=res.node_req_info.mat_model
          this.prod_info.mat_name=res.node_req_info.mat_name
          this.prod_info.mat_type_id=res.node_req_info.mat_type_id
          this.prod_info.mat_type_name=res.node_req_info.mat_type_name
          this.prod_info.mold_no=res.node_req_info.mold_no
          this.prod_info.node_pos=res.node_req_info.node_pos
          this.prod_info.undeal_qty=res.node_req_info.undeal_qty
          this.prod_info.prod_qty=res.node_req_info.undeal_qty
          this.prod_info.mat_no=res.node_req_info.mat_no
          this.prod_info.rawnature_name=res.node_req_info.rawnature_name
          this.prod_info.rawnature_id=res.node_req_info.rawnature_id
          this.prod_info.first_try_date=res.node_req_info.first_try_date
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      //删除工序
      procDelete(item,index){
        event.cancelBubble = true;
        this.prod_info.prod_detail_list.splice(index,1)
        this.isEdit=true
        if(this.prod_info.prod_detail_list.length>0){
          this.prod_info.prod_detail_list.forEach((e,i)=>{
            this.prod_info.prod_detail_list[i].proc_seq=i
          })
        }
      },//工序修改
      onItemClick(item){
        sess.set('procInfo',JSON.stringify(item))
        this.$router.forward({path:'addProcedure',query:{action:'action'}})
      },
      //获取新增工序详情
      getProcInfo(){
        if(sess.get('procInfo')===null){
          return
        }
        let procInfo=sess.get('procInfo')
        sess.remove('procInfo')
        if(procInfo.proc_seq+''===''){
          procInfo.proc_seq=this.prod_info.prod_detail_list.length+1
        }
        if(procInfo!==null){
          let isPush=true
          if(this.prod_info.prod_detail_list.length>0){
            this.prod_info.prod_detail_list.forEach((e,i)=>{
              console.log(e.proc_seq+":"+procInfo.proc_seq)
              if(e.proc_seq==procInfo.proc_seq){
                this.prod_info.prod_detail_list.splice(i,1,procInfo)
                isPush=false
              }
            })
          }
          if(isPush){
            this.prod_info.prod_detail_list.push(procInfo)
            this.isEdit=true
          }

        }
      },
      //获取工艺模板列表
      tmplList(){
        const  requestData={
          curr_page:this.curr_page+1,
          page_size:10,
          query:{
            status:'',
            tmpl_name:'',
            tmpl_type_id:'',
            tmpl_type_name:''
          }
        }
        tmplList(requestData).then(res => {
          this.curr_page=res.curr_page
          this.selectInfo.list=this.selectInfo.list.concat(res.routing_tmpl_info)
          this.total_page=res.total_page
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      //模板列表下拉加载
      onInfinite(done ) {
        if (this.curr_page >= this.total_page) {
          return
        }else{
          let _this=this
          setTimeout(function () {
            _this.tmplList()
            done()
          },500)
        }



      },
      tmplDetail(item){
        const  requestData={
          query:{
            tmpl_id:item.id
          }
        }
        tmplDetail(requestData).then(res => {
          this.prod_info.prod_detail_list=res.routing_tmpl_info.sub_list
          this.prod_info.prod_detail_list.forEach((e,i)=>{
            this.prod_info.prod_detail_list[i].id=''
            this.prod_info.prod_detail_list[i].tmpl_id=''
          })
          this.isEdit=true
          this.selectInfo.switch=false
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      submit(){
        $router.forward({
          path:'scheduling',query:{
            po_id:this.prod_info.po_id,
            undeal_qty:this.prod_info.prod_qty,
            first_try_date:'2018-05-12'
          }
        })
      },
      //生产定单新增修改
      prodOrderModify(state){
        if(this.prod_info.prod_detail_list.length<1){
          $toast.show('请配置工艺路线')
          return
        }
        if(state===0&&this.isEdit===false){
          $toast.show('已保存')
          return
        }
        if(state===undefined){
          if(this.prod_info.po_status===''){
            this.prod_info.po_status=0
          }
        }else{
          this.prod_info.po_status=state
        }
        this.prod_info.prod_detail_list.forEach((e,i)=>{
          this.prod_info.prod_detail_list[i].proc_id=null
          this.prod_info.prod_detail_list[i].id=null
        })
        const  requestData={
          prod_info:this.prod_info
        }
        prodOrderModify(requestData).then(res => {
          this.prod_info=res.proc_info
          this.isEdit=false
          $toast.show('保存成功')
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      prodOrderDetail(){
        const  requestData={
          query:{
            po_id:this.prod_id
          }

        }
        prodOrderDetail(requestData).then(res => {
          this.prod_info=res.prod_info
          if(this.prod_info.prod_detail_list==null){
            this.prod_info.prod_detail_list=[]
          }
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      touchstart(event,item,index){
        this.dragx=event.targetTouches[0].clientX
        this.dragy=event.targetTouches[0].clientY
        this.top=this.dragy-20
        // if(this.action!=='check'){
          this.longTouch(event.currentTarget,item)
        // }

      },
      touchmove(event,item){
        this.dragX=event.targetTouches[0].clientX
        this.dragY=event.targetTouches[0].clientY
        this.top=this.dragY-20
        console.log(this.top)
      },
      touchend(event,item,index){
        clearTimeout(this.Timeout)
        let top=this.top+20
        this.topList.forEach((e,i)=>{
          if((e-75)<top&&top<e){
            console.log(i,index,e)
            if(i==index||!this.drag){
              return
            }
            let currItem=this.prod_info.prod_detail_list[index]
            this.prod_info.prod_detail_list.splice(index,1)
            this.prod_info.prod_detail_list.splice(i,0,currItem)
            this.prod_info.prod_detail_list.forEach((e,i)=>{
              this.prod_info.prod_detail_list[i].proc_seq=i
            })
          }
        })
        if(item.drag){
          this.$set(item,'drag',false)
        }
        this.drag=false
        this.dragX=0
        this.dragY=0

      },
      longTouch(event,item){
        let _this=this
        this.Timeout=setTimeout(function(){
          if(_this.dragX===0&&_this.dragY===0){
            _this.drag=true
            _this.currItem=item
            _this.$set(item,'drag',true)
            let ele=event.parentNode.children
            let scrollTop=document.querySelector('.mw-page-content').scrollTop
            _this.topList=[]
            for(var i=0;i<ele.length;i++){
              _this.topList.push(ele[i].offsetTop-scrollTop+135)
            }
            console.log(_this.topList)
          }
        },400)
      }
    },
    watch:{
      prod_info:{
        handler:function(e){
          clearTimeout(this.times)
          let _this=this
          this.times=setTimeout(function(){
            if(e.prod_qty<=0){
              _this.prod_info.prod_qty=1
            }else if(e.prod_qty>_this.prod_info.bom_qty){
              _this.prod_info.prod_qty=_this.prod_info.bom_qty
            }
          },700)
        },deep:true
      }
    }
  }
</script>
<style lang="scss" scoped>
  .itemBtn{
    position: absolute;
    right: 0px;
    top:0px;
    height: 65px;
    line-height: 65px;
    width: 70px;
    text-align: center;
    color: #416eb6;
  }
  .select-box.active{
    z-index: 10;
  }
  .drag{
    background: #fff;
    padding: 17px 15px;
    border-bottom: 1px solid #ccc;
    transition:all .3s;
    position: relative;
    &.active{
      opacity: 0.3;
    }
    .ion-ios-close{
      position: absolute;
      right: 0;
      top: 0;
      width: 40px;
      height: 75px;
      line-height: 75px;
      font-size: 18px;
      color: red;
      text-align: center;
    }
    p{
      margin: 0;
    }
  }
  .isDrag{
    overflow: hidden;
  }
  .flip-list-move {
    transition: transform .5s;
  }


</style>
