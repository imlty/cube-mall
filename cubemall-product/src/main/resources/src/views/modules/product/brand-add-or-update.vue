<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="品牌名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="品牌名称"></el-input>
    </el-form-item>
    <el-form-item label="品牌图片地址" prop="image">
      <el-input v-model="dataForm.image" placeholder="品牌图片地址"></el-input>
    </el-form-item>
    <el-form-item label="品牌的首字母" prop="letter">
      <el-input v-model="dataForm.letter" placeholder="品牌的首字母"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="seq">
      <el-input v-model="dataForm.seq" placeholder="排序"></el-input>
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
          image: '',
          letter: '',
          seq: ''
        },
        dataRule: {
          name: [
            { required: true, message: '品牌名称不能为空', trigger: 'blur' }
          ],
          image: [
            { required: true, message: '品牌图片地址不能为空', trigger: 'blur' }
          ],
          letter: [
            { required: true, message: '品牌的首字母不能为空', trigger: 'blur' }
          ],
          seq: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/product/brand/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.brand.name
                this.dataForm.image = data.brand.image
                this.dataForm.letter = data.brand.letter
                this.dataForm.seq = data.brand.seq
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
              url: this.$http.adornUrl(`/product/brand/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'image': this.dataForm.image,
                'letter': this.dataForm.letter,
                'seq': this.dataForm.seq
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
