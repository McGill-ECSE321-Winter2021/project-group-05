<template>
<div class="surface">
  <img class ="logo" src="../assets/repairShop.png" alt="Repair Shop">
  <router-link to = "/myAccount/">
    <img class="userCenter" src="../assets/repairShop.png" alt="User Center">
  </router-link>

  <router-link to = "/">
    <button class="logout">log out</button>
  </router-link>
  <div class="nav">
    <ul>
       <router-link to="/homePage_customer/"><li class="active">Home</li></router-link>
        <li>Appointment</li>
      <router-link to="/myAccount/"><li>My Account</li></router-link>
    </ul>
  </div>


</div>
</template>

<script>
export default {
  name: "HomePage_Customer"
}
</script>

<style scoped>

.surface{
  position: relative;
  width:100%;
}
.logo{
  position: absolute;

  top: 8px;
  left:16px;
  width:100px;
  heigh: 100px;
}
.userCenter{
  position: absolute;
  top: 8px;
  right:16px;
  width:100px;
  heigh: 100px;
}
.logout{
  position: absolute;
  top: 120px;
  right:16px;
  width:15%;
  heigh: 15%;

}

li{
  display: inline;
  color: black;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;

}

.active{
  background-color: #4CAF50;
}

.nav{
  position: absolute;
  top: 200px;
  margin-left: 25%;
  margin-right: 25%;
  background-color: #2c3e50;
}



</style>
