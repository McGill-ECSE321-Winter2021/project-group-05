import AdminHeader from "./AdminHeader";
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

//FILTER OUT ONLY UPCOMING APPOINTMENTS
export default {
  name: "AdminAppointmentPage",
  components: {
    AdminHeader
  },
  data() {
    return {
      allTechnicians: [],
      allAppointments: [],
      appointmentOfTechnicians: [],
      allTimeslots: [],
      appointmentId: "",
      technicianEmail: ""
    };
  },
  created() {
    this.getAllTechnicians();
    this.getAllAppointments();
    this.getAllTimeSlots();
  },

  methods: {
    getAllTechnicians: async function() {
      const response = await AXIOS.get(`/person/technician/`);
      this.allTechnicians = response.data;
    },
    getAllAppointments: async function() {
      const response = await AXIOS.get(`/allappointments`);
      this.allAppointments = response.data;
    },
    getAllTimeSlots: async function() {
      const response = await AXIOS.get(`/timeslotAvailable`);
      this.allTimeslots = response.data;
    },
    assignTimeslot: async function(timeSlotId, techicianEmail) {
      const response = await AXIOS.post(
        `/assignSlot/${timeSlotId}` + `?` + `email=` + `${techicianEmail}`
      );
    },
    assignAppointment: async function(appointmentId, technicianEmail) {
      const response = await AXIOS.put(
        `/assignAppointment/${appointmentId}` +
          `?` +
          `email=` +
          `${technicianEmail}`
      );
    }
  }
};