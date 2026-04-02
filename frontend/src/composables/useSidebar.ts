import { inject } from 'vue'

import { sidebarInjectionKey, type SidebarContext } from '@/components/ui/sidebar/context'

export function useSidebar(): SidebarContext {
  const ctx = inject(sidebarInjectionKey)
  if (!ctx) {
    throw new Error('useSidebar must be used within a SidebarProvider.')
  }
  return ctx
}
