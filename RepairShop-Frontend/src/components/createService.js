function BookableServiceDto (name, cost, duration) {
  this.name = name
  this.cost = cost
  this.duration = duration
}

export default {
  name: 'createService',
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

  created: function () {
      // Test data
      const s1 = new BookableServiceDto('Tire')
      const s2 = new BookableServiceDto('Wheel')
      // Sample initial content
      this.services = [s1, s2]
    },

    methods: {
        createService: function (serviceName, serviceCost, serviceDuration) {
          // Create a new service and add it to the list of services
          var p = new BookableServiceDto(serviceName, serviceCost, serviceDuration)
          this.services.push(p)
          // Reset the name field for new people
          this.serviceName = ''
          this.serviceCost = ''
          this.serviceDuration = ''
        }
      }
}


