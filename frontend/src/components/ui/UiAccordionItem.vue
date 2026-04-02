<script setup lang="ts">
import { AccordionItem } from 'radix-vue'

import { cn } from '@/lib/utils'

defineProps<{
  value: string
}>()
</script>

<template>
  <AccordionItem
    :value="value"
    data-slot="accordion-item"
    :class="cn('border-b last:border-b-0', $attrs.class as string | undefined)"
  >
    <slot />
  </AccordionItem>
</template>
