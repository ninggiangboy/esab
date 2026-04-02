<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <ul
    data-slot="sidebar-menu-sub"
    data-sidebar="menu-sub"
    :class="
      cn(
        'border-border ml-3.5 flex min-w-0 translate-x-px flex-col gap-1 border-l pl-2.5 py-0.5',
        'group-data-[collapsible=icon]:hidden',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </ul>
</template>
