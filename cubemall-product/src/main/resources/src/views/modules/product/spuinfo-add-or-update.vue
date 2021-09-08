<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="spu名称" prop="spuName">
      <el-input v-model="dataForm.spuName" placeholder="spu名称"></el-input>
    </el-form-item>
    <el-form-item label="spu描述" prop="spuDescription">
      <el-input v-model="dataForm.spuDescription" placeholder="spu描述"></el-input>
    </el-form-item>
    <el-form-item label="分类ID" prop="categoryId">
      <el-input v-model="dataForm.categoryId" placeholder="分类ID"></el-input>
    </el-form-item>
    <el-form-item label="品牌ID" prop="brandId">
      <el-input v-model="dataForm.brandId" placeholder="品牌ID"></el-input>
    </el-form-item>
    <el-form-item label="权重" prop="weight">
      <el-input v-model="dataForm.weight" placeholder="权重"></el-input>
    </el-form-item>
    <el-form-item label="发布状态" prop="publishStatus">
      <el-input v-model="dataForm.publishStatus" placeholder="发布状态"></el-input>
    </el-form-item>
    <el-form-item label="" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder=""></el-input>
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
          spuName: '',
          spuDescription: '',
          categoryId: '',
          brandId: '',
          weight: '',
          publishStatus: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          spuName: [
            { required: true, message: 'spu名称不能为空', trigger: 'blur' }
          ],
          spuDescription: [
            { required: true, message: 'spu描述不能为空', trigger: 'blur' }
          ],
          categoryId: [
            { required: true, message: '分类ID不能为空', trigger: 'blur' }
          ],
          brandId: [
            { required: true, message: '品牌ID不能为空', trigger: 'blur' }
          ],
          weight: [
            { required: true, message: '权重不能为空', trigger: 'blur' }
          ],
          publishStatus: [
            { required: true, message: '发布状态不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/product/spuinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.spuName = data.spuInfo.spuName
                this.dataForm.spuDescription = data.spuInfo.spuDescription
                this.dataForm.categoryId = data.spuInfo.categoryId
                this.dataForm.brandId = data.spuInfo.brandId
                this.dataForm.weight = data.spuInfo.weight
                this.dataForm.publishStatus = data.spuInfo.publishStatus
                this.dataForm.createTime = data.spuInfo.createTime
                this.dataForm.updateTime = data.spuInfo.updateTime
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
              url: this.$http.adornUrl(`/product/spuinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'spuName': this.dataForm.spuName,
                'spuDescription': this.dataForm.spuDescription,
                'categoryId': this.dataForm.categoryId,
                'brandId': this.dataForm.brandId,
                'weight': this.dataForm.weight,
                'publishStatus': this.dataForm.publishStatus,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
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
