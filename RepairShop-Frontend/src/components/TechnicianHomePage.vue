<template>
    <div id="techHome">
            <TechnicianHeader />
            <div id="techHome">

                         <div class="bookHeader__container">
                                        <h4 class="bookHeader">Welcome, Technician</h4>
                          </div>

                          <button id="Account"v-on:click=" " style="border-radius: 10px;">
                                 Account</button>



                               <button id="viewAppointmentcalendar"v-on:click=" " style="border-radius: 10px;">
                                                       Appointment Calendar</button>


                                  <button id="logout"v-on:click=" " style="border-radius: 10px;">
                                                                            Logout </button>
            </div>
        </div>
</template>

<script>
import TechnicianHeader from './TechnicianHeader'

export default {
    name: "TechnicianHomePage",
    components: {
        TechnicianHeader
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
   margin-left: 350px;
}
</style>
