<template>
    <div>
        <div class="ship-panel-left"><p></p></div>
        <div class="ship-panel-center">
            <div class="ship-top">
                <span class="ship-span">管理地址配置</span>
            </div>
            <div class="ship-sys-line"></div>
            <el-form :model="system" :rules="editFormManager" ref="system" label-width="100px" class="demo-ruleForm">
                <el-form-item label="默认网关" prop="gateway">
                    <el-input v-model="system.gateway" auto-complete="off" placeholder="请输入网关"></el-input>
                </el-form-item>
                <el-form-item label="IP地址" prop="ip">
                    <el-input v-model="system.ip" auto-complete="off" placeholder="请输入IP地址"></el-input>
                </el-form-item>
                <el-form-item label="掩码" prop="mask">
                    <el-input v-model="system.mask" auto-complete="off" placeholder="请输入掩码"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveManager">提交</el-button>
                </el-form-item>
            </el-form>
            <!--dns内端-->
            <div class="ship-top">
                <span class="ship-span">DNS-内端</span>
            </div>
            <div class="ship-sys-line"></div>
            <div class="ship-form">
                <el-form :model="inside" :rules="editFormInside" ref="inside" label-width="100px"
                         class="demo-ruleForm">
                    <el-form-item label="dns" prop="insideDns">
                        <el-input v-model="inside.insideDns" auto-complete="off"
                                  placeholder="请输入内端机dns"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="modInsideDns">提交</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <!--dns外端-->
            <div class="ship-top">
                <span class="ship-span">DNS-外端</span>
            </div>
            <div class="ship-sys-line"></div>
            <div class="ship-form">
                <el-form :model="outside" :rules="editFormOutside" ref="outside" label-width="100px"
                         class="demo-ruleForm">
                    <el-form-item label="dns" prop="outsideDns">
                        <el-input v-model="outside.outsideDns" auto-complete="off"
                                  placeholder="请输入外端机dns"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="modOutsideDns">提交</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <div class="ship-panel-right"><p></p></div>
    </div>
</template>
<script>
    import util from '../js/util';
    import axios from 'axios';
    export default {
        data () {
            let checkDns = (rule, value, callback) => {
                if (!value)
                    callback(new Error('请输入dns'));
                else if (!util.PATTERN.IP.test(value))
                    callback(new Error('dns不合法'));
                else
                    callback();
            };
            let checkGateway = (rule, value, callback) => {
                if (!value)
                    callback(new Error('请输入网关'));
                else if (!util.PATTERN.IP.test(value))
                    callback(new Error('网关不合法'));
                else
                    callback();
            };
            let checkIp = (rule, value, callback) => {
                if (!value)
                    callback(new Error('请输入IP'));
                else if (!util.PATTERN.IP.test(value))
                    callback(new Error('IP不合法'));
                else
                    callback();
            };
            let checkMask = (rule, value, callback) => {
                if (!value)
                    callback(new Error('请输入掩码'));
                else if (!util.PATTERN.MASK.test(value))
                    callback(new Error('掩码不合法'));
                else
                    callback();
            };
            return {
                system: {
                    gateway: '',
                    ip: '',
                    mask: ''
                },
                menus: [
                    {id: '1', name: '内端机'},
                    {id: '2', name: '外端机'}
                ],
                nodeId: '1',
                inside: {
                    insideDns: ''
                },
                outside: {
                    outsideDns: ''
                },
                editFormInside: {
                    insideDns: [{validator: checkDns, trigger: 'blur'}]
                },
                editFormOutside: {
                    outsideDns: [{validator: checkDns, trigger: 'blur'}]
                },
                editFormManager: {
                    gateway: [{validator: checkGateway, trigger: 'blur'}],
                    ip: [{validator: checkIp, trigger: 'blur'}],
                    mask: [{validator: checkMask, trigger: 'blur'}]
                }
            }
        },
        methods: {
            loadInsideDns: function () {
                let self = this;
                axios.get('/ship/node/dns?nodeId=1').then((res) => {
                    self.inside.insideDns = res.data.dns;
                }).catch((err) => {
                    util.dialog.notifyError(self, '内端机dns加载失败');
                })
            },
            loadOutsideDns: function () {
                let self = this;
                axios.get('/ship/node/dns?nodeId=2').then((res) => {
                    self.outside.outsideDns = res.data.dns;
                }).catch((err) => {
                    util.dialog.notifyError(self, '外端机dns加载失败');
                })
            },
            modInsideDns: function () {
                let self = this;
                self.$refs.inside.validate((valid) => {
                    if (valid) {
                        axios.put('/ship/node/dns', {node_id: 1, dns: self.inside.insideDns}).then((res) => {
                            if (res.data.flag === '0') {
                                util.dialog.notifySuccess(self, res.data.msg);
                                self.loadInsideDns();
                            } else {
                                util.dialog.notifyError(self, res.data.msg);
                                self.loadInsideDns();
                            }
                        }).catch((err) => {
                            util.dialog.notifyError(self, '修改失败');
                            self.loadInsideDns();
                        });
                    }
                });
            },
            modOutsideDns: function () {
                let self = this;
                self.$refs.outside.validate((valid) => {
                    if (valid) {
                        axios.put('/ship/node/dns', {node_id: 2, dns: self.outside.outsideDns}).then((res) => {
                            if (res.data.flag === '0') {
                                util.dialog.notifySuccess(self, res.data.msg);
                                self.loadOutsideDns();
                            } else {
                                util.dialog.notifyError(self, res.data.msg);
                                self.loadOutsideDns();
                            }
                        }).catch((err) => {
                            util.dialog.notifyError(self, '修改失败');
                            self.loadOutsideDns();
                        })
                    }
                });
            },
            loadAddr: function () {
                let self = this;
                axios.get('/ship/sys/manAddr').then((res) => {
                    self.system = res.data;
                }).catch((err) => {
                    util.dialog.notifyError(self, '管理地址加载失败');
                })
            },
            saveManager: function () {
                let self = this;
                self.$refs.system.validate((valid) => {
                    if (valid) {
                        axios({
                            method: 'put',
                            url: '/ship/sys/manAddr',
                            data: self.system,
                            timeout: 2000,
                        }).then((res) => {
                            util.dialog.notifySuccess(self, '修改成功');
                            self.loadAddr();
                        }).catch((err) => {
                            if (err.status !== 504) {
                                util.dialog.notifyError(self, '修改失败');
                                self.loadAddr();
                            } else {
                                util.dialog.notifySuccess(self, '修改成功');
                            }
                        })
                    }
                });
            }
        },
        mounted: function () {
            let self = this;
            self.$nextTick(() => {
                self.loadInsideDns();
                self.loadOutsideDns();
                self.loadAddr();
            })
        }
    }
</script>