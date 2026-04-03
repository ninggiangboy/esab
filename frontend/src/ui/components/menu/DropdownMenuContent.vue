<script setup lang="ts">
import { DropdownMenuContent as Reka, DropdownMenuPortal } from 'reka-ui'
import { cn } from '@/ui/lib/utils'

defineProps<{
  class?: string
}>()
</script>

<template>
  <DropdownMenuPortal>
    <Reka
      :class="cn('z-50 min-w-40 rounded-md border bg-popover p-1 shadow-md outline-none', $props.class)"
    >
      <slot />
    </Reka>
  </DropdownMenuPortal>
</template>
