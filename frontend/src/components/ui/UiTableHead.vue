<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <th
    data-slot="table-head"
    :class="
      cn(
        'px-3 text-foreground bg-background-tertiary h-10 text-left align-middle font-medium whitespace-nowrap [&:has([role=checkbox])]:pr-0 [&>[role=checkbox]]:translate-y-[2px]',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </th>
</template>
