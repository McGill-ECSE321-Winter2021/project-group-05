<template>
  <div id="customerRegister">
      <div id="left">
        <img class ="logo"  src="../assets/repairShop.png">
      </div>

      <div id="right">
  <table>
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
      <router-link to = "/homePage_customer/"><button v-bind:disabled= "!customer.username || !customer.password || !customer.email" v-on:click="createCustomer(customer.username,customer.email,customer.password)">
        Register</button></router-link>
    </tr>


  </table>
    </div>
    </div>
</template>

<script src="./CustomerRegister.js">

</script>


<style>
#customerRegister {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
  width: 100%;
  height: 100%;
  margin-top: 300px;
}

#right{

  margin-left: 50%;
  height: 50%;
  margin-top: 300px;
}
#left{

  width: 50%;
  height: 50%;
  float: left;

}
table {
  border-spacing: 30px;
  border-collapse: separate;
}
.logo{
  width:300px;
  height:300px;
}
</style>

