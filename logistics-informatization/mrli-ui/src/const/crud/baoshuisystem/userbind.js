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
export const tableOption = {
  border: true,
  index: true,
  indexLabel: "序号",
  stripe: true,
  menuAlign: "center",
  searchMenuSpan: 6,
  editBtn: false,
  delBtn: false,
  align: "center",
  addBtn: false,
  column: [
    {
      fixed: true,
      label: "id",
      prop: "userId",
      span: 24,
      hide: true,
      editDisabled: true,
      addDisplay: false
    },
    {
      fixed: true,
      label: "用户名",
      prop: "username",
      editDisabled: true,
      slot: true,
      search: true,
      span: 24,
      rules: [
        {
          required: true,
          message: "请输入用户名"
        },
        {
          min: 3,
          max: 20,
          message: "长度在 3 到 20 个字符",
          trigger: "blur"
        }
        // {validator: validateUsername, trigger: 'blur'}
      ]
    },
    {
      label: "用户姓名",
      prop: "manName",
      span: 24,
      editDisabled: true,
      search: true,

      rules: [
        {
          required: true,
          message: "请输入用户名"
        }
      ]
    },
    {
      label: "密码",
      prop: "password",
      type: "password",
      value: "",
      hide: true,
      span: 24,
      rules: [
        {
          min: 6,
          max: 20,
          message: "长度在 6 到 20 个字符",
          trigger: "blur"
        }
      ]
    },
    {
      label: "所属公司",
      prop: "deptId",
      formslot: true,
      slot: true,
      span: 24,
      hide: true,
      dataType: "number",
      rules: [
        {
          required: true,
          message: "请选择公司",
          trigger: "change"
        }
      ]
    },
    {
      label: "所属部门",
      prop: "deptSubId",
      formslot: true,
      slot: true,
      span: 24,
      hide: true
    },
    {
      label: "手机号",
      prop: "phone",
      type: "tel",
      value: "",
      span: 24,
      rules: [
        {
          min: 11,
          max: 11,
          message: "长度在 11 个字符",
          trigger: "blur"
        }
      ]
    },
    {
      label: "角色",
      prop: "role",
      formslot: true,
      slot: true,
      overHidden: true,
      span: 24,
      rules: [
        {
          required: true,
          message: "请选择角色",
          trigger: "blur"
        }
      ]
    },
    {
      label: "状态",
      prop: "lockFlag",
      type: "radio",
      slot: true,
      border: true,
      span: 24,
      dicData: [
        {
          label: "有效",
          value: "0"
        },
        {
          label: "锁定",
          value: "9"
        }
      ]
    },
    {
      label: "绑定状态",
      prop: "userFlag",
      type: "radio",
      slot: true,
      border: true,
      span: 24,
      dicData: [
        {
          label: "已绑定",
          value: "1"
        },
        {
          label: "未绑定",
          value: "0"
        }
      ]
    },
    {
      width: 180,
      label: "创建时间",
      prop: "createTime",
      type: "datetime",
      format: "yyyy-MM-dd HH:mm",
      valueFormat: "yyyy-MM-dd HH:mm:ss",
      editDisabled: true,
      addDisplay: false,
      span: 24
    }
  ]
};
