import Vue from 'vue'
import VueRouter from 'vue-router'

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)



const routes = [
  {
    path: '/',
    name: 'Manger',
    component: () => import('../views/ManagerView.vue'),
    redirect: 'home',
    children: [
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/HomeView.vue') },
      { path: 'user', name: 'User', meta: { name: '所有用户信息' }, component: () => import('../views/manager/UserView.vue') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/manager/PersonView.vue') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/PasswordView.vue') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/LoginView.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/RegisterView.vue') },
  { path: '/forget', name: 'Forget', meta: { name: '忘记密码' }, component: () => import('../views/ForgetView.vue') }
]

const router = new VueRouter({
  routes,
  mode: 'history',
})

export default router
