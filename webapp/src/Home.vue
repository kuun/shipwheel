<template>
    <div class="">
        <div class="nav-bar"><span class="nav-span">SHIPWHEEL</span></div>
        <div class="header-bar">
            <div class="ship-drop">
                <el-dropdown trigger="click">
                    <span class="el-dropdown-link"><img class="ship-img" src="./assets/ico-y.png">admin</span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item><i class="fa fa-pencil fa-fw"></i>密码修改</el-dropdown-item>
                        <el-dropdown-item><i class="fa fa-sign-out fa-fw"></i>安全退出</el-dropdown-item>
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
            <div>
                <transition name="fade" mode="out-in">
                    <router-view class="content-view"></router-view>
                </transition>
            </div>
        </div>
        <div class="footer"></div>
    </div>
</template>
<script>
    export default {}
</script>

<style>
    .nav-span {
        margin-left: 20px;
    }

    .header-bar {
        position: fixed;
        background-color: #f3f8fc;
        height: 50px;
        width: 100%;
        left: 230px;
    }

    .nav-bar {
        position: fixed;
        width: 230px;
        height: 50px;
        line-height: 50px;
        color: #fff;
        font-size: 25px;
        background-color: rgb(50, 65, 87);
    }

    .side-bar {
        position: fixed;
        background-color: rgb(50, 65, 87);
        top: 50px;
        width: 230px;
        height: 100%;
    }

    .footer {
        position: fixed;
        height: 30px;
        left: 230px;
        bottom: 0;
        background-color: #f3f8fc;
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
        position: absolute;
        top: 50px;
        left: 230px;
        right: 0;
        bottom: 25px;
        overflow-y: auto;
    }

    .ship-drop {
        margin-top: 15px;
        float: right;
        margin-right: 235px;
    }

    .ship-img {
        margin-bottom: -5px;
        margin-right: 5px;
    }
</style>