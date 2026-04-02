<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div
    data-slot="card-action"
    :class="
      cn(
        'col-start-2 row-span-2 row-start-1 self-start justify-self-end',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </div>
</template>
