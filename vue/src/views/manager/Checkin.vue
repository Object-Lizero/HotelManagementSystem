 <template>
  <div>
    <div class="search">
      <el-input placeholder="请输入订单编号" style="width: 200px" v-model="orderId"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd" v-if="user.role === 'HOTEL'">入住登记</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="orderId" label="订单编号" ></el-table-column>
        <el-table-column prop="userName" label="入住人" show-overflow-tooltip></el-table-column>
        <el-table-column prop="hotelName" label="酒店"></el-table-column>
        <el-table-column prop="typeName" label="客房类型"></el-table-column>
        <el-table-column prop="roomName" label="房间编号"></el-table-column>
        <el-table-column prop="inTime" label="入住时间" width="90"></el-table-column>
        <el-table-column prop="outTime" label="离开时间" width="90"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" size="mini" @click=checkOut(scope.row.id) :disabled="scope.row.outTime!=null">退房</el-button>
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

    <el-dialog title="请填写信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="选择订单" prop="orderId">
          <el-select v-model="form.orderId" placeholder="请选择订单" style="width: 100%" @change="loadRoomData(form.orderId)">
            <el-option v-for="item in ordersData"  :label="item.orderId +'-' + item.userName +'-' +item.typeName" :value="item.orderId" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分配客房" prop="orderId">
          <el-select v-model="form.roomId" placeholder="请选择客房" style="width: 100%">
            <el-option v-for="item in roomData"  :label="item.name " :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>

<!--        <el-form-item prop="inTime" label="入住时间">-->
<!--          <el-date-picker v-model="form.inTime" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" :picker-options="pickerOptions" style="width: 100%;"></el-date-picker>-->
<!--        </el-form-item>-->

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Checkin",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      orderId: null,
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      form:{},
      rules: {
        name: [
          {required: true, message: '请输入标号', trigger: 'blur'},
        ],
        typeId: [
          {required: true, message: '请输入房间类型', trigger: 'blur'},
        ]
      },
      typeData:[],
      roomData:[],
      ordersData: [],
      ids: [],
      fromVisible:false,
      // pickerOptions: {
      //   //时间选择日期禁用处理
      //   disabledDate(time) {
      //     // 获取当前日期
      //     const currentDate = new Date();
      //     // 获取今天的 00:00:00 的时间戳
      //     const today = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate()).getTime();
      //     // 获取要禁用的日期的时间戳（昨天及之前的日期）
      //     const targetDay = new Date(time.getFullYear(), time.getMonth(), time.getDate()).getTime();
      //     // 如果时间戳小于今天的时间戳，则禁用
      //     return targetDay < today;
      //   }
      // },



    }


  },
  created() {
    this.load(1)
    this.loadOrders()
  },
  methods: {

    loadOrders(){
      this.$request.get('/orders/selectCheckin?id='+this.user.id).then(res=>{
        if(res.code === '200'){
          this.ordersData = res.data
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    loadRoomData(orderId){
      /* 下拉菜单选中选项后   该选项框绑定的属性form.orderId  作为参数传到该方法中，
      向后台发送请求根据订单编号查询房型，再查询该房型剩余空闲的房间显示再下面的下拉框中 */
      this.$request.get('/room/selectByTypeId?orderId='+orderId).then(res=>{
        if(res.code === '200'){
          this.roomData = res.data
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$request({
        url: this.form.id ? '/checkin/update' : '/checkin/add',
        method: this.form.id ? 'PUT' : 'POST',
        data: this.form
      }).then(res => {
        if (res.code === '200') {  // 表示成功保存
          this.$message.success('保存成功')
          this.load(1)
          this.fromVisible = false
        } else {
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/checkin/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/checkin/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/checkin/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          orderId: this.orderId,
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
    reset() {
      this.orderId = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    checkOut(id){
      this.$request.get('/checkin/out/'+id).then(res=>{
        if(res.code === '200') {
          this.$message.success('退房成功')
          this.load(1);
        }
        else{
          this.$message.error(res.msg)
        }

      })
    }
  }
}
</script>

<style scoped>

</style>
