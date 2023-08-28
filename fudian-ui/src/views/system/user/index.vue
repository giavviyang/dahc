<template>
  <div class="app-container-padding">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn" @submit.native.prevent>
      <el-form-item label="用户名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名称"
          @keyup.enter.native="handleQuery"
          clearable/>
      </el-form-item>
      <el-form-item label="用户昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入用户昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="用户状态"
          clearable @change="handleQuery">
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
    </el-form>
    <div class="btn">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd">
        新增用户
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-edit"
        size="mini"
        @click="handleUpdate">
        编辑用户
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-delete"
        size="mini"
        @click="handleDelete">
        删除用户
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-upload2"
        size="mini"
        @click="handleImport"
        v-hasPermi="['system:user:import']"
      >导入用户
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['system:user:export']"
      >导出用户
      </el-button>
    </div>
    <TablePage class="tablePage"
               :tableData="userList"
               :pageSize="queryParams.pageSize"
               :pageNum="queryParams.pageNum"
               :total="total"
               @handleSelectionChange="handleSelectionChange"
               @handleChange="handleChange">
      <template slot="table">
        <el-table-column
          show-overflow-tooltip
          v-for="item in userColumn"
          :key="item.userId"
          :prop="item.prop"
          :label="item.label"
          :min-width="item.width">
        </el-table-column>
        <el-table-column label="状态" align="center" key="status">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="160">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220"  class-name="operation">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="mini"
              @click="handleResetPwd(scope.row)">
              重置密码
            </el-button>
            <el-button
              type="text"
              size="mini"
              @click="handleAuthRole(scope.row)">关联角色
            </el-button>
            <el-button
              type="text"
              size="mini"
              @click="handleAuthProcess(scope.row)">
              关联工序
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <el-dialog :title="dialogType==='add'?'新增用户':dialogType==='edit'?'编辑用户':''"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="700px" class="dialog-style">
      <el-form ref="userFormRef" :model="userForm" size="mini" :rules="userRules" :inline="true" class="ipt">
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="userForm.userName" placeholder="请输入用户名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickName">
          <el-input v-model="userForm.nickName" placeholder="请输入用户昵称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item v-if="userForm.userId == undefined" label="用户密码" prop="password">
          <!--          <el-input v-model="userForm.password" placeholder="请输入用户密码" type="password" maxlength="20"-->
          <!--                    show-password clearable/>  -->
          <el-input v-model="userForm.password" placeholder="请输入用户密码" type="password" maxlength="20"
                    show-password/>
        </el-form-item>
        <el-form-item label="手机号码" prop="phonenumber">
          <el-input v-model="userForm.phonenumber" placeholder="请输入手机号码" maxlength="11" clearable/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" placeholder="请输入邮箱" maxlength="50" clearable/>
        </el-form-item>
        <el-form-item label="用户性别">
          <el-select v-model="userForm.sex" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="userForm.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色" prop="remarks" class="remarks role">
          <el-select v-model="userForm.roleIds" multiple placeholder="请选择角色">
            <el-option
              v-for="item in roleOptions"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remarks" class="remarks">
          <el-input v-model="userForm.remarks" placeholder="请输入备注" maxlength="30" clearable type="textarea"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleSave('userFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
    </el-dialog>
    <!-- 关联角色对话框-->
    <el-dialog :title="'关联角色信息'"
               :visible.sync="roleDialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="50%" class="dialog-style roleDialogVisible">
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="roleData"
                 :isPage="false"
                 :isTitle="true"
                 :tableTitle="haveNoTitle">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in roleItemColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width">
          </el-table-column>
          <el-table-column
            label="操作"  fixed="right"
            width="150" class-name="operation">
            <template slot-scope="scope">
              <el-button @click="moveRoleSelect(roleData,scope.row, scope.$index,haveRoleData)" type="text" size="mini">
                选择
              </el-button>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="haveRoleData"
                 :isPage="false"
                 :isTitle="true"
                 :tableTitle="haveTitle">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in roleItemColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width">
          </el-table-column>
          <el-table-column
            label="操作"  fixed="right"
            width="150" class-name="operation">
            <template slot-scope="scope">
              <el-button type="text" size="mini"
                         @click="removeRoleSelect(haveRoleData,scope.row,scope.$index,roleData)">取消选择
              </el-button>
              <!--              <el-button @click="moveUp(haveSelectData,scope.row, scope.$index)"-->
              <!--                         :disabled="scope.$index === 0" type="text" size="mini">上移-->
              <!--              </el-button>-->
              <!--              <el-button @click="moveDown(haveSelectData,scope.row, scope.$index)"-->
              <!--                         :disabled="getFormLength(scope.$index,haveSelectData)" type="text" size="mini">下移-->
              <!--              </el-button>-->
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleRoleSave">保存</el-button>
        <el-button size="mini" @click="roleDialogVisible=false">取消</el-button>
      </span>
    </el-dialog>
    <!-- 关联工序对话框-->
    <el-dialog :title="'关联工序信息'"
               :visible.sync="procedureDialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="1000px" class="dialog-style roleDialogVisible">
      <el-form ref="userProcedureFormRef" :model="userProcedureForm" size="mini" :rules="userrProcedureRules"
               :inline="true" class="ipt" style="margin-left: -3%">
        <el-form-item label="项目名称">
          <el-select v-model="userProcedureForm.projectId" placeholder="请选择项目名称" @change="userProcedureChange">
            <el-option
              v-for="dict in procedureOptions"
              :key="dict.value"
              :label="dict.label +  '-' +dict.projectState"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="procedureData"
                 :isPage="false"
                 :isTitle="true"
                 :tableTitle="procedureHaveNoTitle">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in procedureItemColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width">
          </el-table-column>
          <el-table-column
            label="操作"  fixed="right"
            width="150" class-name="operation">
            <template slot-scope="scope">
              <el-button @click="moveRoleSelect(procedureData,scope.row, scope.$index,haveProcedureData)" type="text"
                         size="mini" :disabled="projectState == '已完成'">
                选择
              </el-button>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="haveProcedureData"
                 :isPage="false"
                 :isTitle="true"
                 :tableTitle="procedureHaveTitle">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in procedureItemColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width">
          </el-table-column>
          <el-table-column
            label="操作"  fixed="right"
            width="150" class-name="operation">
            <template slot-scope="scope">
              <el-button type="text" size="mini"  :disabled="projectState == '已完成'"
                         @click="removeRoleSelect(haveProcedureData,scope.row,scope.$index,procedureData)">取消选择
              </el-button>
              <!--              <el-button @click="moveUp(haveSelectData,scope.row, scope.$index)"-->
              <!--                         :disabled="scope.$index === 0" type="text" size="mini">上移-->
              <!--              </el-button>-->
              <!--              <el-button @click="moveDown(haveSelectData,scope.row, scope.$index)"-->
              <!--                         :disabled="getFormLength(scope.$index,haveSelectData)" type="text" size="mini">下移-->
              <!--              </el-button>-->
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="procedureRoleSave">保存</el-button>
        <el-button size="mini" @click="procedureDialogVisible=false">取消</el-button>
      </span>
    </el-dialog>


    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true" :visible.sync="upload.open" class="dialog-style" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate">下载模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm" size="mini">确 定</el-button>
        <el-button @click="upload.open = false" size="mini">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import TablePage from "@/components/Public/table/TablePage.vue";
import {saveUserProcedure, queryUserProcedure} from "@/api/projectManage/projectProcedure"
import {queryProjectPullDown} from "@/api/projectManage/projectInitialize"
import {
  addUser,
  changeUserStatus,
  delUser,
  getUser,
  listUser,
  resetUserPwd,
  updateUser,
  queryUserRelevanceRoleList,
  userRelevanceRoleList,
  updateAuthRole
} from "@/api/system/user";
import {getToken} from "@/utils/auth";
import {listRole} from "@/api/system/role";

export default {
  name: "index",
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  components: {TablePage},
  data() {
    return {
      queryParams: {
        pageSize: 20,
        pageNum: 1,
      },
      /*关联工序项目工序下拉框*/
      procedureOptions: [],
      userList: [],
      userColumn: [
        // {label: '用户编号', prop: 'userId', width: '100'},
        {label: '用户名称', prop: 'userName'},
        {label: '用户昵称', prop: 'nickName'},
        {label: '手机号码', prop: 'phonenumber', width: '120'},
        {label: '邮箱', prop: 'email', width: '120'},
      ],
      /*角色表头*/
      roleItemColumn: [
        // {label: '角色编号', prop: 'roleId', width: '100'},
        {label: '角色名称', prop: 'roleName', width: '100'},
        {label: '权限字符', prop: 'roleKey', width: '100'},
        // {label: '显示顺序', prop: 'roleSort', width: '100'},
      ],

      /*工序表头*/
      procedureItemColumn: [
        {label: '工序名称', prop: 'procedureName', width: '100'},
        {label: '工序描述', prop: 'procedureDesc', width: '100'},
        // {label: '核查项数量', prop: 'inspectNumber', width: '100'},
        {label: '创建人', prop: 'createByString', width: '100'},
        {label: '创建时间', prop: 'createTime', width: '100'},
        // {label: '可参与人数', prop: 'remarks', width: '100'},
        // {label: '可参与人员', prop: 'remarks', width: '100'},
        // {label: '排序', prop: 'sort', width: '100'},
      ],
      /*角色表格*/
      roleData: [],
      /*为选择工序表格*/
      procedureData: [],
      /*已选择角色表格*/
      haveRoleData: [],
      /*已选择工序表格*/
      haveProcedureData: [],
      selected: [], //选中数据
      selectedList: [], //选中的完整数据数据
      total: 0,
      dialogType: 'add',
      dialogVisible: false,
      /*关联角色弹窗*/
      roleDialogVisible: false,
      /*关联工序弹窗*/
      procedureDialogVisible: false,
      /*关联工序弹窗*/
      userProcedureForm: {
        projectId: '',
      },
      currentUserId:'',
      userForm: {
        password: '',
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      // 角色选项
      roleOptions: [],
      haveNoTitle: '可选择角色：',
      haveTitle: '已选择角色：',
      procedureHaveTitle: '已选择工序：',
      procedureHaveNoTitle: '可选择工序：',
      userrProcedureRules: {},
      userRules: {
        nickName: [
          {required: true, message: "用户昵称不能为空", trigger: "blur"},
        ],
        userName: [
          {required: true, message: "用户名称不能为空", trigger: "blur"},
          {min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur'}
        ],
        phonenumber: [
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
        ],
        email: [
          {type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"]}
        ],
        password: [
          {required: true, message: "用户密码不能为空", trigger: "blur"},
          {min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur'}
        ],
        typeRange: [
          {required: true, message: "用户范围不能为空", trigger: "change"},
        ],
      },


    };
  },
  created() {
    this.handleQuery();
  },
  methods: {
    /**
     * 查询
     */
    handleQuery() {
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          if (response.code === 200) {
            this.userList = response.rows;
            this.total = response.total;
          }
        }
      );
    },
    /**
     * 新增用户
     */
    handleAdd() {
      this.dialogType = 'add';
      this.dialogVisible = true;
      this.userForm = {};
      this.userForm.status = "0";
      this.$set(this.userForm, 'password', '123456')
      getUser().then(response => {
        if (response.code === 200) {
          this.roleOptions = response.roles;
        }
      });
    },
    /**
     * 编辑核查项
     */
    handleUpdate() {
      if (!this.selected || this.selected === [] || this.selected.length === 0) {
        this.$message({
          message: '请选择一条数据',
          type: 'warning'
        });
      } else if (this.selected.length !== 1) {
        this.$message({
          message: '请选择一条数据',
          type: 'warning'
        });
      } else {
        let userId = this.selected[0].userId || this.selected;
        getUser(userId).then(response => {
          // console.log(response)
          this.userForm = response.data;
          this.dialogType = 'edit';
          this.roleOptions = response.roles;
          this.$set(this.userForm, "roleIds", response.roleIds);
          this.dialogVisible = true;
        });
      }
    },
    /**
     * 删除核查项
     */
    handleDelete() {
      if (!this.selected || this.selected === [] || this.selected.length === 0) {
        this.$message({
          message: '请至少选择一条数据',
          type: 'warning'
        });
      } else {
        this.$confirm('此操作将永久删除选中数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          const userIds = this.selected;
          delUser(userIds).then((res) => {
            if (res.code == 200) {
              this.$message.success("删除成功");
              this.handleQuery();
            }
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$confirm('此操作将重置选中用户的密码为123456, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        let value = '123456';
        resetUserPwd(row.userId, value).then(response => {
          this.$modal.msgSuccess("修改成功，新密码是：" + value);
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消重置'
        });
      });
    },
    /** 分配角色操作 */
    handleAuthRole(row) {
      this.roleDialogVisible = true;
      this.currentUserId=row.userId;
      this.queryUserRelevanceRoleList(row);
    },
    /** 用户关联角色分页查询 */
    queryUserRelevanceRoleList(row) {
      /*未选择数据*/
      userRelevanceRoleList(row.userId).then(response => {
          if (response.code === 20000) {
            this.roleData = response.data;
          } else {
            this.$message.error(response.msg);
          }
        }
      );
      /*选择数据*/
      queryUserRelevanceRoleList(row.userId).then(response => {
          if (response.code === 20000) {
            this.haveRoleData = response.data;
          } else {
            this.$message.error(response.msg);
          }
        }
      );
    },
    // 关联角色选择
    moveRoleSelect(arr1, item, index, arr2) {
      arr1.splice(index, 1);
      arr2.push(item);
    },
    // 关联角色取消选择
    removeRoleSelect(arr1, item, index, arr2) {
      arr1.splice(index, 1);
      arr2.push(item);
    },
    /*保存角色关联数据*/
    handleRoleSave() {
      const userId = this.currentUserId;
      const roleId = this.haveRoleData.map(item => item.roleId)
      const roleIds = roleId.join(",");
      updateAuthRole({userId: userId, roleIds: roleIds}).then((response) => {
        if (response.code == 200) {
          this.$message.success("修改成功");
          this.roleDialogVisible = false;
        } else {
          this.$message.error(response.msg);
        }
      });
    },
    /*下拉框点击事件*/
    userProcedureChange(value) {

      this.projectState = this.procedureOptions.find(item => item.value === value).projectState
      console.log( this.projectState,"待定")
      this.userProcedureForm.projectId = value;
      const notArr = {
        'userId': this.currentUserId,
        'projectId': this.userProcedureForm.projectId,
        'boolean': false,
      }
      queryUserProcedure(notArr).then(res => {
        this.procedureData = res.data;
      });
      const arr = {
        'userId':this.currentUserId,
        'projectId': this.userProcedureForm.projectId,
        'boolean': true,
      }
      queryUserProcedure(arr).then(res => {
        this.haveProcedureData = res.data;
      });
    },
    /**
     * 关联工序
     */
    handleAuthProcess(row) {
        /*项目下拉框*/
        queryProjectPullDown().then(res => {
          this.procedureOptions = res.data;
          if (res.data.length > 0) {
            this.procedureOptions.map(item => {
              if (item.projectState == 0) {
                item.projectState = "未开始";
              } else if (item.projectState == 1) {
                item.projectState = "核查中";
              } else {
                item.projectState = "已完成";
              }
            });
            this.userProcedureForm.projectId = res.data[0].value;
            this.projectState = res.data[0].projectState;
          } else {
            this.projectState = '';
          }
          this.procedureDialogVisible = true;
          const notArr = {
            'userId': row.userId,
            'projectId': this.userProcedureForm.projectId,
            'boolean': false,
          }
          queryUserProcedure(notArr).then(res => {
            this.procedureData = res.data;
            this.procedureData.map(item => {
              item.createTime =  this.dataFormat(item.createTime)
            });
          });
          const arr = {
            'userId': row.userId,
            'projectId': this.userProcedureForm.projectId,
            'boolean': true,
          }
          this.currentUserId = row.userId;
          queryUserProcedure(arr).then(res => {
            this.haveProcedureData = res.data;
            this.haveProcedureData.map(item => {
              item.createTime =  this.dataFormat(item.createTime)
            });
          });
          /*查询选择*/
        });
    },
    // 时间格式化
    dataFormat(value) {
      var year = value.substr(0, 4)
      var month = value.substr(5, 2)
      var day = value.substr(8, 2)
      var hour = value.substr(11, 2)
      var min = value.substr(14, 2)
      var second = value.substr(17,2)
      return year + "-" + month + "-" + day + " " + hour + ":" + min + ":"+second
    },
    /*保存工序*/
    procedureRoleSave() {
      const arr = this.haveProcedureData.map(item => {
        return item.id
      });
      const arrSave = {
        'userId': this.currentUserId,
        'projectId': this.userProcedureForm.projectId,
        'procedureIds': arr,
      }
      saveUserProcedure(arrSave).then(res => {
        if (res.code == 20000) {
          this.$message.success('关联成功');
          this.procedureDialogVisible = false;
        }
      });
    },
    // 关联工序选择
    moveProcedureSelect(arr1, item, index, arr2) {
      arr1.splice(index, 1);
      arr2.push(item);
    },
    // 关联工序取消选择
    removeProcedureSelect(arr1, item, index, arr2) {
      arr1.splice(index, 1);
      arr2.push(item);
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/user/importTemplate', {}, `user_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.handleQuery();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /**
     * 表格复选框
     */
    handleSelectionChange(selection) {
      this.selected = selection.map(item => item.userId);
      this.selectedList = selection;
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.userName + '"用户吗？').then(function () {
        return changeUserStatus(row.userId, row.status);
      }).then(() => {
        this.$message.success(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /**
     * 分页器
     */
    handleChange(pageSize, pageNum) {
      this.queryParams.pageSize = pageSize;
      this.queryParams.pageNum = pageNum;
      this.handleQuery();
    },
    /**
     * 保存核查项弹窗
     */
    handleSave(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.dialogType === 'edit') {
            updateUser(this.userForm).then(response => {
              // this.$modal.msgSuccess("修改成功");
              // Message.success("修改成功");
              this.$message.success('修改成功');
              this.dialogVisible = false;
              this.handleQuery();
            });
          } else {
            addUser(this.userForm).then(response => {
              this.$message.success('新增成功');
              this.dialogVisible = false;
              this.handleQuery();
            });
          }
        } else {
          return false;
        }
      });
    },
  }
}
</script>

<style lang="scss" scoped>
.tablePage {
  height: calc(100% - 100px);
}

.role {
  height: auto;
  margin-bottom: 22px;
}

::v-deep .roleDialogVisible {
  .tablePage {
    height: calc(33vh);
    margin-top: 25px;
  }
}

::v-deep .el-upload {
  width: 100%;

  .el-upload-dragger {
    width: 100%;
  }
}

</style>

