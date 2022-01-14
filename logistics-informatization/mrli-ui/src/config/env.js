// 配置编译环境和线上环境之间的切换

const env = process.env
const baseUrl = ''
// 图表库为avue和pig2套地址
const iconfontVersion = ['667895_v7uduh4zui', '1638883_qi08jij1ln','1435042_yncghlc6mgq','1435042_o6ku3vlo3q','567566_qo5lxgtishg']
const iconfontUrl = '//at.alicdn.com/t/font_$key.css'
const codeUrl = `${window.location.origin}/code`
const actUrl = `${window.location.origin}/act/modeler.html?modelId=`
if (env.NODE_ENV === 'development') {

} else if (env.NODE_ENV === 'production') {

} else if (env.NODE_ENV === 'test') {

}
export {
  baseUrl,
  actUrl,
  iconfontUrl,
  iconfontVersion,
  codeUrl,
  env
}
