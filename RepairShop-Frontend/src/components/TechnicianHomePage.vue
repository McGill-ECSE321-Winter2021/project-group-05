<template>
   <div>
      <TechnicianHeader v-if="this.render"/>
    <div v-if="this.render">
       <WelcomePage  />
    </div>
    <div v-if="!this.render">
      <label>Please login to continue</label>
    </div>
   </div>

</template>

<script>
import TechnicianHeader from './TechnicianHeader'
import WelcomePage from '../components/WelcomePage.vue'

export default {
    name: "TechnicianHomePage",
    components: {

        TechnicianHeader,
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

</style>
