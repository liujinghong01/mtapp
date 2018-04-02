<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>人员列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <div class="content" >
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch" >
          <item v-for="(item,index) in list" :class="item.check?'active':''"  @click.native="checked(item,index)">
            <div class="checkbox">
              <i class="icon ion-android-checkbox-outline-blank"></i>
              <i class="icon ion-android-checkbox"></i>
            </div>
            <span>{{ item.work_name }}</span>
          </item>
          <div  slot="infinite" class="text-center"></div>
        </scroll>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="popAllocate">
          确定
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { getDeptInfo } from '@/api/company'
  import { pgDetail,popAllocate } from '@/api/production/work'
  import SwipeItem from "../../../components/swipeItem"
  import sess from '@/utils/sess'
  export default{
    name:'persList',
    components: {SwipeItem},
    data(){
      return {
        dep_id:'',
        pop_id:'',
        parent_id:'',
        type:'',
        list:[
          // {name:'黄杰',id:1,check:true},
        ]
      }
    },
    computed:mapGetters([
      'chosenCompany',
      'userinfo'
    ]),
    activated(){
    },
    mounted(){
      const param=this.$route.query
      this.pg_id=param.pg_id
      this.pop_id=param.pop_id
      this.pgDetail()

    },
    methods: {
      onInfinite(){},
      onRefresh(){},
      popAllocate(){
        let checkList=[]
        this.list.forEach((e,i)=>{
          if(e.check){
            checkList.push({member_id:e.member_id})
          }
        })
        const requestData = {
          resource_info:{
            device_list:[],
            member_list:checkList,
            pop_id:this.pop_id
          }

        }
        popAllocate(requestData).then(res => {
          this.$router.go(-1)
        }).catch(error => {
          $toast.show(error.description)
        })},
      //获取公司部门以及成员
      pgDetail(){
        const  requestData={
          query:{
            pg_id:this.pg_id
          }
        }
        pgDetail(requestData).then(res => {
          let allocated_list=[]
          if(sess.get('allocated_list')!==null){
            allocated_list=JSON.parse(sess.get('allocated_list'))
            sess.remove('allocated_list')
          }
          this.list=res.pg_info.memeber_list
          if(allocated_list.length>0){
            this.list.forEach((e,i)=>{
              allocated_list.forEach((item,index)=>{
                console.log(e.member_id==item.resource_id)
                if(e.member_id==item.resource_id){
                  this.$set(e,'check',true)
                }
              })
            })
          }
        }).catch(error => {
          $toast.show(error.description)
        })
      },

      checked(item,index){
          if(item.check==undefined){
            this.$set(item,'check',true)
          }else{
            item.check=!item.check
          }

      }

    }
  }
</script>

<style lang="scss" scoped>
.item{
  padding-left:40px ;
  &>div{
    line-height: 20px
  }
  &.active{
    background: #fff;
    &>div.checkbox>i{
      color: #416eb6;
      &.ion-android-checkbox{
        display: block;
      }
    }
  }
  .checkbox{
    width: 40px;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    i{
      position: absolute;
      width: 14px;
      height: 20px;
      font-size: 18px;
      left: 50%;
      top: 50%;
      margin-top: -10px;
      margin-left: -5px;
      &.ion-android-checkbox{
        display: none;
      }
    }
  }
}


</style>
