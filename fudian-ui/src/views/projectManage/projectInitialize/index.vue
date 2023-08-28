<template>
  <div class="app-container-padding">
    <el-tabs type="border-card" @tab-click="handleClick">
      <el-tab-pane label="未开始">
        <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                 @submit.native.prevent>
          <el-form-item label="项目名称" prop="projectName">
            <el-input
              v-model="queryParams.projectTableName"
              placeholder="请输入项目名称"
              @keyup.enter.native="conditionalQueries"
              clearable/>
          </el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries">搜索</el-button>
        </el-form>
        <div class="btn">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd">
            新增项目
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="handleUpdate">
            编辑项目
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-delete"
            size="mini"
            @click="handleDelete">
            删除项目
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-edit-outline"
            size="mini"
            @click="handleDefine">
            定义工序
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-video-pause"
            size="mini"
            @click="handleStart">
            开始核查
          </el-button>
          <el-dropdown>
            <el-button type="primary" size="mini">
              <i class="el-icon-upload" style="display: inline-block;margin-right: 5px;"></i> 导入数据<i
              class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="importExcel('caseExcel')">导入案卷条目</el-dropdown-item>
              <el-dropdown-item @click.native="importExcel('fileExcel')">导入文件条目</el-dropdown-item>
              <el-dropdown-item @click.native="importExcel('imgFile')">导入电子文件</el-dropdown-item>
              <el-dropdown-item @click.native="importExcel('filePathUploader')">根据文件路径导入</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <TablePage class="tablePage"
                   ref="projectRef"
                   :tableData="projectData"
                   :pageSize="queryParams.pageSize"
                   :pageNum="queryParams.pageNum"
                   :total="projectTotal"
                   @handleSelectionChange="handleProjectSelection"
                   @handleChange="handleProjectChange"
                   @handleRowClick="handleProjectClick">
          <template slot="table">
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in projectColumn1"
              :key="index"
              :prop="item.prop"
              :formatter="item.formatter"
              :label="item.label"
              :min-width="item.width">
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"
              width="120" class-name="operation">
              <template slot-scope="scope">
                <el-button @click="handleData(scope.row)"
                           type="text" size="mini">查看数据
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
        <TablePage class="tablePage"
                   :hasSelection="false"
                   :is-title="true"
                   :hasIndex="false"
                   :tableTitle="tableProcessTitle"
                   :tableData="processData"
                   :defaultSort="{prop: 'sort', order: 'asc'}"
                   :pageSize="queryProcessParams.pageSize"
                   :pageNum="queryProcessParams.pageNum"
                   :total="processTotal"
                   @handleChange="handleProcessChange"
        >
          <template slot="table">
            <el-table-column
              label="排序"
              prop="sort"
              width="60"
            />
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in processColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :formatter="item.formatter"
              :min-width="item.width">
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"
              width="270" class-name="operation">
              <template slot-scope="scope">
                <el-button @click="handleExamineDetail(scope.row)"
                           type="text" size="mini">查看核查项
                </el-button>
                <el-button @click="moveUpdateProcess(scope.row)"
                           type="text" size="mini">编辑工序
                </el-button>
                <el-button @click="moveUpTable(processData,scope.row, scope.$index)"
                           :disabled="scope.$index === 0" type="text" size="mini">上移
                </el-button>
                <el-button @click="moveDownTable(processData,scope.row, scope.$index)"
                           :disabled="getFormLength(scope.$index,processData)" type="text" size="mini">下移
                </el-button>
                <el-button type="text" size="mini" @click="deleteItem(processData,scope.row,scope.$index)">移除
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
      </el-tab-pane>
      <el-tab-pane label="核查中">
        <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                 @submit.native.prevent>
          <el-form-item label="项目名称" prop="projectName">
            <el-input
              v-model="queryParams.projectTableName"
              placeholder="请输入项目名称"
              @keyup.enter.native="conditionalQueries"
              clearable/>
          </el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries">搜索</el-button>
        </el-form>
        <div class="btn">
          <el-dropdown>
            <el-button type="primary" size="mini">
              <i class="el-icon-upload" style="display: inline-block;margin-right: 5px;"></i> 导入数据<i
              class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="importExcel('caseExcel')">导入案卷条目</el-dropdown-item>
              <el-dropdown-item @click.native="importExcel('fileExcel')">导入文件条目</el-dropdown-item>
              <el-dropdown-item @click.native="importExcel('imgFile')">导入电子文件</el-dropdown-item>
              <el-dropdown-item @click.native="importExcel('filePathUploader')">根据文件路径导入</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button
            type="primary"
            icon="el-icon-circle-check"
            size="mini"
            @click="handleEnd">
            结束核查
          </el-button>
        </div>
        <TablePage class="tablePage"
                   ref="projectRef2"
                   :tableData="projectData"
                   :pageSize="queryParams.pageSize"
                   :pageNum="queryParams.pageNum"
                   :total="projectTotal"
                   @handleSelectionChange="handleProjectSelection2"
                   @handleChange="handleProjectChange"
                   @handleRowClick="handleProjectClick">
          <template slot="table">
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in projectColumn2"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :formatter="item.formatter"
              :min-width="item.width">
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"
              width="120" class-name="operation">
              <template slot-scope="scope">
                <el-button @click="handleData(scope.row)"
                           type="text" size="mini">查看数据
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
        <TablePage class="tablePage"
                   :hasSelection="false"
                   :is-title="true"
                   :hasIndex="false"
                   :tableTitle="tableProgressTitle"
                   :tableData="processData"
                   :pageSize="queryProcessParams.pageSize"
                   :pageNum="queryProcessParams.pageNum"
                   :total="processTotal"
                   @handleChange="handleProcessChange">
          <template slot="table">
            <el-table-column
              label="排序"
              prop="sort"
              width="60"
            />
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in progressColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :formatter="item.formatter"
              :min-width="item.width">
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"
              width="120" class-name="operation">
              <template slot-scope="scope">
                <el-button @click="handleExamineDetail(scope.row)"
                           type="text" size="mini">查看核查项
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
      </el-tab-pane>
      <el-tab-pane label="已完成">
        <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                 @submit.native.prevent>
          <el-form-item label="项目名称" prop="projectName">
            <el-input
              v-model="queryParams.projectTableName"
              placeholder="请输入项目名称"
              @keyup.enter.native="conditionalQueries"
              clearable/>
          </el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries">搜索</el-button>
        </el-form>
        <TablePage class="completedTable"
                   ref="projectRef3"
                   :tableData="projectData"
                   :pageSize="queryParams.pageSize"
                   :pageNum="queryParams.pageNum"
                   :total="projectTotal"
                   :hasSelection=false
                   @handleSelectionChange="handleProjectSelection3"
                   @handleChange="handleProjectChange"
                   @handleRowClick="handleProjectClick">
          <template slot="table">
            <el-table-column
              show-overflow-tooltip
              v-for="item in projectColumn3"
              :key="item.id"
              :prop="item.prop"
              :label="item.label"
              :formatter="item.formatter"
              :min-width="item.width">
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"
              width="120" class-name="operation">
              <template slot-scope="scope">
                <el-button @click="handleData(scope.row)"
                           type="text" size="mini">查看数据
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
        <TablePage class="tablePage"
                   :is-title="true"
                   :tableTitle="tableResultTitle"
                   :hasSelection="false"
                   :hasIndex="false"
                   :tableData="processData"
                   :pageSize="queryProcessParams.pageSize"
                   :pageNum="queryProcessParams.pageNum"
                   :total="processTotal"
                   @handleChange="handleProcessChange">
          <template slot="table">
            <el-table-column
              label="排序"
              prop="sort"
              width="60"
            />
            <el-table-column
              show-overflow-tooltip
              v-for="item in resultColumn"
              :key="item.id"
              :prop="item.prop"
              :label="item.label"
              :formatter="item.formatter"
              :min-width="item.width">
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"
              width="120" class-name="operation">
              <template slot-scope="scope">
                <el-button @click="handleExamineDetail(scope.row)"
                           type="text" size="mini">查看核查项
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
      </el-tab-pane>
    </el-tabs>
    <el-dialog :title="dialogType==='add'?'新增项目':dialogType==='edit'?'编辑项目':''"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="800px" class="dialog-style ">
      <el-form ref="projectFormRef" :model="projectForm" size="mini" :rules="projectRules" :inline="true"
               class="ipt projectForm">
        <el-form-item label="项目名称" prop="projectName" class="remarks">
          <el-input v-model="projectForm.projectName" placeholder="请输入项目名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="项目描述" prop="projectDes" class="remarks">
          <el-input v-model="projectForm.projectDesc" type="textarea" placeholder="请输入项目描述" maxlength="100"
                    clearable/>
        </el-form-item>
        <el-form-item label="档案类型" prop="archiveTypeId">
          <el-select v-model="projectForm.archiveTypeId" placeholder="请选择档案类型" maxlength="30">
            <el-option v-for="metaDataItem in archivesTypeNameOptions"
                       :key="metaDataItem.value"
                       :label="metaDataItem.label"
                       :value="metaDataItem.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="预计开始时间" prop="startTime">
          <el-date-picker
            v-model="projectForm.startTime"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>

        <!--        <el-form-item label="预计开始时间" prop="startTime">
                  <el-date-picker
                    v-model="projectForm.startTime"
                    type="datetime"
                    placeholder="选择日期时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="预计结束时间" prop="endTime">
                  <el-date-picker
                    v-model="projectForm.endTime"
                    type="datetime"
                    placeholder="选择日期时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>-->
        <el-form-item label="备注" prop="remarks" class="remarks">
          <el-input v-model="projectForm.remarks" placeholder="请输入备注" maxlength="100" clearable type="textarea"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleSave('projectFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
    </el-dialog>
    <el-dialog title="编辑工序"
               :visible.sync="dialogUpdateProcess"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="85%" class="dialog-style processDialog">
      <el-form ref="processFormRef" :model="processForm" size="mini" :rules="processRules" :inline="true" class="ipt">
        <el-form-item label="工序排序" prop="sort">
          <el-input v-model="processForm.sort" placeholder="请输入工序排序" maxlength="30" clearable/>
          <!--          <el-input-number v-model="processForm.processSort"  :min="1" :max="10" label="描述文字"></el-input-number>-->
        </el-form-item>
        <el-form-item label="工序名称" prop="procedureName">
          <el-input v-model="processForm.procedureName" placeholder="请输入工序名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="核查模版" prop="trueingId">
          <el-cascader

            v-model="processForm.trueingId"
            :options="examineTemplateData"
            :props="{ expandTrigger: 'hover' }"
            @change="handleCascader"></el-cascader>
        </el-form-item>
        <el-form-item label="工序描述" prop="procedureDesc">
          <el-input v-model="processForm.procedureDesc" placeholder="请输入工序描述" maxlength="30" clearable/>
        </el-form-item>
      </el-form>
      <div class="tableDes">可选择核查项</div>
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="noSelectData"
                 :isPage="false">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in checkItemColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :formatter="item.formatter"
            :min-width="item.width">
          </el-table-column>
          <el-table-column
            label="操作"  fixed="right"
            width="100" class-name="operation">
            <template slot-scope="scope">
              <el-button @click="moveSelect(noSelectData,scope.row, scope.$index,haveSelectData)" type="text"
                         size="mini">选择
              </el-button>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <div class="tableDes">已选择核查项</div>
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="haveSelectData"
                 :isPage="false">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in checkItemColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :formatter="item.formatter"
            :min-width="item.width">
          </el-table-column>
          <el-table-column
            label="操作"  fixed="right"
            width="150" class-name="operation">
            <template slot-scope="scope">
              <el-button type="text" size="mini"
                         @click="removeSelect(haveSelectData,scope.row,scope.$index,noSelectData)">取消选择
              </el-button>
              <el-button @click="moveUp(haveSelectData,scope.row, scope.$index)"
                         :disabled="scope.$index === 0" type="text" size="mini">上移
              </el-button>
              <el-button @click="moveDown(haveSelectData,scope.row, scope.$index)"
                         :disabled="getFormLength(scope.$index,haveSelectData)" type="text" size="mini">下移
              </el-button>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleProcessSave('processFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogUpdateProcess=false">取消</el-button>
      </span>
    </el-dialog>
    <el-dialog title="查看核查进度"
               :visible.sync="dialogProgress"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="80%" class="dialog-style">
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="progressData"
                 :pageSize="progressSize"
                 :pageNum="progressNum"
                 :total="progressTotal"
                 @handleChange="handleProgressChange"
      >
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in progressColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :formatter="item.formatter"
            :min-width="item.width">
          </el-table-column>
        </template>
      </TablePage>
    </el-dialog>
    <el-dialog title="查看核查结果"
               :visible.sync="dialogResult"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="80%" class="dialog-style">
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="resultData"
                 :pageSize="resultSize"
                 :pageNum="resultNum"
                 :total="resultTotal"
                 @handleChange="handleResultChange"
      >
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in resultColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width">
          </el-table-column>
        </template>
      </TablePage>
    </el-dialog>
    <el-dialog title="查看核查项详情"
               :visible.sync="dialogExamine"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="1300px" class="dialog-style processDialog">
      <TablePage class="examineData"
                 :hasSelection="false"
                 :tableData="examineData"
                 :pageSize="examineSize"
                 :pageNum="examineNum"
                 :total="examineTotal"
                 @handleChange="handleExamineChange">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in examineColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :formatter="item.formatter"
            :min-width="item.width"
            :class-name="item.cellName">
          </el-table-column>
        </template>
      </TablePage>
    </el-dialog>
    <!--   导入案卷条目   -->

    <CaseUpload ref="CaseUpload" :dialogCaseExcel="dialogCaseExcel" :projectId="tableProjectId" :archivesId="archivesId"
                @closeCaseExcel="closeCaseExcel"></CaseUpload>
    <!--  导入文件条目  -->
    <FileUpload ref="FileUpload" :dialogFileExcel="dialogFileExcel" :projectId="tableProjectId" :archivesId="archivesId"
                @closeFileExcel="closeFileExcel"></FileUpload>

    <FilePathUploader ref="FilePathUploader" :dialogSimpleUploader="dialogFilePathUploader" :projectId="tableProjectId"
                      :archivesId="archivesId" @closeFilePath="closeFilePath"></FilePathUploader>
    <!--  分片上传 电子文件  -->

    <ProjectUpload ref="simpleUploader" :projectId="tableProjectId" :archivesId="archivesId"
                     @closeSimpleUploader="closeSimpleUploader"></ProjectUpload>
  </div>
</template>

<script>
  import HaveNoStart from "@/views/projectManage/projectInitialize/haveNoStart.vue";
  import UnderVerification from "@/views/projectManage/projectInitialize/underVerification.vue";
  import Completed from "@/views/projectManage/projectInitialize/completed.vue";
  import {
    addProjectTable,
    batchDeleteByIdsProjectTable,
    search,
    startInspect,
    endOfVerification,
    updateProjectTable
  } from "@/api/projectManage/projectInitialize";
  import {
    batchDeleteByIdsProcedure,
    procedureUpAndDown,
    queryInspectTableList,
    searchProcedureList,
    searchProcedureUnselectedList,
    updateProcedure,
  } from "@/api/projectManage/projectProcedure";
  import {queryDictDataTransition, queryInspectTree} from "@/api/dahc/sys/dictData";
  import TablePage from "@/components/Public/table/TablePage.vue";
  import {queryArchiveTransition} from "@/api/business/archiveType";
  import {formatDate1} from "@/utils";
  import CaseUpload from "@/views/upload/caseUpload.vue";
  import FileUpload from "@/views/upload/fileUpload.vue";
  import ProjectUpload from "@/views/upload/projectUpload.vue";
  import FilePathUploader from "@/views/upload/filePathUploader.vue";
  import SimpleUploader from "@/views/upload/simpleUploader.vue";
  import {listManagement} from "@/api/dahc/trueingManage/trueingManage";

  export default {
    name: "projectInitialize",
    components: {
      SimpleUploader, FileUpload, CaseUpload, TablePage, Completed, UnderVerification, HaveNoStart, FilePathUploader,ProjectUpload
    },
    data() {
      return {
        // caseUpload: true,
        // fileUpload: true,
        // simpleUploader: true,
        dialogCaseExcel: false,
        dialogFileExcel: false,
        dialogFilePathUploader: false,
        dialogSimpleUploader: false,
        selected: [], //选中数据
        projectTotal: 0,
        tableProcessTitle: '工序信息：',
        tableProgressTitle: '核查进度',
        tableResultTitle: '核查结果',
        processData: [],
        dialogProgress: false,
        progressData: [],
        haveSelectData: [],
        progressColumn: [
          {label: '工序名称', prop: 'procedureName', width: '150'},
          {label: '工序描述', prop: 'procedureDesc', width: '180'},
          {label: '核查范围', prop: 'trueingId', width: '100', formatter: this.trueingIdFormatter},
          // {label: '核查状态', prop: 'address', width: '100'},
          {label: '应核查案卷数', prop: 'theNumberOfFilesNotVerified', width: '100'},
          {label: '已核查案卷数', prop: 'numberOfFilesVerified', width: '100'},
          // {label: '合格案卷数', prop: 'remarks', width: '100'},
          // {label: '不合格案卷数', prop: 'remarks', width: '100'},
          {label: '核查人数', prop: 'numberOfPeopleVerified', width: '80'},
        ],
        progressTotal: 0,
        progressSize: 10,
        progressNum: 1,
        queryParams: {
          projectTableName: '',
          pageSize: 20,
          pageNum: 1,
          projectState: 0
        },
        /*工序表格参数*/
        queryProcessParams: {
          projectId: '',
          pageSize: 20,
          pageNum: 1,
        },
        /*核查项表格参数*/
        queryExamineParams: {
          id: '',
          pageSize: 20,
          pageNum: 1,
        },
        projectColumn1: [
          {label: '项目名称', prop: 'projectName', width: '300'},
          {label: '项目描述', prop: 'projectDesc', width: '300'},
          {label: '档案类型', prop: 'archiveTypeId', width: '120', formatter: this.archiveTypeIdFormatter},
          {label: '预计开始时间', prop: 'startTime', width: '100', formatter: formatDate1},
          {label: '预计结束时间', prop: 'endTime', width: '100', formatter: formatDate1},
          // {label: '已导入案卷数', prop: 'haveCaseNum', width: '100',},
          {label: '创建人', prop: 'createByName', width: '60'},
          {label: '创建时间', prop: 'createTime', width: '100', formatter: formatDate1},
          {label: '备注', prop: 'remarks', width: '80'},
        ],
        projectColumn2: [
          {label: '项目名称', prop: 'projectName', width: '120'},
          {label: '项目描述', prop: 'projectDesc', width: '100'},
          {label: '档案类型', prop: 'archiveTypeId', width: '80', formatter: this.archiveTypeIdFormatter},
          {label: '实际开始时间', prop: 'startTime', width: '80', formatter: formatDate1},
          // {label: '已导入案卷数', prop: 'haveCaseNum', width: '80',},
          {label: '预计结束时间', prop: 'endTime', width: '100', formatter: formatDate1},
          {label: '创建人', prop: 'createByName', width: '60'},
          {label: '创建时间', prop: 'createTime', width: '100', formatter: formatDate1},
          {label: '备注', prop: 'remarks', width: '60'},
        ],
        projectColumn3: [
          {label: '项目名称', prop: 'projectName', width: '120'},
          {label: '项目描述', prop: 'projectDesc', width: '100'},
          {label: '档案类型', prop: 'archiveTypeId', width: '80', formatter: this.archiveTypeIdFormatter},
          // {label: '已完成案卷数', prop: 'haveCheckNum', width: '80',},
          {label: '创建人', prop: 'createByName', width: '60'},
          {label: '创建时间', prop: 'createTime', width: '80', formatter: formatDate1},
          {label: '备注', prop: 'remarks', width: '60'},
        ],
        processColumn: [
          {label: '工序名称', prop: 'procedureName', width: '120'},
          {label: '工序描述', prop: 'procedureDesc', width: '150'},
          {label: '核查范围', prop: 'trueingId', width: '80', formatter: this.trueingIdFormatter},
          {label: '创建人', prop: 'createByName', width: '60'},
          {label: '创建时间', prop: 'createTime', width: '60', formatter: formatDate1},
        ],

        processTotal: 0,
        processSize: 10,
        processNum: 1,
        dialogType: 'add',
        dialogVisible: false,
        /*档案类型下拉框数据*/
        archivesTypeNameOptions: [],
        projectForm: {
          projectName: '',
          projectDesc: '',
          archiveTypeId: '',
          startTime: '',
          endTime: '',
          remarks: '',
          projectState: 0,
        },
        dialogExamine: false,
        examineColumn: [
          {label: '核查项名称', prop: 'trueingName', width: '120', cellName: 'cellName'},
          {label: '核查项描述', prop: 'trueingDesc', width: '150', cellName: 'cellName'},
          {label: '核查标准', prop: 'examineStasString', width: '180', cellName: 'cellName'},
          {label: '核查范围', prop: 'trueingScopeStair', width: '80', formatter: this.trueingScopeStairFormatter},
        ],
        dialogUpdateProcess: false,
        processForm: {
          procedureDesc: '',
          procedureName: '',
          sort: '',
          projectId: '',
          trueingId: [],
          examineTemplate: '',
          dahcHcxTrueingManageList: []
        },
        noSelectData: [],
        examineTemplateData: [],
        checkItemColumn: [
          {label: '核查项名称', prop: 'trueingName', width: '100'},
          {label: '核查项描述', prop: 'trueingDesc', width: '100'},
          {label: '核查标准', prop: 'examineStasString', width: '100'},
          // {label: '核查范围', prop: 'trueingScopeStair', width: '100', formatter: this.trueingScopeStairFormatter},
        ],
        checkItemTotal: 0,
        checkItemSize: 10,
        checkItemNum: 1,
        projectRules: {
          projectName: [
            {required: true, message: "项目名称不能为空", trigger: "blur"},
            {max: 30, message: "项目名称长度最大为30"}
          ],
          archiveTypeId: [
            {required: true, message: "档案类型不能为空", trigger: "change"},
          ],
          startTime: [
            {required: true, message: "预计开始时间不能为空", trigger: "blur"},
          ],
          endTime: [
            {required: true, message: "预计结束时间不能为空", trigger: "blur"},
          ],
        },
        processRules: {
          procedureName: [
            {required: true, message: "工序名称不能为空", trigger: "blur"},
          ],
          trueingId: [
            {required: true, message: "核查模板不能为空", trigger: "change"},
          ],
          sort: [
            {required: true, message: "排序不能为空", trigger: "blur"},
            {pattern: /^(0|[1-9][0-9]*)$/, message: '请输入数字', trigger: 'blur'}
          ],
        },
        returnState: 20000,
        /*核查范围字段*/
        dictDataTransition: [],
        projectData: [],
        dialogResult: false,
        resultData: [],
        resultColumn: [
          {label: '工序名称', prop: 'procedureName', width: '100'},
          {label: '工序描述', prop: 'procedureDesc', width: '100'},
          {label: '核查范围', prop: 'trueingId', width: '100', formatter: this.trueingIdFormatter},
          // {label: '合格案卷数', prop: 'name', width: '100'},
          // {label: '不合格案卷数', prop: 'name', width: '100'},
          {label: '核查案卷数', prop: 'theNumberOfFilesNotVerified', width: '100'},
          {label: '核查人数', prop: 'numberOfPeopleVerified', width: '100'},
        ],
        resultTotal: 0,
        resultSize: 10,
        resultNum: 1,
        examineData: [],
        examineTotal: 0,
        examineSize: 10,
        examineNum: 1,
        /*点击项目表格获取项目id*/
        tableProjectId: '',
        archivesId: '',
        trueingScopeStair: '',
        trueingId: ''


      }
    },
    created() {
      // console.log('this.$route',this.$route)
      this.handleQuery();
      this.queryDictDataTransition()
      this.queryArchiveTransition();
    },
    methods: {

      /*切换事件*/
      handleClick(tab, event) {
        if (tab.label == "未开始") {
          this.queryParams.projectState = 0;
          this.handleQuery();
        } else if (tab.label == "核查中") {
          this.queryParams.projectState = 1;
          this.handleQuery();
        } else if (tab.label == "已完成") {
          this.queryParams.projectState = 2;
          this.handleQuery();
        }
        this.queryParams.pageSize = 20;
        this.queryParams.pageNum = 1;
      },
      /*档案类型下拉框*/
      queryArchiveTransition() {
        queryArchiveTransition().then(res => {
          this.archivesTypeNameOptions = res.data
        });
      },
      /*表格档案类型转换*/
      archiveTypeIdFormatter(row, column, cellValue) {
        let result = '';
        this.archivesTypeNameOptions.map((item) => {
          if (item.value == cellValue) {
            result = item.label;
          }
        });
        return result;
      },
      /**
       * 查看核查结果
       */
      handleExamineResult() {
        this.dialogResult = true
      },
      handleResultChange(pageSize, pageNum) {
        this.resultSize = pageSize;
        this.resultNum = pageNum;
      },

      /*项目表格状态字典转换*/
      projectStateFormatter(row, column, cellValue) {
        let result = '';
        if (cellValue == 0) {
          result = ' 未开始 '
        } else if (row.showRecord == 1) {
          result = ' 核查中 '
        }
        if (row.showPiece == 2) {
          result = ' 核查完成 '
        }
        return result;
      },
      importExcel(key) {
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
          if (key === 'caseExcel') {

            this.dialogCaseExcel = true;
          } else if (key === 'fileExcel') {
            this.dialogFileExcel = true;
            // console.log(this.$refs.FileUpload)
            // this.$refs.FileUpload.templateAssociatedMetadataFields();
            this.$refs.FileUpload.queryArchiveBatchData();
          } else if (key === 'filePathUploader') {
            this.dialogFilePathUploader = true;
            // this.$refs.FileUpload.templateAssociatedMetadataFields();
          } else {
            this.dialogSimpleUploader = true;
          }
        }
      },
      closeCaseExcel(val) {
        this.dialogCaseExcel = val;
      },
      closeFileExcel(val) {
        this.dialogFileExcel = val;
      },
      closeSimpleUploader(val) {
        this.dialogSimpleUploader = val;
      },
      closeFilePath(val) {
        this.dialogFilePathUploader = val;
      },
      /*条件查询*/
      conditionalQueries() {
        this.queryParams.pageSize = 20;
        this.queryParams.pageNum = 1;
        this.handleQuery();
      },
      /**
       * 项目查询
       */
      handleQuery() {
        this.queryProcessParams.projectId = ''
        search(this.queryParams).then(res => {
          if (res.code == this.returnState) {
            this.projectData = res.data;
            this.projectTotal = res.total;
            /*查询工序*/
            if (res.data[0] == undefined) {
              this.processData = []
              this.processTotal = 0
            } else {
              this.queryProcessParams.projectId = res.data[0].id;
              this.searchProcedureList()
            }

          }
        });
      },
      /**
       * 新增项目
       */
      handleAdd() {
        this.projectReset();
        this.dialogType = 'add';
        this.dialogVisible = true;
        // this.projectForm = {};
      },
      /*重置项目表单*/
      projectReset() {
        this.projectForm = {
          projectName: '',
          projectDesc: '',
          archiveTypeId: '',
          startTime: [],
          endTime: '',
          remarks: '',
          projectState: 0,
        };
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
          this.projectReset();
          this.dialogType = 'edit';
          this.dialogVisible = true;
          Object.assign(this.projectForm, this.selected[0]);
          console.log(this.selected[0])
          this.projectForm.startTime = [];
          this.projectForm.startTime[0] = this.selected[0].startTime
          this.projectForm.startTime[1] = this.selected[0].endTime
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
            if (this.processData.length > 0) {
              this.$message.error("项目存在工序数据");
            } else {
              let ids = this.selected.map(item => {
                return item.id
              });
              batchDeleteByIdsProjectTable(ids).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("删除成功")
                  this.dialogVisible = false;
                  this.handleQuery();
                }
              });
            }

          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        }
      },
      /**
       * 定义工序
       */
      handleDefine() {
        if (!this.selected || this.selected === [] || this.selected.length === 0) {
          this.$message({
            message: '请至少选择一条数据',
            type: 'warning'
          });
        } else {
          // this.$router.push('/projectManage/definingProcess');
          this.$router.push({path: '/projectManage/definingProcess', query: {id: this.selected[0].id}});
        }
      },

      /**
       * 开始核查
       */
      handleStart() {
        if (!this.selected || this.selected === [] || this.selected.length === 0) {
          this.$message({
            message: '请至少选择一条数据',
            type: 'warning'
          });
        } else {
          this.$confirm('此操作将开始当前项目的核查任务, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            this.selected[0].projectState = 1;
            startInspect(this.selected[0]).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("修改成功")
                this.handleQuery();
              }
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消开始核查'
            });
          });
        }
      },
      /**
       * 表格复选框
       */
      handleProjectSelection(selection) {
        this.selected = selection
        // 清除 所有勾选项
        if (selection.length === 1) {
          this.selected = selection;
          this.queryProcessParams.projectId = this.selected[0].id
          this.tableProjectId = this.selected[0].id
          this.archivesId = this.selected[0].archiveTypeId
          this.searchProcedureList();
        }
        if (selection.length > 1) {
          this.$refs.projectRef.$refs.tableRef.clearSelection();
          this.$refs.projectRef.$refs.tableRef.toggleRowSelection(selection.at(-1), true);
          this.selected = selection.slice(-1);
          this.queryProcessParams.projectId = this.selected[0].id
          this.searchProcedureList();
        }
        if (selection.length === 0) {
          this.selected = [];
          this.queryProcessParams.projectId = this.projectData[0].id
          this.searchProcedureList();
        }
      },
      /**
       * 表格复选框
       */
      handleProjectSelection2(selection) {
        console.log(selection, "点击")
        this.selected = selection
        // 清除 所有勾选项
        if (selection.length === 1) {
          this.selected = selection;
          this.queryProcessParams.projectId = this.selected[0].id
          this.tableProjectId = this.selected[0].id
          this.archivesId = this.selected[0].archiveTypeId
          this.searchProcedureList();
        }
        if (selection.length > 1) {
          this.$refs.projectRef2.$refs.tableRef.clearSelection();
          this.$refs.projectRef2.$refs.tableRef.toggleRowSelection(selection.at(-1), true);
          this.selected = selection.slice(-1);
          this.queryProcessParams.projectId = this.selected[0].id
          this.searchProcedureList();
        }
        if (selection.length === 0) {
          this.selected = [];
          this.queryProcessParams.projectId = this.projectData[0].id
          this.searchProcedureList();
        }
      },
      /**
       * 表格复选框
       */
      handleProjectSelection3(selection) {
        this.selected = selection
        // 清除 所有勾选项
        if (selection.length === 1) {
          this.selected = selection;
          this.queryProcessParams.projectId = this.selected[0].id
          this.tableProjectId = this.selected[0].id
          this.archivesId = this.selected[0].archiveTypeId
          this.searchProcedureList();
        }
        if (selection.length > 1) {
          this.$refs.projectRef3.$refs.tableRef.clearSelection();
          this.$refs.projectRef3.$refs.tableRef.toggleRowSelection(selection.at(-1), true);
          this.selected = selection.slice(-1);
          this.queryProcessParams.projectId = this.selected[0].id
          this.searchProcedureList();
          // this.handleClick(this.activeName)
        }
        if (selection.length === 0) {
          this.selected = [];
          this.queryProcessParams.projectId = this.projectData[0].id
          this.searchProcedureList();
        }
      },


      /**
       * 项目分页器
       */
      handleProjectChange(pageSize, pageNum) {
        this.queryParams.pageSize = pageSize;
        this.queryParams.pageNum = pageNum;
        this.handleQuery();
      },


      // 选择
      moveSelect(arr1, item, index, arr2) {
        arr1.splice(index, 1);
        arr2.push(item);
        console.log('arr1', arr1);
        console.log('arr2', arr2)
      },
      // 取消选择
      removeSelect(arr1, item, index, arr2) {
        arr1.splice(index, 1);
        arr2.push(item);
        console.log('arr1', arr1);
        console.log('arr2', arr2)
      },
      // 上移
      moveUp(arr, item, index) {
        arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
        arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
      },
      // 下移
      moveDown(arr, item, index) {
        arr.splice(index + 2, 0, item);
        arr.splice(index, 1);
      },

      // 控制下移按钮的显示于隐藏
      getFormLength(index, arr) {
        if (index === arr.length - 1) {
          return true;
        } else {
          return false;
        }
      },

      /**
       *点击事件
       */
      handleCascader(value) {
        this.noSelectData = [];
        this.haveSelectData = [];
        /*根据核查范围id查询*/
        this.trueingScopeStair = value[2]
        this.queryInspect();
      },
      /*新增核查项数据查询，未选择表格*/
      queryInspect() {
        this.queryProcessParams.trueingScopeStair = this.trueingScopeStair
        listManagement(this.queryProcessParams).then(response => {
          // this.noSelectData = response.data;
          this.noSelectData = JSON.parse(JSON.stringify(response.data));
          if (this.noSelectData) {
            this.noSelectData.forEach(item => {
              // console.log(item.examineStasString)
              let str = item.examineStasString.replace(/\//g, '；');
              item.examineStasString = str.slice(0, item.examineStasString.length - 1)
            })
          }
        });
      },
      /**
       * 保存项目数据
       */
      handleSave(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const arr = this.projectForm.startTime
            this.projectForm.endTime = this.projectForm.startTime[1]
            this.projectForm.startTime = this.projectForm.startTime[0]
            if (this.dialogType == 'edit') {
              updateProjectTable(this.projectForm).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("修改成功")
                  this.dialogVisible = false;
                  this.handleQuery();
                }
              }).finally(() => {
                this.projectForm.startTime = arr
              });
            } else {
              addProjectTable(this.projectForm).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("新增成功")
                  this.dialogVisible = false;
                  this.handleQuery();
                }
              }).finally(() => {
                this.projectForm.startTime = arr
              });
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      /**
       * 查看数据
       */
      handleData(row) {
        this.$router.push({path: '/projectManage/archivesData', query: {projectId: row.id}});
      },

      /***********************************************************下方表格工序--------开始*****************************************/
      /*工序核查范围*/
      trueingIdFormatter(row, column, cellValue) {
        let result = '';
        this.dictDataTransition.map((item) => {
          if (item.value == cellValue) {
            result = item.label;
          }
        });
        return result;
      },
      /*表格点击事件*/
      handleProjectClick(row, column, event) {
        this.procedureId = row.row.id
        this.tableProjectId = row.row.id
        this.archivesId = row.row.archiveTypeId
        this.queryProcessParams.projectId = row.row.id
        this.searchProcedureList();
      },
      /*工序初始查询*/
      searchProcedureList() {
        searchProcedureList(this.queryProcessParams).then(res => {
          this.processData = res.data
          this.processTotal = res.total
        });
      },
      /*工序分页*/
      handleProcessChange(pageSize, pageNum) {
        this.queryProcessParams.pageSize = pageSize;
        this.queryProcessParams.pageNum = pageNum;
        this.searchProcedureList()
      },
      /**
       * 查看核查项详情
       */
      handleExamineDetail(item) {
        this.dialogExamine = true;
        this.queryExamineParams.id = item.id
        this.queryInspectTableList();
      },
      /*查看核查项分页*/
      handleExamineChange(pageSize, pageNum) {
        this.queryExamineParams.pageSize = pageSize;
        this.queryExamineParams.pageNum = pageNum;
        this.queryInspectTableList();
      },
      /*查看核查项*/
      queryInspectTableList() {
        queryInspectTableList(this.queryExamineParams).then(res => {
          if (res.code === this.returnState) {
            this.examineData = JSON.parse(JSON.stringify(res.data))
            if (this.examineData) {
              this.examineData.forEach(item => {
                let str = item.examineStasString.replace(/\//g, '；');
                item.examineStasString = str.slice(0, item.examineStasString.length - 1)
              })
            }
          }
          // this.examineData = res.data
          this.examineTotal = res.total
        });
      },
      /*查询核查范围，表格转换问题*/
      queryDictDataTransition() {
        queryDictDataTransition().then(res => {
          this.dictDataTransition = res
        });
      },

      //表格核查范围字段字典转换
      trueingScopeStairFormatter(row, column, cellValue) {
        let result = '';
        this.dictDataTransition.map((item) => {
          if (item.value == cellValue) {
            result = item.label;
          }
        });
        return result;
      },
      /*查询字典表tree字典范围数据*/
      queryInspectTree() {
        queryInspectTree("100").then(res => {
          this.examineTemplateData = res.data
        });
      },
      // 编辑工序
      moveUpdateProcess(item) {
        this.definingReset();
        this.queryInspectTree();
        this.dialogUpdateProcess = true;
        Object.assign(this.processForm, item);
        const arrNot = {
          procedureId: item.id,
          boolean: false,
          trueingId: item.trueingId
        }
        /*查询工序未选择的核查项*/
        searchProcedureUnselectedList(arrNot).then(res => {
          this.noSelectData = JSON.parse(JSON.stringify(res.data))
          if (this.noSelectData) {
            this.noSelectData.forEach(item => {
              // console.log(item.examineStasString)
              let str = item.examineStasString.replace(/\//g, '；');
              item.examineStasString = str.slice(0, item.examineStasString.length - 1)
            })
          }
        });
        const arr = {
          procedureId: item.id,
          boolean: true,
          trueingId: item.trueingId
        }
        /*查询工序选择的核查项*/
        searchProcedureUnselectedList(arr).then(res => {
          if (res.code === this.returnState) {
            this.haveSelectData = JSON.parse(JSON.stringify(res.data))
            if (this.haveSelectData) {
              this.haveSelectData.forEach(item => {
                // console.log(item.examineStasString)
                let str = item.examineStasString.replace(/\//g, '；');
                item.examineStasString = str.slice(0, item.examineStasString.length - 1)
              })
            }
          }
        });
      },
      /*保存编辑工序按钮*/
      handleProcessSave(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // if (this.haveSelectData.length > 0) {
              /*编辑*/
              this.processForm.dahcHcxTrueingManageList = this.haveSelectData;
              console.log(this.processForm)
              this.trueingId = this.processForm.trueingId;
              this.processForm.trueingId = this.trueingId[2];
              updateProcedure(this.processForm).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("修改成功")
                  this.dialogUpdateProcess = false;
                  this.searchProcedureList();
                }
              }).finally(() => {
                this.processForm.trueingId = this.trueingId;
              });
            // } else {
            //   this.$message.error("没有核查项信息");
            // }
          }
        });
      },
      /*工序表单清空*/
      definingReset() {
        this.processForm = {
          procedureDesc: '',
          procedureName: '',
          sort: '',
          trueingId: [],
          examineTemplate: '',
          dahcHcxTrueingManageList: []
        }
      },
      /*工序上移动*/
      moveUpTable(arr, item, index) {
        this.$confirm('此操作将上移工序名称为：' + item.procedureName + '的排序, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          /*上移传选中数据和序列前一个工序数据*/
          const ids = {
            'id1': item.id,
            'id2': arr[index - 1].id
          }
          procedureUpAndDown(ids).then(res => {
            if (res.code === this.returnState) {
              this.$message.success("上移成功");
              // arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
              // arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
              this.searchProcedureList();
            }
          });
        })
        // /*上移传选中数据和序列前一个工序数据*/
        // const ids = {
        //   'id1': item.id,
        //   'id2': arr[index - 1].id
        // }
        // procedureUpAndDown(ids).then(res => {
        //   // arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
        //   // arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
        //   this.searchProcedureList();
        // });
      },
      /*工序下移动*/
      moveDownTable(arr, item, index) {
        this.$confirm('此操作将下移工序名称为：' + item.procedureName + '的排序, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          /*上移传选中数据和序列前一个工序数据*/
          const ids = {
            'id1': item.id,
            'id2': arr[index + 1].id
          }
          procedureUpAndDown(ids).then(res => {
            if (res.code === this.returnState) {
              this.$message.success("下移成功");
              // arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
              // arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
              this.searchProcedureList();
            }
          });
        })
        // const ids = {
        //   'id1': item.id,
        //   'id2': arr[index + 1].id
        // }
        // procedureUpAndDown(ids).then(res => {
        //   this.searchProcedureList()
        // });
      },
      // 删除
      deleteItem(arr, row, index) {
        const ids = [];
        ids.push(row.id);
        this.$confirm('此操作将永久删除本行数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          batchDeleteByIdsProcedure(ids).then(res => {
            if (res.code == this.returnState) {
              this.$message.success("删除成功");
              this.searchProcedureList();
            }
          })
        });
      },
      /**
       * 结束核查
       */
      handleEnd() {
        if (!this.selected || this.selected === [] || this.selected.length === 0) {
          this.$message({
            message: '请至少选择一条数据',
            type: 'warning'
          });
        } else {
          this.$confirm('此操作将结束当前项目的核查任务, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            this.selected[0].projectState = 2;
            endOfVerification(this.selected[0]).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("修改成功")
                this.handleQuery();
              }
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消开始核查'
            });
          });
        }
      },
      /**
       * 查看核查进度
       */
      handleExamineProgress() {
        this.dialogProgress = true
      },
      handleProgressChange(pageSize, pageNum) {
        this.progressSize = pageSize;
        this.progressNum = pageNum;
      },


    }
  }
</script>


<style lang="scss" scoped>

  .tablePage {
    height: calc(50% - 52px) !important;

  }

  .completedTable {
    margin-top: 10px;
    height: calc(50% - 14px) !important;
  }

  .processDialog {
    .tablePage {
      height: calc(40vh - 110px) !important;
      margin-bottom: 5px;
    }

    .tableDes:first-of-type {
      padding-top: 0;
    }

    .examineData {
      height: calc(70vh);
    }
  }

  ::v-deep .el-table__body-wrapper {
    .cellName {
      .cell {
        word-break: keep-all;
        word-wrap: break-word;
        text-align: left;
        //color: red;
      }
    }
  }

  ::v-deep .projectForm {
    .el-form-item__label {
      width: 120px;
    }

    .el-form-item__content {
      width: calc(100% - 125px) !important;
    }
  }


  .el-table .warning-row {
    background: #f3fde6;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
