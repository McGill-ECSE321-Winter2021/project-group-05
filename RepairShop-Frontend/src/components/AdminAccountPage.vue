<template>
  <div class="surface">
    <AdminHeader />
  <table>
    <tr>
      <td>
        <input type="text"  placeholder="username">
      </td>
    </tr>
    <tr>
      <td>
        <input type="text"  placeholder="email">
      </td>
    </tr>
    <tr>
      <td>
        <input type="text"  placeholder="password">
      </td>
    </tr>
    <tr>
      <td> <button>save</button>
      <button>delete</button> </td>
    </tr>
  </table>

  </div>
</template>

<script>
import AdminHeader from './AdminHeader'

export default {
  name: "AdminAccountPage",
  components: {
    AdminHeader
  }
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


table{
  border-spacing: 30px;
  border-collapse: separate;
  position: absolute;
  top: 300px;
  margin-left: 30%;
  margin-right: 30%;

}




</style>
