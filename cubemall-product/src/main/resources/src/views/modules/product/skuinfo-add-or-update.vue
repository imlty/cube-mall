<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="spuId" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spuId"></el-input>
    </el-form-item>
    <el-form-item label="sku名称" prop="skuName">
      <el-input v-model="dataForm.skuName" placeholder="sku名称"></el-input>
    </el-form-item>
    <el-form-item label="sku描述" prop="skuDesc">
      <el-input v-model="dataForm.skuDesc" placeholder="sku描述"></el-input>
    </el-form-item>
    <el-form-item label="分类id" prop="categoryId">
      <el-input v-model="dataForm.categoryId" placeholder="分类id"></el-input>
    </el-form-item>
    <el-form-item label="品牌id" prop="brandId">
      <el-input v-model="dataForm.brandId" placeholder="品牌id"></el-input>
    </el-form-item>
    <el-form-item label="默认sku图片" prop="skuDefaultImg">
      <el-input v-model="dataForm.skuDefaultImg" placeholder="默认sku图片"></el-input>
    </el-form-item>
    <el-form-item label="sku名称" prop="skuTitle">
      <el-input v-model="dataForm.skuTitle" placeholder="sku名称"></el-input>
    </el-form-item>
    <el-form-item label="sku子名称" prop="skuSubtitle">
      <el-input v-model="dataForm.skuSubtitle" placeholder="sku子名称"></el-input>
    </el-form-item>
    <el-form-item label="价格" prop="price">
      <el-input v-model="dataForm.price" placeholder="价格"></el-input>
    </el-form-item>
    <el-form-item label="销售数量" prop="saleCount">
      <el-input v-model="dataForm.saleCount" placeholder="销售数量"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          spuId: '',
          skuName: '',
          skuDesc: '',
          categoryId: '',
          brandId: '',
          skuDefaultImg: '',
          skuTitle: '',
          skuSubtitle: '',
          price: '',
          saleCount: ''
        },
        dataRule: {
          spuId: [
            { required: true, message: 'spuId不能为空', trigger: 'blur' }
          ],
          skuName: [
            { required: true, message: 'sku名称不能为空', trigger: 'blur' }
          ],
          skuDesc: [
            { required: true, message: 'sku描述不能为空', trigger: 'blur' }
          ],
          categoryId: [
            { required: true, message: '分类id不能为空', trigger: 'blur' }
          ],
          brandId: [
            { required: true, message: '品牌id不能为空', trigger: 'blur' }
          ],
          skuDefaultImg: [
            { required: true, message: '默认sku图片不能为空', trigger: 'blur' }
          ],
          skuTitle: [
            { required: true, message: 'sku名称不能为空', trigger: 'blur' }
          ],
          skuSubtitle: [
            { required: true, message: 'sku子名称不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '价格不能为空', trigger: 'blur' }
          ],
          saleCount: [
            { required: true, message: '销售数量不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/product/skuinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.spuId = data.skuInfo.spuId
                this.dataForm.skuName = data.skuInfo.skuName
                this.dataForm.skuDesc = data.skuInfo.skuDesc
                this.dataForm.categoryId = data.skuInfo.categoryId
                this.dataForm.brandId = data.skuInfo.brandId
                this.dataForm.skuDefaultImg = data.skuInfo.skuDefaultImg
                this.dataForm.skuTitle = data.skuInfo.skuTitle
                this.dataForm.skuSubtitle = data.skuInfo.skuSubtitle
                this.dataForm.price = data.skuInfo.price
                this.dataForm.saleCount = data.skuInfo.saleCount
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/product/skuinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'spuId': this.dataForm.spuId,
                'skuName': this.dataForm.skuName,
                'skuDesc': this.dataForm.skuDesc,
                'categoryId': this.dataForm.categoryId,
                'brandId': this.dataForm.brandId,
                'skuDefaultImg': this.dataForm.skuDefaultImg,
                'skuTitle': this.dataForm.skuTitle,
                'skuSubtitle': this.dataForm.skuSubtitle,
                'price': this.dataForm.price,
                'saleCount': this.dataForm.saleCount
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
