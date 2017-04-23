/**
 * Created by wx on 2017/4/22.
 */
import Home from './Home.vue';
import Dashboard from "./vue/Dashboard.vue";

let routes = [
    {
        path: '/',
        component: Home,
        name: '首页',
        leaf: true,
        icon: 'fa-home',
        subMenus: [
            {name: '首页', path: '/dashboard', component: Dashboard}
        ]
    }, {
        path: '/',
        component: Home,
        name: '用户管理',
        leaf: true,
        icon: 'fa-user',
        subMenus: [
            {name: '用户管理', path: '/user', component: ''}
        ]
    }, {
        path: '/',
        component: Home,
        name: '网络管理',
        icon: 'fa-internet-explorer',
        subMenus: [
            {name: 'IP地址管理', path: '/ipAddr', component: '', icon: 'fa-star-o'},
            {name: '路由管理', path: '/route', component: '', icon: 'fa-star-o'},
            {name: 'dns', path: '/dns', component: '', icon: 'fa-star-o'}
        ]
    }, {
        path: '/',
        component: Home,
        name: '连通规则',
        leaf: true,
        icon: 'fa-exchange',
        subMenus: [
            {name: '连通规则', path: '/connRule', component: ''}
        ]
    }, {
        path: '/',
        component: Home,
        name: '设备管理',
        icon: 'fa-cog',
        subMenus: [
            {name: '系统设置', path: '/system', component: '', icon: 'fa-cog'}
        ]
    },
];

export default routes;
