<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <td
    data-slot="table-cell"
    :class="
      cn(
        'p-3 align-middle whitespace-nowrap [&:has([role=checkbox])]:pr-0 [&>[role=checkbox]]:translate-y-[2px] truncate bg-inherit',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </td>
</template>
