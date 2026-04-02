import type { ComputedRef, InjectionKey, Ref } from 'vue'

export interface SidebarContext {
  state: ComputedRef<'expanded' | 'collapsed'>
  open: ComputedRef<boolean>
  setOpen: (value: boolean | ((prev: boolean) => boolean)) => void
  openMobile: Ref<boolean>
  setOpenMobile: (value: boolean | ((prev: boolean) => boolean)) => void
  isMobile: ComputedRef<boolean>
  toggleSidebar: () => void
}

export const sidebarInjectionKey: InjectionKey<SidebarContext> = Symbol('sidebar')
