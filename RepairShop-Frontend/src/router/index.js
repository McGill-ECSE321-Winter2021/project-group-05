import Vue from 'vue'
import Router from 'vue-router'
import ServiceCustomer from '@/components/ServiceCustomer'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'ServiceCustomer',
      component: ServiceCustomer
    }
  ]
})
