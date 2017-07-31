<template>
    <div>
        <div class="ship-top">
            <span class="ship-span">DNS管理</span>
        </div>
        <div class="ship-line"></div>
        <div class="ship-panel-left"><p></p></div>
        <div class="ship-panel-center">
            <el-tabs v-model="nodeId" type="card" @tab-click="changeHandle(nodeId)">
                <el-tab-pane label="内端机" name="1">
                    <div class="ship-form">
                        <el-form :model="inside" :rules="editFormInside" ref="inside" label-width="50px" class="demo-ruleForm">
                            <el-form-item label="dns" prop="insideDns">
                                <el-input v-model="inside.insideDns" auto-complete="off" placeholder="请输入内端机dns"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="saveInsideDns">提交</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="外端机" name="2">
                    <div class="ship-form">
                        <el-form :model="outside" :rules="editFormOutside" ref="outside" label-width="50px" class="demo-ruleForm">
                            <el-form-item label="dns" prop="outsideDns">
                                <el-input v-model="outside.outsideDns" auto-complete="off" placeholder="请输入外端机dns"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="saveOutsideDns">提交</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
        <div class="ship-panel-right"><p></p></div>
    </div>
</template>
<script>
    import util from '../js/util';
    export default {
        data () {
            let checkDns = (rule, value, callback) => {
                if (!value)
                    callback(new Error("请输入dns"));
                else if (!util.PATTERN.IP.test(value))
                    callback(new Error("dns不合法"));
                else
                    callback();
            };
            return {
                menus: [
                    {id: '1', name: '内端机'},
                    {id: '2', name: '外端机'}
                ],
                nodeId: '1',
                inside: {
                    outsideDns: ''
                },
                outside: {
                    insideDns: ''
                },
                editFormInside: {
                    insideDns: [{validator: checkDns, trigger: 'blur'}]
                },
                editFormOutside: {
                    outsideDns: [{validator: checkDns, trigger: 'blur'}]
                }
            }
        },
        methods: {
            changeHandle: function (nodeId) {

            },
            //修改内端机dns
            saveInsideDns: function () {
                let self = this;
                self.$refs.inside.validate((valid) => {
                    if (valid) {
                        console.log(self.inside.insideDns);
                    }
                })
            },
            //修改外端机dns
            saveOutsideDns: function () {
                let self = this;
                self.$refs.outside.validate((valid) => {
                    if (valid) {
                        console.log(self.outside.outsideDns);
                    }
                })
            }
        },
        mounted: function () {
            let self = this;
            self.$nextTick(function () {
                self.changeHandle(1);
            })
        },
    }
</script>