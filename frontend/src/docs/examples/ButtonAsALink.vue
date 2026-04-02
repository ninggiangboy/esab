<script setup lang="ts">
import { SquareArrowOutUpRight } from 'lucide-vue-next'

import { buttonVariants } from '@/components/ui/button-variants'
import { cn } from '@/lib/utils'
</script>

<template>
  <a
    :class="cn(buttonVariants({ variant: 'default' }))"
    href="https://react-spectrum.adobe.com/react-aria/Button.html"
    target="_blank"
    rel="noopener noreferrer"
  >
    React Aria Button
    <SquareArrowOutUpRight />
  </a>
</template>
