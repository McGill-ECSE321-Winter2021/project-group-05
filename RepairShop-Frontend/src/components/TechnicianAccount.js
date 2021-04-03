import TechnicianHeader from './TechnicianHeader'
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

//This function creates a technicianDto
function TechnicianDto(username, email, password) {
  this.username = username;
  this.email = email;
  this.password = password;
}

const TechnicianAccountPage = {
  name: "TechnicianAccountPage",
  components: {
    TechnicianHeader
  },

  data() {
    return {
      technicians: [],
      username: "",
      email: "",
      password:"",
      confirmPassword:"",
      error: "",
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
        this.username = localStorage.getItem('savedTechnicianName');
        this.email = localStorage.getItem('savedTechnicianEmail');
        this.password = localStorage.getItem('savedTechnicianPassword');
      }
  },


  methods: {
    updateAccount: function(username, email, password,confirmPassword) {
      if (password == confirmPassword){

      const technicianDTO = new TechnicianDto(username,email,password);
      console.log(technicianDTO);
      AXIOS.put(`/person/technician/${email}/`, technicianDTO)
        .then(response => {
          this.technicians.push(response.data);
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
      AXIOS.delete(`/person/technician/${email}/`)
        .then(response => {
          this.technicians.pop();
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

export default TechnicianAccountPage;
