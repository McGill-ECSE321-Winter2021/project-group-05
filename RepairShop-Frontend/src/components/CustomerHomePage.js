import axios from "axios";
import CustomerHeader from "./CustomerHeader";
import "vueperslides/dist/vueperslides.css";
import { VueMailchimpEmailSignupForm } from "vue-mailchimp-email-signup-form";
import "vue-mailchimp-email-signup-form/dist/vue-mailchimp-email-signup-form.css";

var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

const CustomerHomePage = {
  name: "CustomerHomePage",
  components: {
    CustomerHeader,
    "vue-mailchimp-email-signup-form": VueMailchimpEmailSignupForm
  },
  created() {
    if(localStorage.getItem('loggedInEmail').localeCompare("null") === 0){
        this.render = false;
        console.log(this.render);
    }
    else {
      this.render = true;
      this.showSlides(1);
    }
  },
  data() {
    return {
      slides: [
        {
          title: "Slide #1",
          content: "Slide content."
        }
      ],
      pageTitle: "Welcome to RepairShop, your satisfaction is our top concern",
      error: "",
      login: "log-in",
      link: "/LoginPage",
      slideIndex: 0,
      render: true
    };
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
};

export default CustomerHomePage;
