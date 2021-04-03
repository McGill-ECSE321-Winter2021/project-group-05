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
      cost: "",
      cardNumber: "",
      cvv: "",
      expiry: "",
      username: "",
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
      this.username = this.currentUser.username;
      this.getAllAppointments(this.currentUser.email);
      this.getAllTimeSlots();
      this.getAllServices();
      this.getCardInfo();
    }
  },
  methods: {
    editAppointment: function(id) {
      this.show = true;
    },
    cancelAppointment: function(id) {
      this.showCancel = true;
    },
    getCardInfo: async function() {
      const response = await AXIOS.get(
        `/person/customer/${this.currentUser.email}`
      );
      this.cardNumber = response.data.cardNumber;
      this.cvv = response.data.cvv;
      this.expiry = response.data.expiry;
    },

    handleCancel: function(id) {
      AXIOS.delete(`/appointment/${id}`)
        .then(response => {
          Vue.$toast.success("Your appointment has been deleted", {
            duration: 1000
          });
          this.upcomingAppointments = this.upcomingAppointments.filter(
            appointment => {
              return appointment.id !== id;
            }
          );
        })
        .catch(error => {
          this.error = error;
          Vue.$toast.error(error, {
            duration: 1000
          });
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
            duration: 1000
          });
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
            duration: 1000
          });
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
            duration: 1000
          });
        });
    },

    handleEdit(id, timeSlotId) {
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
          for (var i in this.upcomingAppointments) {
            if (this.upcomingAppointments[i].id == id) {
              this.upcomingAppointments[i].services = response.data.services;
              break;
            }
          }
          Vue.$toast.success("Your appointment has been updated", {
            duration: 1000
          });
        })
        .catch(error => {
          this.error = error;

          Vue.$toast.error("Appointment could not be updated", {
            duration: 1000
          });
        });
    },
    showBillModal: async function() {
      if (
        (this.date !== null || this.date !== "") &&
        this.updatedServices.length > 0
      ) {
        this.showBill = true;
        let services = "";
        this.updatedServices.forEach(str => {
          for (var i = 0; i < str.length; i++) {
            str = str.replace(" ", "+");
          }
          services += str + ",";
        });
        services = services.substring(0, services.length - 1);
        const response = await AXIOS.get(
          "/costOfService/?" + `services=` + `${services}`
        );
        this.cost = response.data;
      } else {
        Vue.$toast.warning(
          "Please select a date and services to book an appointment",
          {
            duration: 1000
          }
        );
      }
    },
    handleBillOk: function() {
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
          Vue.$toast.success("Appointment has been successfully booked", {
            duration: 1000
          });
        })
        .catch(error => {
          Vue.$toast.error(
            "Cannot book appointment at this time, please verify details",
            { duration: 1000 }
          );
        });
    },
    getServiceCost: async function(serviceName) {
      const response = await AXIOS.get(`/bookableService/${serviceName}`);
      return response.data.cost;
    }
  }
};

export default CustomerAppointmentPage;
