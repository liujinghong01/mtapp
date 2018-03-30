<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="back()"></i>
      <span>加工费明细</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <list>
        <!--<item style="border-bottom: 1px solid #ddd;margin: 0" v-show="process_list.length>0">-->
        <item v-if="process_list.length>0" style="border-bottom: 1px solid #ddd;margin: 0"  >
          <p class="label">总计(元)</p>
          <p class="value" style="padding-left: 16px">{{totalPrice}} </p>
        </item>
        <cst-item v-else label="总计(元):" v-model="process_cost" editType="number"  placeholder="请输入加工费" action="action"></cst-item>

        <swipe-item style="margin: 0"
          swipeItemText="删除"
          :canSwipe="true"
          v-on:SwipeItemClick="delItem"
          v-on:ItemClick="onItemClick"
          v-on:SwipeLeft="onSwipeLeft"
          v-for="item in process_list"
          :hasArrow="true"
          :obj="item">
          <span class="label">{{item.process_name}}</span><span class="value">{{item.process_cost}} 元</span>
        </swipe-item>
        <button @click="addOneProcess" v-show="action ==='edit' || action ==='new'" class="button button-calm button-block button-outline">添加加工项目</button>
      </list>
    </div>
    </div>
</template>
<script>
  import swipeItem from '@/components/swipeItem'
  import  cstItem  from '@/components/cstItem'
  import { addProcess,processList,processItemDel } from '@/api/customerOrder/compute'
  import sess from '@/utils/sess'
  export default{
    name:'cst_process_list',
    components:{
      swipeItem,cstItem
    },
    data(){
      return {
        process_cost:'',
        process_list:[

        ],
        action:'',
        currItem:{

        }
      }
    },
    computed:{
      totalPrice(){
        let total = 0;
        if(this.process_list.length > 0){
          for(let i in this.process_list){
            if(parseFloat(this.process_list[i].process_cost)){
              total += parseFloat(this.process_list[i].process_cost)
            }
          }
          return total.toFixed(2)
        }else{
          return 0.00
        }
      }
    },
    mounted(){
      this.compute_id = this.$route.params.compute_id
      this.action = this.$route.params.action
    },
    activated(){
      const processItem = sess.get('process_detail')
      if(processItem){
        if(processItem.id===''){
          this.process_list.push(processItem)
        }else{

          for(let i in this.process_list){
            console.log(this.process_list[i].id === processItem.id)
            if(this.process_list[i].id === processItem.id){
              this.process_list[i] = processItem
            }
          }
        }
        this.saveData()
        sess.remove('process_detail')
      }else{
        this.getProcessList()
      }
    },
    methods:{
      back(){
        if(this.process_list.length<1&&this.process_cost>0){
          sess.set('process_cost',this.process_cost)
          $router.go(-1)
          return
        }else{
          $router.go(-1)
        }

      },
      getProcessList(){
        const reqData = {
          compute_id:this.compute_id
        }
        processList(reqData).then(res=>{
          console.log(res)
          this.process_list = res.process_list
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      backClick(){
        history.go(-1)
      },
      closeMask(){
        this.currItem.swipe = false;
        this.currItem = {}
      },
      delItem(item){
        $dialog.confirm({
          theme:'ios',
          // 标题
          title: '确定要删除'+item.process_name+'吗?',
          // 取消按钮文本
          cancelText: '取消',
          // 确定按钮文本
          okText: '确定'
        }).then((res) => {
          console.log('confirm result: ', res)
          if(res){
            const reqData = {
              compute_id:this.compute_id,
              processItemIds:[item.id]
            }
            processItemDel(reqData).then(res=>{
              $toast.show('删除成功')
              this.process_list = this.process_list.filter(o=>{
                return o.process_name != item.process_name
              })
            }).catch(error=>{

            })
          }

        })
      },
      onItemClick(item){
        if(this.currItem.swipe){
          this.currItem.swipe = false;
          return;
        }
        console.log(item)
        //$router.forward('/customerOrder/compute/cst_process_detail/'+this.action+'/'+encodeURIComponent(JSON.stringify(item)));
        $router.forward({
          path:'/customerOrder/compute/cst_process_detail',
          query:{
            action:this.action,
            process_detail:encodeURIComponent(JSON.stringify(item))
          }
        })
      },
      onSwipeLeft(item){
        this.$set(item,'swipe',true);
        this.currItem = item
      },
      saveData(){
        const reqData = {
          compute_id:this.compute_id,
          process_list:this.process_list
        }

        addProcess(reqData).then(res=>{
          this.getProcessList()
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      addOneProcess(){
        $router.forward({
          path:'/customerOrder/compute/cst_process_detail',
          query:{
            action:'new',
            process_detail:this.compute_id
          }
        });
      }
    },
  }
</script>
<style scoped lang="scss">
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

</style>
