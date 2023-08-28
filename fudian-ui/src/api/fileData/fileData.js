import request from '@/utils/request'

// 修改
export function updateFileData(data) {
  return request({
    url: '/datatemplateCheck/update',
    method: 'post',
    data: data
  })
}
// 删除
export function deleteById(data) {
  return request({
    url: '/datatemplateCheck/deleteById',
    method: 'post',
    data: data
  })
}

// 新增
export function increaseCases(data) {
  return request({
    url: '/datatemplateCheck/increaseCases',
    method: 'post',
    data: data
  })
}


// 文件上下移动
export function modifyTheSort(data) {
  return request({
    url: '/datatemplateCheck/modifyTheSort',
    method: 'post',
    data: data
  })
}

// 导入文件
export function importDataVolume(fromData) {
  return request({
    url: '/datatemplate/importDataVolume',
    method: 'post',
    data: fromData
  })
}

// 导入excel文件
export function exportExcelAccordingToFileType(data) {
  return request({
    url: '/datatemplate/exportExcelAccordingToFileType',
    method: 'post',
    data: data
  })
}

export function getTheFileInformationAndCompressIt(data) {
  return request({
    url: '/datatemplate/getTheFileInformationAndCompressIt',
    method: 'post',
    data: data
  })
}
//上传Excel 导入文件条目数据
export function importDataPiece(fromData) {
  return request({
    url: '/datatemplate/importDataPiece',
    method: 'post',
    data: fromData
  })
}
/*读取图片目录上传图片*/
export function readingPicturesFromAFileDirectory(data) {
  return request({
    url: '/datatemplate/readingPicturesFromAFileDirectory',
    method: 'post',
    data: data
  })
}
//获取异步任务执行结果
export function getInformation(id) {
  return request({
    url: '/datatemplate/getInformation/'+ id,
    method: 'get',
  })
}
//获取异步任务执行结果
export function getzipInformation(id) {
  return request({
    url: '/datatemplate/getzipInformation/'+ id,
    method: 'get',
  })
}

//获取异步任务执行结果
export function downloadFileBig(id) {
  return request({
    url: '/uploader/downloadFileBig/'+ id,
    method: 'get',
  })
}

// 根据批次查询案卷数据
export function queryCaseFileDataBasedOnBatches(query) {
  return request({
    url: '/datatemplateCheck/queryCaseFileDataBasedOnBatches',
    method: 'get',
    params: query
  })
}
// 查询个人核查数据
export function accessPersonalVerificationData(query) {
  return request({
    url: '/homePageExhibition/accessPersonalVerificationData',
    method: 'get',
    params: query
  })
}
// 查询个人存疑数据
export function queryPersonalDoubtfulData(query) {
  return request({
    url: '/homePageExhibition/queryPersonalDoubtfulData',
    method: 'get',
    params: query
  })
}
