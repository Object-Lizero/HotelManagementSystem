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
            <el-button plain type="primary" size="mini" @click="comment(scope.row)" v-if="scope.row.status === '已退房'">评价</el-button>
            <el-button plain type="danger" size="mini" @click="del(scope.row.id)">删除</el-button>
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
    <!-- 评价弹窗-->
    <div>
      <el-dialog title="请输入评价" :visible.sync="fromVisible" width="45%" :close-on-click-modal="false" destroy-on-close>
        <el-form label-width="100px" style="padding-right: 50px" v-model="form" >
          <el-form-item prop="content" label="评价内容">
            <el-input type="textarea" v-model="form.content"></el-input>
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
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      tableData:[],
      pageNum:1,
      pageSize:5,
      total:0,
      fromVisible:false,
      form: {},
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
    del(id){
      this.$request.delete('/orders/deleteById?id='+id).then(res=>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.loadOrders(1)
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    /*保存评价信息*/
    save(){
      if (!this.form.content) {
        this.$message.warning('请输入评价内容')
        return
      }
      let data = {
        content : this.form.content,
        typeId:this.form.typeId,
        hotelId:this.form.hotelId,
        userId:this.form.userId,
        role:this.user.role,
        parentId:0

      }
      this.$request.post('/comment/add',data).then(res=>{
        if(res.code === '200'){
          this.$message.success('评价成功')
          this.form = {}
          this.fromVisible = false
        }else
        {
          this.$message.error(res.msg)
        }
      })
    },
    //打开评价弹窗
    comment(row){
      this.fromVisible = true
      this.form = row
    }

  }
}

</script>
