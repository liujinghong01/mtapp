<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>库存物料管理</span>
            <i class="right iconfont icon-saoyisao" ></i>
        </div>
        <div class="mw-page-content singleDetail">
          <div class="header">
            <item>
              类型:
              <span  class="item-note" style="overflow: visible" @click="onSelect('type')">
                {{ type }}<i class="icon ion-ios-arrow-right" style="color: #DDD;float:right;font-size:20px;margin-top:-4px;"></i>
              </span>
            </item>
            <item v-show="type==='物料调拨'">
              调出仓库:
              <span  class="item-note" style="overflow: visible" @click="onSelect('houseOut')">
                {{ requestData.out_store_house_name }}<i class="icon ion-ios-arrow-right" style="color: #DDD;float:right;font-size:20px;margin-top:-4px;"></i>
              </span>
            </item>
            <item v-show="type==='物料调拨'">
              调入仓库:
              <span  class="item-note" style="overflow: visible" @click="onSelect('houseIn')">
                {{ requestData.in_store_house_name }}<i class="icon ion-ios-arrow-right" style="color: #DDD;float:right;font-size:20px;margin-top:-4px;"></i>
              </span>
            </item>
            <item v-show="type==='直接入库'">
              仓库:
              <span  class="item-note" style="overflow: visible" @click="onSelect('houseIn')">
                {{ requestData.in_store_house_name }}<i class="icon ion-ios-arrow-right" style="color: #DDD;float:right;font-size:20px;margin-top:-4px;"></i>
              </span>
            </item>
            <item v-show="type==='直接出库'">
              仓库:
              <span  class="item-note" style="overflow: visible" @click="onSelect('houseOut')">
                {{ requestData.out_store_house_name }}<i class="icon ion-ios-arrow-right" style="color: #DDD;float:right;font-size:20px;margin-top:-4px;"></i>
              </span>
            </item>
          </div>
          <div class="mateList" v-show="type!=='请选择'" :class="type==='物料调拨'?'active':''" >
            <div class="mateListTop" >
              <span>物料列表</span>
              <span class="right" @click="addMate()">添加</span>
            </div>
            <div class="list" style="padding-top: 29px">
              <div class="item"  v-for="(item,index) in requestData.change_detail_list">
                <div class="itemInfo"  >
                  <div class="numberBox" style="top: 15px;height: 70px">
                    <span @click="onChange(item,'1')" >+</span>
                    <input  :disabled="action!=='new'" type="number" @change="onChange(item)"  v-model="item.quantity" >
                    <span @click="onChange(item,'-1')" >-</span>
                    <div @click="onSelect('storeRoom',index)" v-show="requestData.in_store_house_id!==''" style="margin-top: 44px;text-align: right;padding-right: 10px;color: #4a90e2">
                      {{ item.in_store_room_name?item.in_store_room_name:'请选择库位' }}
                      <i class="icon ion-ios-arrow-right"></i>
                    </div>
                  </div>
                  <h3>物料编码: <span>{{ item.mat_no }}</span></h3>
                  <p>物料名称: <span>{{ item.mat_desc }}</span></p>
                  <p>规格型号: <span>{{ item.mat_model }}</span></p>
                  <p>物料类型: <span>{{ item.mat_type_name }}</span></p>
                </div>
              </div>
            </div>
          </div>
        </div>
      <div class="select-box" :class="selectInfo.switch?'active':''">
        <div class="select-click" @click="selectInfo.switch=false"></div>
        <div class="select-main">
          <div class="main-header">
            <p>{{ selectInfo.title }}</p>
          </div>
          <div class="main-list" style="height: 100%">
            <p  v-for=" (item,index) in selectInfo.list " v-show="item!==requestData.out_store_house_name"  @click="onCheck(item,index)">{{ item }}</p>
          </div>
        </div>
      </div>
      <div class="mw-page-footer">
        <div class="btns">
          <div style="" class="total" style="flex: 0 0 80%;">
            <div style="display: flex">
              <span style="width: 50%;text-align: center">物料种类:{{requestData.change_detail_list.length}}</span>
              <span style="width: 50%;text-align: center">{{totalCount}}</span>
            </div>
          </div>
          <div class="btn" style="background-color: #416eb6;color: white;padding: 15px 0" @click="submit">
            提交
          </div>
        </div>
      </div>
    </div>
