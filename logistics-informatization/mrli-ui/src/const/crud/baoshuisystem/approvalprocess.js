export const tableOption = {
  border: true,
  index: true,
  indexLabel: "序号",
  stripe: true,
  menuAlign: "center",
  selection: true,
  align: "center",
  searchMenuSpan: 6,
  column: [
    {
      type: "select",
      label: "审批流程",
      prop: "processNo",
      span: 28,
      search: true,
      cascaderItem: [],
      display: true,
      sortable: true,
      props: {
        label: "label",
        value: "value"
      },
      dicUrl: "/admin/dict/type/process_no",
      dicMethod: "get",
      rules: [
        {
          required: true,
          message: "请选择审批流程编号"
        }
      ],
      filterable: true
    },
    {
      type: "input",
      label: "审批序号",
      prop: "orderBy",
      span: 28,
      sortable: true,
      rules: [
        {
          required: true,
          message: "审批序号必须填写"
        }
      ]
    },
    {
      type: "input",
      label: "部门名称",
      prop: "linkNam",
      span: 28,
      rules: [
        {
          required: true,
          message: "部门名称必须填写"
        }
      ]
    },
    {
      type: "select",
      label: "角色名称",
      sortable: true,
      prop: "linkCode",
      span: 28,
      cascaderItem: [],
      display: true,
      props: {
        label: "roleName",
        value: "roleCode"
      },
      required: true,
      rules: [
        {
          required: true,
          message: "请选择角色名称"
        }
      ],
      filterable: true,
      clearable: true,
      dicUrl: "/baoshuisystem/bsuser/dict"
    },
    {
      type: "input",
      label: "备注",
      sortable: true,
      prop: "markTxt",
      span: 28
    },
    {
      type: "input",
      label: "添加人",
      sortable: true,
      prop: "operNam",
      span: 28,
      display: false
    },
    {
      type: "input",
      label: "添加时间",
      sortable: true,
      prop: "recordTim",
      span: 28,
      display: false
    }
  ]
};
