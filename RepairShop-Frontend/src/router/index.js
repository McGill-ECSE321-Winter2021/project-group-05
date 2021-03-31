import Vue from "vue";
import Router from "vue-router";
import CustomerRegister from "../components/CustomerRegisterPage";
import CustomerHomePagePage from "../components/CustomerHomePage";
import CustomerAccountPage from "../components/CustomerAccountPage";
import LoginPage from "../components/LoginPage";
import AdminHomePage from "../components/AdminHomePage";
import AdminAccountPage from "../components/AdminAccountPage";
import TimeSlotPage from "../components/TimeSlotPage";
import ServicePageAdmin from "../components/ServicePageAdmin";
import AdminAppointmentPage from "../components/AdminAppointmentPage";
import ServiceCustomer from "../components/ServiceCustomer";
import TechnicianHomePage from "../components/TechnicianHomePage";
import TechnicianAppointmentPage from "../components/TechnicianAppointmentPage";
import TechnicianAccountPage from "../components/TechnicianAccountPage";


Vue.use(Router);

export default new Router({
  mode: "hash",
  routes: [
    {
      path: "/",
      name: "CustomerHomePage",
      component: require("../components/CustomerHomePage.vue").default

    },

    {
      path: "/CustomerRegisterPage",
      name: "CustomerRegisterPage",
      component: require("../components/CustomerRegisterPage.vue").default
    },

    {
      path: "/LoginPage",
      name: "LoginPage",
      component: require("../components/LoginPage.vue").default

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
      path: "/ServicePageAdmin",
      name: "ServicePageAdmin",
      component: require("../components/ServicePageAdmin.vue").default
    },
    {
      path: "/ServiceCustomer",
      name: "ServiceCustomer",
      component: require("../components/ServiceCustomer.vue").default
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
    },
    {
      path: "/TechnicianHomePage",
      name: "TechnicianHomePage",
      component: require("../components/TechnicianHomePage.vue").default
    },
    {
      path: "/TechnicianAppointmentPage",
      name: "TechnicianAppointmentPage",
      component: require("../components/TechnicianAppointmentPage.vue").default
    },
    {
      path: "/TechnicianAccountPage",
      name: "TechnicianAccountPage",
      component: require("../components/TechnicianAccountPage.vue").default

    }
  ]
});
