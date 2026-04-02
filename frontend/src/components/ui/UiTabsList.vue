<script setup lang="ts">
import { TabsList } from 'radix-vue'

import { cn } from '@/lib/utils'
</script>

<template>
  <TabsList
    :class="
      cn(
        'inline-flex h-10 items-center justify-center rounded-md bg-muted p-1 text-muted-foreground',
        'data-[orientation=vertical]:h-auto data-[orientation=vertical]:flex-col',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </TabsList>
</template>
