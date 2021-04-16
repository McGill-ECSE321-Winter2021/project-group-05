//view service for customer
import axios from "axios";
import CustomerHeader from "./CustomerHeader";


var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

//creates service dto
function BookableServiceDto(name, cost, duration) {
  this.name = name;
  this.cost = cost;
  this.duration = duration;
}

export default {
  name: "viewServiceCustomer",
  components: {
    CustomerHeader
  },
  data() {
    return {
      services: [],
      bookableService: {
        selectedService: "",
        selectedCost: "",
        selectedDuration: "",
      },
      errorCreateService: "",
      returnedService: "",
      response: [],
      render: true
    };
  },

  created: function() {
    if(localStorage.getItem('loggedInEmail').localeCompare("null") === 0){
        this.render = false;
        console.log(this.render);
    }
    else {
      this.render = true;
      // new added
      // Initializing services from backend
      AXIOS.get("/bookableServices")
        .then(response => {
          // JSON responses are automatically parsed.
          this.services = response.data;
        })
        .catch(e => {
          this.errorCreateService = e;
        });
    }
  },

  methods: {
    //create a service
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
          this.selectedService = "";
          this.selectedCost = "";
          this.selectedDuration = "";
          this.errorCreateService = "";
        })
        .catch(e => {
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorCreateService = errorMsg;
        });
    },
    //get service
    getServiceByName: function(serviceName){
    console.log(serviceName);
    AXIOS.get("/bookableService/".concat(serviceName))
        .then(response => {
            this.returnedService = response.data;
            console.log(returnedService.duration);

        })
        .catch(e => {
           var errorMsg = e.response.data.message;
           console.log(errorMsg);
           this.errorCreateService = errorMsg;
        });
    }
  }
};
