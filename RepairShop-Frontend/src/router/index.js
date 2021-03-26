import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import CustomerRegister from "../components/CustomerRegister";


Vue.use(Router)

export default new Router({
  routes: [
    {path: '/',
      name: 'Hello',
      component: Hello},

    { path: '/register', component: CustomerRegister }

  ]
})
