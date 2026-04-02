<script setup lang="ts">
import { cn } from '@/lib/utils'

import { type BadgeVariants, badgeVariants } from './badge-variants'

withDefaults(
  defineProps<{
    variant?: BadgeVariants['variant']
  }>(),
  { variant: 'default' },
)
</script>

<template>
  <span
    data-slot="badge"
    :class="cn(badgeVariants({ variant }), $attrs.class as string | undefined)"
  >
    <slot />
  </span>
</template>
