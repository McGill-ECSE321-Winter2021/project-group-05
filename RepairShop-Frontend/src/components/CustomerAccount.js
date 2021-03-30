import CustomerHeader from './CustomerHeader'
import axios from "axios";
import Router from "../router";
import currentUser from "./LoginPage.js";
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';



var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});


Vue.use(VueToast);

//This function creates a customerDto
function CustomerDto(username, email, password) {
  this.username = username;
  this.email = email;
  this.password = password;
}

const CustomerAccountPage = {
  name: "CustomerAccountPage",
  components: {
    CustomerHeader
  },

  data() {
    return {
      customers: [],
      username: currentUser.data().currentUser.email,
      email: currentUser.data().currentUser.email,
      password:"",
      confirmPassword:"",
      error: ""
    };
  },
  methods: {
    updateAccount: function(username, email, password,confirmPassword) {
      if (password == confirmPassword){

      const customerDTO = new CustomerDto(username,email,password);
      console.log(customerDTO);
      AXIOS.put(`/person/customer/${email}/`, customerDTO)
        .then(response => {
          this.customers.push(response.data);
          console.log(response.data);
          Vue.$toast.success('Account credentials successfully updated', {
          duration: 6000});

        })
        .catch(e => {
          console.log(e);
          this.error = e.message;
          Vue.$toast.error(e.response.data, {
          duration: 6000});
        });
      }
      else{
        //alert("confirm password doesn't match with the password")
        Vue.$toast.error("Passwords do not match", {
        duration: 6000});
      }
    },
    deleteAccount: function(email){
      AXIOS.delete(`/person/customer/${email}/`)
        .then(response => {
          this.customers.pop();
          console.log(response.data);
          this.gotoLogin();
          Vue.$toast.success('Account successfully deleted', {
          duration: 6000});
        })
        .catch(e => {
          console.log(e);
          this.error = e.message;
          Vue.$toast.error(e.response.data, {
          duration: 6000});
        });

    },
    gotoLogin: function() {
      Router.push({
        path: "/",
        name: "LoginPage"
      });
    }
  }

};

export default CustomerAccountPage;
