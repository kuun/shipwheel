<template>
    <div class="content">
        <div class="header-bar"></div>
        <div class="nav-bar"></div>
        <div class="side-bar">
            <el-row class="tac">
                <el-col>
                    <el-menu :default-active="$route.path" class="el-menu-vertical-demo" theme="dark" :uniqueOpened="true" :router="true">
                        <template v-for="(item, index) in $router.options.routes">
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