<template>
  <div id="customerRegister">
    <AuthHeader v-bind:pageTitle="pageTitle"/>
    <div id="bottom">
  <table>
    <tr>
      <td>Username:</td>
      <td colspan="2">
        <input type="text"  v-model="customer.username" placeholder="username">
      </td>
    </tr>

    <tr>
      <td>Email Address:</td>
      <td colspan="2">
        <input type="text"  v-model="customer.email"  placeholder="email">
      </td>
    </tr>

    <tr>
      <td>Password:</td>
      <td colspan="2">
        <input type="password"  v-model="customer.password"  placeholder="password">
      </td>
    </tr>

    <tr>
      <td>Confirm password:</td>
      <td colspan="2">
        <input type="password"  v-model="customer.confirmPass"  placeholder="password">
      </td>
    </tr>



    <tr>
      <td></td>
      <router-link to = "/homePage_customer/"><button
        v-bind:disabled= "!customer.username || !customer.password || !customer.email || !customer.confirmPass"
                                                      v-on:click="createCustomer(customer.username,customer.email,customer.password)">
        Sign up</button></router-link>
    </tr>


  </table>
    </div>



  </div>

</template>

<script src="./CustomerRegister.js">

</script>


<style>
body{background: #F6F6F6;}
#customerRegister {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;

  width: 100%;
  height: 100%;
}

#bottom{
  margin-top: 15%;
  margin-left: 30%;
  margin-right: 30%;



}

#top{
  position: relative;

  width: 100%;
  height: 20%;
  float: top;
  background-color:#2373F7;

}
table {
  position: relative;
  border-spacing: 30px;
  border-collapse: separate;
}
.logo{
  position: relative;
  width:100px;
  height:100px;
  margin-top: 20px;
  margin-bottom: 20px;
  margin-right:60%;
}
.slogan{
  position: relative;
  width:60%;
  height:400%;

  margin-top: -100px;

  margin-left: 40%;
}
</style>

