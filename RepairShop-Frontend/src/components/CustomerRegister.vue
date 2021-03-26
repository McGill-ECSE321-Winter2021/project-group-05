<template>
  <div id="repairshop">
  <h1>RepairShop</h1>
  <h2>Create Account</h2>
  <table class="center">
    <tr>
      <td>
        <input type="text"  v-model="customer.username" placeholder="username">
      </td>
    </tr>
    <tr>
      <td>
        <input type="text" v-model="customer.email" placeholder="email">
      </td>
    </tr>
    <tr>
      <td>
        <input type="password"  v-model="customer.password" placeholder="password">
      </td>
    </tr>
    <tr>
      <button v-bind:disabled= "!customer.username || !customer.password || !customer.email" v-on:click="createCustomer(customer.username,customer.email,customer.password)">
        Register</button>
    </tr>


  </table>
  </div>
</template>

<script src="./RepairShop.js">

</script>


<style>
#repairshop {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}

.center{
  margin-top: auto;
  margin-bottom: auto;
  margin-left: auto;
  margin-right: auto;
}
table {
  border-spacing: 30px;
  border-collapse: separate;
}
</style>

