import axios from 'axios'


var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.build.backendHost + ':' + config.build.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
  name: 'repairShop',

  data () {
    return {
      customers: [],
      appointments:[],
      customer:{
        username:'',
        email:'',
        password:''
      },

      errorCustomer: '',
      response: []
    }
  },

  methods: {
    createCustomer: function (username, email, password) {
      console.log("key pressed");
      AXIOS.post('/person/customer/register', {
        "email": username,
        "username": email,
        "password": password
      },{})
        .then(response => {
          // JSON responses are automatically parsed.
          this.customers.push(response.data)
          this.errorCustomer = ''
          this.newCustomer = ''
          console.log(response.data);
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorCustomer = errorMsg
        })
    }

  }
}
