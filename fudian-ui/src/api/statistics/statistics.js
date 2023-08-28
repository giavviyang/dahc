import request from '@/utils/request'

// 核查项目统计
export function verificationOfProjectStatistics(dataUnderVerification) {
  return request({
    url: '/statistics/verificationOfProjectStatistics/'+dataUnderVerification,
    method: 'get',
  })
}
export function verificationItemsAndPeopleCounting(dataUnderVerification) {
  return request({
    url: '/statistics/verificationItemsAndPeopleCounting/'+dataUnderVerification,
    method: 'get',
  })
}
export function checkTheStatisticsOfTheResultsOfTheProject(dataUnderVerification) {
  return request({
    url: '/statistics/checkTheStatisticsOfTheResultsOfTheProject/'+dataUnderVerification,
    method: 'get',
  })
}

