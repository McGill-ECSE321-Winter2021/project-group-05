import Vue from "vue";
import Router from "vue-router";
import Hello from '../components/Hello';
import CustomerRegister from '../components/CustomerRegister';


Vue.use(Router);

export default new Router({
  mode: "hash",
  routes: [
    { path: '/', name: 'Hello', component: require('../components/Hello.vue').default },

    { path: '/register', name:'CustomerRegister',component: require('../components/CustomerRegister.vue').default }
  ]
});
