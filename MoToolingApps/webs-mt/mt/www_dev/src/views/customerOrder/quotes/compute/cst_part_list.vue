<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="back()"></i>
      <span>零部件明细</span>
      <!--<i class="right" @click="onMenuClick" v-if="action!=='approve'&&action!=='check'">新增</i>-->
      <i class="right" ></i>
    </div>
     <div class="mw-page-content">
       <list >
         <item v-if="part_list.length>0" style="border-bottom: 1px solid #ddd;margin: 0" >

         <p class="label">总计(元)</p>
           <p class="value" style="padding-left: 16px">{{totalPrice}} </p>
         </item>
         <cst-item v-else label="总计(元)" v-model="stuff_cost" editType="number" placeholder="请输入材料费" action="action"></cst-item>

         <swipe-item swipeItemText="删除" style="margin:0"
                     :obj="item"
                     v-show="part_list.length"
                     v-for="item in part_list"
                     :hasArrow="true"
                     v-on:ItemClick="onItemClick"
                     v-on:SwipeItemClick="delItem">
           <span class="label">{{item.part_name}}</span><span class="value">{{item.part_cost}} 元</span>
         </swipe-item>
         <button @click="onMenuClick" v-show="action!=='approve'&&action!=='check'" class="button button-calm button-block button-outline">新增零部件</button>

       </list>
     </div>
    </div>
</template>
<script>
  import SwipeItem from "../../../../components/swipeItem";
  import  cstItem  from '@/components/cstItem'
  import sess from '@/utils/sess'
  import { getPartList,partDel } from '@/api/customerOrder/compute'
  export default{
    components: {SwipeItem,cstItem},
    name:'cst_part_list',
    data(){
      return {
        stuff_cost:'',
        part_list:[

        ],
        currItem:{

        },
        compute_id:'',
        action:''
      }
    },
    computed:{
      totalPrice(){
        let total = 0;
        for(let i in this.part_list){
            total += parseFloat(this.part_list[i].part_cost)
        }
        return total.toFixed(2)
      }
    },
    mounted(){
      this.compute_id = this.$route.params.compute_id
      this.action = this.$route.params.action

    },
    activated(){
      if(this.compute_id){
        this.getPartList();
      }

    },
    methods:{
      back(){
        if(this.part_list.length<1&&this.stuff_cost>0){
          sess.set('stuff_cost',this.stuff_cost)
          $router.go(-1)
          return
        }else{
          $router.go(-1)
        }

      },
      onMenuClick(){
        $router.forward('/customerOrder/compute/cst_part_detail/new/'+this.compute_id)
      },
      closeMask(){
        this.currItem.swipe = false;
        this.currItem = {}
      },
      delItem(item){
        console.log(item)
        $dialog.confirm({
          theme:'ios',
          // 标题
          title: '确定要删除'+item.part_name+'吗?',
          // 取消按钮文本
          cancelText: '取消',
          // 确定按钮文本
          okText: '确定'
        }).then((res) => {
          if(res){
            const reqData = {
              part_ids:[item.part_id],
              compute_id:this.compute_id
            }
            partDel(reqData).then(res=>{
              this.part_list = this.part_list.filter(o=>{
                  return o.part_id != item.part_id
              })
              $toast.show('删除成功')
            }).catch(error=>{
              $toast.show(error.description)
            })
          }
        }).catch()
      },
      onItemClick(item){
        if(this.currItem.swipe){
          this.currItem.swipe = false;
          return;
        }
        $router.forward('/customerOrder/compute/cst_part_detail/'+this.action+'/'+item.part_id)
      },
      onSwipeLeft(item){
        this.$set(item,'swipe',true);
        this.currItem = item
      },
      getPartList(){
        const reqData={
          compute_id : this.compute_id
        }
        getPartList(reqData).then(res=>{
          this.part_list = res.part_list
          // if(this.part_list.length<1){
          //   $router.forward('/customerOrder/compute/cst_part_detail/new/'+this.compute_id)
          // }
        }).catch(error=>{
          console.log(error.description)
        })
      }
    },
  }
</script>
<style scoped lang="scss">
  .item{
    padding-left: 15px;
    .item-cont{
      height: 100%;
      span{
        width: 50%;
        float: left;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
      }
    }
    .label{
      width: 30%;
      float: left;
      height: 100%;
      text-align: left;
    }
    .value{
      width: 70%;
      float: right;
      text-align: right;
      padding-right: 15px;
    }

    .detail{
      min-height: 80px;
      width: 100%;
      background-color: #ababab;
      display: none;
      &.active{
        display: block;
      }
    }

  }

</style>
