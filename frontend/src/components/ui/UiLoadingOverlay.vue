<script setup lang="ts">
import { useAttrs } from 'vue'

import { cn } from '@/lib/utils'

import UiSpinner from './UiSpinner.vue'

defineOptions({ inheritAttrs: false })

withDefaults(
  defineProps<{
    /** When true, dims the slotted content and shows a centered spinner on top. */
    isLoading?: boolean
  }>(),
  { isLoading: false },
)

const attrs = useAttrs()
</script>

<template>
  <div
    :class="cn('relative', isLoading && 'opacity-60', attrs.class as string | undefined)"
    :aria-busy="isLoading ? true : undefined"
    v-bind="{ ...attrs, class: undefined }"
  >
    <slot />
    <div
      v-if="isLoading"
      class="absolute inset-0 flex items-center justify-center"
      role="status"
      aria-live="polite"
    >
      <UiSpinner class="size-7 text-primary" />
      <span class="sr-only">Loading</span>
    </div>
  </div>
</template>
