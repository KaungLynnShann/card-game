import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '@/views/LoginView.vue'

import state from '@/store/state'
import Card from '@/views/Card.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'LoginView',
    component: LoginView
  },
  {
    path: '/card',
    name: 'card',
    component: Card
  },
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});
router.beforeEach(async (to, from, next) => {
  let loginUser = state.loginUser;
  let isLogin = sessionStorage.getItem("isLogin");

  // from - Home
  // Need to be login, But is not login
  if (to.meta.requiresAuth == true && !isLogin) {
    next({ path: "/login" });
    console.log(loginUser);
  }
  else {
    next();
  }
});

export default router;
