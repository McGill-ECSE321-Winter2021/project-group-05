
import axios from "axios";


var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});



const CustomerHomePage={
  name: "CustomerHomePage",

  data () {
   return{
     slideIndex : 1,
     slide:
       {style:""}


   }
  },
  created(){
    this.showSlides(this.slideIndex)
  },
  methods:{
// Next/previous controls
    plusSlides:function (n) {
  this.showSlides(this.slideIndex += n);
  },

// Thumbnail image controls
    currentSlide: function (n) {
  this.showSlides(this.slideIndex = n);
  },

    showSlides:function (n) {
  var i;
  var slides = document.getElementsByClassName("mySlides fade");
  var dots = document.getElementsByClassName("dot");
  if (n > slides.length) {this.slideIndex = 1}
  if (n < 1) {this.slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
    console.log(slides[i]);
    slides[i].style.display = "none";
  }
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[this.slideIndex-1].style.display = "block";
  dots[this.slideIndex-1].className += " active";
  }

}
};

export default CustomerHomePage;








