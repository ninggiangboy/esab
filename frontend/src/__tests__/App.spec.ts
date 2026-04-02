import { QueryClient, VueQueryPlugin } from '@tanstack/vue-query'
import { describe, it, expect, beforeEach, vi } from 'vitest'

import { mount, flushPromises } from '@vue/test-utils'
import { createPinia } from 'pinia'

import App from '../App.vue'
import router from '../router'

describe('App', () => {
  beforeEach(() => {
    vi.stubGlobal('localStorage', {
      getItem: vi.fn(() => null),
      setItem: vi.fn(),
      removeItem: vi.fn(),
      clear: vi.fn(),
      key: vi.fn(),
      length: 0,
    })
  })

  it('mounts and shows home view', async () => {
    const queryClient = new QueryClient({
      defaultOptions: { queries: { retry: false } },
    })

    router.push('/')
    await router.isReady()

    const wrapper = mount(App, {
      global: {
        plugins: [createPinia(), router, [VueQueryPlugin, { queryClient }]],
      },
    })

    await flushPromises()
    expect(wrapper.text()).toContain('Welcome to Tanstack Router')
  })
})
