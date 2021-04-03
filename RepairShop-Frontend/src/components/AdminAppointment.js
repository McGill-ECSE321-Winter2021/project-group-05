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
    this.getAllAppointmentsOfTechnicians();
  },

  methods: {
    getAllTechnicians: async function() {
      const response = await AXIOS.get(`/person/technician/`);
      this.allTechnicians = response.data;
      this.allTechnicians.forEach(technician => {
        AXIOS.get(`/appointmentOfTechnician/${technician.email}`)
          .then(response => {
            response.data.forEach(appointment => {
              this.appointmentOfTechnicians.push({
                appointment: appointment,
                technician: technician
              });
            });
          })
          .catch(error => {
            console.log(error);
          });
      });
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
      const appointment = await AXIOS.put(
        `/assignAppointment/${appointmentId}` +
          `?` +
          `email=` +
          `${technicianEmail}`
      );
      AXIOS.get(`/person/technician/${technicianEmail}`)
        .then(response => {
          this.appointmentOfTechnicians.push({
            apppointment: appointment.data,
            technician: response.data
          });
          console.log(this.appointmentOfTechnicians);
        })
        .catch(error => {});
    },
    getAllAppointmentsOfTechnicians: function() {
      this.allTechnicians.forEach(technician => {
        AXIOS.get(`/appointmentOfTechnician/${technician.email}`)
          .then(response => {
            response.data.forEach(appointment => {
              this.appointmentOfTechnicians.push({
                appointment: appointment,
                technician: technician
              });
            });
          })
          .catch(error => {
            console.log(error);
          });
      });
    }
  }
};
