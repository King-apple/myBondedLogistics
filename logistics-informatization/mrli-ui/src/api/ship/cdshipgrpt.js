/*
 *    Copyright (c) 2018-2025, mrli All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: mrli
 */

import request from '@/router/axios'
//查询预报船信息
export function fetchList(query) {
  return request({
    url: '/ship/cdshipgrpt/page',
    method: 'get',
    params: query
  })
}
//查询船舶交接单
export function shipCargoSum(shipNo) {
  return request({
    url: '/ship/shipcargobill/getSum?shipNo='+shipNo,
    method: 'get'
  })
}
//查询船舶交接单
export function shipCargoList(query) {
  return request({
    url: '/ship/shipcargobill/page',
    method: 'get',
    params: query
  })
}
//查询费目代码
export function feeList(query) {
  return request({
    url: '/ship/baseCod/feecod/page',
    method: 'get',
    params: query
  })
}
export function addObj(obj) {
  return request({
    url: '/ship/cdshipgrpt',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/ship/cdshipgrpt/' + id,
    method: 'get'
  })
}
//查询报价单信息
export function getAgentObj(id) {
  return request({
    url: '/ship/agentdoc/' + id,
    method: 'get'
  })
}
//保存报价单
export function commitFee(head,body) {
  return request({
    url: "/ship/shipprepaymnydsj",
    method: "post",
    data: {head:head,body:body}
  });
}
//查询报价单费目列表
export function getfeeList(id) {
  return request({
    url: '/ship/agentbillbody/list?billNo=' + id,
    method: 'get'
  })
}
//获取报价单列表
export function getpayList(id) {
  return request({
    url: '/ship/shipprepaymnydsj/list?shipNo=' + id,
    method: 'get'
  })
}
//作废报价单
export function delShipPay(id) {
  return request({
    url: '/ship/shipprepaymnydsj/' + id,
    method: 'delete'
  })
}
//上报报价单
export function commit(head) {
  return request({
    url: "/ship/shipprepaymnydsj/commit",
    method: "post",
    data: head
  });
}
//结算明细查询
export function getCFeeBillList(id) {
  return request({
    url: "/ship/tcargoshipfeebody/list?feeBillNo="+id,
    method: 'get'
  });
}
//上报结算
export function commitJs(shipNo) {
  return request({
    url: "/ship/shipprepaymnydsj/commitJs?shipNo="+shipNo,
    method: "post",
  });
}
//审核结算
export function commitJl(shipNoS) {
  return request({
    url: "/ship/shipprepaymnydsj/commitJl?shipNoS="+shipNoS,
    method: "post",
  });
}
//获取结算头信息
export function getTcargoBillHead(shipNo) {
  return request({
    url: "/ship/shipprepaymnydsj/cargohead?shipNo="+shipNo,
    method: "get",
  });
}
//外贸预付款自动计算
export function getWFeeBillList(shipNoS) {
  return request({
    url: "/ship/shipprepaymnydsj/getFeeBill?shipNoS="+shipNoS,
    method: "get",
  });
}
//外贸预付款自动计算
export function commitShip(shipNoS) {
  return request({
    url: "/ship/cdshipgrpt/commitShip?shipNoS="+shipNoS,
    method: "post",
  });
}
//处理船舶交接单
export function doShipCargoBill(shipNo) {
  return request({
    url: "/ship/shipcargobill/doShipCargoBill?shipNo="+shipNo,
    method: "post",
  });
}
//完结船舶交接单
export function commitShipCargoBill(shipNo) {
  return request({
    url: "/ship/shipcargobill/commitShipCargoBill?shipNo="+shipNo,
    method: "post",
  });
}
export function putObj(obj) {
  return request({
    url: '/ship/cdshipgrpt',
    method: 'put',
    data: obj
  })
}
