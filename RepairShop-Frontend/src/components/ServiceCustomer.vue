<template>
  <div id="CustomerService">
      <CustomerHeader />
    <label align=CENTER></label>

    <table align=CENTER id="servicesTable">

      <tr>
          <td>
              <h4 align=CENTER>Available services</h4>

          </td>
          <td v-if="returnedService">
              <h4 align=CENTER>About service</h4>
          </td>

      </tr>

      <tr>
          <h2 align=CENTER></h2>
      </tr>

      <tr>
          <td>
              <select v-model="selectedService" multiple size=6 @click="getServiceByName(selectedService)">
                   <option v-for="service in services">{{service.name}}</option>
              </select>

          </td>
          <td >
              <table v-if="returnedService">
                  <tr>
                      <td>
                          <label>Name</label>
                      </td>
                      <td>
                          <label align=CENTER>Cost</label>
                      </td>
                      <td>
                          <label align=CENTER>Duration</label>
                      </td>
                  </tr>
                  <tr>
                      <td>
                          <label>{{returnedService.name}}</label>
                      </td>
                      <td>
                          <label>{{returnedService.cost}}</label>
                      </td>
                      <td>
                          <label>{{returnedService.duration}}</label>
                      </td>
                  </tr>

              </table>
          </td>
      </tr>

      <tr>
        <td>
           <h2 align=CENTER></h2>
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
