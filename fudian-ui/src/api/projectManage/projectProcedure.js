import request from '@/utils/request'

// 查询工序列表
export function searchProcedureList(query) {
  return request({
    url: '/projectprocedure/search',
    method: 'get',
    params: query
  })
}

// 查询工序列表
export function query() {
  return request({
    url: '/projectprocedure/query',
    method: 'get'
  })
}

// 查询工序列表
export function queryInspectTableList(query) {
  return request({
    url: '/projectprocedure/queryInspectTableList',
    method: 'get',
    params: query
  })
}

// 编辑回显核查项 --- 未选择
export function searchProcedureUnselectedList(query) {
  return request({
    url: '/projectprocedure/searchProcedureUnselectedList',
    method: 'get',
    params: query
  })
}

// 工序上下移动
export function procedureUpAndDown(query) {
  return request({
    url: '/projectprocedure/procedureUpAndDown',
    method: 'post',
    params: query
  })
}

// 核查项上下移动
export function procedureInspectUpAndDown(query) {
  return request({
    url: '/projectprocedure/procedureInspectUpAndDown',
    method: 'post',
    params: query
  })
}

// 查询用户关联工序
export function queryUserProcedure(query) {
  return request({
    url: '/projectprocedure/queryUserProcedure',
    method: 'get',
    params: query
  })
}

//获取工序核查案卷的数量
export function obtainTheNumberOfProcessVerificationFiles(query) {
  return request({
    url: '/projectprocedure/obtainTheNumberOfProcessVerificationFiles',
    method: 'get',
    params: query
  })
}

// 查询用户关联工序
export function queryUserProjectProcedure(projectId) {
  return request({
    url: '/projectprocedure/queryUserProjectProcedure/' + projectId,
    method: 'get',
  })
}

// 根据工序id查询数据
export function getOneProjectprocedure(id) {
  return request({
    url: '/projectprocedure/getOne/' + id,
    method: 'get',
  })
}

// 保存用户关联工序
export function saveUserProcedure(data) {
  return request({
    url: '/projectprocedure/saveUserProcedure',
    method: 'post',
    data: data
  })
}

// 新增工序
export function addProcedure(data) {
  return request({
    url: '/projectprocedure/add',
    method: 'post',
    data: data
  })
}

// 编辑项目
export function updateProcedure(data) {
  return request({
    url: '/projectprocedure/update',
    method: 'post',
    data: data
  })
}

// 删除工序
export function batchDeleteByIdsProcedure(ids) {
  return request({
    url: '/projectprocedure/batchDeleteByIds/' + ids,
    method: 'post'
  })
}

// 删除项目
export function deleteProcedureTrueingId(trueingId) {
  return request({
    url: '/projectprocedure/deleteProcedureTrueingId/' + trueingId,
    method: 'post'
  })
}



