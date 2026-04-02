<script setup lang="ts">
import { cn } from '@/lib/utils'

defineOptions({ inheritAttrs: false })
</script>

<template>
  <select
    :class="
      cn(
        'shadow-sm flex h-8 w-full rounded-sm bg-background-secondary px-3 py-1.5 text-sm ring-offset-background',
        'ring-inset ring ring-input',
        'focus-visible:outline-none focus-visible:ring-primary focus-visible:ring-2',
        'disabled:cursor-not-allowed disabled:opacity-80',
        $attrs.class as string | undefined,
      )
    "
    v-bind="$attrs"
  >
    <slot />
  </select>
</template>
