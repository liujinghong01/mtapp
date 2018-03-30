<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>生产部门列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <div class="content" >
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch" >
          <item v-for="(item,index) in pg_list" :class="item.check?'active':''"  @click.native="checked(item)">
            <div class="checkbox">
              <i class="icon ion-android-checkbox-outline-blank"></i>
              <i class="icon ion-android-checkbox"></i>
            </div>
            <span>{{ item.pg_name }}</span>
            <span v-show="item.check" class="rigth" @click="isDefault(item)">
              {{ item.is_default==1?'':'设为默认' }}
              <i :class="item.is_default==1?'ion-checkmark-round':''"></i>
            </span>
          </item>
          <div  slot="infinite" class="text-center"></div>
        </scroll>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="ascertain">
          确定
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { pgList } from '@/api/production/equipment'
  import SwipeItem from "../../../components/swipeItem"
  import sess from '@/utils/sess'
  export default{
    name:'departmentList',
    components: {SwipeItem},
    data(){
      return {
        dep_id:'',
        parent_id:'',
        list:[
          // {name:'黄杰',id:1,check:true},
        ],
        pg_list:[]
      }
    },
    computed:{},
    activated(){
      if(sess.get('pg_list')!=null){
        let pg_list=sess.get('pg_list')
        this.list=JSON.parse(pg_list)
        sess.remove('pg_list')
      }
    },
    mounted(){
      const param = this.$route.query;
      this.dep_id = param.dep_id
      this.parent_id=param.parent_id
      this.pgList()
    },
    methods: {
      onInfinite(){},
      onRefresh(){},
      ascertain(){
        let pg_list=[]
        let isDefault=false
        this.pg_list.forEach((e,i)=>{
          if(e.check){
            if(e.is_default){
              isDefault=true
            }
            pg_list.push({
              is_default:e.is_default,
              pg_id:e.pg_id,
              pg_name:e.pg_name,
            })
          }
        })
        if(pg_list.length>0){
          if(isDefault){
            sess.set('pg_list',JSON.stringify(pg_list))
            this.$router.go(-1)
          }else{
            $toast.show('请设置默认部门')
            return
          }
        }else{
          $toast.show('请选择部门')
          return
        }
      },
      //获取生产小组列表
      pgList(){
        const  requestData={
          query:{
            dep_id:''
          }
        }
        pgList(requestData).then(res => {
          if(this.list.length>0){
            res.pg_list.forEach((item,index)=>{
              let isCheck=false
              this.list.forEach((e,i)=>{
                if(item.pg_id==e.pg_id){
                  this.$set(e,'check',true)
                  this.pg_list.push(e)
                  isCheck=true
                }
              })
              if(!isCheck){
                this.pg_list.push(item)
              }
            })
          }else{
            this.pg_list=res.pg_list
          }

        }).catch(error => {
          $toast.show(error.description)
        })
      },
      checked(item){//is_default
        if(item.check==undefined){
          this.$set(item,'check',true)
        }else{
          item.check=!item.check
        }
      },
      isDefault(item){//is_default
        this.pg_list.forEach((e,i)=>{
          if(e!==item){
            if(e.is_default){
              e.is_default=0
            }
          }
        })
        if(item.is_default==undefined){
          this.$set(item,'is_default',1)
        }else if(item.is_default==1){
          event.cancelBubble = true;
           return
        }else{
          item.is_default=1
        }
        event.cancelBubble = true;

      }

    }
  }
</script>

<style lang="scss" scoped>
.item{
  padding-left:40px ;
  .rigth{
    color: #416eb6;
    position: absolute;
    top:0;
    right: 10px;
    height: 50px;
    line-height: 50px;
    width: 80px;
    text-align: right;

  }
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
