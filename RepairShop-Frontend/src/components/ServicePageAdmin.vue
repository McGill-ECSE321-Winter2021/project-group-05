<template>
  <div align=CENTER id="container">
      <AdminHeader v-if="this.render" />
    <div align=CENTER id="CreateService" v-if="this.render">


    <table cellpadding="10" align=CENTER id="availableServices" style="display: inline-block;margin-top: 20px" width="auto">
      <tr>
          <td>
              <h4 align=CENTER>Available services</h4>
          </td>

      </tr>

      <tr align=CENTER>
          <td>
              <select v-model="selectedService" multiple :size="services.length" @click="getServiceByName(selectedService)">
                  <option v-for="service in services" name="serviceList" style="width:100%;text-align: center;">
                      {{service.name}}
                  </option>
              </select>
          </td>


      </tr>
    </table>

    <table cellpadding="10" align=CENTER id="createServices" style="display: inline-block;" width="auto">
        <tr>
            <td>
                <h4 align=CENTER>Create Service</h4>
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" v-model="newService" placeholder="Enter name" size="25">
            </td>
        </tr>

        <tr>
            <td>
                <input type="number" step="0.01" v-model="newCost" placeholder="Enter cost" size="25">
            </td>
        </tr>

        <tr>
            <td>
                <input type="number" v-model="newDuration" placeholder="Enter duration (minutes)" size="25">
            </td>
        </tr>
        <tr align=CENTER>
            <td>
                <button align=CENTER style="background-color: #2373F7; color: white; margin-top: 16px; border-radius: 10px;" @click="createServiceAdmin(newService, newCost, newDuration)">Create</button>
            </td>
        </tr>
    </table>


    <table cellpadding="10" align=CENTER id="editServices" v-if="returnedService" style="display: inline-block;" width="auto">
        <tr>
            <td>
                <h4 align=CENTER>Edit service</h4>
            </td>

        </tr>
        <tr>
            <td>
                <input type="text" v-model="updatedName" :placeholder="returnedService.name"
                       style="width:200px">
            </td>
        </tr>
        <tr>
            <td>
                <input type="number" step="0.01" v-model="updatedCost" :placeholder="returnedService.cost"
                      size="25">
            </td>
        </tr>
        <tr>
            <td>
                <input type="number" v-model="updatedDuration" :placeholder="returnedService.duration" size="25">
            </td>
        </tr>
        <tr>
            <td align=CENTER>
                <button align=CENTER style="background-color: #2373F7; color: white; margin-top: 16px; border-radius: 10px;" @click="editService(returnedService.name, updatedName, updatedCost, updatedDuration)">Update</button>
                <button align=CENTER style="background-color: firebrick; color: white; margin-top: 16px; border-radius: 10px;" @click="deleteService(updatedName)">Delete</button>

            </td>
        </tr>
    </table>


    <p align=CENTER>
      <!--<span align=CENTER v-if="errorCreateService" style="color:red">{{errorCreateService}}</span>
      <span align=CENTER v-if="errorEditService" style="color:red">{{errorEditService}}</span>
      <span align=CENTER v-if="errorDeleteService" style="color:red">{{errorDeleteService}}</span>-->
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

  }
  input{
    width: 200px;
  }

  select{
    width:100%;
  }


</style>
