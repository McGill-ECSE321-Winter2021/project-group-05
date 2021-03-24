import axios from 'axios'
var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


function CustomerDto (email,username, password) {
  this.email = email
  this.username = username
  this.password = password

}

export default {
  name: 'repairShop',
  created: function () {

  },
  data () {
    return {
      customers: [],
      appointments:[],
      newCustomer:{
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
      AXIOS.post('/person/customer/register', {}, {})
        .then(response => {
          // JSON responses are automatically parsed.
          this.customers.push(response.data)
          this.errorCustomer = ''
          this.newCustomer = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorCustomer = errorMsg
        })
    }
  }
}
