import request from '@/utils/request'

// 查询
export function listData(query) {
  return request({
    url: '/mapper/query',
    method: 'get',
    params: query
  })
}

// 条件查询
export function mapperSearchData(data) {
  return request({
    url: '/mapper/search',
    method: 'get',
	params: data
  })
}

// 新增
export function addData(data) {
  return request({
    url: '/mapper/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateData(data) {
  return request({
    url: '/mapper/upadte',
    method: 'post',
    data: data
  })
}

// 批量删除
export function delData(ids) {
  return request({
    url: '/mapper/' + ids,
    method: 'post'
  })
}
