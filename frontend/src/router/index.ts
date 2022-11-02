import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/Home-View.vue'
import Profile from '../views/Profile-View.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/profile',
    name: 'profile',
    component:Profile

  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
