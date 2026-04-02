<script setup lang="ts">
import { AccordionContent } from 'radix-vue'

import { cn } from '@/lib/utils'
</script>

<template>
  <AccordionContent data-slot="accordion-content" class="overflow-hidden text-sm">
    <div :class="cn('pt-0 pb-4', $attrs.class as string | undefined)">
      <slot />
    </div>
  </AccordionContent>
</template>
