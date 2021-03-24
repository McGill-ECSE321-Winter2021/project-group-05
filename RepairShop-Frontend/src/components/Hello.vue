<template>
  <div id="repairshop">
    <h2>RepairShop</h2>
    <table>
      <tr>
          <td>User</td>
        <tr v-for="customer in customers" >
        <td>{{ customer.email }}</td>
      </tr>
      <td>Services available</td>
      <tr>
        <td>
          <input type="text" v-model="newCustomer.username"  placeholder="username">
        </td>
        <td>
          <input type="text" v-model="newCustomer.email" placeholder="email">
        </td>
        <td>
          <input type="password" v-model="newCustomer.password" placeholder="password">
        </td>
        <td>
          <button v-bind:disabled="!newCustomer.email || !newCustomer.username || !newCustomer.password" @click="createCustomer(newCustomer.username,newCustomer.email,newCustomer.password)">Register</button>
        </td>
      </tr>

    </table>
  </div>
<!--
      <tr>
          <td>
              <input type="text" placeholder="Book an appointment">
          </td>
          <td>
              <button>Book</button>
          </td>
      </tr>
    </table>
    <p>
      <span v-if="errorCustomer" style="color:red">Error: {{errorCustomer}}</span>
    </p>
  </div>

  <tr>
    <td>
        <input type="text" v-model="newEvent.name" placeholder="Event Name">
    </td>
    <td>
      <input type="date" v-model="newEvent.eventDate" placeholder="YYYY-MM-DD">
    </td>
    <td>
      <input type="time" v-model="newEvent.startTime" placeholder="HH:mm">
    </td>
    <td>
      <input type="time" v-model="newEvent.endTime" placeholder="HH:mm">
    </td>
    <td>
      <button v-bind:disabled="!newEvent.name" v-on:click="createEvent(newEvent.name, newEvent.eventDate, newEvent.startTime, newEvent.endTime)">
        Book!</button>
    </td>
  </tr>


-->
</template>

<script src="./RepairShop.js">
</script>


<style>
  #repairshop {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>
