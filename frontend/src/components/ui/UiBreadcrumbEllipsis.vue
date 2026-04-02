<script setup lang="ts">
import { MoreHorizontal } from 'lucide-vue-next'

import { cn } from '@/lib/utils'
</script>

<template>
  <span
    role="presentation"
    aria-hidden="true"
    :class="cn('flex size-9 items-center justify-center', $attrs.class as string | undefined)"
    :data-slot="'breadcrumb-ellipsis'"
  >
    <MoreHorizontal class="size-4" />
  </span>
</template>
