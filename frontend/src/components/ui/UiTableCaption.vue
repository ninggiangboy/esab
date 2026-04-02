<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <caption
    data-slot="table-caption"
    :class="cn('text-muted-foreground my-4 text-sm', $attrs.class as string | undefined)"
  >
    <slot />
  </caption>
</template>
