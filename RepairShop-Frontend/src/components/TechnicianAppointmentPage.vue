<template>
    <div>
        <TechnicianHeader />
        <div id="container"> 
         <table class="table table-striped ">
            <thead class="table-dark " >
                <tr>
                <th scope="col">Appointment id</th>
                <th scope="col">Appointment Date</th>
                <th scope="col">Appointment StartTime</th>
                <th scope="col">Appointment EndTime</th>
                <th scope="col">Customer Email </th>
                </tr>
            </thead>
            <tbody>
                <tr v-bind:key="appointment.id" v-for="appointment in allAppointments">
                <th scope="row">{{appointment.id}}</th>
                <td>{{appointment.timeSlot.date}}</td>
                <td>{{appointment.timeSlot.startTime}}</td>
                <td>{{appointment.timeSlot.endTime}}</td>
                <td>{{appointment.customer.email}} </td>
                </tr>
            </tbody>
        </table>
        </div>
    </div>
</template>

<script src="./TechnicianAppointment.js">
</script>

<style scoped>
#container{
 margin-top: 20px;  
 margin: 8px;
 box-shadow: 0 2px 8px 0 rgba(0,0,0,0.2);
 transition: 0.3s; 
}
</style>