<template>
  <div>
    <el-card class="box-card" shadow="never">
      <el-button size="mini" @click="add">添加</el-button>
    </el-card>
    <el-table :data="tableData" border style="width: 100%;margin-top: 20px;"
      :header-cell-style="{background: 'rgb(242, 243, 244)',color:'#515a6e'}">
      <el-table-column fixed prop="id" label="ID" v-if="false">
      </el-table-column>
      <el-table-column prop="name" label="菜品种类">
      </el-table-column>
      <el-table-column prop="sort" label="排序">
      </el-table-column>
      <el-table-column :formatter="dateFormat" prop="createtime" label="创建时间">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="look(scope.row)">查看</el-button>
          <el-button size="mini" @click="update(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="del(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top:20px;float:right;">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize" :current-page="currentPage" background layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog id="foodTypeManage" :title="title" :visible.sync="dialogFormVisible">
        <el-form :rules="rules" :model="foodType" ref="foodType">
          <el-form-item style="display:none;" label="id" label-width="100px" prop="id">
            <el-input v-model="foodType.id"></el-input>
          </el-form-item>
          <el-form-item label="菜品种类：" label-width="100px" prop="name">
            <el-input :disabled="dialogInputButtonVisible" v-model="foodType.name" clearable></el-input>
          </el-form-item>
          <el-form-item label="排序：" label-width="100px" prop="sort">
            <el-input-number :disabled="dialogInputButtonVisible" v-model="foodType.sort" clearable></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false" :disabled="dialogInputButtonVisible">取 消</el-button>
          <el-button type="primary" @click="submit('foodType')" :disabled="dialogInputButtonVisible">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import formatDate from '../../utils/dateUtil.js';
export default {
  methods: {
    handleSizeChange(val) {
      this.pageSize = val;
      this.getDataPage(this.currentPage, this.pageSize);
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getDataPage(this.currentPage, this.pageSize);
    },
    add() {
      this.title = "添加菜品种类";
      this.foodType.name = "";
      this.foodType.sort = "";
      this.dialogFormVisible = true;
      this.dialogInputButtonVisible = false;
    },
    update(row) {
      this.foodType.id = row.id;
      this.foodType.name = row.name;
      this.foodType.sort = row.sort;
      this.foodType.createtime = row.createtime;
      this.title = "修改菜品种类";
      this.dialogFormVisible = true;
      this.dialogInputButtonVisible = false;
    },
    del(row) {
      const that = this;
      that.$confirm('是否删除该菜品种类?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

      });
    },
    look(row) {
      this.foodType.name = row.name;
      this.foodType.sort = row.sort;
      this.title = "查看菜品种类";
      this.dialogFormVisible = true;
      this.dialogInputButtonVisible = true;
    },
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (!valid) {
          return false;
        } else {
          const that = this;
          if (that.title == "添加菜品种类") {

          } else if (this.title == "修改菜品种类") {

          }
        }
      });
    },
    getDataPage(currentPage, pageSize) {
      const that = this;
      var param = new URLSearchParams();
      param.append("page", currentPage);
      param.append("limit", pageSize);
      this.$http
        .get('/specific/getFoodTypeByPage', {
          params: param
        })
        .then(function (response) {
          if (response.data.code == 200) {
            that.tableData = response.data.data;
            that.total = response.data.count;
          }
        })
    },
    //时间格式化 
    dateFormat(row, column) {
      let date = new Date(row[column.property]);
      return formatDate(date, 'yyyy-MM-dd hh:mm:ss');
    }
  },
  mounted() {
    this.getDataPage(this.currentPage, this.pageSize);
  },
  data() {
    return {
      foodType: {
        id: "",
        name: "",
        sort: "",
        createtime:""
      },
      title: "",
      dialogInputButtonVisible: false,
      dialogFormVisible: false,
      total: 0,
      pageSize: 5,
      currentPage: 1,
      tableData: [],
      rules: {
        name: [
          { required: true, message: '菜品种类不能为空', trigger: 'blur' },
          { max: 20, message: "菜品种类长度不能超过20个字符", trigger: "blur" }
        ]
      }
    }
  }
}
</script>
<style>

</style>