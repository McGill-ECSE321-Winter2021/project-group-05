import axios from "axios";
import AuthHeader from "./AuthHeader";
import Router from "../router";

var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "CustomerRegisterPage",
  components: {
    AuthHeader
  },
  data() {
    return {
      customers: [],

      username: "",
      email: "",
      password: "",
      confirmPass: "",


      errorCustomer: "",
      response: [],
      pageTitle: "Create Account"
    };
  },

  methods: {
    createCustomer: function(username, email, password, confirmPass) {
      console.log(username, email, password);
      if (password != confirmPass){
        alert("Password is not equal to the confirm password")
      }
      AXIOS.post("/person/customer/register/", {
        email: email,
        username: username,
        password: password
      })
        .then(response => {
          // JSON responses are automatically parsed.
          this.customers.push(response.data);
          this.errorCustomer = "";
          this.gotoCustomerHomePage();
        })
        .catch(e => {
          var errorMsg = e;
          this.errorCustomer = errorMsg;
        });
    },
    gotoCustomerHomePage: function() {
      Router.push({
        path: "/CustomerHomePage",
        name: "CustomerHomePage"
      });
    }

  }
};
