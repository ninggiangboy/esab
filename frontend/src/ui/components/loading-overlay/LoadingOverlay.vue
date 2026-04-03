<script setup lang="ts">
import { Spinner } from '@/ui/components/spinner'
import { cn } from '@/ui/lib/utils'

defineProps<{
  class?: string
  show?: boolean
}>()
</script>

<template>
  <div
    v-if="show !== false"
    :class="
      cn(
        'fixed inset-0 z-50 flex items-center justify-center bg-background/60 backdrop-blur-sm',
        $props.class,
      )
    "
  >
    <Spinner class="size-8 text-primary" />
  </div>
</template>
