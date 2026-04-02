<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div
    data-slot="sidebar-group-content"
    data-sidebar="group-content"
    :class="cn('w-full text-sm', $attrs.class as string | undefined)"
  >
    <slot />
  </div>
</template>
