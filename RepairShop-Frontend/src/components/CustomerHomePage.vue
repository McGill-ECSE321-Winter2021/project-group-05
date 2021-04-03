<template>

  <div class ="outer_container">

  <div class="header" v-if="this.render">
  <CustomerHeader />
  </div>
  <WelcomePage v-if="this.render"/>
    <div v-if="!this.render">
      <label>Please login to continue</label>
    </div>

  </div>
</template>

<script src="./CustomerHomePage.js">


</script>

<style>

.outer_container{
  width: 100%;
}

.subscribe{

  margin-left: 30%;
  margin-right: 30%;

}
#button {
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
}

#button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

#button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

#button:hover span {
  padding-right: 25px;
}

#button:hover span:after {
  opacity: 1;
  right: 0;
}

.contactUs{
  margin-bottom: 20px;
  text-align: center;
}

footer, .social_media{
  display: block;
  text-align: center;
}
.social_media a{
  padding: 10px;
}


* {box-sizing:border-box}

/* Slideshow container */
.slideshow-container {
  max-width: 1000px;
  position: relative;
  margin: auto;
}

/* Hide the images by default */
.mySlides {
  display: none;
  -webkit-animation-name: fade;
  -webkit-animation-duration: 1.5s;
  animation-name: fade;
  animation-duration: 1.5s;
}

/* Next & previous buttons */
.prev, .next {
  cursor: pointer;
  position: absolute;
  top: 50%;
  width: auto;
  margin-top: -22px;
  padding: 16px;
  color: white;
  font-weight: bold;
  font-size: 18px;
  transition: 0.6s ease;
  border-radius: 0 3px 3px 0;
  user-select: none;
}

/* Position the "next button" to the right */
.next {
  right: 0;
  border-radius: 3px 0 0 3px;
}

/* On hover, add a black background color with a little bit see-through */
.prev:hover, .next:hover {
  background-color: rgba(0,0,0,0.8);
}

/* Caption text */
.text {
  color: #f2f2f2;
  font-size: 15px;
  padding: 8px 12px;
  position: absolute;
  bottom: 8px;
  width: 100%;
  text-align: center;
}

/* Number text (1/3 etc) */
.numbertext {
  color: #f2f2f2;
  font-size: 12px;
  padding: 8px 12px;
  position: absolute;
  top: 0;
}

/* The dots/bullets/indicators */
.dot {
  cursor: pointer;
  height: 15px;
  width: 15px;
  margin: 0 2px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
  transition: background-color 0.6s ease;
}

.active, .dot:hover {
  background-color: #717171;
}


@-webkit-keyframes fade {
  from {opacity: .4}
  to {opacity: 1}
}

@keyframes fade {
  from {opacity: .4}
  to {opacity: 1}
}

</style>
