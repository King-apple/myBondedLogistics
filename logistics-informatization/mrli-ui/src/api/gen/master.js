import request from '@/router/axios'

export function selectHeadGet(query) {
  return request({
    url: '/gen/masterDataDatasource/page',
    method: 'get',
    params: query
  })
}
export function selectBaseGet(query, dataSourceName) {
  return request({
    url: '/gen/mssasterDataBase/page?dataSourceName=' + dataSourceName,
    method: 'get',
    params: query
  })
}
export function selectDataSourceGet(query) {
  return request({
    url: '/gen/dsconf/page',
    method: 'get',
    params: query
  })
}
export function selectColumsSourceGet(tableName, dsname) {
  return request({
    url: '/gen/generator/columsLov?tableName=' + tableName + '&dsName=' + dsname,
    method: 'get'
  })
}
export function saveDataSourcePost(dataSourceName) {
  return request({
    url: '/gen/masterDataDatasource/save?dataSourceName=' + dataSourceName,
    method: 'post',
  })
}
export function saveDataBasePost(obj,dsname) {
  return request({
    url: '/gen/mssasterDataBase/save?dsName='+dsname,
    method: 'post',
    data:obj
  })
}