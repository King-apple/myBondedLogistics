<template>
  <el-dialog
    width="70%"
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      label-width="120px"
      @submit.native.prevent
    >
      <el-row>
        <el-col :span="8">
          <el-form-item label="作业委托人:" prop="consignNam">
            <el-select
              v-model="dataForm.consignNam"
              placeholder="作业委托人:"
              filterable
              @change="cargoNamChange"
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in clientInfoOptions"
                :key="index"
                :label="item.company"
                :value="item.company"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="货品名:" prop="cargoNamCustoms">
            <el-select
              v-model="dataForm.cargoNamCustoms"
              placeholder="货品名"
              filterable
              @change="cargoNamCustomsChange"
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in cargonamcustomsOptions"
                :key="index"
                :label="item.cargoNam"
                :value="item.cargoNam"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="货类:" prop="cargoKindNam">
            <el-select
              v-model="dataForm.cargoKindNam"
              placeholder="货类"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in cargoKindNamOptions"
                :key="index"
                :label="item.cargoKindNam"
                :value="item.cargoKindNam"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="入库日期:" prop="inDte">
            <el-date-picker
              v-model="dataForm.inDte"
              type="date"
              placeholder="入库日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="入库毛重(kg):"
            prop="inRoughWet"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.inRoughWet"
              placeholder="入库毛重(kg)"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="入库净重(kg):"
            prop="inNetWet"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.inNetWet"
              placeholder="入库净重（kg）"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item
            label="入库额(美元):"
            prop="inCargoAccount"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.inCargoAccount"
              placeholder="入库货品额（美元）"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="原产国(入库):"
            prop="inOriginCountry"
            label-width="120px"
          >
            <el-select
              v-model="dataForm.inOriginCountry"
              placeholder="原产国（入库）:"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in countryInfoOptions"
                :key="index"
                :label="item.countryName"
                :value="item.countryName"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="最终目的地(入):"
            prop="inFinalDestination"
            label-width="120px"
          >
            <el-select
              v-model="dataForm.inFinalDestination"
              placeholder="最终目的地（入库）"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in countryInfoOptions"
                :key="index"
                :label="item.countryName"
                :value="item.countryName"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item
            label="核注清单号(入):"
            prop="inCheckNum"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.inCheckNum"
              placeholder="核注清单号（入库）"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="核对联(入库):"
            prop="inNuclearCouplet"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.inNuclearCouplet"
              placeholder="核对联（入库）"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="报关单号(入库):"
            prop="inCustomsDeclarationNum"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.inCustomsDeclarationNum"
              placeholder="报关单号（入库）"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item
            label="识别代码(入库):"
            prop="inIdentificationCod"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.inIdentificationCod"
              placeholder="识别代码（入库）"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="货票号(入库):"
            prop="inWaybillNum"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.inWaybillNum"
              placeholder="货票号（入库）"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="类别(进区):"
            prop="inCategory"
            label-width="120px"
          >
            <el-select
              v-model="dataForm.inCategory"
              placeholder="类别（进区）"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in areaTypeDict"
                :key="index"
                :label="item.label"
                :value="item.label"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="出库日期:" prop="outDte">
            <el-date-picker
              v-model="dataForm.outDte"
              type="date"
              placeholder="出库日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item
            label="出库毛重(kg ):"
            prop="outRoughWet"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.outRoughWet"
              placeholder="出库毛重（kg）"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item
            label="出库净重(kg):"
            prop="outNetWet"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.outNetWet"
              placeholder="出库净重（kg）"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item
            label="出库额(美元):"
            prop="outCargoAccount"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.outCargoAccount"
              placeholder="出库货品额（美元）"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item
            label="原产国(出库):"
            prop="outOriginCountry"
            label-width="120px"
          >
            <el-select
              v-model="dataForm.outOriginCountry"
              placeholder="原产国（出库）:"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in countryInfoOptions"
                :key="index"
                :label="item.countryName"
                :value="item.countryName"
              >
              </el-option> </el-select></el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item
            label="最终目的地(出):"
            prop="outFinalDestination"
            label-width="120px"
          >
            <el-select
              v-model="dataForm.outFinalDestination"
              placeholder="最终目的地(出库)"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in countryInfoOptions"
                :key="index"
                :label="item.countryName"
                :value="item.countryName"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item
            label="核注清单(出库):"
            prop="outCheckNum"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.outCheckNum"
              placeholder="核注清单号（出库）"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item
            label="核对联(出库):"
            prop="outNuclearCouplet"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.outNuclearCouplet"
              placeholder="核对联（出库）"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item
            label="报关单号(出库):"
            prop="outCustomsDeclarationNum"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.outCustomsDeclarationNum"
              placeholder="报关单号（出库）"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item
            label="识别代码(出库):"
            prop="outIdentificationCod"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.outIdentificationCod"
              placeholder="识别代码（出库）"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item
            label="货票号(出库):"
            prop="outWaybillNum"
            label-width="120px"
          >
            <el-input
              v-model="dataForm.outWaybillNum"
              placeholder="货票号（出库）"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item
            label="类别(出区):"
            prop="outCategory"
            label-width="120px"
          >
            <el-select
              v-model="dataForm.outCategory"
              placeholder="类别（出区）"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in areaTypeDict"
                :key="index"
                :label="item.label"
                :value="item.label"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit"
        >确定</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import {
  getObj,
  addObj,
  putObj,
  getAreaType,
} from "@/api/baoshuisystem/customsaccountingstatistics";
import { getClientInfoAll } from "@/api/baoshuisystem/clientinfo";
import { getCountryInfoAll } from "@/api/baoshuisystem/countryinfo";
//import { getContractObjectInfoAll } from "@/api/baoshuisystem/contractobject";
import { getCargonamcustomsAll } from "@/api/baoshuisystem/cargonamcustoms";
import { all as getCargoKindNamAll} from "@/api/baoshuisystem/goodscategory";
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      //contractObjectOptions: [],
      cargonamcustomsOptions: [],
      cargoKindNamOptions: [],
      clientInfoOptions: [],
      countryInfoOptions: [],
      areaTypeDict: [],
      dataForm: {
        fId: 0,
        consignNam: "",
        cargoNamCustoms: "",
        cargoKindNam: "",
        inDte: "",
        inRoughWet: "",
        inNetWet: "",
        inCargoAccount: "",
        inOriginCountry: "",
        inFinalDestination: "",
        inCheckNum: "",
        inNuclearCouplet: "",
        inCustomsDeclarationNum: "",
        inIdentificationCod: "",
        inWaybillNum: "",
        inCategory: "",
        outDte: "",
        outRoughWet: "",
        outNetWet: "",
        outCargoAccount: "",
        outOriginCountry: "",
        outFinalDestination: "",
        outCheckNum: "",
        outNuclearCouplet: "",
        outCustomsDeclarationNum: "",
        outIdentificationCod: "",
        outWaybillNum: "",
        outCategory: "",
        operNam: "",
      },
      dataRule: {
        // consignNam: [
        //   { required: true, message: "作业委托人不能为空", trigger: "blur" },
        // ],
        // cargoNamCustoms: [
        //   { required: true, message: "货品名不能为空", trigger: "blur" },
        // ],
        // inDte: [
        //   { required: true, message: "入库日期不能为空", trigger: "blur" },
        // ],
        // inRoughWet: [
        //   {
        //     required: true,
        //     message: "入库毛重（kg）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inNetWet: [
        //   {
        //     required: true,
        //     message: "入库净重（kg）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inCargoAccount: [
        //   {
        //     required: true,
        //     message: "入库货品额（美元）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inOriginCountry: [
        //   {
        //     required: true,
        //     message: "原产国（入库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inFinalDestination: [
        //   {
        //     required: true,
        //     message: "最终目的地（入库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inCheckNum: [
        //   {
        //     required: true,
        //     message: "核注清单号（入库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inNuclearCouplet: [
        //   {
        //     required: true,
        //     message: "核对联（入库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inCustomsDeclarationNum: [
        //   {
        //     required: true,
        //     message: "报关单号（入库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inIdentificationCod: [
        //   {
        //     required: true,
        //     message: "识别代码（入库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inWaybillNum: [
        //   {
        //     required: true,
        //     message: "货票号（入库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // inCategory: [
        //   { required: true, message: "类别（进区）不能为空", trigger: "blur" },
        // ],
        // outDte: [
        //   { required: true, message: "出库日期不能为空", trigger: "blur" },
        // ],
        // outRoughWet: [
        //   {
        //     required: true,
        //     message: "出库毛重（kg )不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outNetWet: [
        //   {
        //     required: true,
        //     message: "出库净重（kg）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outCargoAccount: [
        //   {
        //     required: true,
        //     message: "出库货品额（美元）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outOriginCountry: [
        //   {
        //     required: true,
        //     message: "原产国（出库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outFinalDestination: [
        //   {
        //     required: true,
        //     message: "最终目的地（出库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outCheckNum: [
        //   {
        //     required: true,
        //     message: "核注清单号（出库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outNuclearCouplet: [
        //   {
        //     required: true,
        //     message: "核对联（出库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outCustomsDeclarationNum: [
        //   {
        //     required: true,
        //     message: "报关单号（出库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outIdentificationCod: [
        //   {
        //     required: true,
        //     message: "识别代码（出库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outWaybillNum: [
        //   {
        //     required: true,
        //     message: "货票号（出库）不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // outCategory: [
        //   { required: true, message: "类别（出区）不能为空", trigger: "blur" },
        // ],
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  methods: {
    init(id) {
      this.getInfo();
      this.dataForm.fId = id || 0;
      this.visible = true;
      this.canSubmit = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.fId) {
          getObj(this.dataForm.fId).then((response) => {
            this.dataForm = response.data.data;
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.canSubmit = false;
          if (this.dataForm.fId) {
            putObj(this.dataForm)
              .then((data) => {
                this.$notify.success("修改成功");
                this.visible = false;
                this.$emit("refreshDataList");
              })
              .catch(() => {
                this.canSubmit = true;
              });
          } else {
            addObj(this.dataForm)
              .then((data) => {
                this.$notify.success("添加成功");
                this.visible = false;
                this.$emit("refreshDataList");
              })
              .catch(() => {
                this.canSubmit = true;
              });
          }
        }
      });
    },
    getInfo: async function () {
      await getClientInfoAll().then((response) => {
        this.clientInfoOptions = response.data.data;
      });

      await getCargonamcustomsAll().then((response) => {
        this.cargonamcustomsOptions = response.data.data;
      });

      getCargoKindNamAll().then((response) => {
        this.cargoKindNamOptions = response.data.data;
      });

       getCountryInfoAll().then((response) => {
        this.countryInfoOptions = response.data.data;
      });

      await getAreaType().then((response) => {
        this.areaTypeDict = response.data.data;
      });
    },
    cargoNamChange(val) {
      // var obj = this.options.find((item) => {
      //   return item.contactName === val;
      // });
    },
    cargoNamCustomsChange() { }
  },
};
</script>

