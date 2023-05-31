<template>
  <div>
    <el-card class="box-card" shadow="never">
      <el-button size="mini" @click="add">添加</el-button>
    </el-card>
    <el-table :data="tableData" border style="width: 100%;margin-top: 20px;"
      :header-cell-style="{ background: 'rgb(242, 243, 244)', color: '#515a6e' }">
      <el-table-column fixed prop="id" label="ID" v-if="false">
      </el-table-column>
      <el-table-column prop="name" label="菜品名称">
      </el-table-column>
      <el-table-column prop="typename" label="菜品种类">
      </el-table-column>
      <el-table-column prop="price" label="价格">
      </el-table-column>
      <el-table-column prop="image" label="图片">
        <template slot-scope="scope">
          <img :src="scope.row.showimage" style="height:50px;width:50px;"/>
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
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
      <el-dialog id="foodManage" :title="title" :visible.sync="dialogFormVisible">
        <el-form :rules="rules" :model="food" ref="food">
          <el-form-item style="display:none;" label="id" label-width="100px" prop="id">
            <el-input v-model="food.id"></el-input>
          </el-form-item>
          <el-form-item label="菜品名称：" label-width="100px" prop="name">
            <el-input :disabled="dialogInputButtonVisible" v-model="food.name" clearable></el-input>
          </el-form-item>
          <el-form-item label="菜品种类：" label-width="100px" prop="typeid">
            <el-select v-model="food.typeid" placeholder="请选择菜品种类" :disabled="dialogInputButtonVisible">
              <el-option v-for="item in foodTypeAll" :value="item.id" :label="item.name" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="图片：" label-width="100px" prop="image">
            <el-upload v-model="food.image" class="upload-demo" :action="uploadImageUrl" :file-list="fileList"
              :headers="getHeader()" :show-file-list="false" :on-success="fileSuccess" :on-remove="fileRemove">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
            <img v-if="imgViewUrl" :src="imgViewUrl" style="width:100px;height:100px;" />
          </el-form-item>
          <el-form-item label="价格：" label-width="100px" prop="price">
            <el-input-number :precision="2" :disabled="dialogInputButtonVisible" v-model="food.price"
              clearable></el-input-number>
          </el-form-item>
          <el-form-item label="排序：" label-width="100px" prop="sort">
            <el-input-number :disabled="dialogInputButtonVisible" v-model="food.sort" clearable></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false" :disabled="dialogInputButtonVisible">取 消</el-button>
          <el-button type="primary" @click="submit('food')" :disabled="dialogInputButtonVisible">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
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
      this.title = "添加菜品";
      this.food.name = "";
      this.food.price = "";
      this.food.typeid = "";
      this.food.sort = "";
      this.food.image = "";
      this.imgViewUrl = "";
      this.dialogFormVisible = true;
      this.dialogInputButtonVisible = false;
    },
    update(row) {
      this.food.id = row.id;
      this.food.name = row.name;
      this.food.price = row.price;
      this.food.typeid = row.typeid;
      this.food.sort = row.sort;
      this.food.image = row.image;
      this.food.createtime = row.createtime;
      this.imgViewUrl = this.$http.defaults.baseURL + "/file/" + this.food.image;
      this.title = "修改菜品";
      this.dialogFormVisible = true;
      this.dialogInputButtonVisible = false;
    },
    del(row) {
      const that = this;
      that.$confirm('是否删除该菜品?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
      });
    },
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (!valid) {
          return false;
        } else {
          const that = this;
          if (that.title == "添加菜品") {

          } else if (this.title == "修改菜品") {

          }
        }
      });
    },
    getHeader() {
      var authorization = "";
      var user = localStorage.getItem("user");
      if (user != null && user != "") {
        var userObj = JSON.parse(user);
        var token = userObj.token;
        authorization = token;
      }
      return { Authorization: authorization };
    },
    fileSuccess(response, file, fileList) {
      this.food.image = response.data;
      this.imgViewUrl = this.$http.defaults.baseURL + "/file/" + this.food.image;
    },
    fileRemove(file, fileList) {
      this.food.image = "";
    },
    getDataPage(currentPage, pageSize) {
      const that = this;
      var param = new URLSearchParams();
      param.append("page", currentPage);
      param.append("limit", pageSize);
      this.$http
        .get('/specific/getFoodByPage', {
          params: param
        })
        .then(function (response) {
          if (response.data.code == 200) {
            that.tableData = response.data.data;
            for (var i = 0; i < that.tableData.length; i++) {
              that.tableData[i].showimage = that.$http.defaults.baseURL + "/file/" + that.tableData[i].image;
            }
            that.total = response.data.count;
          }
        })
    },
    getFoodTypeAll() {
      const that = this;
      this.$http
        .get('/specific/getFoodTypeAll')
        .then(function (response) {
          if (response.data.code == 200) {
            that.foodTypeAll = response.data.data;
          }
        })
    }
  },
  mounted() {
    this.getDataPage(this.currentPage, this.pageSize);
    this.getFoodTypeAll();
  },
  data() {
    return {
      food: {
        id: "",
        name: "",
        price: "",
        typeid: "",
        typename: "",
        sort: "",
        image: "",
        createtime: ""
      },
      foodTypeAll: [],
      title: "",
      dialogInputButtonVisible: false,
      dialogFormVisible: false,
      total: 0,
      pageSize: 5,
      currentPage: 1,
      tableData: [],
      fileList: [],
      imgViewUrl: "",
      uploadImageUrl: this.$http.defaults.baseURL + "/image/upload",
      rules: {
        name: [
          { required: true, message: '菜品名称不能为空', trigger: 'blur' },
          { max: 20, message: "菜品名称长度不能超过20个字符", trigger: "blur" }
        ]
      }
    }
  }
}
</script>
<style></style>