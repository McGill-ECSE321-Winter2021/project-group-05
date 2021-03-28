<template>
  <div id="CustomerService">
      <CustomerHeader />
    <label align=CENTER></label>
    <label align=CENTER> </label>
    <table align=CENTER>

      <tr>
          <td>
              <h4 align=CENTER>Available services</h4>
          </td>
      </tr>

      <tr>

          <td>
              <select>
                  <option disabled selected value="">Select a service</option>
                  <option v-for="service in services" name="serviceList" style="width:150px">
                      {{service.name}}
                  </option>
              </select>
          </td>
      </tr>

    </table>

    <p align=CENTER>

      <button align=CENTER >Select</button>

    </p>


    <p align=CENTER>
      <span align=CENTER v-if="errorCreateService" style="color:red">{{errorCreateService}}</span>
    </p>
  </div>
</template>

<script src="./viewServiceCustomer.js">
</script>


<style>
  #CustomerService {
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
