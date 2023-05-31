import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/login.vue'
import home from '@/components/home.vue'
import organizeManage from '@/components/departmentManage.vue'
import authority from '@/components/authority.vue'
import menuManage from '@/components/menuManage.vue'
import authorityManage from '@/components/authorityManage.vue'
import userManage from '@/components/userManage.vue'
import roleManage from '@/components/roleManage.vue'
import wxProgram from '@/components/service/wxProgram.vue'
import foodType from '@/components/service/foodType.vue'
import foodManage from '@/components/service/foodManage.vue'
import userLevel from '@/components/service/userLevel.vue'
import orderManage from '@/components/service/orderManage.vue'
import managerConsole from '@/components/service/managerConsole.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: login
    },
    {
      path: '/home',
      name: 'home',
      component: home,
      meta: {
        title: "首页"
      },
      children: [
        {
          path: 'managerConsole',
          component: managerConsole,
          meta: {
            title: "管理台"
          }
        },
        {
          path: 'authority',
          component: authority,
          meta: {
            title: "权限管理"
          },
          children: [{
            path: 'menuManage',
            component: menuManage,
            meta: {
              title: "菜单管理"
            }
          },{
            path: 'authorityManage',
            component: authorityManage,
            meta: {
              title: "权限管理"
            }
          },{
            path: 'roleManage',
            component: roleManage,
            meta: {
              title: "角色管理"
            }
          },{
            path: 'userManage',
            component: userManage,
            meta: {
              title: "用户管理"
            }
          },{
            path: 'departmentManage',
            component: organizeManage,
            meta: {
              title: "部门管理"
            }
          }]
        },{
          path: 'wxProgram',
          component: wxProgram,
          meta: {
            title: "小程序管理"
          },
          children: [{
            path: 'foodType',
            component: foodType,
            meta: {
              title: "菜品种类"
            }
          },{
            path: 'foodManage',
            component: foodManage,
            meta: {
              title: "菜品管理"
            }
          },{
            path: 'userLevel',
            component: userLevel,
            meta: {
              title: "会员等级"
            }
          },{
            path: 'orderManage',
            component: orderManage,
            meta: {
              title: "订单管理"
            }
          }]
        }
      ]
    }
  ]
})
