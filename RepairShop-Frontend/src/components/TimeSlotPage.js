import AdminHeader from './AdminHeader';
import axios from "axios";
import VueCtkDateTimePicker from "vue-ctk-date-time-picker";
import "vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css";


var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "TimeSlotPage",
  components: {
    AdminHeader,
    VueCtkDateTimePicker
  },
  created() {
    this.getAllTechnicians();
  },
  data(){
    return{
      allTechnicians:[],

    }

  },
  methods: {
    getAllTechnicians: async function () {
      const response = await AXIOS.get(`/person/technician/`);
      this.allTechnicians = response.data;
    },
    createTimeSlot(startDate,startTime){

    },
    createTimeSlotAndAssign: function(startDate,startTime,technicianEmail){
    },
    assignTimeslot: async function(timeSlotId, techicianEmail) {
      const response = await AXIOS.post(
        `/assignSlot/${timeSlotId}` + `?` + `email=` + `${techicianEmail}`
      );
    }
  }

}
