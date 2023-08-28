import request from '@/utils/request'

// 查询核查结果数据
export function listData(query) {
  return request({
    url: '/recordTrueing/queryProcedureInspect',
    method: 'get',
    params: query
  })
}
/*电子档案: 查询电子档案数据 案卷级*/
export function queryElectronicArchivesDossierLevel(query) {
  return request({
    url: '/recordTrueing/queryElectronicArchivesDossierLevel',
    method: 'get',
    params: query
  })
}

/*电子档案: 查询电子档案数据 案卷级*/
export function queryElectronicArchivesDossierLevelTemplateTwo(query) {
  return request({
    url: '/recordTrueing/queryElectronicArchivesDossierLevelTemplateTwo',
    method: 'get',
    params: query
  })
}
/*电子档案: 查询电子档案数据 文件级级*/
export function queryCaseLevelArchivesDossierLevelTemplateTwo(query) {
  return request({
    url: '/recordTrueing/queryCaseLevelArchivesDossierLevelTemplateTwo',
    method: 'get',
    params: query
  })
}
/*电子档案: 查询电子档案数据 文件级级*/
export function queryArchivesDossierLevelTemplateTwoBackup(query) {
  return request({
    url: '/recordTrueing/queryArchivesDossierLevelTemplateTwoBackup',
    method: 'get',
    params: query
  })
}
/*文件级数据查询*/
export function caseLevelDataQueries(query) {
  return request({
    url: '/recordTrueing/caseLevelDataQueries',
    method: 'get',
    params: query
  })
}

/*电子档案：查询核查结果*/
export function queryProcedureInspectElectronicArchives(query) {
  return request({
    url: '/recordTrueing/queryProcedureInspectElectronicArchives',
    method: 'get',
    params: query
  })
}

/*电子档案模板二：查询核查结果*/
export function queryElectronicProfileTemplateIIArchives(query) {
  return request({
    url: '/recordTrueing/queryElectronicProfileTemplateIIArchives',
    method: 'get',
    params: query
  })
}

/*存疑查询表格*/
export function conditionalQueries(query) {
  return request({
    url: '/recordTrueing/conditionalQueries',
    method: 'get',
    params: query
  })
}

/*任务管理查询表格*/
export function theTaskManagementPageQueriesTheSuspectData(query) {
  return request({
    url: '/recordTrueing/theTaskManagementPageQueriesTheSuspectData',
    method: 'get',
    params: query
  })
}

/*任务管理查询表格*/
export function taskManagementPageQueriesTheSuspectData(query) {
  return request({
    url: '/recordTrueing/taskManagementPageQueriesTheSuspectData',
    method: 'get',
    params: query
  })
}

/*根据核查结果id查询档案数据 案卷级*/
export function queryBasedOnTheIDOfTheVerificationResult(query) {
  return request({
    url: '/recordTrueing/queryBasedOnTheIDOfTheVerificationResult',
    method: 'get',
    params: query
  })
}

/*根据核查结果id查询档案数据 案卷级*/
export function associateTheCurrentOperation(procedureId) {
  return request({
    url: '/recordTrueing/associateTheCurrentOperation/'+procedureId,
    method: 'post',
  })
}

/*查询档案未核查数量*/
export function theNumberOfUncheckedFilesInTheQueryFile(query) {
  return request({
    url: '/recordTrueing/theNumberOfUncheckedFilesInTheQueryFile',
    method: 'get',
    params: query
  })
}
/*根据核查结果id查询档案数据 案卷级*/
export function doubtQueryVerificationResultData(query) {
  return request({
    url: '/recordTrueing/doubtQueryVerificationResultData',
    method: 'get',
    params: query
  })
}

/*任务管理查询工序*/
export function taskManagementQueryOperations(query) {
  return request({
    url: '/recordTrueing/taskManagementQueryOperations',
    method: 'get',
    params: query
  })
}
/*首页核查结果查询*/
export function homepageCheckResultQuery(query) {
  return request({
    url: '/homePageExhibition/homepageCheckResultQuery',
    method: 'get',
    params: query
  })
}
export function homePageToBePickedUpTaskQuery(query) {
  return request({
    url: '/homePageExhibition/homePageToBePickedUpTaskQuery',
    method: 'get',
    params: query
  })
}

export function theTaskHasBeenClaimed(query) {
  return request({
    url: '/homePageExhibition/theTaskHasBeenClaimed',
    method: 'get',
    params: query
  })
}

/*任务管理查询工序*/
export function download(query) {
  return request({
    url: '/recordTrueing/download',
    responseType: 'blob', //设置后端返回类型为二进制流，不设置会返回乱码
    method: 'get',
    params: query
  })
}


// 保存核查结果
export function insertProcedureInspect(data) {
  return request({
    url: '/recordTrueing/insertProcedureInspect',
    method: 'post',
    data: data
  })
}
/*修改核查人*/
export function modifyTheVerifier(data) {
  return request({
    url: '/recordTrueing/modifyTheVerifier',
    method: 'post',
    data: data
  })
}

// 任务管理完成核查
export function taskManagementCompletesTheVerification(data) {
  return request({
    url: '/recordTrueing/taskManagementCompletesTheVerification',
    method: 'post',
    data: data
  })
}


export function getPucter(data) {
  return request({
    url: '/photoCheck/getPucter',
    method: 'post',
    data: data
  })
}

export function thePictureMovesUpAndDown(data) {
  return request({
    url: '/photoCheck/thePictureMovesUpAndDown',
    method: 'post',
    data: data
  })
}
export function imageReplacement(data) {
  return request({
    url: '/photoCheck/imageReplacement',
    method: 'post',
    data: data
  })
}

export function imagePlusPage(data) {
  return request({
    url: '/photoCheck/imagePlusPage',
    method: 'post',
    data: data
  })
}
export function imagePlusPageTwo(data) {
  return request({
    url: '/photoCheck/imagePlusPageTwo',
    method: 'post',
    data: data
  })
}

export function imageMinus(data) {
  return request({
    url: '/photoCheck/imageMinus',
    method: 'post',
    data: data
  })
}

export function modifyTheNumberOfPages(data) {
  return request({
    url: '/photoCheck/modifyTheNumberOfPages',
    method: 'post',
    data: data
  })
}


// 保存核查结果
export function submissionOfVerificationResultsInDoubt(data) {
  return request({
    url: '/recordTrueing/submissionOfVerificationResultsInDoubt',
    method: 'post',
    data: data
  })
}

// 保存核查结果
export function drawEntityFile(data) {
  return request({
    url: '/recordTrueing/drawEntityFile',
    method: 'post',
    data: data
  })
}

// 保存核查结果
export function collectYourFileByIds(data) {
  return request({
    url: '/recordTrueing/collectYourFileByIds',
    method: 'post',
    data: data
  })
}
