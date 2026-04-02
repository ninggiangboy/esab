import { createRouter, createWebHistory } from 'vue-router'

import DashboardLayout from '@/layouts/DashboardLayout.vue'
import DocsLayout from '@/layouts/DocsLayout.vue'
import DocsUiPage from '@/views/docs/DocsUiPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/docs',
      component: DocsLayout,
      children: [
        {
          path: '',
          name: 'docs',
          component: () => import('@/views/docs/DocsIndexView.vue'),
        },
        {
          path: 'ui/:slug',
          name: 'docs-ui',
          component: DocsUiPage,
        },
      ],
    },
    {
      path: '/dashboard',
      component: DashboardLayout,
      children: [
        {
          path: '',
          name: 'dashboard',
          component: () => import('@/views/DashboardPage.vue'),
        },
        {
          path: 'history',
          name: 'dashboard-history',
          component: () => import('@/views/DashboardPage.vue'),
        },
        {
          path: 'templates',
          name: 'dashboard-templates',
          component: () => import('@/views/DashboardPage.vue'),
        },
        {
          path: 'starred',
          name: 'dashboard-starred',
          component: () => import('@/views/DashboardPage.vue'),
        },
        {
          path: 'profile',
          name: 'dashboard-profile',
          component: () => import('@/views/DashboardPage.vue'),
        },
        {
          path: 'settings',
          name: 'dashboard-settings',
          component: () => import('@/views/DashboardPage.vue'),
        },
        {
          path: 'design-system',
          name: 'dashboard-design-system',
          component: () => import('@/views/DashboardPage.vue'),
        },
        {
          path: 'components',
          name: 'dashboard-components',
          component: () => import('@/views/DashboardPage.vue'),
        },
        {
          path: 'help',
          name: 'dashboard-help',
          component: () => import('@/views/DashboardPage.vue'),
        },
        {
          path: 'feedback',
          name: 'dashboard-feedback',
          component: () => import('@/views/DashboardPage.vue'),
        },
      ],
    },
  ],
})

export default router
