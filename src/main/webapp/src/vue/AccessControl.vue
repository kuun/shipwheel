<template>
    <div>
        <el-row :gutter="10">
            <el-col :lg="4">
                <div class="ship-top">
                    <span class="ship-span">访问控制类型</span>
                </div>
                <div class="ship-control-line"></div>
                <el-tree
                        :data="data"
                        :props="defaultProps"
                        node-key="id"
                        :default-expanded-keys="[1]"
                        accordion
                        highlight-current
                        @node-click="handleNodeClick">
                </el-tree>
            </el-col>
            <el-col :lg="10">
                <div class="ship-top">
                    <span class="ship-span">访问控制策略表</span>
                </div>
                <div class="ship-line"></div>
                <div class="ship-coon-btn">
                    <el-button-group>
                        <el-button @click.native="initAdd"><i class="fa fa-plus" aria-hidden="true"></i></el-button>
                        <el-button @click.native="initMod"><i class="fa fa-pencil" aria-hidden="true"></i></el-button>
                        <el-button @click.native="del"><i class="fa fa-trash" aria-hidden="true"></i></el-button>
                        <el-button @click.native="loadConnRules(1)"><i class="fa fa-refresh" aria-hidden="true"></i>
                        </el-button>
                    </el-button-group>
                </div>
                <div class="ship-control-table">
                    <el-table border tooltip-effect="dark" :data="controlList">
                        <el-table-column type="selection" width="55"></el-table-column>
                        <el-table-column prop="id" label="ID" width="100" sortable></el-table-column>
                        <el-table-column prop="controlType" label="访问控制类型"></el-table-column>
                        <el-table-column prop="status" label="关联规则">
                            <template scope="scope">
                                <el-button type="blue" size="small" @click="ruleShow">显示规则</el-button>
                                <el-button type="blue" size="small" @click="ruleHide">隐藏规则</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="block ship-page">
                        <el-pagination
                                @current-change="handleCurrentChange"
                                :current-page="controlPage.curPage"
                                :page-size="controlPage.limit"
                                :total="controlPage.total">
                        </el-pagination>
                    </div>
                </div>
            </el-col>
            <el-col :lg="10">
                <div class="ship-top">
                    <span class="ship-span">策略</span>
                </div>
                <div class="ship-line"></div>
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
                    <el-table :data="policyList" border tooltip-effect="dark">
                        <el-table-column type="selection" width="55"></el-table-column>
                        <el-table-column prop="id" label="ID" width="100" sortable></el-table-column>
                        <el-table-column prop="content" label="策略内容"></el-table-column>
                    </el-table>
                    <div class="block ship-page">
                        <el-pagination
                                :current-page="policy.curPage"
                                :page-size="policy.limit"
                                :total="policy.total">
                        </el-pagination>
                    </div>
                </div>
            </el-col>
            <el-col :lg="24" :style="{display: showRule}">
                <div class="ship-top">
                    <span class="ship-span">关联规则</span>
                </div>
                <div class="ship-line"></div>
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
                    </el-table>
                </div>
            </el-col>
        </el-row>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                showPolicy: 'none',
                showRule: 'none',
                controlPage: {
                    curPage: 1,
                    limit: 10,
                    total:0
                },
                policy: {
                    curPage: 1,
                    limit: 10,
                    total:0
                },
                cannRuleList: [],
                controlList: [{id: 1, controlType: '白名单'}],
                policyList: [{id: 1, content: '192.168.0.1/24'}],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                },
                data: [{
                    label: 'HTTP',
                    children: [{
                        id: 1,
                        label: 'HTTP URL',
                    }, {
                        id: 2,
                        label: 'HTTP请求方法',
                    }, {
                        id: 3,
                        label: 'HTTP响应内容类型',
                    }, {
                        id: 4,
                        label: 'HTTP响应内容敏感词',
                    }]
                }, {
                    label: '客户端地址',
                    children: [{
                        id: 5,
                        label: '客户端地址',
                    }]
                }]
            }
        },
        methods: {
            ruleShow: function () {
                this.showRule = 'block'
            },
            ruleHide: function () {
                this.showRule = 'none'
            },
            handleNodeClick: function (data) {
                //console.log(data)
            },
            handleSelectionChange: function () {
                
            },
            directFormat: function () {
                
            },
            lisAddrFormat: function () {
                
            },
            sendAddrFormat: function () {
                
            },
            handleCurrentChange: function () {
                
            }
        }
    }
</script>
<style>
    .ship-all {
        width: 100%;
        overflow-y: auto;
    }
    .ship-control-type {
        width: 200px;
        float: left;
    }
    .ship-control-line {
        margin-top: 10px;
    }
    .ship-control-list {
        width: 450px;
        float: left;
        margin-left: 10px;
    }
    .ship-control-content {
        width: auto;
        float: left;
        margin-left: 10px;
    }
    .ship-control-rule {
        width: 100%;
        float: left;
    }
</style>