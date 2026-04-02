<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <tfoot
    data-slot="table-footer"
    :class="
      cn(
        'bg-neutral-400/10 border-t font-medium sticky bottom-0',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </tfoot>
</template>
