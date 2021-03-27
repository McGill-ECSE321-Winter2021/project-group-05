import axios from "axios";

var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

//This function creates a businessDto
function BusinessDto(name, address, phoneNumber, email) {
  this.name = name;
  this.address = address;
  this.phoneNumber = phoneNumber;
  this.email = email;
}

const BusinessPage = {
  name: "BusinessPage",
  data() {
    return {
      business: [],
      name: "",
      email: "",
      address: "",
      phoneNumber: "",
      error: ""
    };
  },
  created() {
    const id = 1; //get id of business
    this.getBusiness(id);
  },
  methods: {
    createBusiness: function(name, address, phoneNumber, email) {
      const businessDto = new BusinessDto(name, address, phoneNumber, email);
      AXIOS.put("/business/", {
        businessDto
      })
        .then(response => {
          this.business.push(response.data);
        })
        .catch(e => {
          this.error = e.response.data.message;
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
        });
    },
    updateBusiness: function(name, address, phoneNumber, email) {
      const id = this.business.pop().id;
      const businessDto = new BusinessDto(name, address, phoneNumber, email);
      console.log(businessDto);
      AXIOS.put(`/business/${id}/`, businessDto)
        .then(response => {
          this.business.push(response.data);
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
          this.error = e.message;
        });
    }
  }
};

export default BusinessPage;
