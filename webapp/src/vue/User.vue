<template>
    <div class="">
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
    </div>
</template>
<script>
    import $ from 'jquery';
    let getHeight = () => {
            return $(window).height();
        };
    export default {
        data () {
            return {
                userList: [
                    {id: 1, name: 'admin'},
                    {id: 2, name: 'secrecy'},
                    {id: 3, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                    {id: 4, name: 'secrecy'},
                ],
                checkList: [],
                tatal: 1,
                currentPage: 1
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
            }
        },

        mounted: function () {
            let self = this;
            self.$nextTick(function () {
                self.tatal = self.userList.length;
            })
        }
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
    .ship-table {
        margin-right: 230px;
    }
    .ship-page {
        float: right;
        margin-right: 220px;
        margin-top: 10px;
    }
</style>