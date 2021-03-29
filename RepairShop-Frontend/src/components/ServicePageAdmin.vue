<template>
  <div id="CreateService">
      <AdminHeader />
    <label align=CENTER></label>
    <label align=CENTER> </label>
    <table align=CENTER>

      <tr>
          <td>
              <h4 align=CENTER>Create service</h4>
          </td>

          <td>
              <h4 align=CENTER>Available services</h4>
          </td>
      </tr>


      <tr>
          <input type="text" v-model="newService" placeholder="Enter name">

          <td>
              <select>
                  <option disabled selected value="">Available services</option>
                  <option v-for="service in services" name="serviceList" style="width:670px">
                      {{service.name}}
                  </option>
              </select>
          </td>
      </tr>
      <tr>
          <input type="number" step="0.01" v-model="newCost" placeholder="Enter cost">
      </tr>
      <tr>
          <input type="number" v-model="newDuration" placeholder="Enter duration (minutes)" size="25">
      </tr>

      <tr>
          <button align=CENTER @click="createServiceAdmin(newService, newCost, newDuration)">Create</button>
      </tr>


    </table>

    <p align=CENTER>
      <span align=CENTER v-if="errorCreateService" style="color:red">{{errorCreateService}}</span>
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

  td {
    width: 250px;
    text-align: center;
    padding: 1px;
  }
</style>
