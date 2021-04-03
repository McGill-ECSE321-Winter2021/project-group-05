import AdminHeader from './AdminHeader';
import axios from "axios";
import VueCtkDateTimePicker from "vue-ctk-date-time-picker";
import "vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css";
import Vue from "vue";
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'


var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

const getDateAndTime = time => {
  return {
    date: time.substring(0, 10),
    time: time.substring(11, 16),
    am: time.substring(17)
  };
};

export default {
  name: "TimeSlotPage",
  components: {
    AdminHeader,
    VueCtkDateTimePicker,
    VueCal
  },
  created() {
    this.getAllTechnicians();

  },
  data(){
    return{
      allTechnicians:[],
      date: "",
      technicianEmail:"",
      timeslot:[],
      stickySplitLabels: false,
      minCellWidth: 400,
      minSplitWidth: 0,
      splitDays: [
        // The id property is added automatically if none (starting from 1), but you can set a custom one.
        // If you need to toggle the splits, you must set the id explicitly.
        { id: 1, class: 'mom', label: 'Mom' },
        { id: 2, class: 'dad', label: 'Dad', hide: false },
        { id: 3, class: 'kid1', label: 'Kid 1' },
        { id: 4, class: 'kid2', label: 'Kid 2' },
        { id: 5, class: 'kid3', label: 'Kid 3' }
      ],
      events: [
        {
          start: '2018-11-19 10:35',
          end: '2018-11-19 11:30',
          title: 'Doctor appointment',
          content: '<i class="v-icon material-icons">local_hospital</i>',
          class: 'health',
          split: 1 // Has to match the id of the split you have set (or integers if none).
        },
        {
          start: '2018-11-19 18:30',
          end: '2018-11-19 19:15',
          title: 'Dentist appointment',
          content: '<i class="v-icon material-icons">local_hospital</i>',
          class: 'health',
          split: 2
        },
        {
          start: '2018-11-20 18:30',
          end: '2018-11-20 20:30',
          title: 'Crossfit',
          content: '<i class="v-icon material-icons">fitness_center</i>',
          class: 'sport',
          split: 1
        }

      ]

    }

  },
  methods: {

    getAllTechnicians: async function () {
      const response = await AXIOS.get(`/person/technician/`);
      this.allTechnicians = response.data;
    },

    createTimeSlot(startDate,startTime,endingTime){

      AXIOS.post(`/timeSlot/` +
        `?` +
        `date=` +
        `${startDate}`+

        `&`+
        `startTime=` +
        `${startTime}` +

        `&` +
        `endTime=` +
        `${endingTime}`

      )
        .then(response => {
          this.timeslot.push(response.data);
          Vue.$toast.success("TimeSlot has been successfully created ", {
            duration: 4000
          });
        })
        .catch(error => {
          console.log(error);
          Vue.$toast.error(
            "Cannot create the timeslot at this time, please verify details",
            { duration: 6000 }
          );
        });

    },
    createTimeSlotAndAssign: function(technicianEmail){
      const dateAndTime = getDateAndTime(this.date);
      console.log(this.date);
      const startDate = dateAndTime.date;
      let startTime = dateAndTime.time;
      let startingHour = startTime.substring(0,2);

      if (dateAndTime.am == "pm"){
        startingHour = parseInt (startingHour) // change to int
        startingHour = startingHour +12; // convert to pm
        startTime = startingHour.toString()+startTime.substring(2,);
      }

      let endingHour = parseInt (startingHour) +24;

      // check if ending hour exceed 24 hrs
      if (endingHour>=24){
        endingHour = 23;
      }
      const endTime = endingHour.toString()+startTime.substring(2,);
      console.log(startDate);
      console.log(startTime);
      console.log(startingHour);
      console.log(endTime);
      this.createTimeSlot(startDate,startTime,endTime);

      this.assignTimeslot(this.timeslot[0].id,technicianEmail);


    },
    assignTimeslot: async function(timeSlotId, techicianEmail) {
      const response = await AXIOS.post(
        `/assignSlot/${timeSlotId}` + `?` + `email=` + `${techicianEmail}`
      )
    .then(response => {

        Vue.$toast.success("TimeSlot has been successfully assigned to the technician ", {
          duration: 4000
        });
      })
        .catch(error => {
          console.log(error);
          Vue.$toast.error(
            "Cannot assign the timeslot to the technician, please verify details",
            { duration: 6000 }
          );
        });
    }
  }

}
