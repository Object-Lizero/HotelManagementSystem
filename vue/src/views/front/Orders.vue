<template>
  <div class="main-content">
    <div class="table" style="margin: 20px auto;width: 92%;">
      <el-table :data="tableData" stripe  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column label="图片">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 80px; height: 60px; border-radius: 10px;" v-if="scope.row.typeImg"
                        :src="scope.row.typeImg" :preview-src-list="[scope.row.typeImg]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="orderId" label="订单编号" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="预定人" show-overflow-tooltip></el-table-column>
<!--TODO 改成*先生/*女士   用户表增加性别        -->
        <el-table-column prop="hotelName" label="酒店"></el-table-column>
        <el-table-column prop="typeName" label="客房类型"></el-table-column>
        <el-table-column prop="time" label="预定时间" width="150"></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column prop="inTime" label="入住时间"></el-table-column>
        <el-table-column prop="outTime" label="离开时间"></el-table-column>
        <el-table-column prop="days" label="入住天数"></el-table-column>
        <el-table-column prop="status" label="订单状态"></el-table-column>
        <!--        <el-table-column prop="user" label="创建人"></el-table-column>-->

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
      tableData:{},
      pageNum:1,
      pageSize:5,
      total:0
    }
  },
  mounted() {
    this.loadOrders(1)
  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadOrders(pageNum){
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/orders/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          userId:this.user.id
        }
      }).then(res => {
        if(res.code === '200'){
          this.tableData = res.data?.list
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
