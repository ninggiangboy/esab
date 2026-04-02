<script setup lang="ts">
import { Label } from 'radix-vue'

import { cn } from '@/lib/utils'

import { labelVariants } from './field-variants'
</script>

<template>
  <Label :class="cn(labelVariants(), $attrs.class as string | undefined)">
    <slot />
  </Label>
</template>
