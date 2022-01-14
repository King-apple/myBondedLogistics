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
import iconList from '@/const/iconList'

export const tableOption = {
    tip: false,
    lazy: true,
    tree: true,
    border: true,
    delBtn:false,
    editBtn:false,
    addBtn:false,
    menu:false,
    header:false,
    "filterBtn": false,
     refreshBtn:false,
    "index": false,
    "stripe": true,
    "menuAlign": "center",
    "align": "center",
    "size": "mini",
    "selection": false,
    column: [
        {
            label: "名称",
            prop: "name",
            width: 180,
            rules: [
                {
                    required: true,
                    message: "请输入名称",
                    trigger: "blur"
                }
            ]
        },
        {
            label: "类型",
            prop: "type",
            type: "select",
            slot: true,
            border: true,
            valueFormat: "string",
            dicData: [
                {
                    label: "左菜单",
                    value: "0"
                },
                {
                    label: "顶菜单",
                    value: "2"
                },
                {
                    label: "按钮",
                    value: "1"
                },
            ],
            rules: [
                {
                    required: true,
                    message: "请选择菜单类型",
                    trigger: "blur"
                }
            ]
        },
    ]
}
