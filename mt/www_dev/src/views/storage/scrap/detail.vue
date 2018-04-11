<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span v-if="action==='new'">新建物料报废单</span>
            <span v-else>物料报废单详情</span>
            <i  v-show="action==='new'"  class="right iconfont icon-saoyisao" ></i>
            <i  v-show="step==='step0'"  class="right " ></i>
            <i  v-show="action==='check'&&step!=='step0'" @click="action='action'"  class="right " >编辑</i>
            <i  v-show="action==='action'"  class="right " @click="CancelEdit()">取消</i>
        </div>
        <div class="mw-page-content singleDetail" :class="action">
          <div class="header">
            <item v-show="action!=='new'">
              物料报废单号:
              <span  class="item-note">{{ scrap_no }}</span>
            </item>
            <item>
              申请人:
              <span  class="item-note">{{ userinfo.username }}</span>
            </item>
            <item>
              申请部门:
              <span v-if="depsList.length===1||action!=='new'" class="item-note">{{ dep_name }}</span>
              <span v-else class="item-note" style="overflow: visible" @click="onSelect('depName')">
                {{ dep_name }}<i class="icon ion-ios-arrow-right" style="color: #DDD;float:right;font-size:20px;margin-top:-4px;"></i>
              </span>
            </item>
            <item  v-show="action!=='new'">
              申请日期:
              <span  class="item-note">{{ request_date }}</span>
            </item>
          </div>

          <div class="mateList" :class="action" >
            <div class="mateListTop" >
              <span>报废物料列表</span>
              <span v-show="action!=='check'" class="right" @click="onSelect('add')">添加</span>
            </div>
            <div class="list">
              <div class="item"  v-for="(item,index) in store_mat_list">
                <div class="itemInfo"  >
                  <div class="numberBox" >
                    <span @click="onChange(item,'1')" >+</span>
                    <input  :disabled="action==='check'" type="number" @change="onChange(item)"  v-model="item.quantity" >
                    <span @click="onChange(item,'-1',index)" >-</span>
                  </div>
                  <h3>物料编码: <span>{{ item.mat_no }}</span></h3>
                  <p>物料名称: <span>{{ item.mat_desc }}</span></p>
                  <p>规格型号: <span>{{ item.mat_model }}</span></p>
                  <p>物料类型: <span>{{ item.mat_type_name }}</span></p>
                  <p  v-if="action==='new'" >报废原因: <input type="text" v-model="item.remark"  placeholder="请输入报废原因"> </p>
                  <p v-else >报废原因: <input disabled type="text" v-model="item.reason"  placeholder="请输入报废原因"> </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      <div class="select-box" :class="selectInfo.switch?'active':''">
        <div class="select-click" @click="selectInfo.switch=false"></div>
        <div class="select-main">
          <div class="main-header">
            <input style="margin-top:10px" v-show="selectInfo.selectType==='add'" v-model="mat_no"  @keyup ="searchMat"  type="text" placeholder="请输物料编号" >
            <p v-show="selectInfo.selectType!=='add'">{{ selectInfo.title }}</p>
          </div>
          <div class="main-list" style="height: 100%">
              <p v-for=" (item,index) in selectInfo.list "   @click="onCheck(item,index)">{{ item }}</p>
          </div>
        </div>
      </div>
      <div v-show="store_mat_list.length>0&&action==='new'" class="mw-page-footer" >
        <div  class="btns">
          <div  class="btn" @click="submit()">
            提交审批
          </div>
        </div>
      </div>
      <div v-show="action==='action'" class="mw-page-footer" >
        <div  class="btns">
          <div  class="btn" @click="submit()">
            保存
          </div>
        </div>
      </div>
      <div v-show="step==='step0'&&action==='check'&&is_scraped==0"  class="mw-page-footer" >
        <div class="btns">
          <div  class="btn" @click="scrapSubmit()">
            执行报废
          </div>
        </div>
      </div>
    </div>

</template>

