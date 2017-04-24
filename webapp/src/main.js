import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import 'font-awesome/css/font-awesome.css'
import App from './App.vue'
import VueRouter from 'vue-router'
import routes from './routes'
Vue.use(ElementUI);
Vue.use(VueRouter);


const router = new VueRouter({routes});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
