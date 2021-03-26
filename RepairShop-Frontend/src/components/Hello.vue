<template>


  <div id="customerLogin">
    <div id="left">
      <img class ="left" width="100" height="100" src="src/assets/logo.png">
    </div>

    <div id="right">
      <table>
        <tr>
          <td>
            <input type="text"  v-model="currentUser.username" placeholder="username">
          </td>
        </tr>
        <tr >
          <td>
            <input type="password"  v-model="currentUser.password"  placeholder="password">
          </td>
        </tr>
        <tr>
          <button v-bind:disabled= "!currentUser.password|| !currentUser.username"
                  v-on:click="loginCustomer(currentUser.username,currentUser.password)">
            Log-in</button>
        </tr>
        <tr>

         <router-link to = "/register/">Don't have an account? Click here</router-link>

        </tr>
      </table>
    </div>


  </div>

</template>

<script src="./Hello.js">
</script>


<style>
  #customerLogin {
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
    margin-top: 150px ;
  }
  table {
    border-spacing: 30px;
    border-collapse: separate;
  }
</style>
