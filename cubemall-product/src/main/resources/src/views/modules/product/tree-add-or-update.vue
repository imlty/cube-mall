<template>
  <el-dialog
    :title="!dataForm.nodeId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="name">
      <el-input v-model="dataForm.name" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="lft">
      <el-input v-model="dataForm.lft" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="rgt">
      <el-input v-model="dataForm.rgt" placeholder=""></el-input>
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
          nodeId: 0,
          name: '',
          lft: '',
          rgt: ''
        },
        dataRule: {
          name: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          lft: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          rgt: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.nodeId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.nodeId) {
            this.$http({
              url: this.$http.adornUrl(`/product/tree/info/${this.dataForm.nodeId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.tree.name
                this.dataForm.lft = data.tree.lft
                this.dataForm.rgt = data.tree.rgt
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
              url: this.$http.adornUrl(`/product/tree/${!this.dataForm.nodeId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'nodeId': this.dataForm.nodeId || undefined,
                'name': this.dataForm.name,
                'lft': this.dataForm.lft,
                'rgt': this.dataForm.rgt
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
