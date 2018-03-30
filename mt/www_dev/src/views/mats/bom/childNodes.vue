<template>
    <div class="mw-page page mats" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span v-if="mat_type=='电极'">电极列表</span>
            <span v-else>物料列表</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content">

          <div v-for="(item,index) in list" class="bom-swipe-item"  @touchstart="touchstart($event)"  @touchend="touchend($event)" >
            <div class="swipe-content" @click="goDetail(item)">
              <div>
                <h3 >物料编号：<span style="
                word-break:break-all;
                display: inherit;
                width: 69%;
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 1;
                overflow: hidden;">{{ item.mat_no }}</span></h3>
                <!--<h3>物料名称：<span>{{ 缺少物料名称字段 }}</span></h3>-->
                <h3>物料名称：<span>{{ item.mat_name }}</span></h3>
                <p >
                  <span>规格尺寸({{ item.spec }})</span>
                </p>
                <p>
                  <span>材质({{ item.rawnature_name }})</span>
                  <span>数量({{ item.total_qty }})</span>
                </p>
              </div>
              <span @click="onClick(item)" class="icon ion-ios-arrow-right" ></span>
            </div>
            <div class="swipe-click" @click="offSwipe('点击事件')"></div>
            <div class="swipe-side" @click="bomNodeDelete(item,index)">删除</div>
          </div>
        </div>
        <div v-show="this.app_flag!=='step30'&&this.app_flag!=='step0'" class="mw-page-footer">
            <div  class="btns">
              <div class="btn" @click="add">添加</div>
            </div>
        </div>
    </div>
</template>

<script>
  import SwipeItem from "../../../components/swipeItem";//bomNodeList
  import { bomNodeList,bomNodeDelete } from '@/api/mats'
    export default{
    name:'bomchildNodes',
      components: {SwipeItem},
      data(){
        return {
          tabs:['半成品','标准件','电极'],
          app_flag:'',
          tabIndex:0,
          mat_no:'',
          bom_id:'',
          node_id:'',
          mat_type:'',
          list:[
//            {
//              "rawnature_name":"请选择",
//              "mat_type":"毛坯",
//              "mat_id":130,
//              "total_qty":1.00000,
//              "length":10.00000,
//              "width":0.00000,
//              "mat_no":"M2017005-20171205135643-20171205135643",
//              "spec":"请选择",
//              "node_id":115,
//              "is_purchase":"1",
//              "height":10.00000
//            },
//            {
//            "rawnature_name":"铜",
//              "mat_type":"毛坯","mat_id":132,"total_qty":1.00000,"length":10.00000,
//              "width":0.00000,"mat_no":"M2017005-20171205153251-20171205153251",
//              "spec":"请选择",
//              "node_id":119,
//              "is_purchase":"1",
//              "height":10.00000
//            },
//            {
//            "rawnature_name":"铜","mat_type":"毛坯","mat_id":134,"total_qty":1.00000,"length":10.00000,"width":0.00000,"mat_no":"M2017005-20171205155421-20171205155421","spec":"请选择","node_id":121,"is_purchase":"1","height":10.00000
//            }
          ],
          swipeX:0,
        }

      },
      methods: {
        add(){
          if(this.mat_type==='电极'){
            this.$router.forward('/mats/bom/nodeDetail/new/'+this.node_id+'/2/'+this.app_flag)
          }else{
            this.$router.forward('/mats/bom/childNodeDetail/new/'+this.node_id+'/materials/'+this.app_flag)
          }
        },
        onClick(item){
          if(this.mat_type==='电极'){
            $router.forward('/mats/bom/nodes/electrodeList/'+this.bom_id+'/'+item.node_id+'/'+item.mat_type+'/'+item.mat_no+'/'+this.app_flag)
          }
          event.cancelBubble = true
        },
        goDetail(item){
          if(this.mat_type==='电极'){
            this.$router.forward('/mats/bom/nodeDetail/check/'+item.node_id+'/2/'+this.app_flag)
          }else{
            $router.forward('/mats/bom/childNodeDetail/check/'+item.node_id+'/materials/'+this.app_flag)
          }
        },
        touchstart(){
          this.swipeX=event.targetTouches[0].clientX
        },
        touchend(){
          var X=event.changedTouches[0].clientX+30
          if(X<this.swipeX){
            var elArr=event.currentTarget.parentNode.children
            for(var i=0;i<elArr.length;i++){
              if(elArr[i].classList.contains("active")){
                elArr[i].classList.remove("active")
              }
            }
            event.currentTarget.classList.add('active')
          }
        },
        offSwipe(){
          event.currentTarget.parentNode.classList.remove('active')
        },
        getData(){
          let matType=''
          if(this.mat_type==='电极'){
            matType='电极'
          }
          const requestData={
            query:{
              bom_id:this.bom_id,
              node_id:this.node_id,
              mat_type:matType
            }
          }
          bomNodeList(requestData).then(res=>{
            this.list=res.bom_node_list
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        bomNodeDelete(item,index){
          event.currentTarget.parentNode.classList.remove('active')
          $dialog.confirm({
            // 设置为ios样式
            theme: 'ios',
            // 标题
            title: '确定要删除吗？',
            // 取消按钮文本
            cancelText: '取消',
            // 确定按钮文本
            okText: '确定'
          }).then(res=>{
            if(res){

              const requestData={node_id:item.node_id}
              bomNodeDelete(requestData).then(res=>{
                this.list.splice(index,1)
                $toast.show('删除成功')
              }).catch(error=>{
                $toast.show(error.description)
              })
            }
          })
        },
      },
      activated:function(){
        this.getData();

      },
      mounted(){
        this.bom_id = this.$route.query.bom_id
        this.node_id = this.$route.query.node_id
        this.mat_type = this.$route.query.type
        this.app_flag =this.$route.query.app_flag
        this.getData();
      }
    }
</script>

<style lang="scss" scoped>

</style>
