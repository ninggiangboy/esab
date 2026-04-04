<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Button } from '@/ui/components/button'
import { ChevronLeft, ChevronRight } from 'lucide-vue-next'

const route = useRoute()
const router = useRouter()
const pageCount = 10
const page = computed({
  get: () => Number(route.query.page ?? 1) || 1,
  set: (p: number) => router.replace({ query: { ...route.query, page: String(p) } }),
})
</script>
<template>
  <div class="flex items-center justify-center gap-1">
    <Button
      variant="ghost"
      size="icon"
      class="size-8"
      :disabled="page <= 1"
      @click="page = page - 1"
    >
      <ChevronLeft class="size-4" />
    </Button>
    <span class="px-2 text-sm tabular-nums">{{ page }} / {{ pageCount }}</span>
    <Button
      variant="ghost"
      size="icon"
      class="size-8"
      :disabled="page >= pageCount"
      @click="page = page + 1"
    >
      <ChevronRight class="size-4" />
    </Button>
  </div>
</template>
