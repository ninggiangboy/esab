<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <thead
    data-slot="table-header"
    :class="cn('sticky top-0 z-[2]', $attrs.class as string | undefined)"
  >
    <slot />
  </thead>
</template>
