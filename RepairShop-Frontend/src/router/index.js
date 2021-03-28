import Vue from "vue";
import Router from "vue-router";
import CustomerRegister from "../components/CustomerRegisterPage";
import CustomerHomePagePage from "../components/CustomerHomePage";
import CustomerAccountPage from "../components/CustomerAccountPage";
import LoginPage from "../components/LoginPage";
import AdminHomePage from "../components/AdminHomePage";
import AdminAccountPage from "../components/AdminAccountPage";
import TimeSlotPage from "../components/TimeSlotPage";
import ServicePage from "../components/ServicePage";
import AdminAppointmentPage from "../components/AdminAppointmentPage";


Vue.use(Router);

export default new Router({
  mode: "hash",
  routes: [
    {
      path: "/",
      name: "LoginPage",
      component: require("../components/LoginPage.vue").default
    },

    {
      path: "/CustomerRegisterPage",
      name: "CustomerRegisterPage",
      component: require("../components/CustomerRegisterPage.vue").default
    },

    {
      path: "/CustomerHomePage",
      name: "CustomerHomePage",
      component: require("../components/CustomerHomePage.vue").default
    },

    {
      path: "/CustomerAccountPage",
      name: "CustomerAccountPage",
      component: require("../components/CustomerAccountPage.vue").default
    },
    {
      path: "/BusinessPage",
      name: "BusinessPage",
      component: require("../components/BusinessPage.vue").default
    },
    {
      path: "/AdminHomePage",
      name: "AdminHomePage",
      component: require("../components/AdminHomePage.vue").default
    },
    {
      path: "/AdminAccountPage",
      name: "AdminAccountPage",
      component: require("../components/AdminAccountPage.vue").default
    },
    {
      path: "/TimeSlotPage",
      name: "TimeSlotPage",
      component: require("../components/TimeSlotPage.vue").default
    },
    {
      path: "/ServicePage",
      name: "ServicePage",
      component: require("../components/ServicePage.vue").default
    },
    {
      path: "/AdminAppointmentPage",
      name: "AdminAppointmentPage",
      component: require("../components/AdminAppointmentPage.vue").default
    },
    {
      path: "/CustomerAppointmentPage",
      name: "CustomerAppointmentPage",
      component: require("../components/CustomerAppointmentPage.vue").default    
    }
  ]
});
