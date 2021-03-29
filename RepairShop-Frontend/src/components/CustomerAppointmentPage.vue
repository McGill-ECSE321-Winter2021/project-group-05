<template>
    <div id="container">
        <CustomerHeader id="header"/>
        <div id="appointmentpage__container">
            <div class="bookHeader__container">
                <h4 class="bookHeader">Book Appointment</h4>
            </div>
            <div id="dateAndService__container">
                <div id="date__container">
                    <VueCtkDateTimePicker v-model="date" />
                </div>
                <div id="selectdiv__container">
                   <select id="select__container" v-model="services" multiple >
                        <option>Wash</option>
                        <option>Renter</option>
                        <option>Driver</option>
                        <option>Fast food</option>
                    </select>
                </div>
            </div>
              <!-- services selected-->
            <div id="serviceList__container">
                <span>Services selected: {{services }}</span>
            </div>

            <div id="bookbtn__container">
                <button id="bookbtn">Book an appointment</button>
            </div>
        </div>
        <div id="upcoming__container">
              <div class="bookHeader__container">
                 <h4 class="bookHeader">Upcoming Appointments</h4>
              </div>
            <table id="requested_appointments" class="table shadow">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Edit Appointment</th>
                    <th>Cancel Appointment</th>
                </tr>
                </thead>
                <tbody>
                <tr v-bind:key="item.first_name" v-for="item in items">
                <td>{{ item.date }}</td>
                <td>{{ item.start_time }}</td>
                <td>{{ item.end_time }}</td>
                <td>
                    <button @click="editAppointment()" class="update__button" id="edit__button">Edit</button>
                </td>
                <td>
                    <button @click="cancelAppointment()" class="update__button" id="cancel__button">Cancel</button>
                </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div id="past__container">
            <b-table striped hover :items="items"></b-table>
        </div>
    </div>
</template>

<script src="./CustomerAppointmentPage.js">
</script>

<style scoped>
#header{
    box-shadow: 0 2px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
}
#container{
}
#appointmentpage__container{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin: 16px;
    box-shadow: 0 2px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
    border-radius: 16px;
    background-color: white;
}
#upcomingappontmenpage__container{
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    margin: 16px;
    box-shadow: 0 2px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
    border-radius: 4px;
    background-color: white;
    justify-content: space-around;
}

#date__container{
    width: 25%;
    border-color: transparent;
}

#bookbtn__container{
    width: 25%;
    align-self: center;
    display: flex;
}
#bookbtn{
    outline:none;
    background-color: #2273F7;
    width: 50%;
    margin: 0 auto;
    color: white;
    border-radius: 8px;
    border-color: #2273F7;
    margin-bottom: 8px;
}

.bookHeader__container{
    margin-top: 8px;
    height: 50px;
    margin-left: 50px;
}

.bookHeader{
    margin: 0 auto;
    width: 20%;
    font-family: Arial, Helvetica, sans-serif;
}
#date__container{
    margin-left: 16px;
}

#dateAndService__container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

#select__container{
    width: 500px;
    margin-right: 16px;
}

#selectdiv__container{
   align-items: flex-end;
}

#serviceList__container{
    font-family: Arial, Helvetica, sans-serif;
    margin: 0 auto;
    margin-bottom: 16px;
    margin-top: 8px;
}

#upcoming__container{
    margin: 16px;
    box-shadow: 0 2px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
    border-radius: 16px;
    background-color: white;
}

h5{
    margin-top: 8px;
}

#edit__button{
    background-color: #2273F7;
}

#cancel__button{
    background-color: #fc4949;
}
.update__button{
    border-radius: 4px;
    color: white;
}

#past__container{
    margin: 16px;
    box-shadow: 0 2px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
    border-radius: 16px;
    background-color: white;
}


</style>