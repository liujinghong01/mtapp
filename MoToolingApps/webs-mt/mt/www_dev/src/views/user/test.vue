<template>
    <section style="height: 100%">
      <header>
       fdsafdsa
      </header>
      <main>
        <div class="content">
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
          <cst-item label="设计费"  :val="300"  editType="number" action="edit"></cst-item>
        </div>
      </main>
      <footer>
        <a href="http://23.83.239.14/app/test.html">fdsaf</a>

      </footer>
    </section>
</template>
<script>
  import CstItem from "../../components/cstItem";
  export default{
    components: {CstItem},
    data(){
      return{
        tabs:['头信息','明细信息'],
        tabIndex:0
      }
    },
    methods:{
      onTabClick(){

      }
    }
  }
</script>
<style scoped lang="scss">
  header, footer, main {
    display: block;
  }
  header {
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    height: 60px;
    visibility: visible;
    width: 100%;
    background-color: #416eb6;
    color: whitesmoke;
  }
  footer {
    position: fixed;
    height: 34px;
    left: 0;
    right: 0;
    bottom: 0;
  }
  main {
    /* main绝对定位，进行内部滚动 */
    position: absolute;
    top: 60px;
    bottom: 34px;
    width: 100%;
    /* 使之可以滚动 */
    overflow-y: hidden;

    .content{
      height: 100%;
      -webkit-overflow-scrolling: touch;
      overflow-y: scroll;
    }
  }


</style>
