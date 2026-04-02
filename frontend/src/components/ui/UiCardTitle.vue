<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div
    data-slot="card-title"
    :class="cn('leading-none font-semibold', $attrs.class as string | undefined)"
  >
    <slot />
  </div>
</template>
