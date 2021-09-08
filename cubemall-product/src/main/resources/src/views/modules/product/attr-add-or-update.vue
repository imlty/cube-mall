<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
    <el-form-item label="搜索类型" prop="searchType">
      <el-input v-model="dataForm.searchType" placeholder="搜索类型"></el-input>
    </el-form-item>
    <el-form-item label="图表" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="图表"></el-input>
    </el-form-item>
    <el-form-item label="选择值" prop="valueSelect">
      <el-input v-model="dataForm.valueSelect" placeholder="选择值"></el-input>
    </el-form-item>
    <el-form-item label="属性类型:0-销售属性,1-基本属性,2-既是基本属性又是销售属性" prop="attrType">
      <el-input v-model="dataForm.attrType" placeholder="属性类型:0-销售属性,1-基本属性,2-既是基本属性又是销售属性"></el-input>
    </el-form-item>
    <el-form-item label="启用" prop="enable">
      <el-input v-model="dataForm.enable" placeholder="启用"></el-input>
    </el-form-item>
    <el-form-item label="分类ID" prop="categoryId">
      <el-input v-model="dataForm.categoryId" placeholder="分类ID"></el-input>
    </el-form-item>
    <el-form-item label="描述" prop="showDesc">
      <el-input v-model="dataForm.showDesc" placeholder="描述"></el-input>
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
          name: '',
          searchType: '',
          icon: '',
          valueSelect: '',
          attrType: '',
          enable: '',
          categoryId: '',
          showDesc: ''
        },
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          searchType: [
            { required: true, message: '搜索类型不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '图表不能为空', trigger: 'blur' }
          ],
          valueSelect: [
            { required: true, message: '选择值不能为空', trigger: 'blur' }
          ],
          attrType: [
            { required: true, message: '属性类型:0-销售属性,1-基本属性,2-既是基本属性又是销售属性不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '启用不能为空', trigger: 'blur' }
          ],
          categoryId: [
            { required: true, message: '分类ID不能为空', trigger: 'blur' }
          ],
          showDesc: [
            { required: true, message: '描述不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/product/attr/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.attr.name
                this.dataForm.searchType = data.attr.searchType
                this.dataForm.icon = data.attr.icon
                this.dataForm.valueSelect = data.attr.valueSelect
                this.dataForm.attrType = data.attr.attrType
                this.dataForm.enable = data.attr.enable
                this.dataForm.categoryId = data.attr.categoryId
                this.dataForm.showDesc = data.attr.showDesc
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
              url: this.$http.adornUrl(`/product/attr/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'searchType': this.dataForm.searchType,
                'icon': this.dataForm.icon,
                'valueSelect': this.dataForm.valueSelect,
                'attrType': this.dataForm.attrType,
                'enable': this.dataForm.enable,
                'categoryId': this.dataForm.categoryId,
                'showDesc': this.dataForm.showDesc
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
