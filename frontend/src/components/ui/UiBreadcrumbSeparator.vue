<script setup lang="ts">
import { ChevronRight } from 'lucide-vue-next'

import { cn } from '@/lib/utils'
</script>

<template>
  <span
    role="presentation"
    aria-hidden="true"
    :class="cn('[&>svg]:size-3.5', $attrs.class as string | undefined)"
  >
    <slot><ChevronRight /></slot>
  </span>
</template>
