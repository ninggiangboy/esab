<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <tr
    data-slot="table-row"
    :class="
      cn(
        'data-[state=selected]:bg-blue-200! dark:data-[state=selected]:bg-blue-900!',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </tr>
</template>
