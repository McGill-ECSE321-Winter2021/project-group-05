<template>
    <div>
        <AdminHeader />
      <!--UPCOMING APPOINTMENS-->
      <div id="upcoming__container">
        <div class="bookHeader__container">
          <h4 class="bookHeader">Future Appointments</h4>
        </div>
        <table id="requested_appointments" class="table ">
          <thead>
          <tr>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Services</th>
          </tr>
          </thead>
          <tr>


          </tr>

        </table>
      </div>
    </div>

</template>

<script src="./AdminAppointmentPage.js">

</script>

<style scoped>
#upcoming__container{
  margin: 16px;
  border-radius: 16px;
  background-color: white;
}
.bookHeader__container{
  margin-top: 8px;
  height: 50px;
  margin-left: 50px;
}

</style>
