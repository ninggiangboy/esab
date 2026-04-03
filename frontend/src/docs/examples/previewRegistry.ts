import type { Component } from 'vue'
import { defineAsyncComponent } from 'vue'

const loaders: Record<string, () => Promise<{ default: Component }>> = {
  ButtonDemo: () => import('./button/ButtonDemo.vue'),
  ButtonOutline: () => import('./button/ButtonOutline.vue'),
  ButtonDestructive: () => import('./button/ButtonDestructive.vue'),
  ButtonGhost: () => import('./button/ButtonGhost.vue'),
  ButtonIcon: () => import('./button/ButtonIcon.vue'),
  ButtonWithIcon: () => import('./button/ButtonWithIcon.vue'),
  ButtonAsALink: () => import('./button/ButtonAsALink.vue'),
  ButtonLoading: () => import('./button/ButtonLoading.vue'),
}

const rawLoaders = {
  ...import.meta.glob<string>('./button/*.vue', {
    query: '?raw',
    import: 'default',
  }),  
}

export function getPreviewComponent(name: string) {
  const load = loaders[name]
  if (!load) return null
  return defineAsyncComponent(load)
}

export async function loadPreviewSource(name: string): Promise<string | null> {
  const needle = `${name}.vue`
  const entry = Object.entries(rawLoaders).find(([p]) => p.endsWith(needle))
  if (!entry) return null
  return entry[1]()
}

export const previewNames = Object.keys(loaders)
