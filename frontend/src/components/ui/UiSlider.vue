<script setup lang="ts">
import { SliderRange, SliderRoot, SliderThumb, SliderTrack } from 'radix-vue'

import { cn } from '@/lib/utils'

withDefaults(
  defineProps<{
    min?: number
    max?: number
    step?: number
    disabled?: boolean
  }>(),
  { min: 0, max: 100, step: 1, disabled: false },
)

const modelValue = defineModel<number[]>({ default: () => [0] })
</script>

<template>
  <SliderRoot
    v-model="modelValue"
    :min="min"
    :max="max"
    :step="step"
    :disabled="disabled"
    :class="
      cn(
        'relative flex w-full touch-none select-none items-center',
        $attrs.class as string | undefined,
      )
    "
  >
    <SliderTrack class="relative h-1.5 w-full grow overflow-hidden rounded-full bg-neutral-500/20">
      <SliderRange class="absolute h-full bg-primary" />
    </SliderTrack>
    <SliderThumb
      class="block size-4 rounded-full border border-primary/50 bg-background shadow transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring disabled:pointer-events-none disabled:opacity-50"
      aria-label="Value"
    />
  </SliderRoot>
</template>
