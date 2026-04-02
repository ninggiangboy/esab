import { onMounted, onUnmounted, ref, type Ref } from 'vue'

/** Tracks whether `document.documentElement` has the `dark` class (matches app theme). */
export function useHtmlDark(): Ref<boolean> {
  const isDark = ref(false)
  let observer: MutationObserver | null = null

  function sync() {
    isDark.value = document.documentElement.classList.contains('dark')
  }

  onMounted(() => {
    sync()
    observer = new MutationObserver(sync)
    observer.observe(document.documentElement, { attributes: true, attributeFilter: ['class'] })
  })

  onUnmounted(() => {
    observer?.disconnect()
    observer = null
  })

  return isDark
}
