<template>
    <div class="mw-page page mats" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span v-if="$route.params.app_flag==='step30'">BOM详情</span>
            <span v-else>BOM编辑</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
            <div style="height: 60px">
              <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
            </div>
          <div style="flex: 1;position: relative">
            <div class="content" v-show="tabIndex===0">
                  <div v-for="(item,index) in list[tabIndex].bom_node_list" class="bom-swipe-item"  @touchstart="touchstart($event)"  @touchend="touchend($event)" >
                    <div class="swipe-content">
                      <div  @click="onItemClick(item)">
                        <h3 style="text-overflow: ellipsis;display: block;white-space: nowrap;">物料编号：<span >{{item.mat_no}}</span></h3>
                        <h3>物料名称：<span>{{item.mat_name}}</span></h3>
                        <p>
                          <span>规格尺寸({{item.spec}})</span>
                          <span>材质({{item.rawnature_name}})</span>
                        </p>
                        <p>
                          <span>数量({{item.total_qty}})</span>
                        </p>
                      </div>
                      <span v-show="item.is_purchase==='1'" @click="goNodesList(item)" class="icon ion-ios-arrow-right" ></span>
                    </div>
                    <div class="swipe-click" @click="offSwipe('点击事件')"></div>
                    <div class="swipe-side" @click="bomNodeDelete(item,index)">
                      <p>删除</p>
                    </div>
                  </div>
            </div>
            <div class="content" v-show="tabIndex===1">
                <div v-for="(item,index) in list[tabIndex].bom_node_list" class="bom-swipe-item"  @touchstart="touchstart($event)"  @touchend="touchend($event)" >
                  <div class="swipe-content" @click="onItemClick(item)">
                    <div>
                      <h3 style="text-overflow: ellipsis;display: block;white-space: nowrap;">物料编号：<span>{{item.mat_no}}</span></h3>
                      <h3>物料名称：<span>{{item.mat_type}}</span></h3>
                      <p>
                        <span>规格尺寸({{item.spec}})</span>
                        <span>材质({{item.rawnature_name}})</span>
                        <span>数量({{item.total_qty}})</span>
                      </p>
                    </div>
                  </div>
                  <div class="swipe-click" @click="offSwipe"></div>
                  <div class="swipe-side" @click="bomNodeDelete(item,index)">
                    <p>删除</p>
                  </div>
                </div>
            </div>
            <div class='content' v-show='tabIndex===2'>
                <div v-for="(item,index) in list[tabIndex].bom_node_list" v-show="!((app_flag==='step30'||app_flag==='step0') && item.is_purchase == 0 )"  class='bom-swipe-item'  @touchstart='touchstart($event)'  @touchend="touchend($event)"  >
                  <div class='swipe-content' @click="electrode(item)" >
                    <div>
                      <h3 style="text-overflow: ellipsis;display: block;white-space: nowrap;">物料编号：<span>{{item.mat_no}}</span></h3>
                      <h3>物料名称：<span>{{item.mat_type}}</span></h3>
                      <p>
                        <span>规格尺寸({{item.spec}})</span>
                        <span>材质({{item.rawnature_name}})</span>

                      </p>
                      <p>
                        <span>数量({{item.total_qty}})</span>
                      </p>
                    </div>
                    <span v-if="item.is_purchase==='1'" class="" ></span>
                    <span v-else v-show="action==='check'&&app_flag!=='step30'&&app_flag!=='step0'" class="right">添加电极</span>
                  </div>
                  <div class='swipe-click' @click='offSwipe'></div>
                  <div class='swipe-side' @click="bomNodeDelete(item,index)">删除</div>
                </div>
            </div>
          </div>
        </div>
      <mw-prompt :show="showApprove"
                 :okFunction="approveProcess"
                 v-on:hidePop="showApprove = false"
                 title="核算单审批"
                 placeholder="请输入审批意见"
                 v-model="approve.suggestion"
                 okText="同意"
                 cancelText="不同意"
                 :cancelFunction="approveProcess"
                 promptType="textarea"
      ></mw-prompt>
        <div class='mw-page-footer'>
            <div  v-if="app_flag!=='step30'&&app_flag!=='step0'" class='btns'>
                <div  v-show="tabIndex!==2" class='btn' @click="$router.forward('/mats/bom/nodeDetail/new/'+node_id+'/'+tabIndex+'/'+app_flag)">
                    添加
                </div>
                <div class="btn" @click="submit">
                    提交审批
                </div>
            </div>
          <div v-else-if="$route.params.action==='approve'" class="btns">
            <div  class="btn" @click="showApprove = true">
              审批
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import SwipeItem from "../../../components/swipeItem"
  import { submitApprove } from '@/api/approve'
  import { bomNodeList,bomNodeDelete,commitBom } from '@/api/mats'
  import MwPrompt from "../../../components/mwPrompt";
  import { mapGetters } from 'vuex'
    export default{
      name:'bomNodes',
      components: {
        MwPrompt,
        SwipeItem},
      data(){
        return {
          action:'',
          swipeX:0,
          tabs:['半成品','标准件','电极'],
          tabIndex:0,
          node_id:'',
          bom_id:'',
          app_flag:'',
          showApprove:false,
          approve:{
            suggestion:''
          },
          list:[
            {
              bom_node_list: [
//                {
//                  "height": 35326,
//                  "is_purchase": "测试内容60ew",
//                  "length": 86815,
//                  "mat_id": 55203,
//                  "mat_no": "测试内容nj77",
//                  "mat_type": "测试内容5v3e",
//                  "node_id": 60111,
//                  "rawnature_name": "测试内容q5mk",
//                  "spec": "测试内容672h",
//                  "total_qty": "测试内容iz75",
//                  "width": 46064
//                }
              ]},
            {
              bom_node_list: [
//                {
//                  "height": 35326,
//                  "is_purchase": "测试内容60ew",
//                  "length": 86815,
//                  "mat_id": 55203,
//                  "mat_no": "测试内容nj77",
//                  "mat_type": "测试内容5v3e",
//                  "node_id": 60111,
//                  "rawnature_name": "测试内容q5mk",
//                  "spec": "测试内容672h",
//                  "total_qty": "测试内容iz75",
//                  "width": 46064
//                }
              ]},
            {
              bom_node_list: [
//                {
//                  "height": 35326,
//                  "is_purchase": "测试内容60ew",
//                  "length": 86815,
//                  "mat_id": 55203,
//                  "mat_no": "测试内容nj77",
//                  "mat_type": "测试内容5v3e",
//                  "node_id": 60111,
//                  "rawnature_name": "测试内容q5mk",
//                  "spec": "测试内容672h",
//                  "total_qty": "测试内容iz75",
//                  "width": 46064
//                }
              ]}
          ],

        }
      },
      computed:{
        ...mapGetters(['userinfo'])
      },
      methods: {
        onInfinite(done){
          done()
        },
        submit(){
          const reqData = {
            bill_id: this.bom_id,
            bill_type: '20',
            approve_flag: '1',
            approve_step: this.app_flag
          }
          submitApprove(reqData).then(res=>{
            $toast.show('提交成功')
            this.$router.go(-1)
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        onRefresh(){},
        getData(){
          const that = this;
          const requestData={
            query:{
              bom_id:that.bom_id,
              node_id:that.node_id,
              mat_type:that.tabs[that.tabIndex]
            }
          }
          bomNodeList(requestData).then(res=>{
            this.list[that.tabIndex].bom_node_list=res.bom_node_list
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
                this.list[this.tabIndex].bom_node_list.splice(index,1)
                $toast.show('删除成功')
              }).catch(error=>{
                $toast.show(error.description)
              })
            }
          })
        },
        onMenuClick(){

        },
        touchstart(){
          this.swipeX=event.targetTouches[0].clientX
        },
        touchend(){
          var X=event.changedTouches[0].clientX+30
          if(this.app_flag==='step30'||this.tabIndex===2){
            return
          }
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
        onTabClick(index){
          if (index===this.tabIndex) {
              return
          }
          this.tabIndex = index
          this.getData()
        },
        onItemClick(item){
          this.$router.forward('/mats/bom/nodeDetail/check/'+item.node_id+'/'+this.tabIndex+'/'+this.app_flag)
        },
        goNodesList(item){
          this.$router.forward({
            path:'/mats/bom/nodes/childNode',
            query:{
              type:item.mat_type,
              bom_id:this.bom_id,
              node_id:item.node_id,
              app_flag:this.app_flag
            }
          })
        },
        electrode(item){
          if(item.is_purchase==='0'){
            if(this.app_flag!=='step30'){
              this.$router.forward('/mats/bom/nodeDetail/new/'+item.node_id+'/2/'+this.app_flag)
            }else{
              $toast.show('该半成品下没有电极')
            }
          }else{
            this.$router.forward({
              path:'/mats/bom/nodes/childNode',
              query:{
                bom_id:this.bom_id,
                node_id:item.node_id,
                app_flag:this.app_flag,
                type:'电极'
              }
            })
          }

        },
        delItem(item){
          console.log(item)
        },
        approveProcess(flag){
          console.log('approveProcess')
          flag = flag === 'ok' ? 1 : 2
          const reqData = {
            approve_sugg:this.approve.suggestion,
            bill_id: this.bom_id,
            bill_type: 20,
            approve_step: this.app_flag,
            approve_man_id:this.userinfo.uid,
            approve_man_name:this.userinfo.username,
            approve_flag:flag
          }
          submitApprove(reqData).then(res=>{
//          $toast.show('提交成功')
            history.go(-1)
          }).catch(error=>{
            $toast.show(error.description)
          })
        }
      },
      activated:function(){
        this.getData()
      },
      mounted(){
        this.node_id = this.$route.params.node_id
        this.bom_id = this.$route.params.bom_id
        this.action=this.$route.params.action
        this.app_flag=this.$route.params.app_flag
      }
    }
</script>

<style lang="scss" scoped>

</style>
