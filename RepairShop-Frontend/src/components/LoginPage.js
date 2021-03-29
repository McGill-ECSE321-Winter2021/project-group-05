import axios from "axios";
import Router from "../router/index";
import AuthHeader from "./AuthHeader";


var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

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
        email: "",
        password: "",
        personType: ""
      },
      error: "",
      response: [],
      pageTitle: "Welcome to repairshop, your satisfaction is our top concern"
    };
  },

  methods: {
    loginUser: function(email, password, personType) {
      if (personType == "Customer") {
        this.loginCustomer(email, password);
        this.currentUser.email= email;
        this.currentUser.password= password;
        console.log(this.currentUser.email);
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
    },

    loginCustomer: function(email, password) {
      const customerDto = new CustomerDto(email, password);
      AXIOS.post("person/customer/login", customerDto)
        .then(response => {
          console.log(response.data);
          this.currentUser = response.data;
          this.error = "";
          this.goToCustomerHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.error = errorMsg;
          alert("wrong email or wrong password");
        });
    },
    loginTechnician: function(email, password) {
      const customerDto = new CustomerDto(email, password);
      AXIOS.post("person/technician/login", customerDto)
        .then(response => {
          console.log(response.data);
          this.currentUser = response.data;
          this.error = "";
          this.goToTechinicianHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.error = errorMsg;
        });
    },
    loginAdmin: function(email, password) {
      const customerDto = new CustomerDto(email, password);
      AXIOS.post("person/administrator/login", customerDto)
        .then(response => {
          console.log(response.data);
          this.currentUser = response.data;
          this.error = "";
          this.goToAdminHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.error = errorMsg;
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
