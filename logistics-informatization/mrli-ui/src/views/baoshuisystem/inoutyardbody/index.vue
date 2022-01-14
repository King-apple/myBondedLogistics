<template>
	<div class="mod-config">
		<basic-container>
			<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
				<el-form-item label="进区核注清单号" v-show="this.flg == 1" label-width="110px" prop="inCheckNum">
					<el-input v-model="dataForm.inCheckNum" placeholder="进区核注清单号" clearable></el-input>
				</el-form-item>
				<el-form-item label="出区核注清单号" v-show="this.flg == 2" label-width="140px" prop="outCheckNum">
					<el-input v-model="dataForm.outCheckNum" placeholder="出区核注清单号" clearable></el-input>
				</el-form-item>
				<el-form-item label="报关单号维护状态" label-width="150px" prop="inCustomsDeclarationStatus">
					<el-select v-model="dataForm.inCustomsDeclarationStatus" placeholder="报关单号维护状态:" filterable style="width: 10vw">
						<el-option v-for="item in inCustomsDeclarationStatusList" :key="item.fId" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="getDataList">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()" v-if="permissions.inoutyardbody_bin_save || permissions.inoutyardbody_bout_save" v-show="viewFlg">新增</el-button>
					<el-button icon="el-icon-plus" type="danger" @click="back()">返回</el-button>
				</el-form-item>
			</el-form>

			<div v-show="this.flg == 1" class="avue-crud">
				<el-table :data="dataList" border v-loading="dataListLoading">
					<el-table-column type="index" header-align="center" align="center" label="序号">
					</el-table-column>
					<el-table-column prop="cargoNo" header-align="center" align="center" label="H.S" sortable>
					</el-table-column>
					<el-table-column prop="cargoNam" header-align="center" align="center" label="品名" sortable>
					</el-table-column>
					<el-table-column prop="format" header-align="center" align="center" label="规格">
					</el-table-column>
					<el-table-column prop="inCustomsDeclaration" header-align="center" align="center" width="115" label="报关单号">
					</el-table-column>
					<el-table-column prop="inCheckNum" header-align="center" align="center" width="115" label="核注清单号">
					</el-table-column>
					<!--<el-table-column
            prop="locate"
            header-align="center"
            align="center"
            width="115"
            label="存放区域"
          >
          </el-table-column>-->

					<el-table-column prop="pieceNum" header-align="center" align="center" label="件数" sortable>
					</el-table-column>

					<el-table-column prop="roughWet" header-align="center" align="center" :label="roughWetValue" width="115" sortable>
					</el-table-column>
					<el-table-column prop="netWet" header-align="center" align="center" :label="netWetValue" width="115" sortable>
					</el-table-column>
					<el-table-column prop="unitPrice" header-align="center" align="center" :label="unitPriceValue" width="115" sortable>
					</el-table-column>
					<el-table-column prop="totalPrice" header-align="center" align="center" :label="totalPriceValue" width="115" sortable>
					</el-table-column>
					<el-table-column prop="transportMeans" header-align="center" align="center" label="运输工具">
					</el-table-column>
					<el-table-column prop="importCountry" header-align="center" align="center" label="进口国别">
					</el-table-column>
					<el-table-column prop="deadLoad" header-align="center" align="center" label="自重" sortable>
					</el-table-column>

					<el-table-column prop="markTxt" header-align="center" align="center" label="备注">
					</el-table-column>
					<el-table-column header-align="center" align="center" label="操作" fixed="right" width="155">
						<template slot-scope="scope">
							<el-button type="text" size="small" icon="el-icon-edit" v-if="permissions.inoutyardbody_bin_edit" @click="addOrUpdateHandle(scope.row.fId)" v-show="viewFlg">修改</el-button>
							<el-button type="text" size="small" icon="el-icon-delete" v-if="permissions.inoutyardbody_bin_del" @click="deleteHandle(scope.row.fId, scope.row)" v-show="viewFlg">删除</el-button>
							<el-button type="text" size="small" icon="el-icon-delete" v-if="permissions.checkWeight_work" @click="addOrUpdateCheckHandle(scope.row.fId)">衡重申请单操作</el-button>
							<el-button type="text" size="small" icon="el-icon-edit" v-if="permissions.inoutyardbody_binhz_edit" @click="updateViewFirst(scope.row)">核注清单号维护</el-button>
							<!--v-if="permissions.checkWeight_work"-->
							<el-button type="text" size="small" icon="el-icon-edit" v-if="permissions.inoutyardbody_binbg_edit" @click="updateViewBaoguan(scope.row)">报关单号维护</el-button>
							<el-button type="text" size="small" icon="el-icon-edit" v-if="permissions.inoutyardbody_bin_copy" @click="updateCopy(scope.row.fId)">复制</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>

			<el-dialog title="进区核注清单号维护" :visible.sync="dialogVisibleFirst" width="30%">
				<el-form :model="form">
					<el-form-item label="进区核注清单号" :label-width="formLabelWidth">
						<el-input v-model="form.inCheckNum" autocomplete="off" @input="change($event)"></el-input>
					</el-form-item>
				</el-form>
				<el-button @click="dialogVisibleFirst = false">取 消</el-button>
				<el-button type="primary" @click="updateInCheckNum('核注')">确 定</el-button>
			</el-dialog>

			<div v-show="this.flg == 2" class="avue-crud">
				<el-table :data="dataList" border v-loading="dataListLoading">
					<el-table-column type="index" header-align="center" align="center" label="序号">
					</el-table-column>
					<el-table-column prop="cargoNo" header-align="center" align="center" label="H.S" sortable>
					</el-table-column>
					<el-table-column prop="cargoNam" header-align="center" align="center" label="品名" sortable>
					</el-table-column>
					<el-table-column prop="format" header-align="center" align="center" label="规格">
					</el-table-column>
					<el-table-column prop="inCustomsDeclaration" header-align="center" align="center" width="125" label="原进区报关单号">
					</el-table-column>
					<el-table-column prop="outCustomsDeclaration" header-align="center" align="center" width="125" label="出区报关单号">
					</el-table-column>
					<el-table-column prop="inCheckNum" header-align="center" align="center" width="125" label="原进区核注清单号">
					</el-table-column>
					<el-table-column prop="outCheckNum" header-align="center" align="center" width="125" label="出区核注清单号">
					</el-table-column>
					<!--<el-table-column
            prop="locate"
            header-align="center"
            align="center"
            width="115"
            label="存放区域"
          >
          </el-table-column>-->

					<el-table-column prop="bucketNum" header-align="center" align="center" label="桶数" sortable>
					</el-table-column>
					<el-table-column prop="pieceNum" header-align="center" align="center" label="件数" sortable>
					</el-table-column>
					<el-table-column prop="roughWet" header-align="center" align="center" :label="roughWetValue" width="115" sortable>
					</el-table-column>
					<el-table-column prop="netWet" header-align="center" align="center" :label="netWetValue" width="100" sortable>
					</el-table-column>
					<el-table-column prop="unitPrice" header-align="center" align="center" :label="unitPriceValue" width="100" sortable>
					</el-table-column>
					<el-table-column prop="totalPrice" header-align="center" align="center" :label="totalPriceValue" width="110" sortable>
					</el-table-column>
					<el-table-column prop="importCountry" header-align="center" align="center" label="进口国别">
					</el-table-column>
					<el-table-column prop="transportMeans" header-align="center" align="center" label="运输工具">
					</el-table-column>
					<el-table-column prop="distinationCountry" header-align="center" align="center" label="目的国">
					</el-table-column>
					<el-table-column prop="deadLoad" header-align="center" align="center" label="自重" sortable>
					</el-table-column>

					<el-table-column prop="markTxt" header-align="center" align="center" label="备注">
					</el-table-column>
					<el-table-column header-align="center" align="center" label="操作" fixed="right" width="155">
						<template slot-scope="scope">
							<el-button type="text" size="small" icon="el-icon-edit" v-if="permissions.inoutyardbody_bout_edit" @click="addOrUpdateHandle(scope.row.fId)" v-show="viewFlg">修改</el-button>
							<el-button type="text" size="small" icon="el-icon-delete" v-if="permissions.inoutyardbody_bout_del" @click="deleteHandle(scope.row.fId, scope.row)" v-show="viewFlg">删除</el-button>
							<el-button type="text" size="small" icon="el-icon-delete" v-if="permissions.checkWeight_work" @click="addOrUpdateCheckHandle(scope.row.fId)">衡重申请单操作</el-button>
							<el-button type="text" size="small" icon="el-icon-edit" v-if="permissions.inoutyardbody_bouthz_edit" @click="updateViewSecond(scope.row)">核注清单号维护</el-button>
							<!--v-show="scope.row.operCheckStatus=='0' || scope.row.operCheckStatus=='2'"-->
							<!-- v-if="permissions.inoutyardhead_in"-->
							<el-button type="text" size="small" icon="el-icon-edit" v-if="permissions.inoutyardbody_boutbg_edit" @click="updateViewBaoguan(scope.row)">报关单号维护</el-button>
							<el-button type="text" size="small" icon="el-icon-edit" v-if="permissions.inoutyardbody_bout_copy" @click="updateCopy(scope.row.fId)">复制</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>

			<el-dialog title="核注清单号维护" :visible.sync="dialogVisibleSecond" width="30%">
				<el-form :model="form">
					<el-form-item label="出区核注清单号" :label-width="formLabelWidth">
						<el-input v-model="form.outCheckNum" autocomplete="off" @input="change($event)"></el-input>
					</el-form-item>
					<el-form-item label="原进区核注清单号" :label-width="formLabelWidth">
						<el-input v-model="form.inCheckNum" autocomplete="off" @input="change($event)"></el-input>
					</el-form-item>
				</el-form>
				<el-button @click="dialogVisibleSecond = false">取 消</el-button>
				<el-button type="primary" @click="updateInCheckNum('核注')">确 定</el-button>
			</el-dialog>

			<el-dialog title="报关单号维护" :visible.sync="dialogVisibleBaoguan" width="30%">
				<el-form :model="form">
					<el-form-item label="进区报关单号" :label-width="formLabelWidth">
						<el-input v-model="form.inCustomsDeclaration" autocomplete="off" @input="change($event)"></el-input>
					</el-form-item>
					<el-form-item v-show="this.flg == 2" label="出区报关单号" :label-width="formLabelWidth">
						<el-input v-model="form.outCustomsDeclaration" autocomplete="off" @input="change($event)"></el-input>
					</el-form-item>
				</el-form>
				<el-button @click="dialogVisibleBaoguan = false">取 消</el-button>
				<el-button type="primary" @click="updateInCheckNum2('报关')">确 定</el-button>
			</el-dialog>

			<div class="avue-crud__pagination">
				<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" background layout="total, sizes, prev, pager, next, jumper">
				</el-pagination>
			</div>
			<!-- 弹窗, 新增 / 修改 -->
			<table-form v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></table-form>

			<!-- 弹窗, 新增 / 修改 -->
			<check-weight v-if="addOrUpdateCheckVisible" ref="addOrUpdateCheck" @refreshDataList="getDataList"></check-weight>
		</basic-container>
	</div>
