<template>
    <nav id="header">

        <img class ="logo" src="../assets/logo_draft1.png" />
        <div id="container">

            <button id="home" v-on:click="goToTechnicianHomePage()" >Home</button>
            <span></span>
            <button id="appointment" v-on:click="goToTechnicianAppointmentPage()">Appointment</button>
            <span></span>
            <button id="account" v-on:click="goToTechnicianAccountPage()">My Account</button>
            <span></span>
            <button id="logout" v-on:click="logout()">Logout</button>
            <span></span>
        </div>
    </nav>
</template>

<script>
import Router from "../router/index";

export default {
    name: "TechnicianHeader",
    methods: {
        goToTechnicianAppointmentPage: function (){
            Router.push({
                path: "/TechnicianAppointmentPage",
                name: "TechnicianAppointmentPage"
            })
        },
        goToTechnicianAccountPage: function(){
            Router.push({
                path: "/TechnicianAccountPage",
                name: "TechnicianAccountPage"
            })
        },

        logout: function(){
            Router.push({
                path: "/",
                name: "LoginPage",
            })
        },

        goToTechnicianHomePage: function(){
            Router.push({
                path: "/TechnicianHomePage",
                name: "TechnicianHomePage"
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
button:hover {
    background-color: firebrick;
    border-radius: 10px;
}

#container{
    width: 50%;
    margin: 0 auto;
}
span{
  margin-left: 40px;
  margin-right: 40px;
}
</style>
