import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listData(query) {
  return request({
    url: '/system/dictData/list',
    method: 'get',
    params: query
  })
}
// 查询【请填写功能名称】列表
export function listAllData(query) {
  return request({
    url: '/system/dictData/listAll',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getData(id) {
  return request({
    url: '/system/dictData/getInfo/' + id,
    method: 'get'
  })
}

// 查询【请填写功能名称】详细
export function queryDictDataTransition() {
  return request({
    url: '/system/dictData/queryDictDataTransition',
    method: 'get'
  })
}

// 查询【请填写功能名称】详细
export function queryDictDataTransitionId(dictTypeId) {
  return request({
    url: '/system/dictData/queryDictDataTransitionId/' + dictTypeId,
    method: 'get'
  })
}

// 查询【请填写功能名称】详细
export function queryInspectTree(dictTypeId) {
  return request({
    url: '/system/dictData/queryInspectTree/' + dictTypeId,
    method: 'get'
  })
}
// 根据工序的字典id获取字典属性
export function queryProcedureData(dictDataId) {
  return request({
    url: '/system/dictData/queryProcedureData/' + dictDataId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addData(data) {
  return request({
    url: '/system/dictData/add',
    method: 'post',
    data: data
  })
}// 新增【请填写功能名称】
export function auditManagementAdd(data) {
  return request({
    url: '/system/dictData/auditManagementAdd',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateData(data) {
  return request({
    url: '/system/dictData/upadte',
    method: 'post',
    data: data
  })
}

// 删除【请填写功能名称】
export function delData(id) {
  return request({
    url: '/system/dictData/' + id,
    method: 'delete'
  })
}
