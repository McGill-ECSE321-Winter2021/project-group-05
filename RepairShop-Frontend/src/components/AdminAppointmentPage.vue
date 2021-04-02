<template>
    <div>
        <AdminHeader />

        <div id="container">
        <!---- technician select--->
        <div id="technician__container"> 
        <select v-model="technicianEmail"  class="custom-select custom-select-sm">
            <option v-bind:key="technician.email" v-for="technician in allTechnicians" v-bind:value="technician.email" selected>
                {{technician.email}}
            </option>
        </select>
        </div>

        <!--- appointment select--->
        <div id="appointment__container">
         <select v-model="appointmentId"  class="custom-select custom-select-sm">
            <option v-bind:key="appointment.id" v-for="appointment in allAppointments" v-bind:value="appointment.id" selected>
                {{appointment.id}}
            </option>
        </select>
        </div>

        <!-----assign button -->
        <div id="button__container">
            <button @click="assignAppointment(appointmentId, technicianEmail)" type="button" class="btn  btn-outline-primary btn-lg btn-block">
                Assign Appointment {{appointmentId}} to {{technicianEmail}}
            </button>
        </div>
        <!-- Table -->

       <table id="table__container" class="table table-striped table-dark table-hover">
            <thead>
                <tr>
                <th scope="col">Appointment id</th>
                <th scope="col">Appointment Date</th>
                <th scope="col">Appointment StartTime</th>
                <th scope="col">Appointment EndTime</th>
                <th scope="col">Technician Email</th>
                <th scope="col">Customer Email </th>
                </tr>
            </thead>
            <tbody>
                <tr v-bind:key="content.appointment.id" v-for="content in appointmentOfTechnicians">
                <th scope="row">{{content.appointment.id}}</th>
                <td>{{content.appointment.timeSlot.date}}</td>
                <td>{{content.appointment.timeSlot.startTime}}</td>
                <td>{{content.appointment.timeSlot.endTime}}</td>
                    <td>{{content.technician.email}}</td>
                <td>{{content.appointment.customer.email}} </td>
                </tr>
            </tbody>
        </table>
    </div>
    </div>
</template>

<script src="./AdminAppointment.js">
</script>

<style scoped>
#container{
    margin: 24px;
}

#technician__container{
    margin-bottom: 16px;
}

#appointment__container{
    margin-bottom: 16px;
}

#button__container{
    margin-bottom: 16px;
}

.button{
    border-radius: 22px;
}
</style>
