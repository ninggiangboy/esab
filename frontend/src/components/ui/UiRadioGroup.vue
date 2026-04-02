<script setup lang="ts">
import { RadioGroupRoot } from 'radix-vue'

import { cn } from '@/lib/utils'

const modelValue = defineModel<string>({ default: '' })

withDefaults(
  defineProps<{
    disabled?: boolean
    orientation?: 'horizontal' | 'vertical'
  }>(),
  { disabled: false, orientation: 'vertical' },
)
</script>

<template>
  <RadioGroupRoot
    v-model="modelValue"
    :disabled="disabled"
    :orientation="orientation"
    :class="
      cn(
        'grid gap-2',
        orientation === 'horizontal' && 'flex flex-row flex-wrap',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </RadioGroupRoot>
</template>
