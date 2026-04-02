import './assets/globals.css'
import 'vue-sonner/style.css'
import 'nprogress/nprogress.css'

import { MutationCache, QueryClient, VueQueryPlugin, matchQuery } from '@tanstack/vue-query'
import { createPinia } from 'pinia'
import NProgress from 'nprogress'
import { createApp } from 'vue'

import App from './App.vue'
import router from './router'

NProgress.configure({ showSpinner: false })

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 1,
      refetchOnWindowFocus: false,
      staleTime: 1000 * 60,
    },
  },
  mutationCache: new MutationCache({
    onSuccess: async (_data, _variables, _context, mutation) => {
      await queryClient.invalidateQueries({
        predicate: (query) =>
          (mutation.meta?.invalidates as unknown[] | undefined)?.some((queryKey) =>
            matchQuery({ queryKey: queryKey as never }, query),
          ) ?? true,
      })
    },
  }),
})

const app = createApp(App)

app.use(VueQueryPlugin, { queryClient })
app.use(createPinia())
app.use(router)

router.beforeEach(() => {
  NProgress.start()
})
router.afterEach(() => {
  NProgress.done()
})

app.mount('#app')
