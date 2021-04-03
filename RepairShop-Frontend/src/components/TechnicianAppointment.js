import TechnicianHeader from "./TechnicianHeader";
import axios from "axios";
import "vue-toast-notification/dist/theme-sugar.css";

var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

const getCurrentUser = () => {
  const email = localStorage.getItem("savedTechnicianEmail");
  const username = localStorage.getItem("savedTechnicianName");
  const password = localStorage.getItem("savedTechnicianPassword");
  return {
    email: email,
    username: username,
    password: password
  };
};

export default {
  name: "TechnicianAppointmentPage",
  components: {
    TechnicianHeader
  },
  data() {
    return {
      allAppointments: [],
      currentUser: {},
      render: true
    };
  },
  created() {
    if(localStorage.getItem('loggedInEmail').localeCompare("null") === 0){
        this.render = false;
        console.log(this.render);
    }
    else {
      this.render = true;
      this.currentUser = getCurrentUser();
      this.getAllAppointments();
    }
  },

  methods: {
    getAllAppointments: async function() {
      const response = await AXIOS.get(
        `/appointmentOfTechnician/${this.currentUser.email}`
      );
      this.allAppointments = response.data;
    }
  }
};
