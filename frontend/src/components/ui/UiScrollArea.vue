<script setup lang="ts">
import {
  ScrollAreaCorner,
  ScrollAreaRoot,
  ScrollAreaScrollbar,
  ScrollAreaThumb,
  ScrollAreaViewport,
} from 'radix-vue'

import { cn } from '@/lib/utils'

withDefaults(
  defineProps<{
    showHorizontalScrollbar?: boolean
    showVerticalScrollbar?: boolean
  }>(),
  {
    showHorizontalScrollbar: true,
    showVerticalScrollbar: true,
  },
)
</script>

<template>
  <ScrollAreaRoot
    data-slot="scroll-area"
    :class="cn('relative', $attrs.class as string | undefined)"
  >
    <ScrollAreaViewport
      data-slot="scroll-area-viewport"
      class="focus-visible:ring-ring/50 size-full rounded-[inherit] transition-[color,box-shadow] outline-none focus-visible:ring-[3px] focus-visible:outline-1"
    >
      <slot />
    </ScrollAreaViewport>
    <ScrollAreaScrollbar
      v-if="showHorizontalScrollbar"
      orientation="horizontal"
      class="flex touch-none p-px transition-colors select-none h-2.5 flex-col border-t border-t-transparent"
    >
      <ScrollAreaThumb class="relative flex-1 rounded-full bg-border" />
    </ScrollAreaScrollbar>
    <ScrollAreaScrollbar
      v-if="showVerticalScrollbar"
      orientation="vertical"
      class="flex touch-none p-px transition-colors select-none h-full w-2.5 border-l border-l-transparent"
    >
      <ScrollAreaThumb class="relative flex-1 rounded-full bg-border" />
    </ScrollAreaScrollbar>
    <ScrollAreaCorner />
  </ScrollAreaRoot>
</template>
