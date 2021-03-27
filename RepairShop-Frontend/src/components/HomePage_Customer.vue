<template>
<div class="surface">
  <div id="top">
    <img class ="logo" src="../assets/repairShop.png">

    <div class="nav">
    <ul>
      <router-link to="/homePage_customer/"><li class="active">Home</li></router-link>
      <li>Appointment</li>
      <router-link to="/myAccount/"><li>My Account</li></router-link>
      <router-link to="/"><li>Logout</li></router-link>
    </ul>
    </div>

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
#top{
  position: relative;

  width: 100%;
  height: 30%;
  float: top;
  background-color:#2373F7;

}
.logo{
  position: relative;
  width:100px;
  height:100px;
  margin-top: 20px;
  margin-bottom: 20px;
  margin-right:60%;
}
.userCenter{
  position: absolute;
  top: 8px;
  right:16px;
  width:100px;
  heigh: 100px;
}
.logout{
  position: relative;
  top: 120px;
  right:16px;
  width:15%;
  heigh: 15%;

}

li{
  display: inline;
  color: black;
  text-align: center;
  padding: 14px 20px;
  text-decoration: none;

}

.active{
  background-color: #4CAF50;
}

.nav{
  position: relative;
  margin-top: -5%;
  margin-left: 30%;
  margin-right: 30%;
}


</style>
