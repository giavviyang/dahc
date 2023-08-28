import request from '@/utils/request'

// 查询项目列表
export function search(query) {
  return request({
    url: '/projecttable/search',
    method: 'get',
    params: query
  })
}
// 查询下拉框
export function queryProjectPullDown(query) {
  return request({
    url: '/projecttable/queryProjectPullDown',
    method: 'get',
    params: query
  })
}

// 查询项目批次 tree树
export function queryArchiveBatchData(query) {
  return request({
    url: '/projecttable/queryArchiveBatchData',
    method: 'get',
    params: query
  })
}

// 查询下拉框
export function queryUserProject() {
  return request({
    url: '/projecttable/queryUserProject',
    method: 'get',
  })
}
// 查询下拉框
export function getOne(id) {
  return request({
    url: '/projecttable/getOne/' + id,
    method: 'get',
  })
}


// 查询存疑页面项目tree树
export function queryTheProjectOperationTree() {
  return request({
    url: '/projecttable/queryTheProjectOperationTree',
    method: 'get',
  })
}

// 查询个人管理项目tree树
export function queryThePersonalManagementProjectTree() {
  return request({
    url: '/projecttable/queryThePersonalManagementProjectTree',
    method: 'get',
  })
}

// 查询个人任务tree树
export function queryThePersonalTaskTree() {
  return request({
    url: '/projecttable/queryThePersonalTaskTree',
    method: 'get',
  })
}

// 新增项目
export function addProjectTable(data) {
  return request({
    url: '/projecttable/add',
    method: 'post',
    data: data
  })
}

// 编辑项目
export function updateProjectTable(data) {
  return request({
    url: '/projecttable/update',
    method: 'post',
    data: data
  })
}

// 开始核查
export function startInspect(data) {
  return request({
    url: '/projecttable/startInspect',
    method: 'post',
    data: data
  })
}

// 结束核查
export function endOfVerification(data) {
  return request({
    url: '/projecttable/endOfVerification',
    method: 'post',
    data: data
  })
}

// 删除项目
export function batchDeleteByIdsProjectTable(ids) {
  return request({
    url: '/projecttable/batchDeleteByIds/' + ids,
    method: 'post'
  })
}

//上传数据
export function importExcel(formData) {
  return request({
    url: '/datatemplate/importData',
    method: 'post',
    data: formData,
  })
}
export function importZip(formData) {
  return request({
    url: '/datatemplate/uploadFileOfZip',
    method: 'post',
    data: formData,
  })
}

// 解析表头推荐模板
export function queryTemplateRelevanceAccordingToExcel(file) {
  return request({
    url: '/datatemplate/queryTemplateRelevanceAccordingToExcel',
    method: 'post',
    data: file,
  })
}

// 上传excel读取表头和对应序号
export function uploadExcelToReadTheHeaderAndSerialNumber(file) {
  return request({
    url: '/datatemplate/uploadExcelToReadTheHeaderAndSerialNumber',
    method: 'post',
    data: file,
  })
}


