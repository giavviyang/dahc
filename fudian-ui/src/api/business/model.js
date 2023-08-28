import request from '@/utils/request'

// 查询
export function listData(query) {
	return request({
		url: '/model/query',
		method: 'get',
		params: query
	})
}

// 条件查询
export function searchData(data) {
	return request({
		url: '/model/search',
		method: 'get',
		params: data
	})
}


// 新增
export function addData(data) {
	return request({
		url: '/model/add',
		method: 'post',
		data: data
	})
}

// 修改
export function updateData(data) {
	return request({
		url: '/model/edit',
		method: 'post',
		data: data
	})
}

// 删除
export function delData(ids) {
	return request({
		url: '/model/batchDeleteByIds/' + ids,
		method: 'post'
	})
}



//根据档案类型查询模板
export function selecctModelByArchiveType(data) {
	return request({
		url: '/archiveType/selecctModelByArchiveType',
		method: 'post',
		params: data
	})
}



// 获取模板的数据
export function selectMapperByModel(data) {
	return request({
		url: '/model/selectMapperByModel',
		method: 'get',
		params: data
	})
}



// 新增
export function addMapperData(data) {
	return request({
		url: '/mapper/add',
		method: 'post',
		data: data
	})
}

// 修改
export function updateMapperData(data) {
	return request({
		url: '/mapper/upadte',
		method: 'post',
		data: data
	})
}

// 删除
export function delMapperData(ids) {
	return request({
		url: '/mapper/batchDeleteByIds/' + ids,
		method: 'post'
	})
}
//批量增加元数据映射
export function bulkAdditions(data) {
  return request({
    url: '/mapper/bulkAdditions',
    method: 'post',
    data: data
  })
}
