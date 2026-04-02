<script setup lang="ts">
import { Toggle as RadixToggle } from 'radix-vue'

import { cn } from '@/lib/utils'

import { type ToggleVariants, toggleVariants } from './toggle-variants'

withDefaults(
  defineProps<{
    variant?: ToggleVariants['variant']
    size?: ToggleVariants['size']
  }>(),
  { variant: 'default', size: 'default' },
)

const pressed = defineModel<boolean>('pressed', { default: false })
</script>

<template>
  <RadixToggle
    v-model:pressed="pressed"
    :class="cn(toggleVariants({ variant, size }), $attrs.class as string | undefined)"
  >
    <slot />
  </RadixToggle>
</template>
