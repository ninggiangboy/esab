<script setup lang="ts">
import { RadioGroupIndicator, RadioGroupItem } from 'radix-vue'

import { cn } from '@/lib/utils'

defineProps<{
  value: string
  disabled?: boolean
}>()
</script>

<template>
  <RadioGroupItem
    :value="value"
    :disabled="disabled"
    :class="
      cn(
        'aspect-square size-4 rounded-full border border-primary text-primary shadow focus:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50',
        $attrs.class as string | undefined,
      )
    "
  >
    <RadioGroupIndicator class="flex items-center justify-center">
      <div class="size-2.5 rounded-full bg-current" />
    </RadioGroupIndicator>
  </RadioGroupItem>
</template>
