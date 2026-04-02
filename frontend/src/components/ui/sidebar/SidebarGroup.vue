<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div
    data-slot="sidebar-group"
    data-sidebar="group"
    :class="cn('relative flex w-full min-w-0 flex-col', $attrs.class as string | undefined)"
  >
    <slot />
  </div>
</template>
