import { useMediaQuery } from '@vueuse/core'
import { computed, type ComputedRef } from 'vue'

const DEFAULT_MOBILE_BREAKPOINT = 768

/** Match `frontend-sample` `use-mobile` breakpoint (max-width: 767px). */
export function useIsMobile(breakpoint = DEFAULT_MOBILE_BREAKPOINT): ComputedRef<boolean> {
  const mq = useMediaQuery(`(max-width: ${breakpoint - 1}px)`)
  return computed(() => mq.value)
}
