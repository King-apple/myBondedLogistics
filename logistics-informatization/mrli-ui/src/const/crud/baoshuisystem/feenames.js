export const tableOption = {
  border: true,
  index: true,
  indexLabel: "序号",
  stripe: true,
  menuAlign: "center",
  align: "center",
  searchMenuSpan: 6,
  column: [
    {
      type: "input",
      label: "费目编码",
      prop: "feeNameCod",
      search: true,
      span: 12,
      display: false
    },
    {
      type: "input",
      label: "费目名称",
      prop: "feeNam",
      sortable: true,
      search: true,
      span: 12,
      rules: [
        {
          required: true,
          message: "费目名称必须填写"
        }
      ]
    },
    {
      type: "input",
      label: "备注",
      prop: "markTxt",
      span: 12
    },
    {
      type: "input",
      label: "添加人",
      prop: "operNam",
      sortable: true,
      span: 12,
      display: false
    },
    {
      type: "input",
      label: "添加时间",
      prop: "recordTim",
      sortable: true,
      span: 12,
      display: false
    }
  ]
};
