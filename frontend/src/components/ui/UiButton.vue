<script setup lang="ts">
import { useAttrs } from 'vue'

import { cn } from '@/lib/utils'

import { type ButtonVariants, buttonVariants } from './button-variants'

withDefaults(
  defineProps<{
    variant?: ButtonVariants['variant']
    size?: ButtonVariants['size']
    type?: 'button' | 'submit' | 'reset'
  }>(),
  {
    type: 'button',
  },
)

defineOptions({ inheritAttrs: false })
const attrs = useAttrs()
</script>

<template>
  <button
    data-slot="button"
    :type="type"
    :class="cn(buttonVariants({ variant, size }), attrs.class as string | undefined)"
    v-bind="{ ...attrs, class: undefined }"
  >
    <slot />
  </button>
</template>
