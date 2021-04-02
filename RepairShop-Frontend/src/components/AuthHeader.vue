<template>
    <nav id="header">
        <img class ="logo" src="../assets/logo_draft1.png" />
        <p class="message">{{pageTitle}}</p>
        <p class="button" v-on:click="gotoLogin()">{{button}}</p>
    </nav>
</template>

<script>
import Router from "../router";

export default {
    name: "AuthHeader",
    props: ["pageTitle", "link","button"],
   methods:{
     gotoLogin: function(){
       Router.push({
         path: "/LoginPage",
         name: "LoginPage",
       });
     }
   }
}
</script>

<style scoped>
#header{
    background-color: #2373F7;
    display: flex;
    align-items: center;
    position: sticky;
    top: 0;
    z-index: 100;
    border-bottom-right-radius: 8px;
    border-bottom-left-radius: 8px;
    height: 100px;
}
.logo{
    border-radius: 20px;
    margin-top: 20px;

}
.message{
   font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
   font-size: 20px;
   color: white;
   padding-top: 16px;
   margin-left: 38px;
}

.button{
  font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-size: 20px;
  color: black;
  padding-top: 16px;
  margin-left: 100px;

}
</style>
