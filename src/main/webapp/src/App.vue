<template>
    <div id="app">
        <transition name="fade" mode="out-in">
            <router-view></router-view>
        </transition>
    </div>
</template>

<script>
    export default {
    }
</script>

<style>
    body {
        font-family: Helvetica, sans-serif;
        margin: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
    }

    #app {
        top: 0;
        bottom: 0;
        width: 100%;
        height: 100%;
    }
</style>
