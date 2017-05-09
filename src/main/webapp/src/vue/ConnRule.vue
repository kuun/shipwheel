<template>
    <div>
        <div class="ship-top">
            <span class="ship-span">连通规则</span>
        </div>
        <div class="ship-line"></div>
        <div class="view">
            <div class="ship-coon-btn">
                <el-button-group>
                    <el-button @click.native="initAdd"><i class="fa fa-plus" aria-hidden="true"></i></el-button>
                    <el-button @click.native="initMod"><i class="fa fa-pencil" aria-hidden="true"></i></el-button>
                    <el-button @click.native="del"><i class="fa fa-trash" aria-hidden="true"></i></el-button>
                    <el-button @click.native="loadConnRules(1)"><i class="fa fa-refresh" aria-hidden="true"></i>
                    </el-button>
                </el-button-group>
            </div>
            <div class="ship-table">
                <el-table :data="cannRuleList" border tooltip-effect="dark" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column prop="id" label="ID" width="100" sortable></el-table-column>
                    <el-table-column prop="rule_type" label="规则类型" width="100"></el-table-column>
                    <el-table-column prop="direct" label="传输方向" :formatter="directFormat" width="120"></el-table-column>
                    <el-table-column prop="listen_addr" label="监听地址" :formatter="lisAddrFormat"></el-table-column>
                    <el-table-column prop="listen_port" label="监听端口"></el-table-column>
                    <el-table-column prop="dst_addr" label="目的地址"></el-table-column>
                    <el-table-column prop="dst_port" label="目的端口"></el-table-column>
                    <el-table-column prop="send_addr" label="发送地址" :formatter="sendAddrFormat"></el-table-column>
                    <el-table-column prop="status" label="连接状态" width="100">
                        <template scope="scope">
                            <el-switch
                                    v-model="scope.row.status"
                                    on-color="#13ce66"
                                    off-color="#ff4949"
                                    :on-value="true"
                                    :off-value="false"
                                    @change="changeSwich(scope.row)">
                            </el-switch>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="block ship-page">
                <el-pagination
                        @current-change="handleCurrentChange"
                        :current-page="page.curPage"
                        :page-size="page.limit"
                        :total="page.total">
                </el-pagination>
            </div>
        </div>
        <!--add and modify-->
        <el-dialog :title="editTitle" v-model="formVisible" :close-on-click-modal="false" size="small">
            <el-form label-width="100px" :model="connRule" :rules="editFormRules" ref="connRule">
                <el-form-item label="规则类型">
                    <el-select v-model="connRule.rule_type">
                        <el-option v-for="type in  appTypes" :value="type.value" :label="type.name"
                                   :key="type.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="传输方向">
                    <el-select v-model="connRule.direct" @change="changeDirect">
                        <el-option v-for="direction in  directions" :value="direction.value" :label="direction.name"
                                   :key="direction.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="监听地址">
                    <el-select v-model="connRule.listen_addr.id">
                        <el-option v-for="ip in  listenAddrs" :value="ip.id" :label="ip.ip" :key="ip.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="监听端口" prop="listen_port">
                    <el-input type="number" v-model="connRule.listen_port" auto-complete="off"
                              placeholder="请输入监听端口"></el-input>
                </el-form-item>
                <el-form-item label="目的地址" prop="dst_addr">
                    <el-input v-model="connRule.dst_addr" auto-complete="off" placeholder="请输入目的地址"></el-input>
                </el-form-item>
                <el-form-item label="目的端口" prop="dst_port">
                    <el-input v-model="connRule.dst_port" auto-complete="off" placeholder="请输入目的端口"></el-input>
                </el-form-item>
                <el-form-item label="发送地址">
                    <el-select v-model="connRule.send_addr.id">
                        <el-option v-for="ip in  seedAddrs" :value="ip.id" :label="ip.ip" :key="ip.id"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="formVisible = false">取消</el-button>
                <el-button type="primary" @click.native="edit">提交</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import util from '../js/util';
    import axios from 'axios';
    import lodash from 'lodash';
    export default {
        data () {
            let checkListenPort = (rule, value, callback) => {
                if (!value)
                    callback(new Error('请输入监听端口'));
                else if (!util.PATTERN.PORT.test(value))
                    callback(new Error('端口必须再1-65535之间'));
                else
                    callback();
            };
            let checkDstPort = (rule, value, callback) => {
                if (!value)
                    callback(new Error('请输入监听端口'));
                else if (!util.PATTERN.PORT.test(value))
                    callback(new Error('端口必须再1-65535之间'));
                else
                    callback();
            };
            return {
                cannRuleList: [],
                selection: [],
                page: {
                    curPage: 1,
                    limit: 15,
                    total: 0
                },
                editTitle: '',
                formVisible: false,
                connRule: {
                    rule_type: 'TCP',
                    direct: 1,
                    listen_addr: {id: 0},
                    listen_port: '',
                    dst_addr: '',
                    send_addr: {id: 0},
                    dst_port: '',
                    status: false
                },
                seedAddrs: [],
                appTypes: [
                    {value: 'TCP', name: 'TCP'},
                    {value: 'HTTP', name: 'HTTP'},
                ],
                listenAddrs: [],
                directions: [
                    {value: 1, name: '内端→外端'},
                    {value: 2, name: '外端→内端'}
                ],
                editFormRules: {
                    listen_port: [{validator: checkListenPort, trigger: 'blur'}],
                    dst_addr: [{required: true, message: '请输入目的地址', trigger: 'blur'}],
                    dst_port: [{validator: checkDstPort, trigger: 'blur'}]
                },
                listenId: 1,
                sendId: 2,
                swichMsg: ''
            }
        },
        methods: {
            loadConnRules: function (page) {
                let self = this;
                axios.post('/ship/node/connRuleList', {page: page, limit: self.page.limit}).then((res) => {
                    self.page.curPage = res.data.curPage;
                    self.page.total = res.data.total;
                    self.cannRuleList = res.data.data;
                }).catch((err) => {
                    util.dialog.notifyError(self, '数据加载失败');
                })
            },
            changeSwich: function (row) {
                let self = this;
                if (row.status) {
                    self.swichMsg = '启动成功';
                } else {
                    self.swichMsg = '停用成功';
                }
                axios.put('/ship/node/connRule/' + row.id + '/status?status=' + row.status).then((res) => {
                    util.dialog.notifySuccess(self, self.swichMsg);
                }).catch((err) => {
                    util.dialog.notifyError(self, '提交失败');
                })
            },
            //跳转页
            handleCurrentChange: function (val) {
                this.loadConnRules(val);
            },
            initAdd: function () {
                let self = this;
                self.editTitle = '新增';
                self.formVisible = true;
                self.connRule.id = undefined;
                axios.get('/ship/node/ipAddrList?nodeId=1').then((res) => {
                    self.listenAddrs = res.data;
                    if (self.listenAddrs.length > 0) {
                        self.connRule.listen_addr.id = self.listenAddrs[0].id;
                    }
                });
                axios.get('/ship/node/ipAddrList?nodeId=2').then((res) => {
                    self.seedAddrs = res.data;
                    if (self.seedAddrs.length > 0) {
                        self.connRule.send_addr.id = self.seedAddrs[0].id;
                    }
                });
                self.$refs.connRule.resetFields();
            },
            initMod: function () {
                let self = this;
                if (self.selection.length !== 1) {
                    util.dialog.notifyError(self, '请选择一条数据进行修改');
                    return false;
                }
                if (self.selection[0].status === true) {
                    util.dialog.notifyError(self, '该规则正在启用中，不能修改');
                    return false;
                }
                let id = self.selection[0].id;
                axios.get('/ship/node/connRule?id=' + id).then((res) => {
                    self.connRule = res.data;
                });
                self.formVisible = true;
                self.editTitle = '修改';
            },
            edit: function () {
                let self = this;
                if (self.connRule.id === undefined) {
                    self.createConnRule();
                } else {
                    self.modConnRule();
                }
            },
            createConnRule: function () {
                let self = this;
                self.$refs.connRule.validate((valid) => {
                    if (valid) {
                        axios.post('/ship/node/connRule', self.connRule).then((res) => {
                            let data = res.data;
                            if (data.flag === '0') {
                                util.dialog.notifySuccess(self, data.msg);
                                self.formVisible = false;
                                self.loadConnRules(self.page.curPage);
                            } else if (data.flag === '1') {
                                util.dialog.notifyError(self, data.msg);
                            }
                        }).catch((err) => {
                            util.dialog.notifyError(self, '添加失败');
                        })
                    }
                })
            },
            modConnRule: function () {
                let self = this;
                self.$refs.connRule.validate((valid) => {
                    if (valid) {
                        axios.put('/ship/node/connRule', self.connRule).then((res) => {
                            let data = res.data;
                            if (data.flag === '0') {
                                util.dialog.notifySuccess(self, data.msg);
                                self.formVisible = false;
                                self.loadConnRules(self.page.curPage);
                            } else if (data.flag === '1') {
                                util.dialog.notifyError(self, data.msg);
                            } else if (data.flag === '2') {
                                util.dialog.notifyError(self, data.msg);
                            } else if (data.flag === '3') {
                                util.dialog.notifyError(self, data.msg);
                            }
                        }).catch((err) => {
                            util.dialog.notifyError(self, '修改失败');
                        })
                    }
                })
            },
            del: function () {
                let self = this;
                if (self.selection.length === 0) {
                    util.dialog.notifyError(self, '至少一条规则删除');
                    return false;
                }
                let status = _.every(self.selection, {status: false});
                if (!status) {
                    util.dialog.notifyError(self, '规则在启动中不能删除');
                    return false;
                }
                self.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _.forEach(self.selection, (s) => {
                        axios.delete('/ship/node/connRule?id=' + s.id).then((res) => {
                            let data = res.data;
                            if (data.flag === '0') {
                                util.dialog.notifySuccess(self, data.msg);
                                self.loadConnRules(self.page.curPage);
                            } else if (data.flag === '1') {
                                util.dialog.notifyError(self, data.msg);
                                self.loadConnRules(self.page.curPage);
                            } else if (data.flag === '2') {
                                util.dialog.notifyError(self, data.msg);
                                self.loadConnRules(self.page.curPage);
                            }
                        }).catch((err) => {
                            util.dialog.notifyError(self, '删除失败');
                            self.loadConnRules(self.page.curPage);
                        });
                    })
                }).catch(() => {
                    util.dialog.notifyInfo(self, '已取消删除');
                });
            },
            //获取checkbox
            handleSelectionChange: function (val) {
                this.selection = val;
            },
            changeDirect: function () {
                let self = this;
                if (self.connRule.direct === 1) {
                    self.listenId = 1;
                    self.sendId = 2;
                } else {
                    self.listenId = 2;
                    self.sendId = 1;
                }
                axios.get('/ship/node/ipAddrList?nodeId=' + self.listenId).then((res) => {
                    self.listenAddrs = res.data;
                    if (self.listenAddrs.length > 0) {
                        self.connRule.listen_addr.id = self.listenAddrs[0].id;
                    }
                });
                axios.get('/ship/node/ipAddrList?nodeId=' + self.sendId).then((res) => {
                    self.seedAddrs = res.data;
                    if (self.seedAddrs.length > 0) {
                        self.connRule.send_addr.id = self.seedAddrs[0].id;
                    }
                });
            },
            directFormat: function (row, column) {
                return column !== 1 ? '内端→外端' : '外端→内端';
            },
            lisAddrFormat: function (row, column) {
                return row.listen_addr.ip;
            },
            sendAddrFormat: function (row) {
                return row.send_addr.ip;
            }
        },
        mounted: function () {
            let self = this;
            self.$nextTick(function () {
                self.loadConnRules(1);
            })
        },
    }
</script>

<style>
    .ship-coon-btn {
        margin-bottom: 10px;
        float: right;
    }
</style>