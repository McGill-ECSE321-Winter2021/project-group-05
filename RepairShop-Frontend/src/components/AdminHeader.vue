<template>
    <nav id="header">
        <img class ="logo" src="../assets/logo_draft1.png" v-on:click="goToAdminHomePage()"/>
        <div id="container">
              <button id="present" v-if="homepageTab" v-on:click="goToAdminHomePage()">Home</button>
              <button id="absent" v-if="!homepageTab" v-on:click="goToAdminHomePage()">Home</button>
            <span></span>
              <button id="present" v-if="appointmentManagerTab" v-on:click="goToAdminAppointmentPage()">Appointment Manager</button>
              <button id="absent" v-if="!appointmentManagerTab" v-on:click="goToAdminAppointmentPage()">Appointment Manager</button>
            <span></span>
             <button id="present" v-if="serviceManagerTab" v-on:click="goToServicePage()">Service Manager</button>
             <button id="absent" v-if="!serviceManagerTab" v-on:click="goToServicePage()">Service Manager</button>
            <span></span>
             <button id="present" v-if="timeslotManagerTab" v-on:click="goToTimeSlotPage()">TimeSlot Manager</button>
             <button id="absent" v-if="!timeslotManagerTab" v-on:click="goToTimeSlotPage()">TimeSlot Manager</button>
            <span></span>
             <button id="present" v-if="businessTab" v-on:click="goToBusinessPage()">Business Manager</button>
             <button id="absent" v-if="!businessTab" v-on:click="goToBusinessPage()">Business Manager</button>
            <span></span>
              <button id="present" v-if="adminAccountTab" v-on:click="goToAccountPage()">My Account </button>
              <button id="absent" v-if="!adminAccountTab" v-on:click="goToAccountPage()">My Account </button>
            <span></span>
              <button id="logout" v-on:click="logout()">Logout</button>
            <span></span>
        </div>
    </nav>
</template>

<script>
import Router from "../router/index";

export default {
    name: "AdminHeader",

    data() {
      return {
        businessTab: false,
        homepageTab: false,
        appointmentManagerTab: false,
        serviceManagerTab: false,
        timeslotManagerTab: false,
        adminAccountTab: false
      };
    },
    created: function(){
        if(this.$router.history.current.path.localeCompare("/BusinessPage") === 0) {
            console.log(this.$router.history.current.path);
            this.businessTab = true;
            this.homepageTab = false;
            this.appointmentManagerTab = false;
            this.serviceManagerTab = false;
            this.timeslotManagerTab = false;
            this.adminAccountTab = false;
        } else if (this.$router.history.current.path.localeCompare("/AdminHomePage") === 0) {
            console.log(this.$router.history.current.path);
            this.businessTab = false;
            this.homepageTab = true;
            this.appointmentManagerTab = false;
            this.serviceManagerTab = false;
            this.timeslotManagerTab = false;
            this.adminAccountTab = false;
        } else if (this.$router.history.current.path.localeCompare("/AdminAppointmentPage") === 0) {
            console.log(this.$router.history.current.path);
            this.businessTab = false;
            this.homepageTab = false;
            this.appointmentManagerTab = true;
            this.serviceManagerTab = false;
            this.timeslotManagerTab = false;
            this.adminAccountTab = false;
        } else if (this.$router.history.current.path.localeCompare("/ServicePageAdmin") === 0) {
            console.log(this.$router.history.current.path);
            this.businessTab = false;
            this.homepageTab = false;
            this.appointmentManagerTab = false;
            this.serviceManagerTab = true;
            this.timeslotManagerTab = false;
            this.adminAccountTab = false;
        } else if (this.$router.history.current.path.localeCompare("/TimeSlotPage") === 0) {
            console.log(this.$router.history.current.path);
            this.businessTab = false;
            this.homepageTab = false;
            this.appointmentManagerTab = false;
            this.serviceManagerTab = false;
            this.timeslotManagerTab = true;
            this.adminAccountTab = false;
        } else if (this.$router.history.current.path.localeCompare("/AdminAccountPage") === 0) {
            console.log(this.$router.history.current.path);
            this.businessTab = false;
            this.homepageTab = false;
            this.appointmentManagerTab = false;
            this.serviceManagerTab = false;
            this.timeslotManagerTab = false;
            this.adminAccountTab = true;
        }
    },
    methods: {
        goToAdminHomePage: function (){
            Router.push({
                path: "/AdminHomePage",
                name: "AdminHomePage"
            })
        },
        goToAccountPage: function(){
            Router.push({
                path: "/AdminAccountPage",
                name: "AdminAccountPage"
            })
        },

        logout: function(){
          Router.replace({
            path: "/",
            name: "HomePage"})
        },

        goToAdminAppointmentPage: function(){
            Router.push({
                path: "/AdminAppointmentPage",
                name: "AdminAppointmentPage"
            })
        },

        goToTimeSlotPage: function(){
            Router.push({
                path: "/TimeSlotPage",
                name: "TimeSlotPage"
        })
        },
         goToBusinessPage: function(){
            Router.push({
                path: "/BusinessPage",
                name: "BusinessPage"
        })
        },
        goToServicePage: function (){
            Router.push({
                path: "/ServicePageAdmin",
                name: "ServicePageAdmin"
            })
        }
    }
}
</script>

<style scoped>
#header{
    background-color: #2373F7;
    display: flex;
    align-items: center;
    position: sticky;
    top: 0;
    z-index: 100;
    border-bottom-left-radius: 4px;
    border-bottom-right-radius: 4px;
}
.logo{
    border-radius: 20px;
    margin-left: 16px;
    height: 75px;
    width: 75px;
    margin: 4px;
}
#absent {
    background-color: Transparent;
    color: white;
    border: none;
    border-radius: 10px;
}
#absent:hover {
    background-color: firebrick;
    color: white;
    border: none;
    border-radius: 10px;
}
#present{
    background-color: firebrick;
    color: white;
    border: none;
    border-radius: 10px;
}

#logout{
    background-color: Transparent;
    color: white;
    border: none;
    border-radius: 10px;
}
#logout:hover {
    background-color: firebrick;
    color: white;
    border: none;
    border-radius: 10px;
}


#container{
    width: 65%;
    margin: 0 auto;
}
span{
  margin-left: 4px;
  margin-right: 4px;
}
</style>
