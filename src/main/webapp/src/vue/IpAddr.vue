<template>
    <div>
        <div class="ship-top">
            <span class="ship-span">IP地址管理</span>
        </div>
        <div class="ship-line"></div>
        <!--<el-tabs v-model="nodeId" type="card" @tab-click="changeHandle">
            <el-tab-pane v-for="menu in menus" :label="menu.name" :name="menu.id"></el-tab-pane>
        </el-tabs>-->

        <div class="view">
            <div class="ship-btn">
                <el-button-group>
                    <el-button @click.native="initAdd"><i class="fa fa-plus" aria-hidden="true"></i></el-button>
                    <el-button @click.native="initMod"><i class="fa fa-pencil" aria-hidden="true"></i></el-button>
                    <el-button @click.native="del"><i class="fa fa-trash" aria-hidden="true"></i></el-button>
                    <el-button @click.native="loadIpAddr(nodeId, 1)"><i class="fa fa-refresh" aria-hidden="true"></i></el-button>
                </el-button-group>
                <div class="ship-tags">
                    <el-tabs v-model="nodeId" type="card" @tab-click="changeHandle">
                        <el-tab-pane v-for="menu in menus" :key="menu.id" :label="menu.name" :name="menu.id"></el-tab-pane>
                    </el-tabs>
                </div>
            </div>
            <div class="ship-table">
                <el-table :data="ipAddrList" border tooltip-effect="dark" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column prop="id" label="ID" width="100" sortable></el-table-column>
                    <el-table-column prop="ip" label="IP"></el-table-column>
                    <el-table-column prop="mask" label="掩码"></el-table-column>
                    <el-table-column prop="ifaceName" label="网卡名称"></el-table-column>
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
        <el-dialog :title="editTitle" v-model="formVisible" :close-on-click-modal="false" size="tiny">
            <el-form label-width="100px" :model="ipAddr" :rules="editFormRules" ref="ipAddr">
                <el-form-item label="IP" prop="ip">
                    <el-input v-model="ipAddr.ip" auto-complete="off" placeholder="请输入IP地址"></el-input>
                </el-form-item>
                <el-form-item label="掩码" prop="mask">
                    <el-input v-model="ipAddr.mask" auto-complete="off" placeholder="请输入掩码"></el-input>
                </el-form-item>
                <el-form-item label="网卡选择" :style="{display: selectDisplay}">
                    <el-select class="select" v-model="ipAddr.iface_id" placeholder="请选择网卡">
                        <el-option v-for="iface in ifaces" :key="iface.id" :value="iface.id" :label="iface.name"></el-option>
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
            let checkIp = (rule, value, callback) => {
                if (!value) {
                    callback(new Error('IP不能为空'));
                } else if (!util.PATTERN.IP.test(value)) {
                    callback(new Error('IP不合法'));
                } else {
                    callback();
                }
            };
            let checkMask = (rule, value, callback) => {
                if (!value) {
                    callback(new Error('掩码不能为空'));
                } else if (!util.PATTERN.MASK.test(value)) {
                    callback(new Error('掩码不合法'));
                } else {
                    callback();
                }
            };
            return {
                ipAddrList: [],
                menus: [
                    {id: '1', name: '内端机'},
                    {id: '2', name: '外端机'}
                ],
                page: {
                    curPage: 1,
                    total: 0,
                    limit: 15
                },

                editTitle: '',
                formVisible: false,
                editFormRules: {
                    ip: [{validator: checkIp, trigger: 'blur'}],
                    mask: [{validator: checkMask, trigger: 'blur'}]
                },
                ifaces: [],
                ipAddr: {
                    ip: '',
                    mask: '',
                    iface_id: 1
                },
                nodeId: '1',
                selection: [],
                selectDisplay: 'none',
            }
        },
        methods: {
            //获取网卡IP
            loadIpAddr: function (nodeId, page) {
                let self = this;
                let data = {
                    page: page,
                    limit: self.page.limit
                };
                axios.post('/ship/engine/ipAddrList?nodeId=' + nodeId, data).then((res) => {
                    self.page.curPage = res.data.curPage;
                    self.page.total = res.data.total;
                    self.ipAddrList = res.data.data;
                }).catch((err) => {
                    util.dialog.notifyError(self, "数据加载失败");
                })

            },
            loadIfaces: function (nodeId) {
                let self = this;
                axios.get('/ship/engine/iface?nodeId=' + nodeId).then((res) => {
                    self.ifaces = res.data;
                    self.ipAddr.iface_id = self.ifaces[0].id;
                });
            },
            //选择内外端
            changeHandle: function (tab, event) {
                let self = this;
                self.nodeId = tab.name;
                self.loadIpAddr(self.nodeId, 1);
                self.loadIfaces(self.nodeId);
            },
            //获取选择
            handleSelectionChange: function (val) {
                this.selection = val;
            },
            //跳转页
            handleCurrentChange: function (val) {
                this.loadIpAddr(this.nodeId, val);
            },
            initAdd: function () {
                let self = this;
                self.formVisible = true;
                self.editTitle = '新增';
                self.ipAddr.id = undefined;
                self.selectDisplay = 'block';
                self.$refs.ipAddr.resetFields();
            },
            initMod: function () {
                let self = this;
                if (self.selection.length !== 1) {
                    util.dialog.notifyError(self, "请选择一条内容修改");
                    return false;
                }
                axios.get('/ship/engine/ipAddr?id=' + self.selection[0].id).then((res) => {
                    self.ipAddr = res.data;
                });
                self.formVisible = true;
                self.editTitle = '修改';
                self.selectDisplay = 'none';
            },
            edit: function () {
                let self = this;
                if (self.ipAddr.id === undefined) {
                    self.createIpAddr();
                } else {
                    self.modifyIpAddr();
                }
            },
            createIpAddr: function () {
                let self = this;
                self.$refs.ipAddr.validate((valid) => {
                    if (valid) {
                        self.ipAddr.node_id = self.nodeId;
                        axios.post('/ship/engine/ipAddr', self.ipAddr).then((res) => {
                            let data = res.data;
                            if (data.flag === '0') {
                                util.dialog.notifySuccess(self, data.msg);
                                self.formVisible = false;
                                self.loadIpAddr(self.nodeId, self.page.curPage);
                            } else if (data.flag === '1') {
                                util.dialog.notifyError(self, data.msg);
                            } else if (data.flag === '2') {
                                util.dialog.notifyError(self, data.msg);
                            } else {
                                util.dialog.notifyError(self, data.msg);
                            }
                        }).catch((err) => {
                            util.dialog.notifyError(self, '添加失败');
                        })
                    }
                })
            },
            modifyIpAddr: function () {
                let self = this;
                self.$refs.ipAddr.validate((valid) => {
                    if (valid) {
                        axios.put('/ship/engine/ipAddr', self.ipAddr).then((res) => {
                            let data = res.data;
                            if (data.flag === '0') {
                                util.dialog.notifySuccess(self, data.msg);
                                self.formVisible = false;
                                self.loadIpAddr(self.nodeId, self.page.curPage);
                            } else if (data.flag === '1') {
                                util.dialog.notifyError(self, data.msg);
                            } else if (data.flag === '2') {
                                util.dialog.notifyError(self, data.msg);
                            } else if (data.flag === '3') {
                                util.dialog.notifyError(self, data.msg);
                            } else {
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
                    util.dialog.notifyError(self, "至少选择一条内容删除");
                    return false;
                }
                self.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _.forEach(self.selection, (s) => {
                        axios.delete('/ship/engine/ipAddr?id=' + s.id).then((res) => {
                            let data = res.data;
                            if (data.flag === '0') {
                                util.dialog.notifySuccess(self, data.msg);
                                self.loadIpAddr(self.nodeId, self.page.curPage);
                            } else if (data.flag === '1') {
                                util.dialog.notifyError(self, data.msg);
                            } else {
                                util.dialog.notifyError(self, data.msg);
                            }
                        }).catch((err) => {
                            util.dialog.notifyError(self, '删除失败');
                            self.loadIpAddr(self.nodeId, self.page.curPage);
                        });
                    })
                }).catch(() => {
                    util.dialog.notifyInfo(self, '已取消删除');
                });

            }
        },
        mounted: function () {
            let self = this;
            self.$nextTick(function () {
                self.loadIpAddr(1, 1);
                self.loadIfaces(1);
            })
        },
    }
</script>
<style>
    .el-select {
        display: block;
    }

    .ship-left {
        width: 20%;
        float: left;
    }

    .ship-right {
        width: 80%;
        float: right;
    }
</style>
