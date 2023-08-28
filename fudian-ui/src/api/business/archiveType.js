import request from '@/utils/request'

// 查询
export function listData(query) {
  return request({
    url: '/archiveType/query',
    method: 'get',
    params: query
  })
}

// 获取文件数据的attr
export function obtainCaseDataAttr(query) {
  return request({
    url: '/archiveType/obtainCaseDataAttr',
    method: 'get',
    params: query
  })
}

// 条件查询
export function searchData(data) {
  return request({
    url: '/archiveType/search',
    method: 'get',
	  params: data
  })
}
// 下拉框查询
export function queryArchiveTransition() {
  return request({
    url: '/archiveType/queryArchiveTransition',
    method: 'get',
  })
}
// 条件查询
export function selectListAndMetadata(data) {
  return request({
    url: '/archiveType/selectListAndMetadata',
    method: 'get',
	  params: data
  })
}

// 条件查询
export function theFileNumberFieldIsThatDatabaseField(archiveTypeId) {
  return request({
    url: '/archiveType/theFileNumberFieldIsThatDatabaseField/' + archiveTypeId,
    method: 'get',
  })
}

// 文件档号
export function caseFileNumberAttr(archiveTypeId) {
  return request({
    url: '/archiveType/caseFileNumberAttr/' + archiveTypeId,
    method: 'get',
  })
}

// 新增
export function addData(data) {
  return request({
    url: '/archiveType/add',
    method: 'post',
    data: data
  })
}
// 设置映射关系
export function updateTheMapping(data) {
  return request({
    url: '/archiveType/insertListAndMetadataList',
    method: 'post',
    data: data
  })
}
// 回显元数据表格信息
export function queryArchiveAndMetadata(data) {
  return request({
    url: '/archiveType/queryArchiveAndMetadata',
    method: 'post',
    data: data
  })
}

// 修改
export function updateData(data) {
  return request({
    url: '/archiveType/edit',
    method: 'post',
    data: data
  })
}

// 删除
export function delData(ids) {
  return request({
    url: '/archiveType/batchDeleteByIds/'+ ids,
    method: 'post'
  })
}

//根据档案类型获取数据
export function getDataByArchiveType(data) {
  return request({
    url: '/archiveType/getDataByArchiveType',
    method: 'post',
    data: data
  })
}
