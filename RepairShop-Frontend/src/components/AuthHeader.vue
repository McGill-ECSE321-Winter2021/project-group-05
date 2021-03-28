<template>
    <nav id="header">
        <img class ="logo" src="../assets/logo_draft1.png" />
        <p class="message">{{pageTitle}}</p>
    </nav>
</template>

<script>
export default {
    name: "AuthHeader",
    props: ["pageTitle"]
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

}
.message{
   font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
   font-size: 20px;
   color: white;
   padding-top: 16px;
   margin-left: 8px;
}
</style>
