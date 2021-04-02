import CustomerHeader from "./CustomerHeader";
import axios from "axios";
import Router from "../router";
import Vue from "vue";
import VueToast from "vue-toast-notification";
import "vue-toast-notification/dist/theme-sugar.css";

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
function CustomerDto(username, email, password, cvv, cardNumber, expiry) {
  this.username = username;
  this.email = email;
  this.password = password;
  this.cvv = cvv;
  this.cardNumber = cardNumber;
  this.expiry = expiry;
}

export default {
  name: "CustomerAccount",
  components: {
    CustomerHeader
  },

  data() {
    return {
      customers: [],
      username: "",
      email: "",
      password: "",
      confirmPassword: "",
      error: "",
      showDelete: false,
      showUpdate: false,
      cvv: "",
      cardNumber: "",
      expiry: ""
    };
  },

  created: function() {
    this.username = localStorage.getItem("savedCustomerName");
    this.email = localStorage.getItem("savedCustomerEmail");
    this.getCustomer();
  },

  methods: {
    getCustomer: async function() {
      const response = await AXIOS.get(`/person/customer/${this.email}`);
      this.username = response.data.username;
      this.email = response.data.email;
      this.password = response.data.password;
      this.confirmPassword = response.data.password;
      this.cvv = response.data.cvv;
      this.cardNumber = response.data.cardNumber;
      this.expiry = response.data.expiry;
      console.log(response.data);
    },
    showUpdateModal: function() {
      this.showUpdate = true;
    },

    updateAccount: function(
      username,
      email,
      password,
      confirmPassword,
      cvv,
      cardNumber,
      expiry
    ) {
      if (password == confirmPassword) {
        const customerDTO = new CustomerDto(
          username,
          email,
          password,
          cvv,
          cardNumber,
          expiry
        );
        console.log(customerDTO);
        if (cardNumber.length < 16) {
          Vue.$toast.error("Card Number has to be 16 digits long", {
            duration: 6000
          });
          return;
        }
        AXIOS.put(`/person/customer/${email}/`, customerDTO)
          .then(response => {
            this.customers.push(response.data);
            console.log(response.data);
            Vue.$toast.success("Account credentials successfully updated", {
              duration: 6000
            });
          })
          .catch(e => {
            console.log(e);
            this.error = e.message;
            Vue.$toast.error(e.response.data, {
              duration: 6000
            });
          });
      } else {
        //alert("confirm password doesn't match with the password")
        Vue.$toast.error("Passwords do not match", {
          duration: 6000
        });
      }
    },
    showDeleteModal: function() {
      this.showDelete = true;
    },
    deleteAccount: function(email) {
      AXIOS.delete(`/person/customer/${email}/`)
        .then(response => {
          this.customers.pop();
          console.log(response.data);
          this.gotoLogin();
          Vue.$toast.success("Account successfully deleted", {
            duration: 6000
          });
        })
        .catch(e => {
          console.log(e);
          this.error = e.message;
          Vue.$toast.error(e.response.data, {
            duration: 6000
          });
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
