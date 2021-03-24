import Vue from 'vue'
import Router from 'vue-router'
import ServiceCustomer from '@/components/ServiceCustomer'
import ServiceAdmin from '@/components/ServiceAdmin'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'ServiceCustomer',
      component: ServiceCustomer
    },
    {
      path: '/createService',
      name: 'ServiceAdmin',
      component: ServiceAdmin
    }
  ]
})
