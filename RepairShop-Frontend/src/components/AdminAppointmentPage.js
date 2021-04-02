import AdminHeader from './AdminHeader'
import axios from "axios";

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

const AdminAppointmentPage = {
  name: "AdminAppointmentPage",
  components:{
    AdminHeader
  },
  data(){
    return{
      allAppointments:[],
      allAppointmentsFormated:[],
      upcomingAppointments:[],
      pastAppointments:[],
      error:""
    };
  },
  methods: {
    getAllAppointments: function(){
      AXIOS.get(`/appointments`)
        .then(response =>{
          response.data.forEach(appointment => {
            this.allAppointments.push(appointment);
            // TODO: why this one returns the past appointments?
            this.allAppointmentsFormated.push(formatAppointment(appointment));
            // seperate the future and the past appointments
            this.seperateAppointments();
          });
        })
        .catch(error => {
          this.error=error;
        });
    },

    seperateAppointments: function(appointment){
      const isInFuture = filterUpcomingAppointments(appointment.timeslot.date);
      if (isInFuture){
        this.upcomingAppointments.push(appointment);
      }
      else{
        this.pastAppointments.push(appointment);
      }


    }
  }

}
export default AdminAppointmentPage;

