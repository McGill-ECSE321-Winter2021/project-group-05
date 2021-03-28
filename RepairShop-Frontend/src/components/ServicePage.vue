<template>
  <div id="CreateService">
      <AdminHeader />
    <h2 align=CENTER>Create service</h2>
    <label align=CENTER></label>
    <label align=CENTER> </label>
    <table align=CENTER>

      <tr>
          <td>
              <h4 align=CENTER>Create service</h4>
          </td>

          <td>
          </td>

          <td>
          </td>

          <td>

              <select>
                  <option v-for="service in services" name="serviceList">
                    {{service.name}}
                  </option>
              </select>
          </td>
      </tr>


      <tr>
          <input type="text" v-model="newService" placeholder="Enter name">
      </tr>
      <tr>
          <input type="number" step="0.01" v-model="newCost" placeholder="Enter cost">
      </tr>
      <tr>
          <input type="number" v-model="newDuration" placeholder="Enter duration (minutes)" size="25">
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
