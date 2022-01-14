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

import request from "@/router/axios";

export async function getContractType() {
  return await request({
    url: "/admin/dict/type/contract_type",
    method: "get"
  });
}

export async function getSettlementBasisDic() {
  return await request({
    url: "/admin/dict/type/settlement_basis_dic",
    method: "get"
  });
}

export async function getClearingFormDic() {
  return await request({
    url: "/admin/dict/type/clearing_form_dic",
    method: "get"
  });
}

export function getCheckStatusDic() {
  return request({
    url: "/admin/dict/type/check_status_dic",
    method: "get"
  });
}

export async function getContractCheckProcess() {
  return await request({
    url: "/admin/dict/type/contract_check_process",
    method: "get"
  });
}
//进出口
export function getInoutIdDic() {
  return request({
    url: "/admin/dict/type/import_export",
    method: "get"
  });
}
//内外贸
export function getTradeIdDic() {
  return request({
    url: "/admin/dict/type/trade_type",
    method: "get"
  });
}

export function getContractFileDic() {
  return request({
    url: "/admin/dict/type/contract_file",
    method: "get"
  });
}

export function getPkgKindNamDic() {
  return request({
    url: "/admin/dict/type/pkg_kind_nam",
    method: "get"
  });
}

export function getSettlementMethodDic() {
  return request({
    url: "/admin/dict/type/settlement_method",
    method: "get"
  });
}

export function getCheckWeightWorkTypeDic() {
  return request({
    url: "/admin/dict/type/checkweight_work_type",
    method: "get"
  });
}

export function getCheckweightLoadDic() {
  return request({
    url: "/admin/dict/type/checkweight_load",
    method: "get"
  });
}

export function getCheckweightStorageLocDic() {
  return request({
    url: "/admin/dict/type/checkweight_storage_loc",
    method: "get"
  });
}

export function getCFeeWayAllDic() {
  return request({
    url: "/baoshuisystem/getCheckWeightDic/getCFeeWayAll",
    method: "get"
  });
}

export function getCDatacodesAllDic() {
  return request({
    url: "/baoshuisystem/getCheckWeightDic/getCDatacodesAll",
    method: "get"
  });
}

export function getCSubunitAllDic() {
  return request({
    url: "/baoshuisystem/getCheckWeightDic/getCSubunitAll",
    method: "get"
  });
}

export function getCargoPage(query) {
  return request({
    url: "/baoshuisystem/contractobject/getCargo",
    method: "get",
    params: query
  });
}

export function getStatement() {
  return request({
    url: "/admin/dict/type/statement_type",
    method: "get"
  });
}

export function getInyardType() {
  return request({
    url: "/admin/dict/type/inyard_type",
    method: "get"
  });
}

export function getCunitNam() {
  return request({
    url: "/baoshuisystem/mstorestatus/getCunitNam",
    method: "get"
  });
}

export function getCargoNam() {
  return request({
    url: "/baoshuisystem/mstorestatus/getCargoNam",
    method: "get"
  });
}

export function getSubunit(uniCode) {
  return request({
    url: "/baoshuisystem/mstorestatus/getSubunit/" + uniCode,
    method: "get"
  });
}
//获取标的额单位
export function getCargoFeeUnit() {
  return request({
    url: "/admin/dict/type/cargo_Fee_Unit",
    method: "get"
  });
}

export function getDepositPlace() {
  return request({
    url: "/admin/dict/type/deposit_place",
    method: "get"
  });
}

export function getShip(obj) {
  return request({
    url: "/baoshuisystem/mstorestatus/getShip",
    method: "get",
    params: obj
  });
}

export function getShipperDoc() {
  return request({
    url: "/baoshuisystem/mstorestatus/getShipperDoc",
    method: "get"
  });
}

export function getPortCbz() {
  return request({
    url: "/admin/dict/type/port_cbz",
    method: "get"
  });
}

export function getportRqcbs() {
  return request({
    url: "/admin/dict/type/port_rqcbs",
    method: "get"
  });
}

export function getOverShortFlag() {
  return request({
    url: "/admin/dict/type/over_short_flag",
    method: "get"
  });
}

export function getInportWayAll() {
  return request({
    url: "/baoshuisystem/getCheckWeightDic/getInportWayAll",
    method: "get"
  });
}

export function getInoutType() {
  return request({
    url: "/admin/dict/type/inout_type",
    method: "get"
  });
}

export function getOutYardType() {
  return request({
    url: "/admin/dict/type/outyard_type",
    method: "get"
  });
}

export function getInoutFlag() {
  return request({
    url: "/admin/dict/type/inout_flag",
    method: "get"
  });
}

export function getLoadingShip() {
  return request({
    url: "/admin/dict/type/loading_ship",
    method: "get"
  });
}

export function getWeightUnit() {
  return request({
    url: "/admin/dict/type/weight_unit",
    method: "get"
  });
}

export function getTaxRate() {
  return request({
    url: "/admin/dict/type/tax_rate",
    method: "get"
  });
}

export function getOverQuantity() {
  return request({
    url: "/admin/dict/type/over_quantity",
    method: "get"
  });
}

export function getBusinessOptions() {
  return request({
    url: "/admin/dict/type/business_options",
    method: "get"
  });
}
export function getCompleteApprove() {
  return request({
    url: "/admin/dict/type/complete_approve_flag",
    method: "get"
  });
}
