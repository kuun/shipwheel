<template>
    <div id="app">
        <div class="header-bar"></div>
        <div class="nav-bar"></div>
        <div class="side-bar">
            <el-row class="tac">
                <el-col>
                    <el-menu default-active="1" class="el-menu-vertical-demo" theme="dark" :uniqueOpened="true">
                        <div v-for="menu in menus">
                            <el-submenu :index="menu.index" v-if="!menu.leaf">
                                <template slot="title">
                                    <i :class="['fa', menu.icon]"></i>
                                    <span class="ship-span">{{ menu.name}}</span>
                                </template>
                                <el-menu-item :index="subMenu.index"
                                              v-for="subMenu in menu.subMenus">
                                    <i :class="['fa', subMenu.icon]"></i>
                                    <span class="ship-span">{{ subMenu.name }}</span>
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item :index="menu.subMenus[0].index" v-if="menu.leaf">
                                <i :class="['fa', menu.icon]"></i>
                                <span class="ship-span">{{ menu.subMenus[0].name }}</span>
                            </el-menu-item>
                        </div>
                    </el-menu>
                </el-col>
            </el-row>
        </div>
        <div class="content-view">
            <transition name="fade" mode="out-in">
                <!--<router-view></router-view>-->
            </transition>
        </div>
        <div class="footer"></div>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                menus: [{
                        name: '首页',
                        icon: 'fa-home',
                        index: '2',
                        leaf: true,
                        subMenus: [
                            {name: '首页', index: '1-1', link: ''}
                        ]
                    }, {
                        name: '用户管理',
                        icon: 'fa-user',
                        index: '2',
                        leaf: true,
                        subMenus: [
                            {name: '用户管理', index: '2-1', link: ''}
                        ]
                    }, {
                        name: '网络管理',
                        icon: 'fa-internet-explorer',
                        index: '3',
                        subMenus: [
                            {name: 'IP地址管理', icon: 'fa-star-o', index: '3-1'},
                            {name: '路由管理', icon: 'fa-star-o', index: '3-2'},
                            {name: 'dns', icon: 'fa-star-o', index: '3-3'}
                        ]
                    }, {
                        name: '连通规则',
                        icon: 'fa-exchange',
                        index: '4',
                        leaf: true,
                        subMenus: [
                            {name: '连通规则', icon: 'fa-exchange', index: '4-1'}
                        ]
                    }, {
                        name: '设备管理',
                        icon: 'fa-cog',
                        index: '5',
                        subMenus: [
                            {name: '系统设置', icon: 'fa-cog', index: '5-1'}
                        ]
                    }
                ],
            }
        },

        methods: {
            changeRouter: function (subMenu) {
                router.push(subMenu.link);
            }
        }
    }
</script>

<style>
    body {
        font-family: Helvetica, sans-serif;
        margin: 0;
    }

    .header-bar {
        position: fixed;
        background-color: #EDEDED;
        height: 50px;
        width: 100%;
        margin-left: 230px;
    }

    .nav-bar {
        position: fixed;
        width: 230px;
        height: 50px;
        background-color: rgb(50, 65, 87);
    }

    .side-bar {
        position: fixed;
        background-color: rgb(50, 65, 87);
        margin-top: 50px;
        width: 230px;
        height: 100%;
    }

    .footer {
        position: fixed;
        height: 30px;
        margin-left: 230px;
        bottom: 0;
        background-color: #EDEDED;
        width: 100%;
    }

    .el-menu-item {
        font-size: 16px;
    }

    .el-submenu__title {
        font-size: 16px;
    }

    .ship-span {
        margin-left: 5px;
    }

    .content-view {
        position: fixed;
        margin-left: 230px;
        margin-top: 50px;
        margin-bottom: 25px;
        height: 100%;
        width: 100%;
    }
</style>
