import axios from "axios";
import Router from "../router/index";
import AuthHeader from "./AuthHeader";
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';


var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

Vue.use(VueToast);

function CustomerDto(email, password) {
  this.email = email;
  this.password = password;
}

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
      pageTitle: "Welcome to RepairShop, your satisfaction is our top concern"
    };
  },


  methods: {
    loginUser: function(email, password, personType) {
      if (personType == "Customer") {
        this.loginCustomer(email, password);
        this.currentUser.email= email;
        this.currentUser.password= password;
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

    loginCustomer: function(email, password) {
      const customerDto = new CustomerDto(email, password);
      AXIOS.post("person/customer/login", customerDto)
        .then(response => {
//          console.log(response.data);
//          console.log(response.data);
//          this.currentUser.username = response.data.username;
//          this.currentUser.email = response.data.email;
//          this.currentUser.password = response.data.password;
//          this.currentUser.personType = response.data.personType;
//          console.log(this.currentUser.username);
//          console.log(this.currentUser.email);
//          console.log(this.currentUser.password);
//          console.log(this.currentUser.personType);
          localStorage.setItem('savedCustomerEmail', response.data.email);
          localStorage.setItem('savedCustomerName', response.data.username);
          localStorage.setItem('savedCustomerPassword', response.data.password);
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
          this.goToTechinicianHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.error = errorMsg;
          Vue.$toast.error(e.response.data, {
          duration: 6000});

        });
    },
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
          this.goToAdminHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.error = errorMsg;
          Vue.$toast.error(e.response.data, {
          duration: 6000});
        });
    },
    goToCreateAccountPage: function() {
      Router.push({
        path: "/CustomerRegisterPage",
        name: "CustomerRegisterPage"
      });
    },
    goToCustomerHomePage: function() {
      Router.push({
        path: "/CustomerHomePage",
        name: "CustomerHomePage"
      });
    },
    goToAdminHomePage: function() {
      Router.push({
        path: "/AdminHomePage",
        name: "AdminHomePage"
      });
    },
    goToTechinicianHomePage: function() {
      Router.push({
        path: "/TechnicianHomePage",
        name: "TechnicianHomePage"
      });
    }
  }
};
