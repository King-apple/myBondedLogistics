<template>
    <div>
      <basic-container>
          <iframe id="reportFrame" width="100%" height="750" :src="url">
            
          </iframe>
      </basic-container>
    </div>
  </template>
  <script>
  
    import request from "@/router/axios";
    import "@/styles/common/edit.scss";
    import { mapGetters } from "vuex";   
    import { mapState } from "vuex";
    import Vue from "vue";
   
    export default {      
      data() {
        return {
          url:null,
        };
      },
      computed: {
        ...mapGetters(["permissions"]),
        ...mapState({
          userInfo: (state) => state.user.userInfo,
        }),
      },
      created() {
        this.url="http://10.168.113.227:8080/webroot/decision/view/report?viewlet=DZLH/shipment.cpt&SUBUNIT_COD=" + this.userInfo.subunitCod ;
      },      
      mounted: function () {},
      methods: {
       
        

      },
    };
    </script>