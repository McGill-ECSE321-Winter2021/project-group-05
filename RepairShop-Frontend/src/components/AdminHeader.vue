<template>
    <nav id="header">
        <img class ="logo" src="../assets/logo_draft1.png" v-on:click="goToAdminHomePage()"/>
        <div id="container">
            <button id="home" v-on:click="goToAdminHomePage()" >Home</button>
            <span></span>
            <button id="appointment" v-on:click="goToAdminAppointmentPage()">Appointment Manager</button>
            <span></span>
             <button id="service" v-on:click="goToServicePage()">Service Manager</button>
            <span></span>
             <button id="timeslot" v-on:click="goToTimeSlotPage()">TimeSlot Manager</button>
            <span></span>
             <button id="business" v-on:click="goToBusinessPage()">Business Manager</button>
            <span></span>
            <button id="account" v-on:click="goToAccountPage()">My Account </button>
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
            Router.push({
                path: "/",
                name: "LoginPage"
            })
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
button {
    background-color: Transparent;
    color: white;
    border: none;
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
