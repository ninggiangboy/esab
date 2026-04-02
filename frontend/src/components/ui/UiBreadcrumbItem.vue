<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <li
    :class="cn('inline-flex items-center gap-1.5 sm:gap-2.5', $attrs.class as string | undefined)"
  >
    <slot />
  </li>
</template>
