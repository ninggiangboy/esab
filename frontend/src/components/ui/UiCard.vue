<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <div
    data-slot="card"
    :class="
      cn(
        'bg-card text-card-foreground flex flex-col gap-6 rounded-xl border py-6',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </div>
</template>
