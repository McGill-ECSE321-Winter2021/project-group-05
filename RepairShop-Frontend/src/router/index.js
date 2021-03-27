import Vue from "vue";
import Router from "vue-router";
import Hello from "../components/Hello";
import CustomerRegister from "../components/CustomerRegister";
import HomePage_Customer from "../components/HomePage_Customer";
import MyAccount from "../components/MyAccount";

Vue.use(Router);

export default new Router({
  mode: "hash",
  routes: [
    {
      path: "/",
      name: "Hello",
      component: require("../components/Hello.vue").default
    },

    {
      path: "/register",
      name: "CustomerRegister",
      component: require("../components/CustomerRegister.vue").default
    },

    {
      path: "/homePage_customer",
      name: "HomePage_Customer",
      component: require("../components/HomePage_Customer.vue").default
    },

    {
      path: "/myAccount",
      name: "MyAccount",
      component: require("../components/MyAccount.vue").default
    },
    {
      path: "/business",
      name: "BusinessPage",
      component: require("../components/BusinessPage").default
    }
  ]
});
