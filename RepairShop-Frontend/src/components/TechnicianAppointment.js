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

export default {
  name: "TechnicianAppointmentPage",
  components: {
    TechnicianHeader
  },
  data() {
    return {
      technician: [],
      allAppointments: [],
      appointmentOfTechnicians: []
    };
  },
  created() {
    this.getAllTechnicians();
  },

  methods: {
    getAllTechnicians: async function() {
      const response = await AXIOS.get(`/person/technician/`);
      console.log(response.data);
    }
  }
};
