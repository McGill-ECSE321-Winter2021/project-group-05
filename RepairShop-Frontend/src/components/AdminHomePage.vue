<template>
    <div id="adminHomeHeader">
        <AdminHeader v-if="this.render"/>
        <div id="adminHome" v-if="this.render">

                     <div class="bookHeader__container">
                                    <h4 class="bookHeader">Welcome, Admin</h4>
                      </div>

                      <button id="Account"v-on:click=" " style="border-radius: 10px;">
                             Account</button>

                         <button id="business" v-on:click="" style="border-radius: 10px;">
                              Business Manager</button>

                           <button id="viewAppointmentcalendar"v-on:click=" " style="border-radius: 10px;">
                                                   Appointment Manager</button>

                            <button id="service"v-on:click=" " style="border-radius: 10px;">
                                                                        Service Manager </button>

                              <button id="logout"v-on:click=" " style="border-radius: 10px;">
                                                                        Logout </button>
        </div>
        <div v-if="!this.render">
           <label>Please login to continue</label>
        </div>
    </div>
</template>





<script>
import AdminHeader from './AdminHeader'

export default {
    name: "AdminHomePage",
    components: {
        AdminHeader
    },
    data() {
        return {
          render: true
        };
    },
    created: function() {
        if(localStorage.getItem('loggedInEmail').localeCompare("null") === 0){
           this.render = false;
           console.log(this.render);
        }
        else {
          this.render = true;
        }
      }
}
</script>

<style scoped>



#adminHome {
  background-color: white;
  align-items: center;
  display: flex;
  flex-direction: column;


  margin-top: 25px;
}


#Account {
  background-color: #2373F7;
  color: white;
  margin-top: 25px;

}
#business {
  background-color:#2373F7;
  color: white;
  margin-top: 25px;
}

#viewAppointmentcalendar {
  background-color:#2373F7;
  color: white;
  margin-top: 25px;
}

#service {
  background-color:#2373F7;
  color: white;
  margin-top: 25px;
}
#logout {
  background-color:firebrick;
  color: white;
  margin-top: 25px
}
</style>
