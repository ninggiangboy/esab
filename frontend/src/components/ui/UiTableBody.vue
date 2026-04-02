<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <tbody
    data-slot="table-body"
    :class="
      cn(
        '[&_tr:nth-child(odd)]:bg-background-secondary [&_tr:nth-child(even)]:bg-background-tertiary',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </tbody>
</template>
