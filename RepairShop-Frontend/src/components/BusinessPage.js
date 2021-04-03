import axios from "axios";
import AdminHeader from "./AdminHeader";
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

//This function creates a businessDto
function BusinessDto(name, address, phoneNumber, email) {
  this.name = name;
  this.address = address;
  this.phoneNumber = phoneNumber;
  this.email = email;
}

const BusinessPage = {
  name: "BusinessPage",
  components: {
    AdminHeader
  },
  data() {
    return {
      business: [],
      name: "",
      email: "",
      address: "",
      phoneNumber: "",
      error: "",
      showDelete: false,
      showUpdate: false,
      id: "",
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
        this.id = 1;
        this.getBusiness(this.id);
    }
  },
  methods: {
    createBusiness: function(name, address, phoneNumber, email) {
      const businessDto = new BusinessDto(name, address, phoneNumber, email);
      AXIOS.put("/business/", {
        businessDto
      })
        .then(response => {
          this.business.push(response.data);
          Vue.$toast.success("Business successfully created", {
            duration: 6000
          });
        })
        .catch(e => {
          this.error = e.response.data.message;
          Vue.$toast.error(e.response.data, {
            duration: 6000
          });
        });
    },
    getBusiness: function(id) {
      AXIOS.get(`/business/${id}`)
        .then(response => {
          //console.log(response.data);
          this.name = response.data.name;
          this.email = response.data.email;
          this.address = response.data.address;
          this.phoneNumber = response.data.phoneNumber;
          this.business.push(response.data);
        })
        .catch(e => {
          this.error = e.response.data.message;
          Vue.$toast.error(e.response.data, {
            duration: 6000
          });
        });
    },
    showUpdateModal: function() {
      this.showUpdate = true;
    },
    updateBusiness: function(name, address, phoneNumber, email) {
      const id = this.business.pop().id;
      const businessDto = new BusinessDto(name, address, phoneNumber, email);
      console.log(businessDto);
      AXIOS.put(`/business/${id}/`, businessDto)
        .then(response => {
          this.business.push(response.data);
          console.log(response.data);
          Vue.$toast.success("Business information successfully updated", {
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

export default BusinessPage;
