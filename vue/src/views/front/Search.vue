<template>
  <div class="main-content">
    <!--搜索结果展示-->
  <div style="margin: 50px auto;width: 60%;text-align: left">

    <div style="margin-top: 30px;">
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in hotelData">
          <img :src="item.avatar" style="width: 100%;height: 175px;border-radius: 10px;cursor: pointer" alt="" @click="navToDetail(item.id)">
          <div style="font-size: 16px;font-weight: bold;margin-top: 10px;color: #455873FF;">{{ item.name }}</div>
          <div style="margin-top: 10px;">
            <span style="font-weight: bold;font-size: 16px;color: red">￥{{ item.price }}</span> 起
            <span style="font-weight: bold;font-size: 14px;color: #455873FF;margin-left: 20px" ><i class="el-icon-chat-line-square"></i>  1688点评</span>
          </div>
        </el-col>

      </el-row>
    </div>
  </div>
  </div>
</template>

<script>

export default {

  data() {
    let name=this.$route.query.name
    return{
      hotelData:[],
      name:name
    }
  },
  mounted() {
    this.loadHotels();
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadHotels(){
      //调用后台查询所有酒店信息接口
      this.$request.get('/hotel/selectByName?name='+this.name).then(res=>{
        if(res.code === '200'){
          this.hotelData = res.data;
        }
        else {
          this.$message.error(res.msg);
        }
      })
    },

  }
}
</script>
