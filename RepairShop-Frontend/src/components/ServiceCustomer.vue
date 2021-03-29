<template>
  <div id="CustomerService">
      <CustomerHeader />
    <label align=CENTER></label>

    <table align=CENTER id="servicesTable">

      <tr>
          <h4 align=CENTER>Available services</h4>
      </tr>

      <tr>
          <h2 align=CENTER></h2>
      </tr>


      <tr>

          <select multiple size=6>
               <option v-for="service in services">{{service.name}}</option>
          </select>
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