</template>

<script>
	import { fetchList, delObj, putObj, updateByForCheckNum, updateByForCustomsDeclaration, updateCopy } from '@/api/baoshuisystem/inoutyardbody'
	import { getContractObjectInfoAll } from '@/api/baoshuisystem/contractobject.js'
	import TableForm from './inoutyardbody-form'
	import { getCountryInfoAll as countryList } from '@/api/baoshuisystem/countryinfo'
	import { mapGetters } from 'vuex'
	import { getObj } from '@/api/baoshuisystem/inoutyardhead'
	import CheckWeight from './checkweight-form'
	export default {
		data() {
			return {
				dataForm: {
					key: '',
					hFid: null,
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				addOrUpdateVisible: false,
				addOrUpdateCheckVisible: false,
				flg: 0,
				arr: [],
				roughWetValue: '毛重',
				netWetValue: '净重', //净重
				unitPriceValue: '单价', //单价
				totalPriceValue: '总价', //总价
				businessCheckStatus: 0,
				viewFlg: false,
				dialogVisibleFirst: false,
				dialogVisibleSecond: false,
				dialogVisibleBaoguan: false,
				form: {},
				formLabelWidth: '110px',
				inCustomsDeclarationStatusList: [{
					label: '已维护完成',
					value: 'success'
				}, {
					label: '未维护',
					value: 'fail'
				}]
			}
		},
		components: {
			CheckWeight,
			TableForm

		},
		created() {
			console.log(this.$route.query)
			this.flg = this.$route.query.flg
			this.businessCheckStatus = this.$route.query.businessCheckStatus
			if(this.businessCheckStatus == "0" || this.businessCheckStatus == "2") {
				this.viewFlg = true;
			}
			if(this.$route.query.id) {
				this.dataForm.hFid = this.$route.query.id
			}
			countryList().then((res) => {
				console.log(res)
				this.arr = res.data.data

			})
			this.getDataList()

		},
		computed: {
			...mapGetters(['permissions'])
		},
		methods: {
			// 获取数据列表
			getDataList() {
				console.log(this.dataForm)
				this.dataListLoading = true
				fetchList(Object.assign(this.dataForm, {
					current: this.pageIndex,
					size: this.pageSize
				})).then(response => {
					this.dataList = response.data.data.records
					this.totalPage = response.data.data.total
				})

				getContractObjectInfoAll().then((res) => {
					console.log(res)
				})

				getObj(this.$route.query.id).then((res) => {
					console.log(res)
					console.log(res.data.data.netWetUnit)
					this.roughWetValue = '毛重(' + res.data.data.netWetUnit + ')'; //毛重
					this.netWetValue = '净重(' + res.data.data.netWetUnit + ')'; //净重
					this.unitPriceValue = '单价(' + res.data.data.unitPriceKnd + ')'; //单价
					this.totalPriceValue = '总价(' + res.data.data.unit + ')'; //总价
				})

				this.dataListLoading = false
			},
			// 每页数
			sizeChangeHandle(val) {
				this.pageSize = val
				this.pageIndex = 1
				this.getDataList()
			},
			// 当前页
			currentChangeHandle(val) {
				this.pageIndex = val
				this.getDataList()
			},
			// 新增 / 修改
			addOrUpdateHandle(id) {
				this.addOrUpdateVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(
						id,
						this.$route.query.id,
						this.$route.query.flg,
						this.$route.query.contNo,
						this.$route.query.cFid)
				})
			},
			// 新增 / 修改
			addOrUpdateCheckHandle(id) {
				this.addOrUpdateCheckVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdateCheck.init(id, this.$route.query.id, this.$route.query.flg)
				})
			},

			// 删除
			deleteHandle(id, item) {
				this.$confirm('是否确认删除合同编号为' + item.contNo, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function() {
					return delObj(id)
				}).then(data => {
					this.$message.success('删除成功')
					this.getDataList()
				})
			},
			back() {
				this.$router.go(-1);
			},
			formatimportCountry(row, column) {
				let name;
				this.arr.forEach(function(res) {
					if(res.countryCode == row.importCountry) {
						console.log(res.countryName)
						name = res.countryName
					}
				});
				return name;
			},
			updateViewFirst(e) {
				console.log(e)
				this.dialogVisibleFirst = true;
				this.form.fId = e.fId;
				this.form.hFid = e.hFid;
				this.form.inCheckNum = e.inCheckNum
			},

			updateViewSecond(e) {
				console.log(e)
				this.form = {}
				this.dialogVisibleSecond = true;
				this.form.fId = e.fId;
				this.form.hFid = e.hFid;
				this.form.outCheckNum = e.outCheckNum
				this.form.inCheckNum = e.inCheckNum
			},
			updateViewBaoguan(e) {
				console.log(e)
				this.dialogVisibleBaoguan = true;
				this.form.fId = e.fId;
				this.form.hFid = e.hFid;
				this.form.inCustomsDeclaration = e.inCustomsDeclaration
				this.form.outCustomsDeclaration = e.outCustomsDeclaration
			},
			updateCopy(fId) {
				let params = {
					fId: fId
				}
				updateCopy(params).then((res) => {
					console.log(res)
					if(res.data.code == 0) {
						this.$notify.success('添加成功')
						this.getDataList();
					}
				})
			},
			updateInCheckNum(val) {
				//    this.form.inCheckNumFlg = val //代表只修改出入区核注清单号
				console.log(this.form)
				updateByForCheckNum(this.form).then((res) => {
					console.log(res)

					if(res.data.code == 0) {
						this.getDataList();
						this.dialogVisibleFirst = false;
						this.dialogVisibleSecond = false;
						this.dialogVisibleBaoguan = false;
						this.$notify.success('成功更改')
					} else {
						this.$notify.error('更改失败')
					}

				})
			},
			updateInCheckNum2(val) {
				//    this.form.inCheckNumFlg = val //代表只修改出入区核注清单号
				console.log(this.form)
				updateByForCustomsDeclaration(this.form).then((res) => {
					console.log(res)

					if(res.data.code == 0) {
						this.getDataList();
						this.dialogVisibleFirst = false;
						this.dialogVisibleSecond = false;
						this.dialogVisibleBaoguan = false;
						this.$notify.success('成功更改')
					} else {
						this.$notify.error('更改失败')
					}

				})
			},
			change(e) {
				this.$forceUpdate()
			},

		}
	}
</script>