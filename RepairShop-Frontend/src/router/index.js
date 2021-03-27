import Vue from 'vue'
import Router from 'vue-router'
import ServiceCustomer from '@/components/ServiceCustomer'
import CreateService from '@/components/CreateService'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/customer',
      name: 'ServiceCustomer',
      component: ServiceCustomer
    },
    {
      path: '/',
      name: 'CreateService',
      component: CreateServiceAdmin
    }
  ]
})
