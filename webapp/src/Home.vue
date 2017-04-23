<template>
    <div class="content">
        <div class="header-bar"></div>
        <div class="nav-bar"></div>
        <div class="side-bar">
            <el-row class="tac">
                <el-col>
                    <el-menu default-active="$route.path" class="el-menu-vertical-demo" theme="dark" uniqueOpened router>
                        <div v-for="(menu, index) in $router.options.routes">
                            <el-submenu :index="index + ''" v-if="!menu.leaf">
                                <template slot="title">
                                    <i :class="['fa', menu.icon]"></i>
                                    <span class="ship-span">{{ menu.name}}</span>
                                </template>
                                <el-menu-item v-for="subMenu in menu.subMenus" :index="subMenu.path" :key="subMenu.path">
                                    <i :class="['fa', subMenu.icon]"></i>
                                    <span class="ship-span">{{ subMenu.name }}</span>
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item :index="menu.subMenus[0].path" v-if="menu.leaf">
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
                <router-view></router-view>
            </transition>
        </div>
        <div class="footer"></div>
    </div>
</template>
<script>
    export default {
        name: 'home',
        data () {
            return {}
        }
    }
</script>

<style>
    .content {
        position: fixed;
        width: 100%;
        height: 100%;
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