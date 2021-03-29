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

const filterUpcomingAppointments = appointmentDate => {
  const currentDate = new Date();
  const currentDayOfMonth = currentDate.getDate();
  const currentMonth = currentDate.getMonth() + 1;
  const currentYear = currentDate.getFullYear();

  const date = new Date(appointmentDate);
  const appointmentDayOfMonth = date.getDate();
  const appointmenntMonth = date.getMonth() + 1;
  const appointmenntYear = date.getFullYear();

  // console.log(currentDayOfMonth);
  // console.log(currentMonth);
  // console.log(currentYear);
  // console.log("--------");
  // console.log(appointmentDayOfMonth);
  // console.log(appointmenntMonth);
  // console.log(appointmenntYear);

  return (
    currentDayOfMonth === appointmentDayOfMonth &&
    currentMonth === appointmenntMonth &&
    currentYear === appointmenntYear
  );
};

const formatAppointment = appointment => {
  const serviceNames = [];
  appointment.services.forEach(item => {
    serviceNames.push(item.name);
  });
  const date = appointment.timeSlot.date;
  const startTime = appointment.timeSlot.startTime;
  const endTime = appointment.timeSlot.endTime;
  return {
    Services: serviceNames,
    date: date,
    startTime: startTime,
    endTime: endTime
  };
};

const CustomerAppointmentPage = {
  name: "CustomerAppointmentPage",
  components: {
    CustomerHeader,
    VueCtkDateTimePicker
  },
  data() {
    return {
      show: false,
      date: "",
      updatedDate: "",
      updatedServices: [],
      error: "",
      modalError: "",
      allServicesAvailable: [],
      services: [],
      allTimeSlots: [],
      allServiceNames: [],
      futureTimeSlots: [],
      allTimeSlotsDates: [],
      upcomingAppointments: [],
      allAppointments: [],
      allAppointmentsFormated: []
    };
  },
  created() {
    this.getAllAppointments("customer@gmail.com");
    this.getAllTimeSlots();
    this.getAllServices();
  },
  methods: {
    editAppointment: function(id) {
      this.show = true;
    },
    cancelAppointment: function(id) {
      AXIOS.delete(`/appointment/${id}`)
        .then(response => {
          //toast message for success
          console.log("suceess");
        })
        .catch(error => {
          this.error = error;
        });
    },

    getAllAppointments: function(email) {
      AXIOS.get(`/appointment/person/${email}`)
        .then(response => {
          response.data.forEach(appointment => {
            this.allAppointments.push(appointment);
            this.allAppointmentsFormated.push(formatAppointment(appointment));
            this.getUpcomingAppointments(appointment);
          });
          //console.log(this.allAppointments);
        })
        .catch(error => {
          this.error = error;
        });
    },

    getUpcomingAppointments: function(appointment) {
      const bool = filterUpcomingAppointments(appointment.timeSlot.date);
      if (bool) {
        this.upcomingAppointments.push(appointment);
      }
    },

    getAllTimeSlots: function() {
      AXIOS.get(`/timeslotAvailable`)
        .then(response => {
          response.data.forEach(timeSlot => {
            this.allTimeSlots.push(timeSlot);
            const date = timeSlot.date + " " + timeSlot.startTime;
            this.allTimeSlotsDates.push({ id: timeSlot.id, date: date });
            //get future timeslots
          });
          console.log(this.allTimeSlotsDates);
        })
        .catch(error => {
          this.error = error;
        });
    },

    getAllServices: function() {
      AXIOS.get(`bookableServices`)
        .then(response => {
          response.data.forEach(service => {
            this.allServicesAvailable.push(service);
          });
          this.allServicesAvailable.forEach(service => {
            this.allServiceNames.push(service.name);
          });
        })
        .catch(error => {
          this.error = error;
        });
    },

    handleOk(id, timeSlotId) {
      let services = "";
      this.updatedServices.forEach(str => {
        for (var i = 0; i < str.length; i++) {
          str = str.replace(" ", "+");
        }
        services += str + ",";
      });
      services = services.substring(0, services.length - 1);
      AXIOS.put(
        `/appointment/${id}` +
          `?` +
          `timeSlotId=` +
          `${timeSlotId}` +
          `&` +
          `serviceNames=` +
          `${services}`
      )
        .then(response => {
          //handle success
          console.log("success");
        })
        .catch(error => {
          this.error = error;
        });
    }
  }
};

export default CustomerAppointmentPage;
