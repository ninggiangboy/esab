import { inject, provide, ref, watchEffect, type InjectionKey, type Ref } from 'vue'

export type Theme = 'dark' | 'light' | 'system'

const STORAGE_KEY = 'vite-ui-theme'

export const themeInjectionKey: InjectionKey<{
  theme: Ref<Theme>
  setTheme: (t: Theme) => void
}> = Symbol('theme')

export function provideTheme(defaultTheme: Theme = 'light') {
  const stored = localStorage.getItem(STORAGE_KEY) as Theme | null
  const initial = stored && ['dark', 'light', 'system'].includes(stored) ? stored : defaultTheme
  const theme = ref<Theme>(initial)

  watchEffect(() => {
    const root = document.documentElement
    root.classList.remove('light', 'dark')

    if (theme.value === 'system') {
      const systemTheme = window.matchMedia('(prefers-color-scheme: dark)').matches
        ? 'dark'
        : 'light'
      root.classList.add(systemTheme)
      return
    }

    root.classList.add(theme.value)
  })

  function setTheme(t: Theme) {
    localStorage.setItem(STORAGE_KEY, t)
    theme.value = t
  }

  provide(themeInjectionKey, { theme, setTheme })
}

export function useTheme() {
  const ctx = inject(themeInjectionKey)
  if (!ctx) {
    throw new Error('useTheme must be used within a ThemeProvider')
  }
  return ctx
}
