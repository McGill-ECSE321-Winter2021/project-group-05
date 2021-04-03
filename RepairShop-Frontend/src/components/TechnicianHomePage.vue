<template>
    <div>
        <div v-if="this.render">
            <TechnicianHeader v-if="this.render"/>
            <div id="techHome" align=CENTER>

              <div class="bookHeader__container">
                  <h4 class="bookHeader">Welcome, Technician</h4>
              </div>
              <button id="Account"v-on:click=" " style="border-radius: 10px;">Account</button>
              <button id="viewAppointmentcalendar" v-on:click=" " style="border-radius: 10px;">Appointment Calendar</button>
              <button id="logout"v-on:click=" " style="border-radius: 10px;">Logout </button>
            </div>
            </div>
    <div v-if="!this.render">
      <label>Please login to continue</label>
    </div>
    </div>
</template>

<script>
import TechnicianHeader from './TechnicianHeader'

export default {
    name: "TechnicianHomePage",
    components: {
        TechnicianHeader
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

#techHome {
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


#viewAppointmentcalendar {
  background-color:#2373F7;
  color: white;
  margin-top: 25px;
}

#logout {
  background-color:firebrick;
  color: white;
  margin-top: 25px;
}
</style>
