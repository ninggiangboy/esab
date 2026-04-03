<script setup lang="ts">
import { DropdownMenuItem as Reka } from 'reka-ui'
import { cn } from '@/ui/lib/utils'

defineProps<{
  class?: string
}>()
</script>

<template>
  <Reka
    :class="
      cn(
        'relative flex cursor-pointer select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none',
        'focus:bg-accent focus:text-accent-foreground data-[disabled]:pointer-events-none data-[disabled]:opacity-50',
        $props.class,
      )
    "
    v-bind="$attrs"
  >
    <slot />
  </Reka>
</template>
