<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const title = computed(() => {
  const last = route.path.split('/').filter(Boolean).pop() ?? 'dashboard'
  return last.charAt(0).toUpperCase() + last.slice(1).replace(/-/g, ' ')
})
</script>

<template>
  <div>
    <h1 class="text-2xl font-semibold text-foreground">{{ title }}</h1>
    <p class="mt-2 text-muted-foreground">Path: {{ route.path }}</p>
  </div>
</template>
