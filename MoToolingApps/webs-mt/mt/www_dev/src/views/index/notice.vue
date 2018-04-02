<template>
  <div class="mw-page page approve" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left"></i>
      <span>通知</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <div style="height: 30%">
      </div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  export default {
    name:'notice',
    computed:mapGetters([
      'hideNavbar'
    ]),

  }
</script>
