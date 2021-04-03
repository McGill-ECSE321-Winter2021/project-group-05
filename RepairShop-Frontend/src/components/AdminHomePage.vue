<template>
  <div id="container">
    <div id="adminHomeHeader" v-if="this.render">

        <AdminHeader />

    </div>
    <div v-if="this.render">
      <WelcomePage />
    </div>
     <div v-if="!this.render">
       <label>Please login to continue</label>
     </div>
  </div>
</template>





<script>
import AdminHeader from './AdminHeader'
import WelcomePage from '../components/WelcomePage.vue'
export default {
    name: "AdminHomePage",
    components: {

        AdminHeader,
        WelcomePage
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
