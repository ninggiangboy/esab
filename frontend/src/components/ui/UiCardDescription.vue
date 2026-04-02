<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div
    data-slot="card-description"
    :class="cn('text-muted-foreground text-sm', $attrs.class as string | undefined)"
  >
    <slot />
  </div>
</template>
