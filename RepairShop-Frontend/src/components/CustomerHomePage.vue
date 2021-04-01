<template>
  <div id="customerHome">
      <CustomerHeader />


              <div id="customerHome">

                           <div class="bookHeader__container">
                                          <h4 class="bookHeader">Welcome, Customer</h4>
                            </div>

                            <button id="Account"v-on:click=" " style="border-radius: 10px;">
                                   Account</button>

                               <button id="makeAppointments" v-on:click="" style="border-radius: 10px;">
                                    Make Appointments</button>

                                 <button id="viewAppointments"v-on:click=" " style="border-radius: 10px;">
                                                         View my Appointments</button>

                                    <button id="logout"v-on:click=" " style="border-radius: 10px;">
                                                                              Logout </button>
              </div>
          </div>
  </div>
</template>

<script>
import CustomerHeader from './CustomerHeader'

export default {
  name: "CustomerHomePage",
  components: {
    CustomerHeader
  }
}

</script>

<style scoped>




#customerHome {
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
#makeAppointments {
  background-color:#2373F7;
  color: white;
  margin-top: 25px;
}

#viewAppointments {
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
