<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div
    data-slot="skeleton"
    :class="cn('bg-neutral-500/20 animate-pulse rounded', $attrs.class as string)"
  >
    <slot />
  </div>
</template>
