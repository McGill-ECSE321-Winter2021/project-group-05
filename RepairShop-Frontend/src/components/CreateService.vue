<template>
  <div>
    <div id="CreateService" v-if="this.render">
    <h2>RepairShop</h2>
    <label align=CENTER>Create new service</label>
    <label align=CENTER> </label>
    <table align="CENTER">

      <tr v-for="service in services">
          <td>{{service.name}}</td>
          <td>{{service.cost}}</td>
          <td>{{service.duration}}</td>
      </tr>

      <tr>
          <td>
              <input type="text" v-model="newService" placeholder="Enter name">
          </td>
          <td>
              <input type="number" step="0.01" v-model="newCost" placeholder="Enter cost">
          </td>
          <td>
              <input type="number" v-model="newDuration" placeholder="Enter duration">
          </td>
      </tr>


    </table>
    <Button align=CENTER v-bind:disabled="!newService && !newCost && !newDuration" @click="createServiceAdmin(newService, newCost, newDuration)">Create</Button>
    <p>
      <span v-if="errorCreateService" style="color:red">Error: {{errorCreateService}}</span>
    </p>
    </div>
    <div v-if="!this.render">
       <label>Please login to continue</label>
    </div>
  </div>
</template>

<script src="./createServiceAdmin.js">
</script>


<style>
  #CreateService {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>
