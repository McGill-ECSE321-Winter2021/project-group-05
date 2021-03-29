import axios from "axios";
import CustomerHeader from "./CustomerHeader";
import VueCtkDateTimePicker from "vue-ctk-date-time-picker";
import "vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css";

// Import Bootstrap an BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

const CustomerAppointmentPage = {
  name: "CustomerAppointmentPage",
  components: {
    CustomerHeader,
    VueCtkDateTimePicker
  },
  data() {
    return {
      date: "",
      services: [],
      items: [
        { id: 1, date: 40, start_time: "Dickerson", end_time: "Macdonald" },
        { id: 2, date: 21, start_time: "Larsen", end_time: "Shaw" },
        { id: 3, date: 89, start_time: "Geneva", end_time: "Wilson" },
        { id: 4, date: 38, start_time: "Jami", end_time: "Carney" }
      ]
    };
  },
  methods: {
    editAppointment: function() {
      console.log("edit");
    },
    cancelAppointment: function() {
      console.log("cancel");
    }
  }
};

export default CustomerAppointmentPage;
