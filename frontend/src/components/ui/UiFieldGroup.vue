<script setup lang="ts">
import { cn } from '@/lib/utils'

import { type FieldGroupVariants, fieldGroupVariants } from './field-variants'

withDefaults(
  defineProps<{
    variant?: FieldGroupVariants['variant']
  }>(),
  { variant: 'default' },
)
</script>

<template>
  <div
    role="group"
    :class="cn(fieldGroupVariants({ variant }), $attrs.class as string | undefined)"
  >
    <slot />
  </div>
</template>
