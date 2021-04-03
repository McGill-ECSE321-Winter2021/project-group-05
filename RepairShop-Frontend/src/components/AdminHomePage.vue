<template>
  <div id="container">
    <div id="adminHomeHeader">

        <AdminHeader />

    </div>
    <div >
      <WelcomePage />
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
