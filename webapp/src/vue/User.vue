<template>
    <div>
        <div class="ship-top">
            <span class="ship-span">用户管理</span>
        </div>
        <div class="ship-line"></div>
        <div class="view">
            <div class="ship-btn">
                <el-button-group>
                    <el-button @click="mod"><i class="fa fa-pencil" aria-hidden="true"></i></el-button>
                    <el-button><i class="fa fa-refresh" aria-hidden="true"></i></el-button>
                    <el-button><i class="fa fa-trash" aria-hidden="true"></i></el-button>
                </el-button-group>
            </div>
            <div class="ship-table">
                <el-table :data="userList" border tooltip-effect="dark" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column prop="id" label="ID" width="100" sortable></el-table-column>
                    <el-table-column prop="name" label="账户名称"></el-table-column>
                </el-table>
            </div>
            <div class="block ship-page">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-size="10"
                        :total="tatal">
                </el-pagination>
            </div>
        </div>
        <!--add or modify-->
        <el-dialog :title="title" v-model="addFormVisible" :close-on-click-modal="false" size="tiny">
            <el-form label-width="80px" :model="user" :rules="editFormRules" ref="user">
                <el-form-item label="用户名" prop="name">
                    {{ user.name }}
                </el-form-item>
                <el-form-item label="旧密码" prop="old_pwd">
                    <el-input type="password" v-model="user.old_pwd" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="new_pwd">
                    <el-input type="password" v-model="user.new_pwd" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="next_pwd">
                    <el-input type="password" v-model="user.next_pwd" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="edit">提交</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    let pasReg = /^(?![^a-zA-Z]+$)(?!\D+$).{8,20}$/;
    export default {
        data () {
            let checkNewPwd = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入新密码'));
                } else if (!pasReg.test(this.user.new_pwd)) {
                    callback(new Error('密码长度最少为8位的数字加字母组成'));
                } else {
                    callback();
                }
            };
            let checkNextPwd = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入确认密码'));
                } else if (value !== this.user.new_pwd) {
                    callback(new Error('两次密码输入不一致'));
                } else {
                    callback();
                }
            };
            return {
                addFormVisible: false,
                title: '',
                userList: [
                    {id: 1, name: 'admin'},
                ],
                checkList: [],
                tatal: 1,
                currentPage: 1,
                user: {
                    name: 'admin',
                    old_pwd: '',
                    new_pwd: '',
                    next_pwd: ''

                },
                editFormRules: {
                    old_pwd: [{required: true, message: '请输入旧密码', trigger: 'blur'}],
                    new_pwd: [{validator: checkNewPwd, trigger: 'blur'}],
                    next_pwd: [{validator: checkNextPwd, trigger: 'blur'}]
                }
            }
        },
        methods: {
            handleSizeChange: function (val) {

            },

            handleCurrentChange: function (val) {

            },

            handleSelectionChange: function (val) {
                this.checkList = val;
            },
            mod: function () {
                console.log(this.checkList);
                let self = this;
                self.addFormVisible = true;
                self.title = '修改用户';
                self.user ={
                    name: 'admin',
                    old_pwd: '',
                    new_pwd: '',
                    next_pwd: ''
                }
            },
            edit: function () {
                let self = this;
                self.$refs.user.validate((valid) => {
                    if (valid) {
                        self.$notify.success({ message: '添加成功', offset: 100, duration: 2000})
                    }
                })
            }
        },

        mounted: function () {
            let self = this;
            self.$nextTick(function () {
                self.tatal = self.userList.length;
            })
        },
    }
</script>

<style>
    .ship-content {
        position: absolute;
        left: 230px;
        width: 100%;
        overflow-y: auto;
        overflow-x: hidden;
    }

    .view {
        padding: 10px;
    }

    .ship-top {
        margin-top: 10px;
        font-size: 20px;
    }

    .ship-line {
        margin-top: 10px;
        border-bottom: solid 1px #e5e8ed;
    }

    .ship-btn {
        margin-bottom: 10px;
    }

    .ship-page {
        float: right;
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .el-dialog__header {
        padding: 20px 20px 0;
        border-bottom: solid 1px #e5e8ed;
        padding-bottom: 10px;
    }
</style>