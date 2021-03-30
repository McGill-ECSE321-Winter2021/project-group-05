import axios from "axios";
import AuthHeader from "./AuthHeader";
import Router from "../router";
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
      passwordsMatchFlag: false
  },

  methods: {
    createCustomer: function(username, email, password, confirmPass) {
      console.log(username, email, password);
      if (password != confirmPass){
        //alert("Password is not equal to the confirm password")
        Vue.$toast.error('Password is not equal to the confirm password', {
        duration: 6000});
        this.passwordsMatchFlag = false;
      } else {
          this.passwordsMatchFlag = true;
      }
      if(this.passwordsMatchFlag == true) {
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
          Vue.$toast.success('Signup successful', {
          duration: 6000});
        })
        .catch(e => {
          var errorMsg = e;
          this.errorCustomer = errorMsg;
          Vue.$toast.error(e.response.data, {
          duration: 6000});
        });}
    },
    gotoCustomerHomePage: function() {
      Router.push({
        path: "/CustomerHomePage",
        name: "CustomerHomePage"
      });
    }

  }
};
