<template>
  <div class="mw-page page company" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>选择部门</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content" v-if="type==='batch'">
      <von-checkbox :options="depNames" v-model="chosenDeps" theme="positive"></von-checkbox>
    </div>
    <div class="mw-page-content" v-else-if="type==='single'">
      <von-radio :options="depNames" v-model="chosenDep"></von-radio>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="menuClick">确定</div>
      </div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { getSubDeps ,batchMoveMember} from '@/api/company'
  export default{
    name:'chooseDep',
    data(){
      return{
        depList:[],
        chosenDeps:[],
        depNames:[],
        members:[],
        dep_id:'',
        type:'batch',
        chosenDep:''
      }
    },
    created(){
      this.members = this.$route.query.members
      this.dep_id = this.$route.params.dep_id!='null'?this.$route.params.dep_id:''
      this.type = this.$route.params.type
      this.getSubDeps();
    },
    computed:{

    },
    methods:{
      //获取公司部门以及成员
      getSubDeps(){
        const that = this;
        if(!that.chosenCompany.company_id){
          return
        }
        const requestData = {
          company_id:that.chosenCompany.company_id,
        }
        getSubDeps(requestData).then(res=>{
          console.log(res);
//          that.depList = res.deps;
          if(!res.deps||res.deps.length===0){
              return
          }
          let tmpDeps = [];
          let tmpNames = [];
          for(let i in res.deps){
            tmpNames.push(res.deps[i].dep_name)
            tmpDeps.push(res.deps[i])
            if(res.deps[i].deps.length>0){
              for(let j in res.deps[i].deps){
                tmpNames.push(res.deps[i].dep_name+'>'+res.deps[i].deps[j].dep_name);
                tmpDeps.push(res.deps[i].deps[j])
              }
            }
          }
          that.depList = tmpDeps;
          that.depNames = tmpNames;
        }).catch(error=>{
          console.log(error);
          $toast.show(error.description)
        })
      },

      menuClick(){
        const that = this;
        const requestData = {
          company_id:that.chosenCompany.company_id,
          dep_ids:that.type === 'single'?that.chosenDep:this.dep_ids,
          member_ids:that.members,
          member_dep_id:that.dep_id
        }
        batchMoveMember(requestData).then(res=>{
          history.go(-1);
        }).catch(error=>{
          $toast.show(error.description)
        })
      },

    },
    computed:{
      ...mapGetters(['chosenCompany']),
      dep_ids(){
        let a = []
        this.depList.forEach((o,i)=>{
          if (this.chosenDeps.indexOf(i)>-1) {
            a.push(o.dep_id)
          }
        })
        return a
      }
    }
  }
</script>
