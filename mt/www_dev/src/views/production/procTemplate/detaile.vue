<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span v-if="action==='new'">新建工艺模板</span>
      <span v-else>工艺模板详情</span>
      <i v-if="action==='check'" class="right" @click="Edit">编辑</i>
      <i v-if="action==='action'" class="right" @click="cancelEdit">取消</i>
      <i v-if="action==='new'" class="right"></i>
    </div>
    <div class="mw-page-content " :class="drag?'isDrag':''">
      <cst-item label="模板类型" placeholder="请选择模板类型" @click.native="goTemplType()" v-model="routing_tmpl_info.tmpl_type_name" action="check" :hasArrow="action!=='check'?true:false"></cst-item>
      <cst-item label="模板名称" placeholder="请输入模板名称" v-model="routing_tmpl_info.tmpl_name" :action="action" ></cst-item>
      <cst-item label="模板描述" placeholder="请输入模板描述"  v-model="routing_tmpl_info.tmpl_desc" :action="action" ></cst-item>
      <item  >
        <span>备注:</span>
        <textarea style="resize: none;width: 100%;height:52px;line-height: 16px;font-size: 12px"  :disabled="action=='check'?true:false" v-model="routing_tmpl_info.remark" placeholder="请输入备注"></textarea>
      </item>
      <div class="proc_list" v-show="routing_tmpl_info.sub_list.length>0">
        <div>
          <div class="item-divider" style="padding-left: 15px;">工序列表</div>
        </div>
        <!--<swipe-item v-for="(item,index) in routing_tmpl_info.sub_list" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="action!=='check'?true:false" swipeItemText="删除" v-on:SwipeItemClick="processDelete(item,index)">-->
          <!--<p style="margin-bottom: 5px">工序名称：{{ item.proc_name }}</p>-->
          <!--<p>工序类型：{{ item.proc_type==1?'生产':'外协' }}</p>-->
        <!--</swipe-item>-->
        <transition-group name="flip-list" tag="div">
          <div @click="onItemClick(item)" v-bind:key="item.id" class="drag" :class="item.drag===true?'active':''" v-for="(item,index) in routing_tmpl_info.sub_list" @touchstart="touchstart($event,item,index)" @touchmove="touchmove($event,item)" @touchend="touchend($event,item,index)" >
            <p >工序名称：{{ item.proc_name }}</p>
            <p>工序类型：{{ item.proc_type==1?'生产':'外协' }}</p>
            <i v-show="action!=='check'" @click="processDelete(item,index)" class="ion-ios-close"></i>
          </div>
        </transition-group>

      </div>
      <p v-show="action!=='check'" @click="$router.forward({path:'templateAddProc',query:{action:'new'}})" style="margin-top:10px;text-align: center;color: #416eb6;border-bottom: 1px solid #416eb6;border-top: 1px solid #416eb6;line-height: 40px;background:#fff">+添加工序</p>
      <div class="mw-page-footer" v-show="action!=='check'">
        <div class="btns">
          <div class="btn" @click="tmplModify">
            保存
          </div>
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
  import { getDeptInfo } from '@/api/company'
  import { tmplModify,tmplDetail } from '@/api/production/procTemplate'
  import CstItem from "../../../components/cstItem"
  import SwipeItem from "../../../components/swipeItem";
  import sess from '@/utils/sess'
  export default{
    name:'templateDetaile',
    components: {CstItem,SwipeItem},
    computed:{},
    data(){
      return {
        action:'new',
        tmpl_id:'',
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
        routing_tmpl_info:{//routing_tmpl_info.sub_list
          id:'',
          remark:'',//	备注
          tmpl_desc:'',//	模板描述
          tmpl_name:'',//	模板名称
          tmpl_type_id:'',//	工艺种类ID
          tmpl_type_name:'',//
          sub_list:[
            // {
            //   ass_calc_qty:'',//	计价数量(单位数量)	number	外协
            //   ass_price:'',//	外协金额	number	外协
            //   ass_time:'',//	外协工期	number	外协
            //   ass_unit:'',//	外协单位(计价单位)	string	外协
            //   ass_up:'',//	外协单价(计价单价)	number	外协
            //   device_id:'',//	设备	number
            //   device_name:'请选择',
            //   id:'',//	模板子Id	number
            //   is_need_qc:'1',//	是否要质检	string
            //   labor_time:'',//	人工时间	number
            //   machine_time:'',//	机器时间	number
            //   other_time:'',//	其它辅助时间	number
            //   pg_id:'',//	生产小组ID	number
            //   pg_name:'请选择',
            //   pg_type:0,
            //   pic_path:'',//	附图路径	string
            //   proc_desc:'',//	加工内容	string
            //   proc_id:'',//	工序Id	number
            //   proc_name:'请选择工序',//	工序名称	string
            //   proc_qty:'',//	生产数量	number	外协
            //   proc_seq:'',//	工序序号	number
            //   proc_type:'1',//	工序类型	string	(1：生产，2:外协）
            // }
          ],

        }
      }
    },
    activated(){
      if(this.action!=='check'){
        this.getTempType()
        this.getProcInfo()
      }
    },
    mounted(){
      const param = this.$route.query;
      this.action=param.action
      if(this.action=='check'){
        this.tmpl_id=param.tmpl_id
        this.tmplDetail()
      }
    },
    methods: {
      goTemplType(){
        if(this.action!=='check'){
          $router.forward({path:'templateTypeList'})
        }

      },
      Edit(){
        this.action='action'
      },
      cancelEdit(){
        this.action='check'
        this.tmplDetail()
      },
      //删除工序
      processDelete(item,index){
        this.routing_tmpl_info.sub_list.splice(index,1)
        $toast.show('删除成功')
        if(this.routing_tmpl_info.sub_list.length>0){
          this.routing_tmpl_info.sub_list.forEach((e,i)=>{
            this.routing_tmpl_info.sub_list[i].proc_seq=i
          })
        }
      },
      //获取新增工序详情
      getProcInfo(){
        let procInfo=sess.get('procInfo')
        sess.remove('procInfo')
        if(procInfo!==null){
          let isPush=true
          if(this.routing_tmpl_info.sub_list.length>0){
            this.routing_tmpl_info.sub_list.forEach((e,i)=>{
              console.log(e.proc_seq+":"+procInfo.proc_seq)
              if(e.proc_seq===procInfo.proc_seq){
                this.routing_tmpl_info.sub_list.splice(i,1,procInfo)
                isPush=false
              }
            })
          }
          if(isPush){
            procInfo.proc_seq=this.routing_tmpl_info.sub_list.length
            this.routing_tmpl_info.sub_list.push(procInfo)
          }

        }
      },
      onItemClick(item){
        sess.set('procInfo',JSON.stringify(item))
        this.$router.forward({path:'templateAddProc',query:{action:this.action}})
      },
      //获取工艺模板类型
      getTempType(){
        let tempType=sess.get('tempType')
        sess.remove('tempType')
        if(tempType!==null){
          this.routing_tmpl_info.tmpl_type_name=tempType.tmpl_type_name
          this.routing_tmpl_info.tmpl_type_id=tempType.tmpl_type_id
        }

      },
      //工艺模板新增修改
      tmplModify(){
        const  requestData={
          routing_tmpl_info:this.routing_tmpl_info
        }
        tmplModify(requestData).then(res => {
          this.$router.go(-1)
          $toast.show('保存成功')
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      tmplDetail(){
        const  requestData={
          query:{
            tmpl_id:this.tmpl_id
          }

        }
        tmplDetail(requestData).then(res => {
            this.routing_tmpl_info=res.routing_tmpl_info
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      touchstart(event,item,index){
        this.dragx=event.targetTouches[0].clientX
        this.dragy=event.targetTouches[0].clientY
        this.top=this.dragy-20
        if(this.action!=='check'){
          this.longTouch(event.currentTarget,item)
        }

      },
      touchmove(event,item){
        this.dragX=event.targetTouches[0].clientX
        this.dragY=event.targetTouches[0].clientY
        this.top=this.dragY-20
      },
      touchend(event,item,index){
        clearTimeout(this.Timeout)
        let top=this.top+20
        this.topList.forEach((e,i)=>{
          let E=e-75
          if((e-75)<top&&top<e){
            if(i==index||!item.drag){
              return
            }
            let currItem=this.routing_tmpl_info.sub_list[index]
            this.routing_tmpl_info.sub_list.splice(index,1)
            this.routing_tmpl_info.sub_list.splice(i,0,currItem)
            this.routing_tmpl_info.sub_list.forEach((e,i)=>{
              this.routing_tmpl_info.sub_list[i].proc_seq=i
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
              _this.topList.push(ele[i].offsetTop-scrollTop+75)
            }
          }
        },400)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .proc_list{
    &:after{
      display: block;
      content: " ";
      clear: both;
    }
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
    transition: transform 1s;
  }

</style>
