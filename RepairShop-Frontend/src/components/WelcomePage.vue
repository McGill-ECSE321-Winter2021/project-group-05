<template>

  <div class ="outer_container">
    <!-- Slideshow container -->
    <div class="slideshow-container">

      <!-- Full-width images with number and caption text -->
      <div class="mySlides">
        <div class="numbertext">1 / 3</div>
        <img src="../assets/promotion.jpg" style="width:100%;height: 500px; display: block">

      </div>

      <div class="mySlides">
        <div class="numbertext">2 / 3</div>
        <img src="../assets/welcome.jpg" style="width:100%;height: 500px">

      </div>

      <div class="mySlides">
        <div class="numbertext">3 / 3</div>
        <img src="../assets/repairShop.jpg" style="width:100%;height: 500px">

      </div>

      <!-- Next and previous buttons -->
      <a class="prev" v-on:click="plusSlides(-1)">&#10094;</a>
      <a class="next" v-on:click="plusSlides(1)">&#10095;</a>
    </div>
    <br>

    <!-- The dots/circles -->
    <div style="text-align:center">
      <span class="dot" v-on:click="currentSlide(1)"></span>
      <span class="dot" v-on:click="currentSlide(2)"></span>
      <span class="dot" v-on:click="currentSlide(3)"></span>
    </div>

    <br>
    <div class="service">
      <h2 style="display: block; margin-left: 10px;
font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif">The Best Experience Ever With Us</h2>
    <!--service display-->
    <div class="row">

      <div class="column">
        <img src="../assets/changeTire.jpg" alt="Change Tire" style="width:100%;margin-left: 20px;
        height: 40vh;max-width: 80vh">
        <p align="center" style="margin-top: 10px; ">Change Tire</p>
      </div>
      <div class="column">
        <img src="../assets/wash_car.jpg" alt="Wash Car" style="width:100%;margin-right: 20px;height: 40vh;max-width: 80vh">
        <p align="center" style="margin-top: 10px">Wash Car</p>
      </div>
      <div class="column">
        <img src="../assets/fuel.jpeg" alt="fuel" style="width:100%;margin-left: 20px;height: 40vh;max-width: 80vh">
        <p align="center" style="margin-top: 10px">Fuel Up</p>
      </div>
      <div class="column">
        <img src="../assets/windowReplacement.jpeg" alt="windowReplacement" style="width:100%;margin-right: 20px;height: 40vh;max-width: 80vh">
        <p align="center" style="margin-top: 10px">Window Replacement</p>
      </div>
    </div>
    </div>

    <div class="map" >
      <h2 style="display: block; margin-left: 20px;
font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif">Where to Find Us?</h2>
      <div class="row">
      <div class="column">
      <GmapMap map-type-id="roadmap" :zoom="15"
               :center="{lat: 45.5047884, lng: -73.5793451}"
               :position="{lat: 45.5047884, lng: -73.5793451}"
               style="width: 100%; height: 400px; margin-left: 20px;max-width: 80vh">
      </GmapMap>
      </div>
      <div class="column">
        <h2 style="display: block;
font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif">Address:</h2>
        <br>
        <h3 tyle="display: block;
font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif">845 Sherbrooke St W, Montreal, Quebec H3A 0G4</h3>
        <br>
        <h2 style="display: block;
font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif">Phone Number:</h2>
        <br>
        <h3 tyle="display: block;
font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif">514-849-0000</h3>
        </div>
    </div>
      </div>
    <br>

    <div class="subscribe" align="center">
      <vue-mailchimp-email-signup-form
        :element-id="'first-email-signup-form'"
        :url="'https://gmail.us1.list-manage.com/subscribe/post?u=e052a68428b7bbf7ea4807ebc&amp;id=ad5339535a'"
        :title="'Don’t want to miss any promotions? '"
        :subtitle="'We take privacy seriously and we will never spam or sell your information.'"
      />
    </div>

    <div class="contactUs">
      <h2>Contact Us</h2>
      <p>Email us at <a href = "mailto: repairshop@mail.ca" target="_blank">repairshop@mail.ca</a></p>

      <button id="button" style="vertical-align:middle;">
        <span>
          <a href="mailto: repairshop@mail.ca" target="_blank"
             style="color:black;text-decoration:none">Contact Us </a>
        </span>
      </button>
      <hr>
    </div>

    <div>
      <div class="social_media">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <!-- Add font awesome icons -->
        <a href="#" class="fa fa-facebook" style="text-decoration:none"></a>
        <a href="#" class="fa fa-twitter" style="text-decoration:none"></a>
      </div>
      <footer>
        <p>© 2021 Repair Shop</p>

      </footer>
    </div>

  </div>
</template>

<script>

import {VueMailchimpEmailSignupForm} from "vue-mailchimp-email-signup-form";
import * as VueGoogleMaps from 'vue2-google-maps';
import Vue from 'vue';

Vue.use( VueGoogleMaps, {
  load: {
    key: 'AIzaSyAynLP1uHulI_p5eXizGNNbx0nhkALPydM',
    libraries: 'places',
  },
})

export default {
  name: "WelcomePage",
  created(){
    this.showSlides(1);

  },
  components: {
    "vue-mailchimp-email-signup-form": VueMailchimpEmailSignupForm,

  },
  data(){
    return{
      slideIndex: 1
    }

  },

  methods: {
    // Next/previous controls
    plusSlides: function(n) {
      this.showSlides((this.slideIndex += n));
    },

    // Thumbnail image controls
    currentSlide: function(n) {
      this.showSlides((this.slideIndex = n));
    },

    showSlides: function(n) {
      var slides = document.getElementsByClassName("mySlides");
      console.log(slides);

      var dots = document.getElementsByClassName("dot");
      if (n > slides.length) {
        this.slideIndex = 1;
      }
      if (n < 1) {
        this.slideIndex = slides.length;
      }

      console.log(slides.length); // todo: slides length is 0
      console.log(slides);
      if (slides.length != 0){
      for (var i = 0; i < slides.length; i++) {
        slides[i].style.background = "blue";
        slides[i].style.display = "none";
      }
      for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
      }
      console.log(this.slideIndex - 1);

      console.log(slides);
      slides.item(this.slideIndex - 1).style.display = "block";
      dots[this.slideIndex - 1].className += " active";
    }
    }
  }
}

</script>

<style>

/* Three image containers (use 25% for four, and 50% for two, etc) */
.column {
  float: left;
  width: 50%;
  padding: 20px;
}

/* Clear floats after image containers */
.row::after {
  content: "";
  clear: both;
  display: table;
}

.outer_container{
  width: 100%;
}

.subscribe{
    margin-left: 30%;
    margin-right: 30%;
    width:auto;

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
  max-width: 2000px;
  position: relative;
  margin: auto;
}

/* Hide the images by default */
.mySlides {
  -webkit-animation-name: fade;
  -webkit-animation-duration: 1.5s;
  animation-name: fade;
  animation-duration: 1.5s;
  display: none;
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
