<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div role="group" :class="cn('grid gap-2', $attrs.class as string | undefined)">
    <slot />
  </div>
</template>
