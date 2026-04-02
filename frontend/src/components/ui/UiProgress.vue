<script setup lang="ts">
import { computed } from 'vue'

import { cn } from '@/lib/utils'

const props = withDefaults(
  defineProps<{
    modelValue?: number | null
    max?: number
  }>(),
  {
    modelValue: 0,
    max: 100,
  },
)

const pct = computed(() => Math.min(100, Math.max(0, ((props.modelValue ?? 0) / props.max) * 100)))
</script>

<template>
  <div
    role="progressbar"
    :aria-valuemin="0"
    :aria-valuemax="max"
    :aria-valuenow="modelValue ?? 0"
    :class="cn('w-full', $attrs.class as string | undefined)"
  >
    <div class="relative h-4 w-full overflow-hidden rounded-full bg-neutral-500/15">
      <div class="h-full bg-primary-foreground transition-all" :style="{ width: `${pct}%` }" />
    </div>
  </div>
</template>
