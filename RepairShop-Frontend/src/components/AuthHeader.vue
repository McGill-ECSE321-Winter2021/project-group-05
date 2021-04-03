<template>
    <nav id="header">
        <img class ="logo" src="../assets/logo_draft1.png" />
        <p class="message">{{pageTitle}}</p>
        <p class="button" v-if="this.currentPage" v-on:click="gotoLogin()">{{button}}</p>
    </nav>
</template>

<script>
import Router from "../router";

export default {
    name: "AuthHeader",
    props: ["pageTitle", "link","button"],
    data() {
    return {
      currentPage: true

    };
    },
    created: function(){
    if(this.$router.history.current.path.localeCompare("/LoginPage") === 0) {
      this.currentPage = false;
    }
    else {
      this.currentPage = true;
    }
    },

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
  display: inline-block;
  border-radius: 4px;
  background-color: lightskyblue;
  border: none;
  text-align: center;
  font-size: 20px;
  padding: 8px;
  width: 150px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
  position: absolute;
  right: 65px;
  top: 23px;
}

.button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 25px;
}

.button:hover span:after {
  opacity: 1;
  right: 0;
}

</style>