<script>
  import { searchMat,scrapSubmit,getScrapDetail,scrap } from '@/api/storage/scrap'
  import { submitApprove } from '@/api/approve'
  import SwipeItem from "../../../components/swipeItem";
  import { todayDate } from '@/utils'
  import { mapGetters } from 'vuex'
    export default{
      name:'scrapDetail',
      components: {SwipeItem},
      data(){
        return {
          step:'',
          scrap_no:'',
          is_scraped:'',
          action:'',
          depsList:[],
          dep_name:'请选择',
          dep_id:'',
          mat_no:'',
          bill_id:'',
          request_date:'',
          searchMatLit:[],
          store_mat_list:[],
          selectInfo:{
            switch:false,
            selectType:'',
            title:'请选择部门',
            list:[]
          }
        }
      },
      activated(){
      },
      methods: {
        scrapSubmit(){
          const requestData={
            scrap_id:this.scrap_id
          }
          $dialog.confirm({
            theme: 'ios',
            title: '确定要执行报废吗？',
            cancelText: '取消',
            okText: '确定'
          }).then(res=>{
            if(res){
              scrap(requestData).then(res => {
                if(res.scrap_info){
                  $toast.show('执行报废成功')
                  $router.go(-1)
                }
              }).catch(error => {
                $toast.show(error.description)
              })
            }
          })

        },
        CancelEdit(){
          this.scrapDetail()
          this.action='check'
        },
        scrapDetail(){
          const requestData={
            query:{
              scrap_id:this.scrap_id
            }
          }
          getScrapDetail(requestData).then(res => {
            this.scrap_no=res.scrap_mat_info.scrap_no
            this.dep_id=res.scrap_mat_info.dep_id
            this.dep_name=res.scrap_mat_info.dep_name
            this.request_date=res.scrap_mat_info.request_date
            this.store_mat_list=res.scrap_mat_info.scrap_detail_list
            this.step=res.scrap_mat_info.approve_step
            this.is_scraped=res.scrap_mat_info.is_scraped

          }).catch(error => {
            $toast.show(error.description)
          })
        },
        submitApprove(){
          const requestData={
              approve_flag:'1',
              approve_man_id:'',
              approve_man_name:'',
              approve_step:'step10',
              approve_sugg:'',
              bill_id:this.bill_id,
              bill_type:'10'
          }
          submitApprove(requestData).then(res => {
            $router.go(-1)
            $toast.show('报废单信息提交成功')
          }).catch(error => {
            $toast.show(error.description)
          })
        },
        submit(){
          if(this.dep_name==='请选择'){
            $toast.show('请选择申请部门')
            return
          }
          let listIndexOf=0
          if(this.store_mat_list.length>0){
            this.store_mat_list.forEach((e,i)=>{
              if(e.remark===''){
                listIndexOf=1
                return
              }
            })
          }else{
            $toast.show('报废物料不能为空')
            return
          }

          if(listIndexOf==0){
            const requestData={
              scrap_mat_info:{
                approve_step:this.step,
                approve_sugg:'',
                dep_id:this.dep_id,
                dep_name:this.dep_name,
                request_date:todayDate(),
                scrap_detail_list:this.store_mat_list,
                scrap_id:this.scrap_id,
                scrap_no:this.scrap_no
              }
            }
            scrapSubmit(requestData).then(res => {
              if(this.action==='new'){
                this.bill_id=res.scrap_id
                this.submitApprove()
              }else{
                this.CancelEdit()
                $toast.show('报废单信息编辑成功')
              }

            }).catch(error => {
              $toast.show(error.description)
            })
          }else{
            $toast.show('请填写报废说明')
          }
        },
        onSelect(data){
          this.selectInfo.selectType=data
          this.selectInfo.list=[]
          this.mat_no=''
          switch (data){
            case 'add':{
              this.selectInfo.switch=true
              break
            }
            case  'depName':{
              this.depsList.forEach((e,i)=>{
                this.selectInfo.list.push(e.dep_name)
              })

              this.selectInfo.switch=true
              break
            }
          }

        },
        Delete(index){
          this.store_mat_list.splice(index, 1)
        },
        onCheck(item,index){
          switch (this.selectInfo.selectType){
            case 'add':{
              let listIndexOf=0
              this.store_mat_list.forEach((e,i)=>{
                if(e.mat_no==item){
                  listIndexOf=1
                  return
                }
              })
              if(listIndexOf===0){
                let e=this.searchMatLit[index]
                this.$set(e,'deal_flag','')
                this.$set(e,'detail_id','')
                this.$set(e,'quantity',1)
                this.$set(e,'remark','')
                this.store_mat_list.push(e)
              }else{
                $toast.show('该物料已被添加到列表中')
              }
              break
            }
            case 'depName':{

              this.dep_name=this.depsList[index].dep_name
              this.dep_id=this.depsList[index].dep_id
              break
            }
          }
          this.selectInfo.switch=false
        },
        searchMat() {//物料编号模糊查询
          if(this.mat_no.length>0){
            const requestData={
              query:{
                mat_desc:'',
                mat_id:'',
                mat_model:'',
                mat_no:this.mat_no
              }
            }
            searchMat(requestData).then(res => {
              this.searchMatLit=res.store_mat_list
              if(this.searchMatLit.length>0){
                this.selectInfo.list=[]
                this.searchMatLit.forEach((e, i) => {
                  this.selectInfo.list.push(e.mat_no)
                })
              }
            }).catch(error => {
              $toast.show(error.description)
            })
          }

        },
        onChange(e,type,index){
          if(this.action!=='check'){
          if(type==='-1'){
            if(Number(e.quantity)>1){
              e.quantity=Number(e.quantity)-1
            }else{
              $dialog.confirm({
                    theme: 'ios',
                    title: '确定要删除吗？',
                    cancelText: '取消',
                    okText: '确定'
                  }).then(res=>{
                    if(res){
                      this.Delete(index)
                    }
                  })
            }
          }else if(type==='1'){
            if(e.quantity<e.stock_qty){
              e.quantity=Number(e.quantity)+1
            }else{
              $toast.show('数量已达到库存数')
            }
          }else{
            if(e.quantity<1){
              $dialog.confirm({
                theme: 'ios',
                title: '确定要删除吗？',
                cancelText: '取消',
                okText: '确定'
              }).then(res=>{
                if(res){
                  this.Delete(index)
                }else{
                  e.quantity=1
                }
              })
            }else if(e.quantity>e.stock_qty){
              e.quantity=e.stock_qty
            }
          }
            // let quantity=Number(e.quantity)
            // if(quantity%1!==0){
            //   e.quantity=parseInt(quantity)
            // }
            // if(e.quantity<=0){
            //   $dialog.confirm({
            //     theme: 'ios',
            //     title: '确定要删除吗？',
            //     cancelText: '取消',
            //     okText: '确定'
            //   }).then(res=>{
            //     if(res){
            //       this.Delete(index)
            //     }else{
            //       e.quantity=1
            //     }
            //   })
            // }
            // if(quantity>e.stock_qty){
            //   e.quantity=e.stock_qty
            // }
          }
        },
      },
      computed:mapGetters(['userinfo','chosenCompany']),
      mounted(){
        this.action=this.$route.params.action
        this.scrap_id=this.$route.params.scrap_id
        if(this.action==='new'){
          if(this.chosenCompany.deps.length>1){
            this.depsList=this.chosenCompany.deps
          }else{
            this.depsList=this.chosenCompany.deps
            this.dep_name=this.chosenCompany.deps[0].dep_name
            this.dep_id=this.chosenCompany.deps[0].dep_id
          }
        }else if(this.action==='check'){
          this.scrapDetail()
        }

      }
    }
</script>

<style lang="scss" scoped>
  .singleDetail {
    &>.new {
      padding-top: 98px !important;
      .mateListTop{
        top: 98px !important;
      }
    }
    &>.action, &>.check {
      padding-top: 196px !important;
      .mateListTop{
        top: 196px !important;
      }
    }
    .list{
      padding-top: 30px;
    }

  }
  .item span{overflow: visible}
  input:disabled{background: #fff}
</style>
