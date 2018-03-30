<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>零部件详情</span>
      <!--<i class="right" @click="action = 'edit'" v-if="action!=='approve'&&action!=='check'">修改</i>-->
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
          <list>
            <cst-item label="部件名称" :canEdit="true" :action="action" placeholder="请输入部件名称" v-model="part_detail.part_name"></cst-item>
            <cst-item label="数量" :canEdit="true" :action="action" editType="number" placeholder="请输入部件数量" v-model="part_detail.part_count"></cst-item>
            <cst-item label="重量(kg)" :canEdit="true" :action="action" placeholder="0" editType="number" v-model="part_detail.part_weight"></cst-item>
            <cst-item label="费用" :canEdit="true" :action="action" v-model="part_detail.part_cost" placeholder="0"></cst-item>
            <div class="item-divider prd-header">
              <span style="padding-left:15px;">子部件</span>
              <span style="padding-right:15px;float:right;" v-show="action==='edit'|| action==='new'" @click="$router.forward('/customerOrder/compute/cst_part_sub_detail/new')">新增</span>
            </div>

            <swipe-item swipeItemText="删除" v-for="item in part_detail.sub_part_list" v-show="part_detail.sub_part_list.length" :obj="item"  :hasArrow="true" v-on:SwipeLeft="onSwipeLeft" v-on:ItemClick="onItemClick" v-on:SwipeItemClick="delItem">
              <span class="label">{{item.sub_part_name}}</span><span class="value">{{item.stuff_up}}</span>
            </swipe-item>
          </list>
          <div class="btns" v-show="action!=='check' && action!=='approve'">
            <div class="btn" @click="saveData">
              保存
            </div>
          </div>
        </div>
    </div>
</template>
<script>
  import CstItem from "../../../../components/cstItem";
  import SwipeItem from "../../../../components/swipeItem";
  import {partModify ,partDetail,subPartDel } from '@/api/customerOrder/compute'
  import sess from '@/utils/sess'
    export default{
      components: {
        SwipeItem,
        CstItem},
      name:'cst_part_detail',
      data(){
        return{
          part_id:'',
          compute_id:'',
          currItem:{},
          part_detail:{
            part_cost:'',
            part_hardness:'',
            part_count:'',//数量
            part_id:'',
            part_name:'',
            part_size:'',
            part_type:'',
            part_weight:0,
            part_length:0,
            part_height:0,
            part_width:0,
            sub_part_list:[

            ]
          },
          action:''
        }
      },
      computed:{
        totalWeight(){
          let result = 0
          let totalPrice = 0.00
          if(this.part_detail.sub_part_list.length>0){
            this.part_detail.sub_part_list.forEach(item=>{
              result += parseFloat(item.stuff_weight)
              totalPrice += parseFloat(item.stuff_up)
            })
            this.part_detail.part_weight = result =  Number(result).toFixed(2)
            this.part_detail.part_cost = Number(totalPrice).toFixed(2)
          }
        }
      },
      methods:{
        onItemClick(item){
          if(this.currItem.swipe){
            this.currItem.swipe = false;
            return;
          }
          let nextAction = (this.action === 'check' || this.action === 'check')?'check':'edit'
          this.$router.forward({path:'/customerOrder/compute/cst_part_sub_detail/'+nextAction,query:{cst_part_sub_detail:JSON.stringify(item)}})
        },
        onMenuClick(){
          this.action = 'edit'
        },
        onSwipeLeft(item){
          this.$set(item,'swipe',true);
          this.currItem = item
        },
        saveData(){
          if (!this.part_detail.part_name){
            $toast.show('请填写部件名称')
            return
          }
          if(Number(this.part_detail.part_count)<1){
            $toast.show('请填写数量')
            return
          }
          this.action = 'check'
          const reqData = {
            compute_id:this.compute_id,
            part_list:[this.part_detail]
          }
          partModify(reqData).then(res=>{
            $toast.show("保存成功")
            this.$router.go(-1)
            console.log(res)
          }).catch(error=>{
             $toast.show(error.description)
          })
        },
        getData(){
          const reqData={
            part_id:this.part_id,
          }
          partDetail(reqData).then(res=>{
            console.log(res.part_cost)
            console.log(res)
            this.part_detail = res
          }).catch(error=>{
            console.log(error.description)
            $toast.show(error.description)
          })
        },
        delItem(item){
          const reqData = {
            compute_id:this.compute_id,
            part_id:this.part_id,
            sub_part_ids:[item.sub_part_id]
          }
          subPartDel(reqData).then(res=>{
            $toast.show(删除成功)
            this.part_detail.sub_part_list = this.part_detail.sub_part_list.filter(o=>{
                return item.id != o.id
            })
            if(this.part_detail.sub_part_list.length===0){
              this.part_detail.part_cost = 0
            }
          }).catch(error=>{
              $toast.show(error.description)
          })
        },
      },
      activated(){
        const part_detail = sess.get('part_detail')
        sess.remove('part_detail')
        if(part_detail){
          if (this.part_detail.sub_part_list.length === 0){
            this.part_detail.sub_part_list.push(part_detail)
          }else{
            let isExist = false,index = 0

            this.part_detail.sub_part_list.forEach((o,i)=>{
              if (o.sub_part_name === part_detail.sub_part_name){
                isExist = true
                index = i
              }
            })
            if (!isExist) {
              this.part_detail.sub_part_list.push(part_detail)
            }else {
              this.$set(this.part_detail.sub_part_list,index,part_detail)
            }
          }
          this.totalWeight()

        }
      },
      mounted(){
        this.action = this.$route.params.action
        if(this.action==='new'){
          this.compute_id = this.$route.params.part_id
        }else{
          this.part_id = this.$route.params.part_id
          this.getData()
        }
      },
    }
</script>
<style scoped lang="scss">
  .item{
      .item-cont{
        height: 100%;

        span{
          width: 50%;
          float: left;
          text-overflow: ellipsis;
          white-space: nowrap;
          /*padding: 5px;*/
          overflow: hidden;
        }

      }

    .label{
      width: 30%;
      float: left;
      height: 100%;
    }
    .value{
      text-align: right;
      float: right;
      padding-right: 15px;
    }
  }
</style>
