<template>
    <nav id="header">

        <img class ="logo" src="../assets/logo_draft1.png"  v-on:click="goToCustomerHomePage()"/>
        <div id="container">

            <button id="home" v-on:click="goToCustomerHomePage()" >Home</button>
            <span></span>
            <button id="appointment" v-on:click="goToCustomerAppointmentPage()">Appointment</button>
            <span></span>
            <button id="account" v-on:click="goToAccountPage()">My Account </button>
            <span></span>
            <button id="logout" v-on:click="logout()" >Logout</button>
            <span></span>
        </div>
    </nav>
</template>

<script>
import Router from "../router/index";

export default {
    name: "CustomerHeader",
    methods: {
        goToCustomerHomePage: function (){
            Router.push({
                path: "/CustomerHomePage",
                name: "CustomerHomePage"
            })
        },
        goToAccountPage: function(){
            Router.push({
                path: "/CustomerAccountPage",
                name: "CustomerAccountPage"
            })

        },

        logout: function(){
         Router.replace({path: "/",
           name: 'LoginPage'})

        },
        goToCustomerAppointmentPage: function(){
            Router.push({
                path: "/CustomerAppointmentPage",
                name: "CustomerAppointmentPage"
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
