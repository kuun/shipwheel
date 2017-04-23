<template>
    <div id="app">
        <transition name="fade" mode="out-in">
            <router-view></router-view>
        </transition>
    </div>
</template>

<script>
    export default {
        name: 'app'
    }
</script>

<style>
    body {
        font-family: Helvetica, sans-serif;
        margin: 0;
    }

    #app {
        position: fixed;
        top: 0;
        bottom: 0;
        width: 100%;
    }
</style>
