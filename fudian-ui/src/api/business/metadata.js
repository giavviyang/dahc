import request from '@/utils/request'

// 查询
export function listData(query) {
  return request({
    url: '/metadata/query',
    method: 'get',
    params: query
  })
}

// 条件查询
export function searchData(query) {
  return request({
    url: '/metadata/search',
    method: 'get',
	  params: query
  })
}
// 条件查询
export function queryMetadataSelect() {
  return request({
    url: '/metadata/queryMetadataSelect',
    method: 'get'
  })
}

// 新增
export function addData(data) {
  return request({
    url: '/metadata/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateData(data) {
  return request({
    url: '/metadata/edit',
    method: 'post',
    data: data
  })
}

// 删除
export function delData(ids) {
  return request({
    url: '/metadata/batchDeleteByIds/' + ids,
    method: 'post'
  })
}
