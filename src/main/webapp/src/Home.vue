<template>
    <div>
        <div class="nav-bar"><span class="nav-span">SHIPWHEEL</span></div>
        <div class="header-bar">
            <div class="ship-drop">
                <el-dropdown trigger="click">
                    <span class="el-dropdown-link ship-pointer"><img class="ship-img" src="./assets/ico-y.png">{{ user.name }}</span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="initModPwd"><i class="fa fa-pencil fa-fw"></i><span class="">密码修改</span></el-dropdown-item>
                        <el-dropdown-item divided @click.native="logout"><i class="fa fa-sign-out fa-fw"></i>安全退出</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>

        <div class="main">
            <div class="side-bar">
                <el-row class="tac">
                    <el-col>
                        <el-menu :default-active="$route.path" class="el-menu-vertical-demo" theme="dark"
                                 :uniqueOpened="true" :router="true">
                            <template v-for="(item, index) in $router.options.routes" v-if="!item.hidden">
                                <el-submenu :index="index + ''" v-if="!item.leaf">
                                    <template slot="title">
                                        <i :class="['fa', item.icon]"></i>
                                        <span class="ship-span">{{ item.name}}</span>
                                    </template>
                                    <el-menu-item v-for="child in item.children" :index="child.path" :key="child.path">
                                        <i :class="['fa', child.icon]"></i>
                                        <span class="ship-span">{{ child.name }}</span>
                                    </el-menu-item>
                                </el-submenu>
                                <el-menu-item :index="item.children[0].path" v-if="item.leaf">
                                    <i :class="['fa', item.icon]"></i>
                                    <span class="ship-span">{{ item.children[0].name }}</span>
                                </el-menu-item>
                            </template>
                        </el-menu>
                    </el-col>
                </el-row>
            </div>
            <div>
                <transition name="fade" mode="out-in">
                    <router-view class="content-view"></router-view>
                </transition>
            </div>
        </div>
        <div class="footer"></div>

        <!--密码修改-->
        <el-dialog :title="title" v-model="formVisible" :close-on-click-modal="false" size="tiny">
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
                <el-button @click.native="formVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editPwd">提交</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import axios from 'axios';
    import crypto_js from 'crypto-js';
    import util from './js/util';
    export default {
        data () {
            let checkNewPwd = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入新密码'));
                } else if (!util.PATTERN.PASSWORD.test(this.user.new_pwd)) {
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
                formVisible: false,
                title: '',
                user: {
                    name: '',
                    old_pwd: '',
                    new_pwd: '',
                    next_pwd: '',
                    region: ''
                },
                options: [
                    {value:1, label: '111'},
                    {value:2, label: '222'},
                ],
                editFormRules: {
                    old_pwd: [{required: true, message: '请输入旧密码', trigger: 'blur'}],
                    new_pwd: [{validator: checkNewPwd, trigger: 'blur'}],
                    next_pwd: [{validator: checkNextPwd, trigger: 'blur'}]
                }
            }
        },
        methods: {
            initModPwd: function () {
                let self = this;
                self.formVisible = true;
                self.title = '密码修改';
                self.$refs.user.resetFields();
            },
            editPwd: function () {
                let self = this;
                self.$refs.user.validate((valid) => {
                    if (valid) {
                        let data = {
                            name: self.user.name,
                            old_pwd: crypto_js.SHA256(self.user.old_pwd).toString(crypto_js.enc.Hex),
                            new_pwd: crypto_js.SHA256(self.user.new_pwd).toString(crypto_js.enc.Hex)
                        };
                        axios.put('/ship/user', data).then((res) => {
                            if (res.data.flag === "0") {
                                util.dialog.notifySuccess(self, res.data.msg);
                                self.formVisible = false;
                            } else if (res.data.flag === "1") {
                                util.dialog.notifyError(self, res.data.msg);
                            } else {
                                util.dialog.notifyError(self, res.data.msg);
                            }
                        }).catch((err) => {
                            util.dialog.notifyError(self, '修改失败');
                        })
                    }
                })
            },
            logout: function () {
                sessionStorage.removeItem('user');
                this.$router.push({path: '/login'})
            }
        },
        mounted: function () {
            let _this = this;
            _this.$nextTick(() => {
                let user = JSON.parse(sessionStorage.getItem("user"));
                _this.user.name = user.name;
            })
        }
    }
</script>
<style>
    /*.el-input__inner {
        width: 100%!important;
    }*/
</style>