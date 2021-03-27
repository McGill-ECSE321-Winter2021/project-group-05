<template>


  <div id="customerLogin">
    <div id="top">
      <img class ="logo" src="../assets/repairShop.png">
      <h1 class="slogan">Welcome to repairshop, <br/>
        Your satisfaction is our top concern</h1>
    </div>

    <div id="bottom">
      <table>
        <tr>
          <td>Username:</td>
          <td colspan="2">
            <input type="text"  v-model="currentUser.username" placeholder="username">
          </td>
        </tr>
        <tr>
          <td>Password:</td>
          <td colspan="2">
            <input type="password"  v-model="currentUser.password"  placeholder="password">
          </td>
        </tr>
        <tr>
          <td></td>
          <td>Forgot password?</td>
          <td>
          <router-link to = "/homePage_customer/"> <button v-bind:disabled= "!currentUser.password|| !currentUser.username"
                  v-on:click="loginCustomer(currentUser.username,currentUser.password)">
            Sign in </button> </router-link></td>

        </tr>
        <tr>
          <td></td>
         <td><router-link to = "/register/">Don't have an account? Create One</router-link>
         </td>
        </tr>
      </table>
    </div>


  </div>

</template>

<script src="./Hello.js">
</script>


<style>
body{background: #F6F6F6;}

  #customerLogin {

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
