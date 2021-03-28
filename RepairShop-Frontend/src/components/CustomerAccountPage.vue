<template>
  <div id="accountPage">
    <CustomerHeader />
    <div id="accountForm">
      <form>
        <label>Username: </label>
        <input required v-model="username" >

        <label>Email:</label>
        <input required type="email" v-model="email">

        <label>Password:</label>
        <input required type="password" v-model="password">

     <button id="save"v-on:click="updateAccount(username, email, password)">
       save</button>
      <button id="delete" v-on:click="deleteAccount(email)">
        delete</button>

    </form>
    </div>
  </div>
</template>

<script src = "./CustomerAccount.js">

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
