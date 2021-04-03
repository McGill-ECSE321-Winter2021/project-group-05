import axios from "axios";
import AdminHeader from "./AdminHeader";
import Vue from "vue";
import VueToast from "vue-toast-notification";
import "vue-toast-notification/dist/theme-sugar.css";

var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

function createPersonDto(username, password, email, personType) {
  this.username = username;
  this.password = password;
  this.email = email;
  this.personType = personType;
}

export default {
  name: "AddStaffPage",
  components: {
    AdminHeader
  },
  data() {
    return {
      username: "",
      email: "",
      password: "",
      confirmPassword: "",
      personType: "",
      show: false,
      render: true
    };
  },

  created: function() {
    if(localStorage.getItem('loggedInEmail').localeCompare("null") === 0){
          this.render = false;
          console.log(this.render);
    }
    else {
      this.render = true;
    }
  },
  methods: {
    createAccount: async function(
      username,
      email,
      password,
      confirmPassword,
      personType
    ) {
      if (personType === "TECHNICIAN") {
        this.createTechnician(username, password, email, personType);
      } else {
        this.createAdmin(username, password, email, personType);
      }
    },
    createTechnician: function(username, password, email, personType) {
      const technicianDto = new createPersonDto(
        username,
        password,
        email,
        personType
      );
      AXIOS.post(`/person/technician/register`, technicianDto)
        .then(response => {
          //success
          Vue.$toast.success(
            `Technician ${this.username} has been successfully created`,
            {
              duration: 1000
            }
          );
        })
        .catch(error => {});
    },
    createAdmin: function(username, password, email, personType) {
      const adminDto = new createPersonDto(
        username,
        password,
        email,
        personType
      );
      AXIOS.post(`/person/administrator/register`, adminDto)
        .then(response => {
          //success
          Vue.$toast.success(
            `Admin ${this.username} has been successfully created`,
            {
              duration: 1000
            }
          );
        })
        .catch(error => {});
    },
    showAddModal: function() {
      if (this.personType === "" || this.personType === null) {
        //error
        Vue.$toast.error(`User role is required to create a staff`, {
          duration: 2000
        });
        return;
      }
      if (this.email === "" || this.email === null) {
        Vue.$toast.error(`Email cannot be empty`, {
          duration: 2000
        });
        return;
      }
      if (this.username === "" || this.username === null) {
        Vue.$toast.error(`Username cannot be empty`, {
          duration: 2000
        });
        return;
      }
      if (this.password === "" || this.confirmPassword === "") {
        Vue.$toast.error(`Username cannot be empty`, {
          duration: 2000
        });
        return;
      }
      if (this.password !== this.confirmPassword) {
        Vue.$toast.error(`Passwords do not match`, {
          duration: 2000
        });
        return;
      }
      this.show = true;
    }
  }
};
