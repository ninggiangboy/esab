<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import UiPagination from '@/components/ui/UiPagination.vue'

const route = useRoute()
const router = useRouter()

const page = computed({
  get: () => Math.max(1, Number(route.query.page) || 1),
  set: (p: number) =>
    router.replace({ query: { ...route.query, page: p <= 1 ? undefined : String(p) } }),
})
</script>

<template>
  <div class="space-y-2">
    <UiPagination v-model="page" :page-count="10" />
    <p class="m-0 text-sm text-muted-foreground">Route query: {{ route.query }}</p>
  </div>
</template>
