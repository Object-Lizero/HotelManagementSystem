<template>
  <div class="main-content" style="width:80%;margin: 10px auto;">
    <div style="font-weight: bold;font-size: 16px;padding: 15px 30px;border-bottom: 1px solid #EEEEEE">详情信息</div>
    <div style="display: flex;margin-top: 20px">
      <div style="flex: 1;padding-left: 60px">
        <img style="height: 450px;border-radius: 10px;" :src="typeData.img" alt="">
      </div>
      <div style="flex: 1;">
        <div  style="font-size: 16px; margin: 5px 25px ">
          <div style="font-weight: bold; margin-bottom: 10px">基本信息</div>
          <div style="font-size: 14px;display: flex;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; ">
          <span style="flex:1">
                <i class="el-icon-circle-check"></i>类型：{{typeData.name}}
          </span>
            <span style="flex:1;">
                <i class="el-icon-circle-check"></i>间数：{{typeData.num}}
          </span>
            <span style="flex:1;">
                <i class="el-icon-circle-check"></i>酒店：{{typeData.hotelName}}
          </span>
          </div>
        </div>
        <div style="font-size: 16px; margin: 5px 25px ">
          <div style="font-weight: bold; margin-bottom: 10px">洗浴用品</div>
          <div style="display: flex;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; ">
          <span style="flex:1">
                <i class="el-icon-circle-check"></i>牙刷
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>牙膏
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>沐浴露
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>洗发水
          </span>
          </div>
          <div style="display: flex;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; ">
          <span style="flex:1">
                <i class="el-icon-circle-check"></i>护发素
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>香皂
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>梳子
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>剃须刀
          </span>
          </div>
        </div>
        <div style="font-size: 16px; margin: 5px 25px ">
          <div style="font-weight: bold; margin-bottom: 10px">卫浴设施</div>
          <div style="display: flex;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; ">
          <span style="flex:1">
                <i class="el-icon-circle-check"></i>私人浴室
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>私人卫生间
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>吹风机
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>淋浴
          </span>
          </div>
          <div style="display: flex;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; ">
          <span style="flex:1">
                <i class="el-icon-circle-check"></i>毛巾
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>浴巾
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>拖鞋
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>24H热水
          </span>
          </div>
        </div>
        <div style="font-size: 16px; margin: 5px 25px ">
          <div style="font-weight: bold; margin-bottom: 10px">客房设施</div>
          <div style="display: flex;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; ">
          <span style="flex:1">
                <i class="el-icon-circle-check"></i>空调
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>手动窗帘
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>鸭绒被
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>毛毯
          </span>
          </div>

        </div>
        <div style="font-size: 16px; margin: 5px 25px ">
          <div style="font-weight: bold;margin-bottom: 10px ">媒体科技</div>
          <div style="display: flex;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; ">
          <span style="flex:1">
                <i class="el-icon-circle-check"></i>电视机
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>有线频道
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>智能门锁
          </span>
            <span style="flex:1">
                <i class="el-icon-circle-check"></i>电影服务
          </span>
          </div>
          <div style="display: flex;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; ">
          <span style="flex:1">
                <i class="el-icon-circle-check"></i>智能客控
          </span>
          </div>

        </div>

        <div style="font-size: 16px;margin-top: 10px; margin-left: 25px;color: red">价格：￥{{typeData.price}}/晚</div>
        <div style="margin-top: 20px; margin-left: 25px;">
          <el-button type="warning">加入收藏</el-button>
          <el-button type="success">立即预定</el-button>
        </div>
      </div>
    </div>
    <div style="font-weight: bold;font-size: 16px;padding: 15px 30px;border-bottom: 1px solid #EEEEEE">评论信息</div>
  </div>
</template>

<script>

export default {

  data() {
    //获取主页点击客房详细页面时带的参数 酒店id
    let typeId = this.$route.query.id
    return {
      typeData:{},
      typeId:typeId,
    }
  },
  mounted() {
    this.loadType()
  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadType(){
      this.$request.get('/type/selectById?id='+this.typeId).then(res=>{
        if(res.code === '200'){
          this.typeData = res.data;
        }else{
          this.$message.error(res.msg)
        }
      })
    }
  }
}

</script>
