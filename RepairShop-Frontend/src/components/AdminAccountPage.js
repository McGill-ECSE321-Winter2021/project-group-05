import AdminHeader from "./AdminHeader";
import axios from "axios";
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

//This function creates a adminDto
function AdminDto(username, email, password) {
  this.username = username;
  this.email = email;
  this.password = password;
}

const AdminAccountPage = {
  name: "AdminAccountPage",
  components: {
    AdminHeader
  },
  data() {
    return {
      admins: [],
      username: "",
      email: "",
      password: "",
      confirmPassword: "",
      error: "",
      showDelete: false,
      showUpdate: false
    };
  },

  created: function() {
    this.username = localStorage.getItem("savedAdminName");
    this.email = localStorage.getItem("savedAdminEmail");
    this.password = localStorage.getItem("savedAdminPassword");
    this.confirmPassword = localStorage.getItem("savedAdminPassword");
  },

  methods: {
    showUpdateModal: function() {
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
      this.showUpdate = true;
    },
    showDeleteModal: function() {
      this.showDelete = true;
    },
    updateAccount: function(username, email, password, confirmPassword) {
      if (this.confirmPassword == confirmPassword) {
        const adminDTO = new AdminDto(username, email, password);
        console.log(adminDTO);
        AXIOS.put(`/person/administrator/${email}/`, adminDTO)
          .then(response => {
            this.admins.push(response.data);
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
        //alert("confirm password doesn't match with the password");
        Vue.$toast.error("Passwords do not match", {
          duration: 6000
        });
      }
    },
    deleteAccount: function(email) {
      AXIOS.delete(`/person/administrator/${email}/`)
        .then(response => {
          this.admins.pop();
          console.log(response.data);
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
    }
  }
};

export default AdminAccountPage;
