<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div data-slot="card-content" :class="cn('px-6', $attrs.class as string | undefined)">
    <slot />
  </div>
</template>
