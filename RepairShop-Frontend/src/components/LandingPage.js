//landing page
import Router from "../router/index";

import axios from "axios";
import AdminHeader from "./AdminHeader";

//configurations
var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;


export default{
  name: "viewServiceCustomer",
  data() {
    return {
    flag: false

    };
  },
  created: function(){
    setTimeout(this.toggle,5);
  },
  methods: {
    play video
  	play: function() {
      Router.push({
        path: "/welcome",
        name: "HomePage"
      })
    },

    toggle: function() {
       this.flag = true;

    }
  }
};
