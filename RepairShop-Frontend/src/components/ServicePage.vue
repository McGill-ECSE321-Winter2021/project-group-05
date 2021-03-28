<template>
  <div id="CreateService">
      <AdminHeader />
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
    <Button align=CENTER @click="createServiceAdmin(newService, newCost, newDuration)">Create</Button>
    <p>
      <span v-if="errorCreateService" style="color:red">{{errorCreateService}}</span>
    </p>
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