<template>
    <nav id="header">

        <img class ="logo" src="../assets/logo_draft1.png"  v-on:click="goToCustomerHomePage()"/>
        <div id="container">

              <button id="present" v-if="homeTab" v-on:click="goToCustomerHomePage()">Home</button>
              <button id="absent" v-if="!homeTab" v-on:click="goToCustomerHomePage()">Home</button>
            <span></span>
              <button id="present" v-if="appointmentTab" v-on:click="goToCustomerAppointmentPage()">Appointment</button>
              <button id="absent" v-if="!appointmentTab" v-on:click="goToCustomerAppointmentPage()">Appointment</button>
            <span></span>
              <button id="present" v-if="customerAccountTab" v-on:click="goToAccountPage()">My Account </button>
              <button id="absent" v-if="!customerAccountTab" v-on:click="goToAccountPage()">My Account </button>
            <span></span>
              <button id="logout" v-on:click="logout()">Logout</button>
            <span></span>
        </div>
    </nav>
</template>

<script>
import Router from "../router/index";

export default {
    name: "CustomerHeader",

    data() {
          return {
            homeTab: false,
            appointmentTab: false,
            customerAccountTab: false
          };
    },

    created: function(){
        if(this.$router.history.current.path.localeCompare("/") === 0) {
            console.log(this.$router.history.current.path);
            this.homeTab = true;
            this.appointmentTab = false;
            this.customerAccountTab = false;
        } else if (this.$router.history.current.path.localeCompare("/CustomerAppointmentPage") === 0) {
            console.log(this.$router.history.current.path);
            this.homeTab = false;
            this.appointmentTab = true;
            this.customerAccountTab = false;
        } else if (this.$router.history.current.path.localeCompare("/CustomerAccountPage") === 0) {
            console.log(this.$router.history.current.path);
            this.homeTab = false;
            this.appointmentTab = false;
            this.customerAccountTab = true;
        }

    },


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
         localStorage.setItem('loggedInEmail', "null");
         console.log(localStorage.getItem('loggedInEmail'));
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
    width: 50%;
    margin: 0 auto;
}
span{
  margin-left: 40px;
  margin-right: 40px;
}
</style>
