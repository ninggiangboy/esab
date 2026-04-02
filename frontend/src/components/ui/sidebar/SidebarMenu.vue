<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <ul
    data-slot="sidebar-menu"
    data-sidebar="menu"
    :class="cn('flex w-full min-w-0 flex-col gap-1', $attrs.class as string | undefined)"
  >
    <slot />
  </ul>
</template>
