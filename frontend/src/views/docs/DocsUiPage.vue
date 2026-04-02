<script setup lang="ts">
import { shallowRef, watch, type Component } from 'vue'
import { useRoute } from 'vue-router'

import { docNavSlugs } from '@/docs/nav'

const route = useRoute()
const modules = import.meta.glob<Promise<{ default: Component }>>('./pages/*.vue')

const Page = shallowRef<Component | null>(null)
const unknownSlug = shallowRef(false)

watch(
  () => route.params.slug,
  async (slug) => {
    if (typeof slug !== 'string') {
      Page.value = null
      unknownSlug.value = true
      return
    }
    if (!docNavSlugs.has(slug)) {
      Page.value = null
      unknownSlug.value = true
      return
    }
    unknownSlug.value = false
    const loader = modules[`./pages/${slug}.vue`]
    if (!loader) {
      Page.value = null
      unknownSlug.value = true
      return
    }
    Page.value = (await loader()).default
  },
  { immediate: true },
)
</script>

<template>
  <article v-if="unknownSlug" class="prose dark:prose-invert max-w-none">
    <h1>Not found</h1>
    <p>
      <RouterLink to="/docs">Back to docs</RouterLink>
    </p>
  </article>
  <component :is="Page" v-else-if="Page" />
</template>
