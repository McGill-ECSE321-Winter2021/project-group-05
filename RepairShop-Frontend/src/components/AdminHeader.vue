<template>
    <nav id="header">
        <img class ="logo" src="../assets/logo_draft1.png" v-on:click="goToAdminHomePage()"/>
        <div id="container">
            <button id="homepagePresent" v-if="homepageTab" v-on:click="goToAdminHomePage()">Home</button>
            <button id="homepageAbsent" v-if="!homepageTab" v-on:click="goToAdminHomePage()">Home</button>
            <span></span>
            <button id="appointmentPresent" v-if="appointmentManagerTab" v-on:click="goToAdminAppointmentPage()">Appointment Manager</button>
            <button id="appointmentAbsent" v-if="!appointmentManagerTab" v-on:click="goToAdminAppointmentPage()">Appointment Manager</button>
            <span></span>
             <button id="servicePresent" v-if="serviceManagerTab" v-on:click="goToServicePage()">Service Manager</button>
             <button id="serviceAbsent" v-if="!serviceManagerTab" v-on:click="goToServicePage()">Service Manager</button>
            <span></span>
             <button id="timeslotPresent" v-if="timeslotManagerTab" v-on:click="goToTimeSlotPage()">TimeSlot Manager</button>
             <button id="timeslotAbsent" v-if="!timeslotManagerTab" v-on:click="goToTimeSlotPage()">TimeSlot Manager</button>
            <span></span>
             <button id="businessPresent" v-if="businessTab" v-on:click="goToBusinessPage()">Business Manager</button>
             <button id="businessAbsent" v-if="!businessTab" v-on:click="goToBusinessPage()">Business Manager</button>
            <span></span>
            <button id="accountPresent" v-if="adminAccountTab" v-on:click="goToAccountPage()">My Account </button>
            <button id="accountAbsent" v-if="adminAccountTab" v-on:click="goToAccountPage()">My Account </button>
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
          Router.replace({path: "/",
            name: 'LoginPage'})
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
#businessAbsent {
    background-color: Transparent;
    color: white;
    border: none;
    border-radius: 10px;
}
#businessPresent{
    background-color: firebrick;
    color: white;
    border: none;
    border-radius: 10px;
}
#homepageAbsent {
    background-color: Transparent;
    color: white;
    border: none;
    border-radius: 10px;
}
#homepagePresent{
    background-color: firebrick;
    color: white;
    border: none;
    border-radius: 10px;
}
#appointmentAbsent {
    background-color: Transparent;
    color: white;
    border: none;
    border-radius: 10px;
}
#appointmentPresent{
    background-color: firebrick;
    color: white;
    border: none;
    border-radius: 10px;
}
#serviceAbsent {
    background-color: Transparent;
    color: white;
    border: none;
    border-radius: 10px;
}

#servicePresent{
    background-color: firebrick;
    color: white;
    border: none;
    border-radius: 10px;
}
#timeslotAbsent {
    background-color: Transparent;
    color: white;
    border: none;
    border-radius: 10px;
}
#timeslotPresent{
    background-color: firebrick;
    color: white;
    border: none;
    border-radius: 10px;
}
#accountAbsent {
    background-color: Transparent;
    color: white;
    border: none;
    border-radius: 10px;
}
#accountPresent{
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

button:hover {
    background-color: firebrick;
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
