// general home page
import axios from "axios";
import AuthHeader from "./AuthHeader";
import WelcomePage from "./WelcomePage.vue";

//configuration
var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

const HomePage = {
  name: "HomePage",
  components: {
    AuthHeader,
    WelcomePage
  },
  created() {

  },
  data() {
    return {

      pageTitle: "Welcome to RepairShop, your satisfaction is our top concern",
      error: "",
      login: "Login",
      link: "/LoginPage",

    };
  }
};

export default HomePage;
