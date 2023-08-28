<template>
  <div class="app-container-padding">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn" @submit.native.prevent>
      <el-form-item label="角色名称" prop="roleName">
        <el-input
          v-model="queryParams.roleName"
          placeholder="请输入角色名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="角色状态"
          clearable
          @change="handleQuery">
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
    </el-form>
    <div class="btn">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['system:role:add']"
      >新增角色
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-edit"
        size="mini"
        @click="handleUpdate"
        v-hasPermi="['system:role:edit']"
      >编辑角色
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-delete"
        size="mini"
        @click="handleDelete">
        删除角色
      </el-button>
    </div>
    <TablePage class="tablePage"
               :tableData="roleList"
               :is-page="false"
               @handleSelectionChange="handleSelectionChange">
      <template slot="table">
        <el-table-column
          show-overflow-tooltip
          v-for="item in roleColumn"
          :key="item.roleId"
          :prop="item.prop"
          :label="item.label"
          :min-width="item.width">
        </el-table-column>
        <el-table-column label="状态" align="center" key="status" width="120">
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
        <el-table-column label="操作" width="120" class-name="operation">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="handleDataScope(scope.row)">
              设置权限
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <el-dialog :title="dialogType==='add'?'新增角色':dialogType==='edit'?'编辑角色':''"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="700px" class="dialog-style">
      <el-form ref="roleFormRef" :model="roleForm" size="mini" :rules="roleRules" :inline="true" class="ipt">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="roleForm.roleName" placeholder="请输入角色名称"/>
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDes">
          <el-input v-model="roleForm.roleDes"/>
        </el-form-item>
        <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip content="管理员权限字符为：manager" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
            权限字符
          </span>
          <el-input v-model="roleForm.roleKey" placeholder="请输入权限字符"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="roleForm.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" class="remarks">
          <el-input v-model="roleForm.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleSave('roleFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
    </el-dialog>
    <!-- 分配角色数据权限对话框 -->
    <el-dialog title="分配数据权限"
               :visible.sync="dialogScope"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="40%" class="dialog-style">
      <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">展开/折叠</el-checkbox>
      <el-tree
        class="tree-border"
        :data="menuOptions"
        show-checkbox
        ref="menu"
        node-key="id"
        :check-strictly="false"
        empty-text="加载中，请稍候"
        :props="defaultProps"
      ></el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="submitDataScope">保存</el-button>
        <el-button size="mini" @click="dialogScope=false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import TablePage from "@/components/Public/table/TablePage.vue";
import {
  addRole,
  changeRoleStatus,
  dataScope,
  delRole,
  getRole,
  listRole,
  saveRoleMenu,
  updateRole
} from "@/api/system/role";
import {treeselect as menuTreeselect, roleMenuTreeselect} from "@/api/system/menu";

export default {
  name: "index",
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  components: {TablePage},
  data() {
    return {
      queryParams: {},
      roleList: [],
      roleColumn: [
        {label: '角色名称', prop: 'roleName', width: '100'},
        {label: '角色描述', prop: 'roleDes', width: '100'},
        {label: '权限字符', prop: 'roleKey', width: '80'},
      ],
      selected: [], //选中数据
      total: 0,
      dialogType: 'add',
      dialogVisible: false,
      roleForm: {},
      // 是否显示弹出层
      dialogScope: false,
      menuExpand: false,
      menuCheckStrictly: '',
      // 菜单列表
      menuOptions: [],
      roleId:'',
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      roleRules: {
        roleName: [
          {required: true, message: "角色名称不能为空", trigger: "blur"}
        ],
        roleKey: [
          {required: true, message: "权限字符不能为空", trigger: "blur"}
        ],
      }
    }
  },
  created() {
    this.handleQuery();
  },
  methods: {
    /**
     * 查询
     */
    handleQuery() {
      listRole(this.queryParams).then(response => {
          console.log(this.queryParams)
          if (response.code === 200) {
            this.roleList = response.data;
          }
        }
      );
    },
    /**
     *
     */
    handleCascader(value) {
      console.log(value)
    },
    /**
     * 新增角色
     */
    handleAdd() {
      this.dialogType = 'add';
      this.dialogVisible = true;
      this.roleForm = {};
      this.roleForm.status = "0";
    },
    /**
     * 编辑角色
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
        this.roleId = this.selected[0].roleId || this.selected;
        getRole(this.roleId).then(response => {
          this.roleForm = response.data;
          this.dialogType = 'edit';
          this.dialogVisible = true;
        });
      }
    },
    /**
     * 删除角色
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
          delRole(this.selected.map(item => {
            return item.roleId
          }).toString()).then((res) => {
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
    /**
     * 设置权限
     */
    handleDataScope(row) {
        if (row.admin) {
          this.$message({
            message: '不允许操作超级管理员',
            type: 'warning'
          });
        } else {
         this.roleId = row.roleId;
          menuTreeselect().then(response => {
            this.menuOptions = response.data;
            this.dialogScope = true;
            this.roleForm = {};
          });
          roleMenuTreeselect(this.roleId).then(response => {
            console.log(response)
            let checkedKeys = response.checkedKeys
            checkedKeys.forEach((v) => {
              this.$nextTick(() => {
                this.$refs.menu.setChecked(v, true, false);
              })
            })
          });
        }
      },
    /**
     * 表格复选框
     */
    handleSelectionChange(selection) {
      this.selected = selection;
    },
    // 角色状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.roleName + '"用户吗？').then(function () {
        return changeRoleStatus(row.roleId, row.status);
      }).then(() => {
        this.$message.success(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      if (type == 'menu') {
        let treeList = this.menuOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value;
        }
      }
    },
    /**
     * 保存角色弹窗
     */
    handleSave(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.dialogType === 'edit') {
            updateRole(this.roleForm).then(response => {
              this.$message.success('修改成功');
              this.dialogVisible = false;
              this.handleQuery();
            });
          } else {
            addRole(this.roleForm).then(response => {
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
    submitDataScope() {
      let checkedKeys = this.$refs.menu.getCheckedKeys();
      // 半选中的部门节点
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      this.roleForm.menuIds =checkedKeys;
      this.roleForm.roleId=this.roleId;
      saveRoleMenu(this.roleForm).then(response => {
        this.$modal.msgSuccess("设置成功");
        this.dialogScope = false;
        this.handleQuery();
      });

    },
  },
}
</script>

<style lang="scss" scoped>

.tablePage {
  height: calc(100% - 100px);
}

::v-deep .tree-border {
  max-height: calc(90vh - 100px);
  overflow: auto;
}
</style>

