import axios from "axios";
import AdminHeader from "./AdminHeader";
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

Vue.use(VueToast);

function BookableServiceDto(name, cost, duration) {
  this.name = name;
  this.cost = cost;
  this.duration = duration;
}

export default {
  name: "createServiceAdmin",
  components: {
    AdminHeader
  },

  data() {
    return {
      services: [],
      bookableService: {
        newService: "",
        newCost: "",
        newDuration: ""
      },
      returnedService: "",
      errorCreateService: "",
      errorEditService: "",
      errorDeleteService: "",

      updatedName: "",
      updatedCost: "",
      updatedDuration: "",
      response: []
    };
  },

  created: function() {
    // Initializing services from backend
    AXIOS.get("/bookableServices")
      .then(response => {
        // JSON responses are automatically parsed.
        this.services = response.data;
      })
      .catch(e => {
        this.errorCreateService = e;
        Vue.$toast.error(e.response.data, {
        duration: 6000});
      });
  },

  methods: {
    createServiceAdmin: function(serviceName, serviceCost, serviceDuration) {
      console.log(serviceName, serviceCost, serviceDuration);
      const bookableServiceDto = new BookableServiceDto(
        serviceName,
        serviceCost,
        serviceDuration
      );
      AXIOS.post("/bookableService", bookableServiceDto)
        .then(response => {
          // JSON responses are automatically parsed.
          this.services.push(response.data);
          this.newService = "";
          this.newCost = "";
          this.newDuration = "";
          this.errorCreateService = "";
          Vue.$toast.success('Service successfully created', {
          duration: 6000});
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorCreateService = errorMsg;
          Vue.$toast.error(e.response.data, {
          duration: 6000});
        });
    },

    getServiceByName: function(serviceName){
        console.log(serviceName);
        AXIOS.get("/bookableService/".concat(serviceName))
            .then(response => {
                this.returnedService = response.data;
                this.updatedName = response.data.name;
                this.updatedCost = response.data.cost;
                this.updatedDuration = response.data.duration;
            })
            .catch(e => {
               var errorMsg = e.response.data.message;
               console.log(errorMsg);
               this.errorCreateService = errorMsg;
               Vue.$toast.error(e.response.data, {
               duration: 6000});
            });
    },

    editService: function(service, newServiceName, newServiceCost, newServiceDuration){
        console.log(service, newServiceName, newServiceCost, newServiceDuration);
        const editBookableServiceDto = new BookableServiceDto(
          newServiceName,
          newServiceCost,
          newServiceDuration
        );
        AXIOS.put("bookableService/".concat(service), editBookableServiceDto)
            .then(response => {
                this.services.push(response.data);
                console.log(response.data);
                for(let i = 0; i < this.services.length; i++) {
                    if(this.services[i].name.localeCompare(service) === 0){
                        this.services.splice(i,1);
                    }
                }
                Vue.$toast.success('Service successfully updated', {
                duration: 6000});
            })
            .catch(e => {
               var errorMsg = e.response.data.message;
               console.log(errorMsg);
               this.errorEditService = errorMsg;
               Vue.$toast.error(e.response.data, {
               duration: 6000});
            });
    },

    deleteService: function(serviceToDelete) {
        console.log(serviceToDelete);
        AXIOS.delete("/bookableService/".concat(serviceToDelete))
            .then(response => {
                console.log(response.data);
                for(let i = 0; i < this.services.length; i++) {
                   if(this.services[i].name.localeCompare(serviceToDelete) === 0){
                       this.services.splice(i,1);
                   }
                }
                Vue.$toast.success('Service successfully deleted', {
                duration: 6000});
                this.updatedName = "";
                this.updatedCost = "";
                this.updatedDuration = "";
            })
            .catch(e => {
                var errorMsg = e.response.data.message;
                console.log(errorMsg);
                this.errorDeleteService = errorMsg;
                Vue.$toast.error(e.response.data, {
                duration: 6000});
            })
    }
  }
};
