<template>
    <div id="container">
        <CustomerHeader id="header" v-if="this.render"/>
        <div id="appointmentpage__container" v-if="this.render">
            <div class="bookHeader__container">
                <h4 class="bookHeader">Book Appointment</h4>
            </div>
            <div id="dateAndService__container">
                <div id="date__container">
                    <VueCtkDateTimePicker v-model="date" />
                </div>
                <div id="selectdiv__container">
                   <div>
                        <!-- <b-form-select id="select__container" multiple v-model="updatedServices" :options="allServiceNames" :select-size="8"></b-form-select> -->
                        <b-form-checkbox-group
                            v-model="updatedServices"
                            :options="allServiceNames"
                            class="mb-3"
                            value-field="item"
                            text-field="name"
                            disabled-field="notEnabled"
                            stacked
                        >
                        </b-form-checkbox-group>
                    </div>

                </div>
            </div>
              <!-- services selected-->
            <div id="serviceList__container">
                <span>Services selected: {{updatedServices }}</span>
            </div>

            <div id="bookbtn__container">
                <b-button block id="bookbtn" @click="showBillModal()">Book an appointment</b-button>
                <b-modal
                 v-model="showBill"
                 title="Payment Information"
                 @ok="handleBillOk()"
                 >
                    <p id="cost__container">Cost of Appointment <strong>{{cost}}</strong></p>
                       <b-form-group
                        label="Name On Credit Card"
                        label-for="name-input"
                        invalid-feedback="Name is required">
                            <b-form-input
                                id="name-input"
                                v-model="username"
                                required>
                            </b-form-input>
                       </b-form-group>

                        <b-form-group
                        label="Card Number"
                        label-for="name-input"
                        invalid-feedback="Card Number is required">
                            <b-form-input
                                id="name-input"
                                v-model="cardNumber"
                                required>
                            </b-form-input>
                        </b-form-group>

                        <b-form-group
                        label="CVV"
                        label-for="name-input"
                        invalid-feedback="CVV is required">
                            <b-form-input
                                v-model="cvv"
                                id="name-input"
                                required>
                            </b-form-input>
                        </b-form-group>

                        <b-form-group
                        label="Date (YYYY-MM-DD)"
                        label-for="name-input"
                        invalid-feedback="Date is required">
                            <b-form-input
                                id="name-input"
                                v-model="expiry"
                                required>
                            </b-form-input>
                        </b-form-group>
                <!----payement information--->
                    <form ref="form" @submit.stop.prevent="handleSubmit">

                    </form>
                </b-modal>
            </div>
        </div>

        <!--UPCOMING APPOINTMENTS-->

        <div id="upcoming__container" v-if="this.render">
              <div class="bookHeader__container">
                 <h4 class="bookHeader">Future Appointments</h4>
              </div>
            <table id="requested_appointments" class="table table-hover table-bordered border-primary" >
                <thead class="table-dark">
                <tr>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Services</th>
                    <th>Edit Appointment</th>
                    <th>Cancel Appointment</th>

                </tr>
                </thead>
                <tbody>
                <tr v-bind:key="appointment.id" v-for="appointment in upcomingAppointments">
                <td> <strong>{{ appointment.timeSlot.date }}</strong></td>
                <td><strong>{{ appointment.timeSlot.startTime }}</strong></td>
                <td><strong>{{ appointment.timeSlot.endTime }}</strong></td>
                <td>
                    <div  v-bind:key="service.id" v-for="service in appointment.services">
                        <tr><strong>{{service.name}}</strong></tr>
                    </div>
                </td>
                <td>
                    <!---EDIT APPOINTMENT----->

                    <button v-b-modal.appointment.id @click="editAppointment(appointment.id)" class="update__button" id="edit__button">Edit</button>
                    <b-modal v-model="show" title="Edit Appointment" @ok="handleEdit(appointment.id, updatedDate)">
                            <b-container fluid>
                                <b-row class="mb-4">
                                <b-col cols="2">Date</b-col>

                                <b-col>
                                <div>
                                    <b-form-select v-model="updatedDate" :options="allTimeSlotsDates"  value-field="id" text-field="date"></b-form-select>
                                    <div class="mt-3">Selected Timeslot: <p>{{ updatedDate }}</p></div>
                                </div>
                                </b-col>
                                </b-row>

                                <b-row class="mb-4">
                                <b-col cols="2">Services</b-col>

                                <b-col>
                                <div>
                                    <b-form-select multiple v-model="updatedServices" :options="allServiceNames" :select-size="8"></b-form-select>
                                    <div class="mt-3">Selected Services: <p>{{ updatedServices }}</p></div>
                                </div>
                                </b-col>
                                </b-row>

                            </b-container>
                    </b-modal>
                </td>

                <!--CANCEL APPOINTMENT--->

                <td>
                    <button @click="cancelAppointment(appointment.id)" class="update__button" id="cancel__button">Cancel</button>
                    <b-modal  v-model="showCancel" title="Cancel Appointment" @ok="handleCancel(appointment.id)">
                        <div class="d-block text-center">
                          <h3 id="cancel__warning">Are you sure you want to cancel this appointment?</h3>
                        </div>
                    </b-modal>
                </td>
                </tr>
                </tbody>
            </table>
        </div>

        <!---PAST APPOINTMENT-->
        <div id="past__container" v-if="this.render">
            <div class="bookHeader__container">
                <h4 class="bookHeader">Past Appointment</h4>
            </div>
            <b-table striped hover :items="pastAppointments"></b-table>
        </div>
      <div v-if="!this.render">
         <label>Please login to continue</label>
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
    background-color: lightgray;
}

#cancel__warning{
    color: red;
}

#cost__container{
    font-size: 25;
    margin-left: 120px;
}
#appointmentpage__container{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin: 16px;
    background-color: white;
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
    border-radius: 8px;
}
#upcomingappontmenpage__container{
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    margin: 16px;
    background-color: white;
    justify-content: space-around;
}

#date__container{
    width: 25%;
    border-color: transparent;
}

#bookbtn__container{

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
    padding-top: 8px;
    margin: 0 auto;
    width: 20%;
    font-family: Arial, Helvetica, sans-serif;
}
#date__container{
    /* margin-left: 16px; */
}

#dateAndService__container{
    display: flex;
    flex-direction: row;
    margin-left: 50px;
    margin-right: 50px;
    justify-content:center
}

#select__container{
    width: 500px;
    margin-right: 16px;
}

#selectdiv__container{
   /* align-items: flex-end; */
   padding-left: 100px;
   padding-right: 100px;

}

#serviceList__container{
    font-family: Arial, Helvetica, sans-serif;
    margin: 0 auto;
    margin-bottom: 16px;
    margin-top: 8px;
}

#upcoming__container{
    margin: 16px;
    border-radius: 16px;
    background-color: white;
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
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
    border-radius: 16px;
    background-color: white;
}


</style>
