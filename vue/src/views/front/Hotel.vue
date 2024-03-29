<template>
  <div class="main-content">
    <div style="display: flex;width: 70%;margin: 25px auto;">
      <div style="flex: 1;">
        <img :src="hotelData.avatar" alt="" style="width: 100%;height: 250px; border-radius: 10px">
      </div>
      <div style="flex: 1; margin-left: 30px">
        <div style="font-size: 18px;font-weight: bold;color: #0F294DFF">{{hotelData.name}}</div>
        <div style="color: #0F294DFF ;margin-top: 10px">酒店官网：<a :href="hotelData.url" target="_blank">{{hotelData.url}}</a></div>
        <div style="color: #0F294DFF ;margin-top: 10px">联系电话：{{hotelData.phone}}</div>
        <div style="color: #0F294DFF ;margin-top: 10px">联系邮箱：{{hotelData.email}}</div>
        <div style="color: #0F294DFF ;margin-top: 10px">客房价格：<span style="color: red">￥{{hotelData.price}} </span> 起</div>
<!--       样式设置 至多显示5行，多了 显示。。。-->
        <div style="color: #0F294DFF ;margin-top: 10px;      display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 5;overflow: hidden;text-overflow: ellipsis;">酒店介绍：{{hotelData.description}}</div>
      </div>
      <div style="flex: 1;"></div>
    </div>
    <div style="width: 70%;margin: 0 auto;">
        <el-row gutter="20">
          <el-col :span="6" v-for="item in TypeData">
            <img :src="item.img" style="width: 100%;height: 200px;border-radius: 10px; cursor: pointer" @click="ToDetail(item.id)" >
            <div style="font-size: 16px;margin-top: 10px;color: #455873FF;">
              <span style="font-weight: bold">{{ item.name }}</span>
              <span style="font-weight: bold;font-size: 16px;color: red">￥{{ item.price }}</span> 起
              <span style="font-size: 14px;color: #455873FF;margin-left: 20px;" ><i class="el-icon-s-home" style="margin: 3px"></i>剩余{{item.num}}间</span>
            </div>
          </el-col>
        </el-row>
    </div>
  </div>
</template>

<script>

export default {

  data() {
    //获取主页点击酒店详细页面时带的参数 酒店id
    let hotelId = this.$route.query.id
    return {
      hotelData:{},
      TypeData:[],
      hotelId:hotelId,
    }
  },
  mounted() {
    this.loadHotel()
    this.loadTypes()
  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadHotel(){
      this.$request.get('/hotel/selectById?id='+this.hotelId).then(res=> {
            if(res.code === '200'){
              this.hotelData = res.data
            }else {
              this.$message.error(res.msg)
            }
          }
      )
    },
    ToDetail(id){
      location.href= '/front/detail?id='+id
    },
    loadTypes(){
      this.$request.get('/type/selectByHotelId?id='+this.hotelId).then(res=> {
            if(res.code === '200'){
              this.TypeData = res.data
            }else {
              this.$message.error(res.msg)
            }
          }
      )
    }
  }
}

</script>
