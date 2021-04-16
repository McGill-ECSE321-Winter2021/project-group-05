//login page
import axios from "axios";
import Router from "../router/index";
import AuthHeader from "./AuthHeader";
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';
import CustomerHomePage from '@/components/CustomerHomePage';
import AdminHomePage from '@/components/AdminHomePage';
import TechnicianHomePage from '@/components/TechnicianHomePage';

//configuration
var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

Vue.use(VueToast);

//creates customer dto
function CustomerDto(email, password) {
  this.email = email;
  this.password = password;
}

//page view
export default {
  name: "LoginPage",
  components: {
    AuthHeader
  },
  data() {
    return {
      currentUser: {
        username: "",
        email: "",
        password: "",
        personType: ""
      },
      error: "",
      response: [],
      pageTitle: "Welcome to RepairShop, your satisfaction is our top concern",


    };
  },


  methods: {
    //login
    loginUser: function(email, password, personType) {

      if (personType == "Customer") {
        this.loginCustomer(email, password);
        return;

      }
      if (personType == "Technician") {
        this.loginTechnician(email, password);
        return;

      }
      if (personType == "Admin") {
        this.loginAdmin(email, password);
        return;

      }
      this.error = "You need to select a role";
      Vue.$toast.error('You need to select a role', {
      duration: 6000});
    },

    // login customer
    loginCustomer: function(email, password) {
      console.log("goto-->");
      const customerDto = new CustomerDto(email, password);
      AXIOS.post("person/customer/login", customerDto)
        .then(response => {

          localStorage.setItem('savedCustomerEmail', response.data.email);
          localStorage.setItem('savedCustomerName', response.data.username);
          localStorage.setItem('savedCustomerPassword', response.data.password);
          localStorage.setItem('loggedInEmail', response.data.email);
          this.error = "";
          this.goToCustomerHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.error = errorMsg;
          //alert("wrong email or wrong password");
          Vue.$toast.error('Wrong email/password combination', {
          duration: 6000});
        });
    },
    //login technician
    loginTechnician: function(email, password) {
      const customerDto = new CustomerDto(email, password);
      AXIOS.post("person/technician/login", customerDto)
        .then(response => {
          console.log(response.data);
          this.currentUser = response.data;
          this.error = "";
          localStorage.setItem('savedTechnicianEmail', response.data.email);
          localStorage.setItem('savedTechnicianName', response.data.username);
          localStorage.setItem('savedTechnicianPassword', response.data.password);
          localStorage.setItem('loggedInEmail', response.data.email);
          console.log("going in...");
          this.goToTechinicianHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.error = errorMsg;
          Vue.$toast.error(e.response.data, {
          duration: 6000});

        });
    },
    //login admin
    loginAdmin: function(email, password) {
      const customerDto = new CustomerDto(email, password);
      AXIOS.post("person/administrator/login", customerDto)
        .then(response => {
          console.log(response.data);
          this.currentUser = response.data;
          this.error = "";
          localStorage.setItem('savedAdminEmail', response.data.email);
          localStorage.setItem('savedAdminName', response.data.username);
          localStorage.setItem('savedAdminPassword', response.data.password);
          localStorage.setItem('loggedInEmail', response.data.email);
          this.goToAdminHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.error = errorMsg;
          Vue.$toast.error(e.response.data, {
          duration: 6000});
        });
    },
    //go to create account
    goToCreateAccountPage: function() {

      Router.push({
        path: "/CustomerRegisterPage",
        name: "CustomerRegisterPage"
      });
    },
    //go to home page (customer)
    goToCustomerHomePage: function() {
      Router.push({
        path: "/CustomerHomePage",
        name: "CustomerHomePage"
      });
    },
    //go to home page (admin)
    goToAdminHomePage: function() {
      Router.push({
        path: "/AdminHomePage",
        name: "AdminHomePage"
      });
    },
    //go to home page (technician)
    goToTechinicianHomePage: function() {
      Router.push({
        path: "/TechnicianHomePage",
        name: "TechnicianHomePage"
      });
    }
  }
};
