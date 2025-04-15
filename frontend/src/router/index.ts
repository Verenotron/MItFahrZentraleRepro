import { createRouter, createWebHistory } from 'vue-router'
//import HomeView from '../views/HomeView.vue'
import TourenListeView from './../views/TourenListeView.vue'
import TourView from './../views/TourView.vue'
import LoginView from '@/views/LoginView.vue'

const routes = [
  {
    path: '/touren',
    name: 'TourenListe',
    component: TourenListeView
  },
  {
    path: '/',
    redirect: '/touren',
  },
  {
    path: '/tour/:tourid',
    name: 'TourView',
    component: TourView,
    props: true
  },
  {
    path: '/login',
    name: 'LoginView',
    component: LoginView,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes, //Darf nicht leer sein
})

export default router
