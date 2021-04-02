<template>
  <div id="container">
    <AdminHeader id="header"/>
    <!---- technician select--->
    <div id="technician__container">
      <select v-model="technicianEmail"  class="custom-select custom-select-sm">
        <option v-bind:key="technician.email"
                v-for="technician in allTechnicians" v-bind:value="technician.email" selected>
          {{technician.email}}
        </option>
      </select>
    </div>

    <div id="dateAndService__container">
      <div id="date__container">
        <VueCtkDateTimePicker v-model="date" />
      </div>
    </div>

    <!-----assign button -->
    <div id="button__container">
      <button @click="createTimeSlotAndAssign(appointmentId, technicianEmail)" type="button"
              class="btn  btn-outline-primary btn-lg btn-block">
        Assign Appointment {{appointmentId}} to {{technicianEmail}}
      </button>
    </div>

  </div>
</template>

<script src="./TimeSlotPage.js">

</script>

<style scoped>
#technician__container{
  margin-bottom: 16px;
}

</style>
