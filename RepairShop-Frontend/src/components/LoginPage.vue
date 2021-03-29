<template>
  <div id="loginPage">

    <AuthHeader v-bind:pageTitle="pageTitle"/>

      <div id="form__container">
        <form>
          <!--  Select input-->
          <label>Select the role that applies to you </label>
          <select require v-model="currentUser.personType">
            <option disabled value="">Please select one</option>
            <option>Customer</option>
            <option>Technician</option>
            <option>Admin</option>
          </select>

          <!-- Email input text -->
            <label>Email:</label>
            <input type="text" required v-model="currentUser.email">

          <!-- Password input text -->
            <label>Password: </label>
            <input type="password" required v-model="currentUser.password" >

          <!--Forgot password and sign in button -->
          <div id="forgotAndSignContainer">
            <button id="forgotPassword__button">Forgot password?</button>

            <button id="signin__button"  v-bind:disabled= "!currentUser.password|| !currentUser.email" v-on:click="loginUser(currentUser.email,currentUser.password, currentUser.personType )">Sign in </button>

          </div>
          <!-- Don't have an account-->
          <div id="createAccountContainer">
            <button id="createAccount__button" v-on:click="goToCreateAccountPage()">Don't have an account? Create one</button>
          </div>

        </form>
      </div>
    </div>

</template>

<script src="./LoginPage.js">
</script>


<style>
body{
  background: #F6F6F6;
}

#form__container{
    background-color: #F6F6F6;
    align-items: center;
    display: flex;
    flex-direction: column;
    margin-top: 100px;
}

form{
    width: 50%;
    display: flex;
    flex-direction: column;
    padding: 20px;
    border: 1px solid lightgray;
}

#createAccount__button{
  color: blue;
  background-color: Transparent;
  background-repeat:no-repeat;
  border: none;
  cursor:pointer;
  overflow: hidden;
  outline:none;
}

#forgotPassword__button{
  background-color: Transparent;
  background-repeat:no-repeat;
  border: none;
  cursor:pointer;
  overflow: hidden;
  outline:none;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  font-size: 16px;

}

#signin__button{
  color: white;
  border-radius: 10px;
  background-color: #2373F7;
}
label {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  font-size: 16px;
  margin-top: 8px;
}

input{
  width: 100%;
  border-radius: 4px;
}

#createAccountContainer{
  margin-top: 16px;
  align-self: center;
}

#forgotAndSignContainer{
 margin-top: 16px;
}
#signin__button{
  align-self: center;
  margin-left: 200px;
}
</style>
