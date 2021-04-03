import Router from "../router/index";

export default{

  methods: {
  	play: function() {
      Router.push({
        path: "/welcome",
        name: "HomePage"
      })
    }
  }
}
