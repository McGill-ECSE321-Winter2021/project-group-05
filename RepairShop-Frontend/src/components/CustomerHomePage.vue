<template>

  <div class ="outer_container">
  <div class="header" v-if="this.render">
  <!-- <AuthHeader v-bind:pageTitle="pageTitle"
  v-bind:button="login"/> -->
  <CustomerHeader />
  </div>


  <!-- Slideshow container -->
  <div class="slideshow-container" v-if="this.render">

    <!-- Full-width images with number and caption text -->
    <div class="mySlides">
      <div class="numbertext">1 / 3</div>
      <img src="../assets/repairShop.png" style="width:100%">
      <div class="text">Caption Text</div>
    </div>

    <div class="mySlides">
      <div class="numbertext">2 / 3</div>
      <img src="../assets/logo_draft1.png" style="width:100%">
      <div class="text">Caption Two</div>
    </div>

    <div class="mySlides">
      <div class="numbertext">3 / 3</div>
      <img src="../assets/logo.png" style="width:100%">
      <div class="text">Caption Three</div>
    </div>

    <!-- Next and previous buttons -->
    <a class="prev" v-on:click="plusSlides(-1)">&#10094;</a>
    <a class="next" v-on:click="plusSlides(1)">&#10095;</a>
  </div>
  <br>

  <!-- The dots/circles -->
  <div style="text-align:center" v-if="this.render">
    <span class="dot" v-on:click="currentSlide(1)"></span>
    <span class="dot" v-on:click="currentSlide(2)"></span>
    <span class="dot" v-on:click="currentSlide(3)"></span>
  </div>

    <br>
  <div class="map" v-if="this.render">

  </div>



    <div class="subscribe" v-if="this.render">
      <vue-mailchimp-email-signup-form
        :element-id="'first-email-signup-form'"
        :url="'https://gmail.us1.list-manage.com/subscribe/post?u=e052a68428b7bbf7ea4807ebc&amp;id=ad5339535a'"
        :title="'Don’t want to miss any promotions? '"
        :subtitle="'We take privacy seriously and we will never spam or sell your information.'"
      />
    </div>

    <div class="contactUs" v-if="this.render">
      <h2>Contact Us</h2>
      <p>Email us at <a href = "mailto: repairshop@mail.ca" target="_blank">repairshop@mail.ca</a></p>

      <button id="button" style="vertical-align:middle">
        <span>
          <a href="mailto: repairshop@mail.ca" target="_blank"
             style="color:white;text-decoration:none">Contact Us </a>
        </span>
      </button>
      <hr>
    </div>

    <div v-if="this.render">
      <div class="social_media">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

      <!-- Add font awesome icons -->
        <a href="#" class="fa fa-facebook" style="text-decoration:none"></a>
        <a href="#" class="fa fa-twitter" style="text-decoration:none"></a>
      </div>
      <footer v-if="this.render">
        <p>© 2021 Repair Shop</p>

     </footer>
    </div>
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
  margin-left: 20px;
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
