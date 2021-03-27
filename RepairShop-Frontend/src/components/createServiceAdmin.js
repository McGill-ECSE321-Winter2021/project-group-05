import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


function BookableServiceDto (name, cost, duration) {
  this.name = name
  this.cost = cost
  this.duration = duration
}

export default {
  name: 'createServiceAdmin',
  data () {
    return {
      services: [],
      newService: '',
      newCost: '',
      newDuration: '',
      errorCreateService: '',
      response: []
    }
  },

  created: function () {                            // new added
      // Initializing services from backend
      AXIOS.get('/services')
      .then(response => {
        // JSON responses are automatically parsed.
        this.services = response.data
      })
      .catch(e => {
        this.errorCreateService = e
      })
    },

    methods: {
        createServiceAdmin: function (serviceName, serviceCost, serviceDuration) {
          // Create a new service and add it to the list of services
          var p = new BookableServiceDto(serviceName, serviceCost, serviceDuration)
          this.services.push(p)
          // Reset the name field for new services
          this.serviceName = ''
          this.serviceCost = ''
          this.serviceDuration = ''
        }
      }
}


