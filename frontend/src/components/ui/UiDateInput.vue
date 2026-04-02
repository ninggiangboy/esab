<script setup lang="ts">
import { useAttrs } from 'vue'

import { cn } from '@/lib/utils'

defineOptions({ inheritAttrs: false })

const model = defineModel<string>({ default: '' })

const attrs = useAttrs()
</script>

<template>
  <input
    v-model="model"
    type="date"
    data-slot="date-input"
    :class="
      cn(
        'h-8 w-full rounded-sm bg-background-secondary px-3 py-1.5 text-sm shadow-sm ring-offset-background',
        'ring ring-inset ring-input focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary',
        'disabled:cursor-not-allowed disabled:opacity-80',
        '[&::-webkit-calendar-picker-indicator]:hidden [&::-webkit-calendar-picker-indicator]:appearance-none',
        '[&::-moz-calendar-picker-indicator]:hidden',
        attrs.class as string | undefined,
      )
    "
    v-bind="{ ...attrs, class: undefined }"
  />
</template>
