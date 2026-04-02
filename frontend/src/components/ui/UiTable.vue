<script setup lang="ts">
import { onMounted, ref } from 'vue'

import { cn } from '@/lib/utils'

defineOptions({ inheritAttrs: false })

withDefaults(
  defineProps<{
    containerClass?: string
  }>(),
  { containerClass: '' },
)

const tableContainerRef = ref<HTMLDivElement | null>(null)
const tableRef = ref<HTMLTableElement | null>(null)

function calculateScrollPosition() {
  const container = tableContainerRef.value
  const table = tableRef.value
  if (!container || !table) return

  const { scrollLeft, scrollWidth, clientWidth } = container
  const isAtHorizontalStart = scrollLeft === 0
  const isAtHorizontalEnd = Math.abs(scrollLeft + clientWidth - scrollWidth) < 1

  table.setAttribute('data-at-start', String(isAtHorizontalStart))
  table.setAttribute('data-at-end', String(isAtHorizontalEnd))
}

onMounted(() => {
  calculateScrollPosition()
})
</script>

<template>
  <div class="relative grid bg-background-secondary rounded-md overflow-hidden border">
    <div
      ref="tableContainerRef"
      data-slot="table-container"
      :class="cn('w-full overflow-x-auto', containerClass)"
      @scroll="calculateScrollPosition"
    >
      <table
        ref="tableRef"
        data-slot="table"
        :class="cn('table group w-full caption-bottom text-sm', $attrs.class as string | undefined)"
        v-bind="$attrs"
      >
        <slot />
      </table>
    </div>
  </div>
</template>
