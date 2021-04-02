<template>
    <div>
        <AdminHeader />
        <div id="page__container">
            <div id="assign__container">
                <div id="appointments">
                     <select id="appointment__select" v-model="appointmentId">
                        <option v-bind:key="appointment.id" v-for="appointment in allAppointments" >
                            {{appointment.id}}
                        </option>
                    </select>
                </div>
                <button id="assign__button">assign</button>
                <div id="technicians" >
                    <select v-model="technicianEmail">
                        <option v-bind:key="technician.email" v-for="technician in allTechnicians" v-bind:value="technician.email">
                            {{technician.email }}
                        </option>
                    </select>
                </div>
            </div>
        </div>

        <!-- Table -->
        <table>
            <thead>
            <tr>
                <th>Technician</th>    
                <th>Date</th>
                <th>availabiltiy</th>
                <th>appointmendId</th>
            </tr>
            </thead>
        </table>

    </div>
</template>

<script src="./AdminAppointment.js">
</script>

<style scoped>
#appointment__select{
    width: 1000px;
}
</style>