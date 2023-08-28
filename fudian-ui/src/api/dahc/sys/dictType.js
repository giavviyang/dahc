import request from '@/utils/request'

// 查询字典类型列表
export function listType() {
  return request({
    url: '/system/dictType/list',
    method: 'get',
  })
}

// 查询字典类型详细
export function getType(dictId) {
  return request({
    url: '/system/dictType/' + dictId,
    method: 'get'
  })
}

// 查询字典类型详细
export function queryDictType() {
  return request({
    url: '/system/dictType/queryDictType',
    method: 'get'
  })
}

// 新增字典类型
export function addType(data) {
  return request({
    url: '/system/dictType/add',
    method: 'post',
    data: data
  })
}

// 修改字典类型
export function updateType(data) {
  return request({
    url: '/system/dictType/update',
    method: 'post',
    data: data
  })
}

// 删除字典类型
export function delType(dictId) {
  return request({
    url: '/system/dictType/' + dictId,
    method: 'Post'
  })
}
