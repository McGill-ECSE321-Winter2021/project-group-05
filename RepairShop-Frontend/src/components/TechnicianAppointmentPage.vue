<template>
    <div>
        <TechnicianHeader />
        <div>
         <table id="table__container" class="table table-striped table-dark table-hover">
            <thead>
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

</style>