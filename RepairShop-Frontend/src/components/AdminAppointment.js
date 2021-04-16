//admin appointments view
import AdminHeader from "./AdminHeader";
import axios from "axios";
import "vue-toast-notification/dist/theme-sugar.css";

//configuration
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
      technicianEmail: "",
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
      this.getAllTechnicians();
      this.getAllAppointments();
      this.getAllTimeSlots();
      this.getAllAppointmentsOfTechnicians();
    }
  },

  methods: {
    //get all technicians
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
    //get all appts
    getAllAppointments: async function() {
      const response = await AXIOS.get(`/allappointments`);
      this.allAppointments = response.data;
    },
    //get all time slots
    getAllTimeSlots: async function() {
      const response = await AXIOS.get(`/timeslotAvailable`);
      this.allTimeslots = response.data;
    },
    //assing a time slot to a technician
    assignTimeslot: async function(timeSlotId, techicianEmail) {
      const response = await AXIOS.post(
        `/assignSlot/${timeSlotId}` + `?` + `email=` + `${techicianEmail}`
      );
    },
    //assign an appointment
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
    //get all technician appts
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
