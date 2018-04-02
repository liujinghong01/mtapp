<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>新建入库单</span>
            <i class="right iconfont icon-saoyisao" ></i>
        </div>
        <div class="mw-page-content singleDetail">
          <div class="header">
            <item>
              入库类型:
              <span  class="item-note" style="overflow: visible" @click="onSelect('type')">
                {{ singleType }}<i class="icon ion-ios-arrow-right" style="color: #DDD;float:right;font-size:20px;margin-top:-4px;"></i>
              </span>
            </item>
            <item>
              单号:{{ numberList }}
              <span class="item-note" @click="onSelect('number')">
                {{ singleNumber }}
              </span>
            </item>
          </div>
        </div>
      <div class="select-box" :class="selectInfo.switch?'active':''">
        <div class="select-click" @click="selectInfo.switch=false"></div>
        <div class="select-main">
          <div class="main-header">
            <p v-show="selectInfo.selectType==='type'" >{{ selectInfo.title }}</p>
            <input style="margin-top:10px" v-show="selectInfo.selectType==='number'"  v-model="requestData.query.bill_no" type="text" placeholder="请输入单号">
          </div>
          <div class="main-list" style="height: 100%">
            <scroll  :on-infinite="onInfinite"  style="-webkit-overflow-scrolling: touch;top: 70px;">
              <p v-for=" (item,index) in selectInfo.list "  v-show=" !(item==='入库凭证号'||item=='模号'||item==='领料单号'||item==='内部定单号') "   @click="onCheck(item,index)">{{ item }}</p>
              <div v-if="selectInfo.selectType==='type'||requestData.curr_page>=requestData.total_page" slot="infinite" class="text-center">没有更多数据</div>
              <div v-if="requestData.query.bill_no===''" slot="infinite" class="text-center">请输入单号</div>
            </scroll>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
  import { billSearch } from '@/api/storage/storagePush'
  import SwipeItem from "../../../components/swipeItem";
    export default{
      name:'newStoragePull',
      components: {SwipeItem},
      data(){
        return {
          singleType:'请选择',//单号类型
          singleNumber:'请输入单号',
          requestData:{
            query:{type:'',bill_no:''},
            curr_page:1,
            page_size:20
          },
          selectInfo:{
            switch:false,
            selectType:'',
            title:'请选择类型',
            list:['生产类型']
          },
          typeList:[//1模号，2生产单号，3采购单号，4送货单号，5内部定单号，6领料单号
            '模号',
            '生产单号',
            '采购单号',
            '送货单号',
            '内部定单号',
            '内部定单号',
            '入库凭证号',
            '出库凭证号'

          ],
          numbList:[]//单号模糊查询列表
        }
      },
      computed:{
        numberList:function(){
          if(this.requestData.query.type!==''&&this.requestData.query.bill_no!==''){
            this.selectInfo.list=[]
            this.requestData.curr_page=1
            this.numbList=[]
            this.getNumb()
          }
        }
      },
      activated(){
      },
      methods: {
        onSelect(data){
          this.selectInfo.selectType=data
          switch (data){
            case 'type':{
              this.selectInfo.list=this.typeList
              this.singleNumber='请输入单号'
              this.requestData.query.bill_no=''
              this.selectInfo.switch=true
              break
            }
            case  'number':{
              if(this.requestData.query.type===''){
                $toast.show('请先选择类型')
              }else{
                this.selectInfo.list=[]
                this.selectInfo.switch=true
              }
              break
            }
          }

        },
        onCheck(item,index){
          switch (this.selectInfo.selectType){
            case 'type':{
              this.singleType=item
              this.requestData.query.type=index+1
              break
            }
            case 'number':{
              this.singleNumber=item
              this.$router.forward('/storage/storagePush/singleDetail/action/'+item+'/'+this.singleType)
              break
            }
          }
          this.selectInfo.switch=false
        },
        getNumb() {//单号模糊查询
          billSearch(this.requestData).then(res => {
            if(res.bill_no_list.length>0){
              this.numbList = res.bill_no_list
              this.requestData.total_page = res.total_page
              this.numbList.forEach((e, i) => {
                this.selectInfo.list.push(e.bill_no)
              })
            }else{
              this.requestData.total_page =''
            }
          }).catch(error => {
            $toast.show(error.description)
          })

        },
        onInfinite(done){
          var _this=this
          setTimeout(function(){
            _this.requestData.curr_page++
            if(_this.requestData.curr_page<=_this.requestData.total_page){
              _this.getNumb()
            }

            done()
          },1000)
        }
      }

    }
</script>

<style lang="scss" scoped>

</style>
