import axios from "axios";
import CustomerHeader from "./CustomerHeader";
import VueCtkDateTimePicker from "vue-ctk-date-time-picker";
import "vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css";
import Vue from "vue";
import VueToast from "vue-toast-notification";
import "vue-toast-notification/dist/theme-sugar.css";

// Import Bootstrap an BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

Vue.use(VueToast);

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

  if (
    currentMonth <= appointmenntMonth ||
    currentDate <= appointmentDate ||
    currentYear <= appointmenntYear
  ) {
    return true;
  }
  return false;
};
const filterPastAppointments = appointmentDate => {
  const currentDate = new Date();
  const currentDayOfMonth = currentDate.getDate();
  const currentMonth = currentDate.getMonth() + 1;
  const currentYear = currentDate.getFullYear();

  const date = new Date(appointmentDate);
  const appointmentDayOfMonth = date.getDate();
  const appointmenntMonth = date.getMonth() + 1;
  const appointmenntYear = date.getFullYear();

  if (
    currentDate > appointmentDate ||
    currentDayOfMonth > appointmenntMonth ||
    currentYear > appointmenntYear
  ) {
    return true;
  }
  return false;
};

const customizeAppointment = appointment => {
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

const getCurrentUser = () => {
  const email = localStorage.getItem("savedCustomerEmail");
  const username = localStorage.getItem("savedCustomerName");
  const password = localStorage.getItem("savedCustomerPassword");
  return {
    email: email,
    username: username,
    password: password
  };
};

const getDateAndTime = time => {
  return {
    date: time.substring(0, 10),
    time: time.substring(11, 16)
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
      showBill: false,
      showCancel: false,
      date: "",
      updatedDate: "",
      updatedServices: [],
      error: "",
      modalError: "",
      allServicesAvailable: [],
      allTimeSlots: [],
      allServiceNames: [],
      futureTimeSlots: [],
      allTimeSlotsDates: [],
      upcomingAppointments: [],
      allAppointments: [],
      pastAppointments: [],
      currentUser: {},
      renderComponent: true
    };
  },
  created() {
    this.currentUser = getCurrentUser();
    this.getAllAppointments(this.currentUser.email);
    this.getAllTimeSlots();
    this.getAllServices();
  },
  methods: {
    forceRerender() {
      this.$forceUpdate();
    },
    editAppointment: function(id) {
      this.show = true;
    },
    cancelAppointment: function(id) {
      this.showCancel = true;
    },

    handleCancel: function(id) {
      console.log(id);
      AXIOS.delete(`/appointment/${id}`)
        .then(response => {
          console.log("suceess");
          Vue.$toast.warning("Your appointment has been deleted", {
            duration: 1000
          });
        })
        .catch(error => {
          this.error = error;
          Vue.$toast.error(error.response.data, {
          duration: 6000});
        });
    },

    getAllAppointments: function(email) {
      AXIOS.get(`/appointment/person/${email}`)
        .then(response => {
          response.data.forEach(appointment => {
            this.allAppointments.push(appointment);
            this.getPastAppointments(appointment);
            this.getUpcomingAppointments(appointment);
          });
        })
        .catch(error => {
          this.error = error;
          Vue.$toast.error(error.response.data, {
          duration: 6000});
        });
    },

    getUpcomingAppointments: function(appointment) {
      const bool = filterUpcomingAppointments(appointment.timeSlot.date);
      if (bool) {
        this.upcomingAppointments.push(appointment);
      }
    },

    getPastAppointments: function(appointment) {
      const bool = filterPastAppointments(appointment.timeSlot.date);
      if (bool) {
        this.pastAppointments.push(customizeAppointment(appointment));
      }
    },

    getAllTimeSlots: function() {
      AXIOS.get(`/timeslotAvailable`)
        .then(response => {
          response.data.forEach(timeSlot => {
            this.allTimeSlots.push(timeSlot);
            const date = timeSlot.date + " " + timeSlot.startTime;
            this.allTimeSlotsDates.push({ id: timeSlot.id, date: date });
          });
        })
        .catch(error => {
          this.error = error;
          Vue.$toast.error(error.response.data, {
          duration: 6000});
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
          Vue.$toast.error(error.response.data, {
          duration: 6000});
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
          Vue.$toast.warning("Your appointment has been updated", {
            duration: 6000
          });
        })
        .catch(error => {
          this.error = error;

          Vue.$toast.warning("Appointment could not be updated", {
            duration: 6000
          });
        });
    },
    showBillModal: function() {
      if (
        (this.date !== null || this.date !== "") &&
        this.updatedServices.length > 0
      ) {
        this.showBill = true;
      } else {
        Vue.$toast.warning(
          "Please select a date and services to book an appointment",
          {
            duration: 6000
          }
        );
      }
    },
    handleBillOk: function() {
      this.forceRerender();
      let services = "";
      this.updatedServices.forEach(str => {
        for (var i = 0; i < str.length; i++) {
          str = str.replace(" ", "+");
        }
        services += str + ",";
      });
      services = services.substring(0, services.length - 1);
      const dateAndTime = getDateAndTime(this.date);
      AXIOS.post(
        `/appointment/` +
          `?` +
          `customerEmail=` +
          this.currentUser.email +
          `&` +
          `serviceNames=` +
          services +
          `&` +
          `startTime=` +
          dateAndTime.time +
          `&` +
          `date=` +
          dateAndTime.date
      )
        .then(response => {
          this.getUpcomingAppointments(response.data);
        })
        .catch(error => {
          Vue.$toast.warning(
            "Cannot book appointment at this time, please verify details",
            { duration: 6000 }
          );
        });
    }
  }
};

export default CustomerAppointmentPage;
