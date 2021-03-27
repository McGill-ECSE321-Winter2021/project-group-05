import Vue from "vue";
import Router from "vue-router";
import BusinessPage from "../components/BusinessPage";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "BusinessPage",
      component: BusinessPage
    }
  ]
});
