/**
 * Created by wx on 2017/4/22.
 */
import Login from './login.vue'
import Dashboard from "./vue/Dashboard.vue";
import Home from "./Home.vue";
import User from "./vue/User.vue";
import IpAddr from './vue/IpAddr.vue';
import Route from './vue/Route.vue';
import Dns from './vue/Dns.vue';
import ConnRule from './vue/ConnRule.vue';
import System from './vue/System.vue';

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    }, {
        path: '/',
        component: Home,
        icon: 'fa-home',
        leaf: true,
        children: [
            {path: '/dashboard', component: Dashboard, name: '首页',}
        ]
    },/* {
        path: '/',
        component: Home,
        leaf: true,
        icon: 'fa-user',
        children: [
            {path: '/user', component: User, name: '用户管理',}
        ]
    },*/ {
        path: '/',
        component: Home,
        name: '网络管理',
        icon: 'fa-internet-explorer',
        children: [
            {path: '/ipAddr', component: IpAddr, name: 'IP地址管理', icon: 'fa-star-o'},
            {path: '/route', component: Route, name: '路由管理', icon: 'fa-star-o'},
            /*{path: '/dns', component: Dns, name: 'dns配置', icon: 'fa-star-o'},*/
        ]
    }, {
        path: '/',
        component: Home,
        leaf: true,
        icon: 'fa-exchange',
        children: [
            {path: '/connRule', component: ConnRule, name: '连通规则'}
        ]
    }, {
        path: '/',
        component: Home,
        name: '设备管理',
        icon: 'fa-cog',
        children: [
            {path: '/system', component: System, name: '系统设置', icon: 'fa-cog'}
        ]
    }
];

export default routes;
