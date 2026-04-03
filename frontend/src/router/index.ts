import DocPage from '@/docs/pages/DocPage.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/docs/ui/button',
    },
    {
      path: '/docs',
      redirect: '/docs/ui/button',
    },
    {
      path: '/docs/:section/:slug',
      name: 'doc',
      component: DocPage,
    },
  ],
})

export default router
