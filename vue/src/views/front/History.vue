<template>
  <div class="main-content">
    <div class="table" style="margin: 20px auto;width: 92%;">
      <el-table :data="historyData" stripe  @selection-change="handleSelectionChange">

        <el-table-column prop="orderId" label="订单编号" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="预定人" show-overflow-tooltip></el-table-column>
        <el-table-column prop="hotelName" label="酒店"></el-table-column>
        <el-table-column prop="typeName" label="客房类型"></el-table-column>
        <el-table-column prop="roomName" label="房间号"></el-table-column>
        <el-table-column prop="inTime" label="入住时间"></el-table-column>
        <el-table-column prop="outTime" label="退房时间"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="danger" size="mini" @click=del(scope.row.id)>删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>

export default {

  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      historyData:{},
      pageNum:1,
      pageSize:5,
      total:0
    }
  },
  mounted() {
    this.loadHistory(1)
  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadHistory(pageNum){
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/checkin/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          userId:this.user.id
        }
      }).then(res => {
        if(res.code === '200'){
          this.historyData = res.data?.list
          this.total = res.data?.total

        }else{
          this.$message.error(res.msg)
        }
      })
    },
    //切换分页
    handleCurrentChange(pageNum) {
      this.loadOrders(pageNum)
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },

  }
}

</script>
