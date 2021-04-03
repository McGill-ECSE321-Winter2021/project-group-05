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
    this.populateCalendar();

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
      splitDays: [],
      events: [],
      timeslotLength:-1,
      allTimeSlotOfTechnician:[]

    }

  },
  methods: {

    getAllTechnicians: async function () {
      const response = await AXIOS.get(`/person/technician/`);
      this.allTechnicians = response.data;
      this.allTechnicians.forEach((tech) => {
        this.splitDays.push({
          id: tech.id, // changed the id to tech id
          class: tech.email,
          label: tech.email,
          hide: false
        });
      })

    },

    createTimeSlot(startDate, startTime, endingTime) {

      AXIOS.post(`/timeSlot/` +
        `?` +
        `date=` +
        `${startDate}` +

        `&` +
        `startTime=` +
        `${startTime}` +

        `&` +
        `endTime=` +
        `${endingTime}`
      )
        .then(response => {
          this.timeslot.push(response.data);
          this.timeslotLength++;

        })
        .catch(error => {
          console.log(error);
          Vue.$toast.error(
            "Cannot create the timeslot at this time, please verify details",
            {duration: 6000}
          );
        });

    },
    createTimeSlotAndAssign: async function (technicianEmail) {
      const dateAndTime = getDateAndTime(this.date);
      console.log("date from the calendar" + this.date);
      const startDate = dateAndTime.date;
      let startTime = dateAndTime.time;
      let startingHour = startTime.substring(0, 2);

      if (dateAndTime.am == "pm") {
        startingHour = parseInt(startingHour) // change to int
        startingHour = startingHour + 12; // convert to pm
        startTime = startingHour.toString() + startTime.substring(2,);
      }

      let endingHour = parseInt(startingHour) + 24;

      // check if ending hour exceed 24 hrs
      if (endingHour >= 24) {
        endingHour = 23;
      }
      const endTime = endingHour.toString() + startTime.substring(2,);
      console.log(startDate);
      console.log(startTime);
      console.log(startingHour);
      console.log(endTime);
      this.createTimeSlot(startDate,startTime,endTime);
      const tech= await AXIOS.get(`/person/technician/${technicianEmail}/`);
      const techid = tech.data.id
      console.log(this.timeslot[this.timeslotLength].id);
      this.assignTimeslot(this.timeslot[this.timeslotLength].id, technicianEmail,techid, startDate,startTime, endTime);

    },

    assignTimeslot: async function (timeSlotId, techicianEmail,technicianId,startDate,startTime,endTime) {
      const response = await AXIOS.post(
        `/assignSlot/${timeSlotId}` + `?` + `email=` + `${techicianEmail}`
      )
        .then(response => {

          console.log(technicianId);
          this.events.push({
              start:startDate + " "+startTime,
              end: startDate+ " "+endTime,
              title:'available',
              split:technicianId
            }

          )
          Vue.$toast.success("TimeSlot has been successfully assigned to the technician ", {
            duration: 4000
          });
        })
        .catch(error => {
          console.log(error);
          Vue.$toast.error(
            "Cannot assign the timeslot to the technician, please verify details",
            {duration: 6000}
          );
        });
    },

    currentTechAvaliability: async function(email){
      const array=[];
       await AXIOS.get(`/timeSlotOfTechnician/${email}`).then( response =>{

         (response.data).forEach((timeSlot)=>{
           this.allTimeSlotOfTechnician.push(timeSlot);
           array.push(timeSlot);
          // console.log(timeSlot);
       })

      });
     // console.log("currentTechAvaliability"+allTimeSlotOfTechnician);

      console.log(this.allTimeSlotOfTechnician);
      return array;
    },

    populateCalendar: async function(){

      const response = await AXIOS.get(`/person/technician/`);
      this.allTechnicians = response.data;

      console.log(this.allTechnicians);
      this.allTechnicians.forEach((tech) => {


        const technicianId = tech.id;
        const array=this.currentTechAvaliability(tech.email);
       // const array=this.allTimeSlotOfTechnician.get(0);
       // console.log(array);
       // console.log(this.allTimeSlotOfTechnician);
        (Array.from(array)).forEach((availability)=>{
          console.log(availability);
          console.log(availability.date + " "+availability.startTime);

          console.log(availability.date + " "+availability.startTime);
          this.events.push({
            start:availability.date + " "+availability.startTime,
            end: availability.date + " "+availability.endTime,
            title:'available',
            split:technicianId
          })
        })

      })

    }

  }

}