</template>

<script>
  import { storehouseList,toragesManage,storeRoomList } from '@/api/storage/management'
  import sess from '@/utils/sess'
    export default{
      name:'newManagement',
      data(){
        return {
          type:'请选择',//单号类型
          storehouse_list:[],//仓库列表
          storeRoom_list:[],//库位列表
          storeRoomIndex:'',
          action:'',
          requestData:{//
            change_detail_list:[
              // {
              //   change_detail_id:'',
              //   deal_flag:'',
              //   in_store_room_id:'',
              //   in_store_room_name:'',
              //   mat_desc:'',
              //   mat_id:'',
              //   mat_model:'',
              //   mat_no:'',
              //   mat_type_id:'',
              //   mat_type_name:'',
              //   out_store_room_id:'',
              //   out_store_room_name:'',
              //   quantity:'',
              //   unit:'',
              //   weight:''
              // }
            ],
            change_id:'',
            change_no:'',
            change_type:'',
            create_time:'',
            creator_id:'',
            in_store_house_id:'',
            in_store_house_name:'请选择仓库名称',
            out_store_house_id:'',
            out_store_house_name:'请选择仓库名称',
            remark:'',
            total_price:'',
            total_qty:'',
            total_weight:''
          },
          selectInfo:{
            switch:false,
            selectType:'',
            title:'请选择类型',
            list:[]
          },
          typeList:[
            '物料调拨',
            '直接入库',
            '直接出库'
          ],
          numbList:[]//单号模糊查询列表
        }
      },
      computed:{
        numberList:function(){
        },
        totalCount(){
          let sumCount = 0
          let total_price=0
          this.requestData.change_detail_list.filter(o=>{
            total_price+=Number(o.quantity)*Number(o.total_price)
            sumCount += Number(o.quantity)
          })
          this.requestData.total_price=total_price//total_qty
          this.requestData.total_qty=sumCount
          return '总数量:'+sumCount
        },
      },
      activated(){
        const mats = JSON.parse(sess.get('mats'))
        if(mats){
          this.requestData.change_detail_list=mats
          console.log("本地储存读取成功"+mats)
          sess.remove('mats')
        }else{
          console.log("本地储存为空")
        }
        // this.storehouseList()
      },
      methods: {
        submit(){
          if(this.requestData.change_detail_list.length<1){
            $toast.show("请添加物料")
            return
          }
          if(this.requestData.in_store_house_id===''){
            if(this.requestData.out_store_house_id===''){
              $toast.show("请选择仓库")
              return
            }
          }else{
            let isId=true
            this.requestData.change_detail_list.forEach((e,i)=>{
              if(e.in_store_room_id===undefined || e.in_store_room_id===''){
                isId=false
              }
            })
            if(!isId){
              $toast.show("请选择物料库位")
              return
            }
          }
          const  requestData={
            change_info:this.requestData
          }
          toragesManage(requestData).then(res => {

          }).catch(error => {
            $toast.show(error.description)
          })
        },
        storeRoomList(){
          const  requestData={
            query:{
              store_house_id:this.requestData.in_store_house_id
            }

          }
          storeRoomList(requestData).then(res => {
              this.storeRoom_list=res.store_house_info.store_room_list
          }).catch(error => {
            $toast.show(error.description)
          })
        },
        onChange(e,type,index){
          if(this.action!=='new'){
            return
          }
          if(type==='-1'){
            e.quantity=Number(e.quantity)-1
          }else if(type==='1'){
            if(this.requestData.out_store_house_id===''){
              e.quantity=Number(e.quantity)+1
            }else if(e.quantity<e.stock_qty){
              e.quantity=Number(e.quantity)+1
            }
          }
          let quantity=Number(e.quantity)
          if(quantity%1!==0){
            e.quantity=parseInt(quantity)
          }
          if(e.quantity<=0){
            $dialog.confirm({
              theme: 'ios',
              title: '确定要删除吗？',
              cancelText: '取消',
              okText: '确定'
            }).then(res=>{
              if(res){
                this.requestData.change_detail_list.splice(index,1)
              }else{
                e.quantity=1
              }
            })
          }
          if(quantity>e.stock_qty&&this.requestData.out_store_house_id!==''){
            e.quantity=e.stock_qty
          }
        },
        onSelect(data,index){
          this.selectInfo.selectType=data
          switch (data){
            case 'type':{
              this.selectInfo.list=this.typeList
              break
            }
            case  'houseOut':{
              this.selectInfo.list=[]
              this.storehouse_list.forEach((e,i)=>{
                this.selectInfo.list.push(e.store_house_name)

              })
              break
            }
            case  'houseIn':{
              this.selectInfo.list=[]
              this.storehouse_list.forEach((e,i)=>{
                this.selectInfo.list.push(e.store_house_name)
              })
              break
            }
            case 'storeRoom':{
              this.selectInfo.list=[]
              this.storeRoomIndex=index
              this.storeRoom_list.forEach((e,i)=>{
                this.selectInfo.list.push(e.store_room_name)
              })
              break
            }
          }
          this.selectInfo.switch=true
        },
        onCheck(item,index){
          switch (this.selectInfo.selectType){
            case 'type':{
              if(this.type===item){
                break
              }
              this.type=item
              this.requestData.change_detail_list=[]
              this.requestData.out_store_house_name='请选择仓库名称'
              this.requestData.out_store_house_id=''
              this.requestData.in_store_house_name='请选择仓库名称'
              this.requestData.in_store_house_id=''
              break
            }
            case 'houseOut':{
              if(this.requestData.out_store_house_name===item){
                break
              }
              this.requestData.out_store_house_name=item
              this.requestData.out_store_house_id=this.storehouse_list[index].store_house_id
              this.requestData.in_store_house_name='请选择仓库名称'
              this.requestData.in_store_house_id=''
              break
            }
            case 'houseIn':{
              if(this.requestData.in_store_house_name===item){
                break
              }
              this.requestData.in_store_house_name=item
              this.requestData.in_store_house_id=this.storehouse_list[index].store_house_id
              this.storeRoomList()
              this.requestData.change_detail_list.forEach((e,i)=>{
                if(e.in_store_room_id){
                  e.in_store_room_id=''
                  e.in_store_room_name='请选择库位'
                }
              })
              break
            }
            case 'storeRoom':{
              let storeRoom=this.storeRoom_list[index]
              let mate=this.requestData.change_detail_list
              if(!mate[this.storeRoomIndex].in_store_room_id){
                this.$set(mate[this.storeRoomIndex],'in_store_room_id',storeRoom.store_room_id)
                this.$set(mate[this.storeRoomIndex],'in_store_room_name',item)
              }else{
                mate[this.storeRoomIndex].in_store_room_id=storeRoom.store_room_id
                mate[this.storeRoomIndex].in_store_room_name=item
              }

              break
            }
          }
          this.selectInfo.switch=false
        },
        storehouseList() {//仓库列表
          const requestData={}
          storehouseList(requestData).then(res => {
            this.storehouse_list=res.storehouse_list
          }).catch(error => {
            $toast.show(error.description)
          })

        },
        addMate(){
          if(this.requestData.change_detail_list.length>0){
            let list=JSON.stringify(this.requestData.change_detail_list)
            sess.set('matList',list)
            console.log('本地储存成功'+list)
          }else{
            sess.set('matList',[])
            console.log('本地储存为空')
          }

          if(this.type==='物料调拨'&&this.requestData.out_store_house_id!==''&&this.requestData.in_store_house_id!==''){
            this.$router.forward({
              path:'/storage/management/matList',
              query:{
                house_id:this.requestData.out_store_house_id
              }
            })
          }else if(this.type==='直接入库'&&this.requestData.in_store_house_id!==''){
            this.$router.forward({
              path:'/storage/management/matList',
              query:{
                house_id:''
              }
            })
          }else if(this.type==='直接出库'&&this.requestData.out_store_house_id!==''){
            this.$router.forward({
              path:'/storage/management/matList',
              query:{
                house_id:this.requestData.out_store_house_id,

              }
            })
          }else{
            $toast.show('请先选择仓库信息')
          }
        }
      },
      mounted(){
        this.action=this.$route.params.action
        this.storehouseList()
      }

    }
</script>

<style lang="scss" scoped>
  .singleDetail .mateList{
      &.active{
        padding-top: 148px;
        .mateListTop{
          top: 148px;
        }
      }
      .mateListTop{
        top: 98px;
      }
  }
</style>
