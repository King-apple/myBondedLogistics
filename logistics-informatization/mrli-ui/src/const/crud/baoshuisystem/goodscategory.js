export const tableOption = {
  border: true,
  index: true,
  indexLabel: "序号",
  stripe: true,
  menuAlign: "center",
  align: "center",
  editBtn: false, //修改按钮
  searchMenuSpan: 6,
  column: [
    {
      type: "input",
      label: "货类编码",
      prop: "cargoKindCod",
      span: 12,
      sortable: true
    },
    {
      type: "input",
      label: "货类名称",
      prop: "cargoKindNam",
      span: 12,
      search: true,
      sortable: true,
      rules: [
        {
          required: true,
          message: "货类名称必须填写"
        }
      ]
    },
    {
      type: "input",
      label: "备注",
      prop: "markTxt",
      span: 12,
      sortable: true
    },
    {
      type: "input",
      label: "添加人",
      prop: "operNam",
      display: false,
      span: 12,
      sortable: true
    },
    {
      type: "input",
      label: "添加时间",
      prop: "recordTim",
      span: 12,
      display: false,
      sortable: true
    }
  ]
};
