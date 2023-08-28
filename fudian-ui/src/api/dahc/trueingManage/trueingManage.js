import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listManagement(query) {
  return request({
    url: '/trueing/management/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】列表
export function getOneManagement(id) {
  return request({
    url: '/trueing/management/getOne/'+ id,
    method: 'get'
  })
}
// 根据工序和项目id查询核查项数据
export function queryProcedureInspect(query) {
  return request({
    url: '/trueing/management/queryProcedureInspect',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getManagement(id) {
  return request({
    url: '/trueing/management/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addManagement(data) {
  return request({
    url: '/trueing/management/add',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateManagement(data) {
  return request({
    url: '/trueing/management/update',
    method: 'post',
    data: data
  })
}

// 删除【请填写功能名称】
export function delManagement(ids) {
  return request({
    url: '/trueing/management/remove/' + ids,
    method: 'post'
  })
}
