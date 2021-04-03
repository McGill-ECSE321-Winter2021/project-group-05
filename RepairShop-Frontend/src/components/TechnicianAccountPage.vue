<template>
  <div id="accountPage">
    <TechnicianHeader v-if="this.render"/>
    <div id="accountForm" v-if="this.render">
      <form>


        <label>Username</label>
        <input required v-model="username">


        <label>Email</label>
        <input required type="email" v-model="email">


        <label>Password</label>
        <input required  v-model="password" type="password">

        <label>Confirm Password</label>
        <input required  v-model="confirmPassword" type="password">


     <button id="save"v-on:click="updateAccount(username, email, password,confirmPassword)" style="border-radius: 10px;">
       save</button>
      <button id="delete" v-on:click="deleteAccount(email)" style="border-radius: 10px;">
        delete</button>

    </form>
    </div>
    <div v-if="!this.render">
      <label>Please login to continue</label>
    </div>
  </div>
</template>

<script src = "./TechnicianAccount.js">

</script>

<style scoped>

#accountForm {
  background-color: #F6F6F6;
  align-items: center;
  display: flex;
  flex-direction: column;
  height: 100vh;
}
form {
  width: 50%;
  display: flex;
  flex-direction: column;
  padding: 20px;
  border: 1px solid lightgray;
}

label {
  font-family: 'Times New Roman', Times, serif;
  font-size: 14px;
}

#save {
  background-color: #2373F7;
  color: white;
  margin-top: 16px;
}
#delete{
  background-color:firebrick;
  color: white;
  margin-top: 16px;
}

</style>
