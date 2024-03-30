<template>
  <div class="main-content" style="width:80%;margin: 10px auto;">
    <div style="font-weight: bold;font-size: 16px;padding: 15px 30px;border-bottom: 1px solid #EEEEEE">
      <span>详情信息</span>
      <span v-if="typeData.num===0" style="margin-left: 350px; font-size: 16px ;color: red;font-weight: bold;">当前房型，暂无剩余，请选择其他房间</span>

    </div>
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
          <el-button type="warning" @click="addCollect()">加入收藏</el-button>
          <el-button type="success" @click="initDialog()">立即预定</el-button>
        </div>
      </div>
    </div>
    <div style="font-weight: bold;font-size: 16px;padding: 15px 30px;border-bottom: 1px solid #EEEEEE">评论信息</div>
    <div></div>
<!--    新增订单对话框-->
    <div>
      <el-dialog title="请选择时间" :visible.sync="fromVisible" width="28%" :close-on-click-modal="false" destroy-on-close>
        <el-form label-width="100px" style="padding-right: 50px" >
          <el-form-item prop="inTime" label="入住时间">
            <el-date-picker v-model="inTime" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" :picker-options="pickerOptions" style="width: 100%;"></el-date-picker>
          </el-form-item>
          <el-form-item prop="outTime" label="离开时间">
            <el-date-picker v-model="outTime" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"  :picker-options="pickerOptions" style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="fromVisible = false">取 消</el-button>
          <el-button type="primary" @click="save()">确 定</el-button>
        </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>

export default {

  data() {
    //获取主页点击客房详细页面时带的参数 酒店id
    let typeId = this.$route.query.id
    return {
      typeData: {},
      typeId: typeId,
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      fromVisible: false,
      inTime: null,
      outTime: null,
      pickerOptions: {
        //时间选择日期禁用处理
        disabledDate(time) {
          // 获取当前日期
          const currentDate = new Date();
          // 获取今天的 00:00:00 的时间戳
          const today = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate()).getTime();
          // 获取要禁用的日期的时间戳（昨天及之前的日期）
          const targetDay = new Date(time.getFullYear(), time.getMonth(), time.getDate()).getTime();
          // 如果时间戳小于今天的时间戳，则禁用
          return targetDay < today;
        }
      },

    }
  },
  mounted() {
    this.loadType()
  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {

    loadType() {
      this.$request.get('/type/selectById?id=' + this.typeId).then(res => {
        if (res.code === '200') {
          this.typeData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    addCollect() {
      //用于接口传参的对象
      let data = {
        userId: this.user.id,
        typeId: this.typeId
      }
      this.$request.post('/collect/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success("收藏成功")
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    // 打开选择日期对话框
    initDialog() {
      this.inTime=null
      this.outTime=null
      this.fromVisible = true

    },
    save() {
      let data = {
        userId: this.user.id,
        typeId: this.typeData.id,
        hotelId: this.typeData.hotelId,
        inTime: this.inTime,
        outTime: this.outTime,
      }

      this.$request.post('/orders/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success("预定成功")
          this.fromVisible = false
          // TODO  试着添加支付功能  跳转支付界面
        } else if (res.code === '5008'){
          this.$message.warning(res.msg)
          this.fromVisible = false
        }else if(res.code === ' 5007') {
          this.$message.warning(res.msg)
        }else {
          this.$message.error(res.msg)
          this.fromVisible = false
        }
      })
    }
  }
}

</script>
