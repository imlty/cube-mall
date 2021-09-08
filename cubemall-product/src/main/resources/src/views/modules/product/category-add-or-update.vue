<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="分类名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="分类名称"></el-input>
    </el-form-item>
    <el-form-item label="商品数量" prop="goodsNum">
      <el-input v-model="dataForm.goodsNum" placeholder="商品数量"></el-input>
    </el-form-item>
    <el-form-item label="是否显示" prop="isShow">
      <el-input v-model="dataForm.isShow" placeholder="是否显示"></el-input>
    </el-form-item>
    <el-form-item label="是否导航" prop="isMenu">
      <el-input v-model="dataForm.isMenu" placeholder="是否导航"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="seq">
      <el-input v-model="dataForm.seq" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="上级ID" prop="parentId">
      <el-input v-model="dataForm.parentId" placeholder="上级ID"></el-input>
    </el-form-item>
    <el-form-item label="模板ID" prop="templateId">
      <el-input v-model="dataForm.templateId" placeholder="模板ID"></el-input>
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
          goodsNum: '',
          isShow: '',
          isMenu: '',
          seq: '',
          parentId: '',
          templateId: ''
        },
        dataRule: {
          name: [
            { required: true, message: '分类名称不能为空', trigger: 'blur' }
          ],
          goodsNum: [
            { required: true, message: '商品数量不能为空', trigger: 'blur' }
          ],
          isShow: [
            { required: true, message: '是否显示不能为空', trigger: 'blur' }
          ],
          isMenu: [
            { required: true, message: '是否导航不能为空', trigger: 'blur' }
          ],
          seq: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          parentId: [
            { required: true, message: '上级ID不能为空', trigger: 'blur' }
          ],
          templateId: [
            { required: true, message: '模板ID不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/product/category/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.category.name
                this.dataForm.goodsNum = data.category.goodsNum
                this.dataForm.isShow = data.category.isShow
                this.dataForm.isMenu = data.category.isMenu
                this.dataForm.seq = data.category.seq
                this.dataForm.parentId = data.category.parentId
                this.dataForm.templateId = data.category.templateId
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
              url: this.$http.adornUrl(`/product/category/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'goodsNum': this.dataForm.goodsNum,
                'isShow': this.dataForm.isShow,
                'isMenu': this.dataForm.isMenu,
                'seq': this.dataForm.seq,
                'parentId': this.dataForm.parentId,
                'templateId': this.dataForm.templateId
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
