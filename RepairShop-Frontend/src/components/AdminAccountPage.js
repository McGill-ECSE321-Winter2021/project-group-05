import AdminHeader from './AdminHeader'
import axios from "axios";

var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

//This function creates a businessDto
function AdminDto(username, email, password) {
  this.username = username;
  this.email = email;
  this.password = password;
}

const AdminAccountPage =  {
  name: "AdminAccountPage",
  components: {
    AdminHeader
  },
  data() {
    return {
      admins: [],
      username: "",
      email: "",
      password:"",
      confirmPassword:'',
      error: ""
    };
  },
  methods:{

  updateAccount: function(username, email, password,confirmPassword) {
    if(this.confirmPassword==confirmPassword){
    const adminDTO = new AdminDto(username,email,password);
    console.log(adminDTO);
    AXIOS.put(`/person/administrator/${email}/`, adminDTO)
      .then(response => {
        this.admins.push(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
        this.error = e.message;
      });}
    else{
      alert("confirm password doesn't match with the password");
    }
  },
  deleteAccount: function(email){
    AXIOS.delete(`/person/administrator/${email}/`)
      .then(response => {
        this.admins.pop();
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
        this.error = e.message;
      });

  }
}
};

export default AdminAccountPage;
