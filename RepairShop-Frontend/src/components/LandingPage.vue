
<template>
<div id="app">
  <video id="vid" ref="myvideo" disablepictureinpicture controlslist="nodownload" autoplay
    src="../assets/LandingPage.mp4"
    width="1080"></video>

    <div class="demo" v-if="this.flag">
    <button  @click="play()">Get started</button>
    </div>

</div>
</template>

<script src="./LandingPage.js">

</script>

<style>
      video {
        position: relative;
        width: 100%;
        top: 0;
        height: auto;
      }
      body {background-color: black;}


button{
  margin:auto;
  display:block;
}

/* BUTTON STYLES */
button{
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  padding:16px 32px;
  background-color: lightskyblue;
  border-radius: 10px;
  font-size:23px;
  color:#000;
  background: lightskyblue;
  border:0;
  font-weight:200;
}
button:hover{
  background: #3B5C76;
}

.demo {
  margin: 0;
  position: absolute;
  top: 70%;
  left: 50%;
  -ms-transform: translateY(-50%);
  transform: translateY(-50%);
  -ms-transform: translateX(-50%);
  transform: translateX(-50%);
}
</style>

