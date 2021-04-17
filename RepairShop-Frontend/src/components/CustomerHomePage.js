//customer home page
import axios from "axios";
import CustomerHeader from "./CustomerHeader";
import "vueperslides/dist/vueperslides.css";
import { VueMailchimpEmailSignupForm } from "vue-mailchimp-email-signup-form";
import "vue-mailchimp-email-signup-form/dist/vue-mailchimp-email-signup-form.css";
import WelcomePage from "../components/WelcomePage.vue";

//configurations
var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

//customer home page view
const CustomerHomePage = {
  name: "CustomerHomePage",
  components: {
    CustomerHeader,
    "vue-mailchimp-email-signup-form": VueMailchimpEmailSignupForm,
    WelcomePage,
  },
  //if logged in
  created() {
    if (localStorage.getItem("loggedInEmail").localeCompare("null") === 0) {
      this.render = false;
      console.log(this.render);
    } else {
      this.render = true;
      this.showSlides(1);
    }
  },
  data() {
    return {
      slides: [
        {
          title: "Slide #1",
          content: "Slide content.",
        },
      ],
      pageTitle: "Welcome to RepairShop, your satisfaction is our top concern",
      error: "",
      login: "log-in",
      link: "/LoginPage",
      slideIndex: 0,
      render: true,
    };
  },
};

export default CustomerHomePage;
