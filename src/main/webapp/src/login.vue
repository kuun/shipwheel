<template>
    <div>
        <div class="main-login">
            <div class="container">
                <center>
                    <div class="middle">
                        <div id="login">
                            <form>
                                <fieldset class="clearfix">
                                    <p>
                                        <span class="fa fa-user ship-fa"></span>
                                        <input type="text" required v-model="name">
                                    </p>
                                    <!-- JS because of IE support; better: placeholder="Username" -->
                                    <p>
                                        <span class="fa fa-lock ship-fa"></span>
                                        <input type="password" required v-model="password">
                                    </p>
                                    <!-- JS because of IE support; better: placeholder="Password" -->

                                    <div>
                                        <button class="btn btn-success btn-width" type="button" id="clickLogin" @click="login">Login</button>
                                        <!--<span style="width:48%; text-align:left;  display: inline-block;">
                                        </span>
                                        <span style="width:50%; text-align:right;  display: inline-block;">

                                        </span>-->
                                    </div>
                                </fieldset>
                                <div class="clearfix"></div>
                            </form>

                            <div class="clearfix"></div>

                        </div> <!-- end login -->
                        <div class="logo">LOGO

                    <div class="clearfix"></div>
                        </div>

                    </div>
                </center>
            </div>

        </div>
    </div>
</template>
<script>
    import util from './js/util'
    import crypto_js from 'crypto-js';
    import $ from 'jquery';
    import axios from 'axios';
    import './css/login.css'
    import 'bootstrap/dist/css/bootstrap.min.css'
    $(document).keypress((event) => {
        //按回车键提交
        if (event.keyCode === 13) {
            $("#clickLogin").click();
        }
    });
    export default {
        data () {
            return {
                name: '',
                password: '',
            }
        },
        methods: {
            login: function () {
                let _this = this;
                if (_this.name === '') {
                    util.dialog.notifyError(_this, '用户名不能为空');
                    return false;
                }
                if (_this.password === ''){
                    if (_this.name === '') {
                        util.dialog.notifyError(_this, '密码不能为空');
                        return false;
                    }
                }
                let data = {
                    name:_this.name,
                    password: crypto_js.SHA256(_this.password).toString(crypto_js.enc.Hex)
                };
                axios.post('/ship/login', data).then((res) => {
                    if (res.data.flag === "0") {
                        sessionStorage.setItem('user', JSON.stringify(data));
                        _this.$router.push({path: '/dashboard'})
                    } else if (res.data.flag === "1") {
                        util.dialog.notifyError(_this, res.data.msg);
                        _this.name = '';
                        _this.password = '';
                    } else {
                        util.dialog.notifyError(_this, '登陆失败');
                        _this.name = '';
                        _this.password = '';
                    }
                });

            }
        }
    }
</script>
<style>
    .ship-fa {
        border-right: solid 1px #e0e2e4;
    }
</style>