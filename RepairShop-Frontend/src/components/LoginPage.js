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
        password: ""
      },
      errorCustomer: "",
      response: [],
      pageTitle: "Welcome to repairshop, your satisfaction is our top concern"
    };
  },

  methods: {
    loginCustomer: function(email, password) {
      const customerDto = new CustomerDto(email, password);
      AXIOS.post("person/customer/login", customerDto)
        .then(response => {
          console.log(response.data);
          this.currentUser = response.data;
          this.errorCustomer = "";
          this.goToCustomerHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.errorCustomer = errorMsg;
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
    }
  }
};
