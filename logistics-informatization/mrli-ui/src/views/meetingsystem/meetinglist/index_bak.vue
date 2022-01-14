<template>
	<div class="app-container">
		<el-col :span="20" :xs="24">
			<el-form :model="queryParams" ref="queryForm" :inline="true" v-show="true" label-width="68px">
				<el-form-item label="ID号" prop="id">
					<el-input v-model="dcsj.fid" placeholder="请输入ID号" clearable size="small" style="width: 150px" @keyup.enter.native="handleQuery" />
				</el-form-item>
				
				<el-form-item label="船名" prop="id">
					<el-input v-model="dcsj.cm" placeholder="请输入船名" clearable size="small" style="width: 150px" @keyup.enter.native="handleQuery" />
				</el-form-item>

				<el-form-item>
					<el-button icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
					<el-button icon="el-icon-refresh" size="mini" @click="handleQuery">刷新</el-button>
					<el-button icon="el-icon-delete" type="danger" size="mini" @click="deleteQuery">删除</el-button>
				</el-form-item>
			</el-form>
		</el-col>
		<el-table ref="singleTable" :data="stackList" @row-click="clickData" :row-height=10 :row-style="tableRowStyle" :header-cell-style="{background:'#eef1f6',color:'#606266'}" highlight-current-row @current-change="handleCurrentChange" style="width: 100%" height="300">
			<el-table-column type="index" label="序号" width="50">
			</el-table-column>
			<el-table-column property="fid" :row-style="{height: '80px'}" sortable label="F_ID">
			</el-table-column>
			<el-table-column property="cbxh" sortable label="船舶序号">
			</el-table-column>
			<el-table-column property="cm" sortable label="船名">
			</el-table-column>

			<el-table-column property="hz" sortable label="货名">
			</el-table-column>
			<el-table-column property="hd" sortable label="货代">
			</el-table-column>
			<el-table-column property="hzhu" sortable label="货主">
			</el-table-column>
			<el-table-column property="cq"  :formatter="getAreaName" sortable label="厂区">
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	import { fetchList } from '@/api/meetingsystem/meetingsystem.js'

	export default {
		data() {
			return {
				queryParams: {},
				currentRow: null,
				currentRow2: null,
				backgroundColor: '#FFD700',
				dialogFormVisible: false,
				dialogFormVisible2: false,
				dialogTableVisibleMass: false,
				formLabelWidth: '120px',
				stackTitle: "新增垛位信息",
				stackList: [],
				stackList2: [],
				rowDetail: [],
				rowDetail2: [],
				rowDetail3: [],
				dcsj2: {},
				dcsj3: {},
				masstitle: "已派剁列表",
				dcsj: {
					fid: null
				},
				form: {
					index: 1
				},
				form2: {},
				gridData: {},
				total: 0,
				tableData: [],
				tableData2: {
					vesselNoticeNum: '',
				},
				updFlg: false
			}
		},
		mounth() {

		},
		created() {
			this.getList();
		},
		methods: {
			getList() {
				console.log(fetchList)
				console.log("dddddddddddddddddddd")
				this.rowDetail = []
				this.rowDetail2 = []
				this.rowDetail3 = []
				this.loading = true;
				fetchList().then(response => {
					this.stackList = response.rows;
					this.total = response.total;
					this.loading = false;
				})
			},
			setCurrent(row) {
				this.$refs.singleTable.setCurrentRow(row);
			},
			handleCurrentChange(val) {
				console.log(val)
				this.currentRow = val;
			},
			handleCurrentChange2(val) {
				this.currentRow2 = val;
			},
			clickData(item) {
				this.rowDetail3 = []
				//				this.stackList2 = item
				this.tableData2.vesselNoticeNum = item.vesselNoticeNum
				this.rowDetail2 = item
				massList(this.tableData2).then((res) => {
					console.log(res)
					this.stackList2 = res.rows
				})

			},
			clickData2(item) {
				this.rowDetail3 = item
			},
			tableRowStyle({
				row
			}) {
				if(row === this.currentRow) {
					return {
						backgroundColor: this.backgroundColor
					};
				}

				return '';
			},
			tableRowStyle2({
				row
			}) {
				if(row === this.currentRow2) {
					return {
						backgroundColor: this.backgroundColor
					};
				}
				return '';
			},
			handleQuery() { //搜索id
				this.getList();

			},
			dialogForm(val) {
				if('1' === val) {
					this.updFlg = false
					this.stackTitle = "新增垛位信息"
					if(this.rowDetail2 == false) {
						alert("请选中堆场数据信息！")
						return;
					}
					this.dialogFormVisible = true;
//					console.log(this.rowDetail2)
					this.form.massNum = this.rowDetail2['cq']+this.rowDetail2['q']+"-"+this.rowDetail2['z']
				} else {
					this.updFlg = true

					if(this.rowDetail3 == false) {
						alert("请先选择您要修改的垛位信息！");
						return;
					}
					this.$confirm('是否修改选中的信息, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
							this.$message({
							type: 'success',
							message: '修改成功!'
						});
					this.form.currentStatus = 1
					this.form.massNum = this.rowDetail3.massNum //更新条件
					massUpdate(this.form).then((res) => {
					
					})
						
						

					}).catch(() => {
						this.$message({
							type: 'info',
							message: '修改取消'
						});
					});

				}

				this.form.fid = this.rowDetail2['fid']
				this.form.cm = this.rowDetail2['cm']

			},

			dialogForm2() {
				//				this.form2=
				if(this.rowDetail3 == false) {
					alert("请先选择您要修改的垛位信息！");
					return;
				}
				this.form2.massNum = this.rowDetail3.massNum
				this.dialogFormVisible2 = true;

			},
			dialogFormMass(val) {
				if(val === '1') {
					this.masstitle = "已派剁列表"
					this.dialogTableVisibleMass = true
					let param = {}
					getSendMass(param).then((res) => {
						this.gridData = res.rows
					})
				} else {
					this.masstitle = "已跑剁列表"
					this.dialogTableVisibleMass = true
					let param = {}
					getRunOverMass(param).then((res) => {
						this.gridData = res.rows
					})
				}

			},
			submit() {
				if(!this.updFlg) { //新增 
					this.dialogFormVisible = false
					this.form.currentStatus = 1
					this.form.companyId = 12
					this.form.vesselNoticeNum = this.form.fid
					this.form.seqNum = 1
					this.form.colorShowType = 1
					this.form.dispatchName = 'admin'
					this.form.vesselName = this.rowDetail2.cm
					this.form.cargoName = this.rowDetail2.hz
					this.form.ownerName = this.rowDetail2.hzhu
					massInster(this.form).then((res) => {
						if(res.code === 200) {
							this.tableData2.vesselNoticeNum = this.form.vesselNoticeNum
							massList(this.tableData2).then((res) => {
								this.stackList2 = res.rows
							})
							alert("成功插入！")
						}
					})
				} else { //更新
					
				}

			},

			submit2() {

				if(this.form2.newMassNum == null || this.form2.newMassNum == "") {
					alert("填写项不能为空！");
				}
				massUpdateForNum(this.form2).then((res) => {
					if(res.code === 200) {
						//							this.tableData2.vesselNoticeNum = this.form.vesselNoticeNum
						massList(this.tableData2).then((res) => {
							this.stackList2 = res.rows
						})
						this.dialogFormVisible2 = false;
						alert("更新成功！")
					}
				})
			},
			deleteQuery() {
				let that = this;
				if(that.rowDetail2.length <= 0) {
					alert("请选择要删除的数据！");
					return;
				}
				that.loading = true;
				that.$confirm('是否确认删除当前记录?', "警告", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}).then(function() {
					that.dcsj2 = {};
					that.dcsj2 = {
						fid: that.rowDetail2['fid'],
//						cbxh: that.rowDetail2['cbxh'],
//						cm: that.rowDetail2['cm'],
//						hz: that.rowDetail2['hz'],
//						hd: that.rowDetail2['hd'],
						sflag: 1
					}

					delDcsj(that.dcsj2)
					that.getList();
				})

			},
			deleteQuery2() {
				let that = this;
				if(that.rowDetail3 == false) {
					alert("请选择要删除的数据！");
					return;
				}	
				that.loading = true;
				that.$confirm('是否确认删除当前记录?', "警告", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}).then(function() {
					that.dcsj3 = {};
					that.dcsj3 = {
						massNum: that.rowDetail3['massNum'],
					}

					deleteQuery2(that.dcsj3).then((res)=>{
						alert("删除成功！")
					})
					massList(that.tableData2).then((res) => {
						this.stackList2 = res.rows
						
					})
					//					that.getList();
				})

				},
				exportList() {
						let param = {}
						if(this.masstitle == "已派剁列表") {
							this.$confirm('是否确认导出所有数据项?', "警告", {
								confirmButtonText: "确定",
								cancelButtonText: "取消",
								type: "warning"
							}).then(function() {
								return getSendMassExcel(param);
							}).then(response => {
								this.download(response.msg);
							})
						}
						else {
							this.$confirm('是否确认导出所有数据项?', "警告", {
								confirmButtonText: "确定",
								cancelButtonText: "取消",
								type: "warning"
							}).then(function() {
								return getRunOverMassExcel(param);
							}).then(response => {
								this.download(response.msg);
							})

						}
					},
					getAreaName(item) {
						return item.cq + item.q +'-' + item.z
		},
		getCurrentStatus(item) {
			if(item.currentStatus == '1') {
				return "计划"
			} else {
				return "完成"
			}

		}

	}
	}
</script>

<style>
	.el-table .current-row>td {
		background: transparent !important;
	}
</style>