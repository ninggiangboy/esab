<script setup lang="ts">
import { ChevronLeft, ChevronRight } from 'lucide-vue-next'
import { computed } from 'vue'

import { useIsMobile } from '@/composables/useIsMobile'
import { cn } from '@/lib/utils'

const props = withDefaults(
  defineProps<{
    /** 1-based current page */
    modelValue?: number
    pageCount: number
  }>(),
  { modelValue: 1 },
)

const emit = defineEmits<{
  'update:modelValue': [page: number]
}>()

const isMobile = useIsMobile()

const page = computed({
  get: () => props.modelValue,
  set: (v) => emit('update:modelValue', v),
})

const baseClass =
  'cursor-pointer select-none text-[13px] font-medium flex w-8 h-8 items-center justify-center rounded-sm hover:bg-background-secondary'

function go(p: number) {
  page.value = Math.min(Math.max(1, p), props.pageCount)
}

/** Page numbers to show with null = ellipsis */
const sequence = computed(() => {
  const n = props.pageCount
  const cur = page.value
  if (n < 1) return []
  if (n === 1) return [1]

  const narrow = isMobile.value
  const window = narrow ? 0 : 2

  if (n <= 5 + window * 2) {
    return Array.from({ length: n }, (_, i) => i + 1)
  }

  const pages = new Set<number>([1, n, cur])
  for (let d = 1; d <= window; d++) {
    pages.add(cur - d)
    pages.add(cur + d)
  }
  const sorted = [...pages].filter((p) => p >= 1 && p <= n).sort((a, b) => a - b)

  const out: (number | null)[] = []
  for (let i = 0; i < sorted.length; i++) {
    const p = sorted[i]!
    if (i > 0 && p - sorted[i - 1]! > 1) out.push(null)
    out.push(p)
  }
  return out
})
</script>

<template>
  <nav class="flex items-center justify-center gap-1" aria-label="Pagination">
    <button
      type="button"
      :class="cn(baseClass, page <= 1 && 'opacity-50 cursor-not-allowed hover:bg-transparent')"
      :disabled="page <= 1"
      aria-label="Previous page"
      @click="go(page - 1)"
    >
      <ChevronLeft class="w-4 h-4" />
    </button>

    <template v-for="(p, idx) in sequence" :key="idx">
      <span v-if="p === null" class="block px-1 text-muted-foreground select-none">…</span>
      <button
        v-else
        type="button"
        :class="
          cn(baseClass, p === page && 'bg-background-secondary shadow-sm border text-foreground')
        "
        @click="go(p)"
      >
        {{ p }}
      </button>
    </template>

    <button
      type="button"
      :class="
        cn(baseClass, page >= pageCount && 'opacity-50 cursor-not-allowed hover:bg-transparent')
      "
      :disabled="page >= pageCount"
      aria-label="Next page"
      @click="go(page + 1)"
    >
      <ChevronRight class="w-4 h-4" />
    </button>
  </nav>
</template>
