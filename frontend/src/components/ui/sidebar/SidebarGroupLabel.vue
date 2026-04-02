<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div
    data-slot="sidebar-group-label"
    data-sidebar="group-label"
    :class="
      cn(
        'text-sidebar-foreground/85 ring-sidebar-ring flex h-8 shrink-0 items-center rounded-md px-2 text-xs font-medium transition-[margin,opacity] duration-200 ease-linear focus-visible:ring-2 outline-hidden [&>svg]:size-4 [&>svg]:shrink-0',
        'group-data-[collapsible=icon]:-mt-8 group-data-[collapsible=icon]:opacity-0',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </div>
</template>
