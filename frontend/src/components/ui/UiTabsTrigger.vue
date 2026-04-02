<script setup lang="ts">
import { TabsTrigger } from 'radix-vue'

import { cn } from '@/lib/utils'

defineProps<{
  value: string
}>()
</script>

<template>
  <TabsTrigger
    :value="value"
    :class="
      cn(
        'inline-flex cursor-pointer justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium outline-none ring-offset-background transition-all',
        'data-[state=active]:bg-background data-[state=active]:text-foreground data-[state=active]:shadow-sm',
        'data-[disabled]:pointer-events-none data-[disabled]:opacity-50',
        'focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2',
        'group-data-[orientation=vertical]:w-full',
        $attrs.class as string | undefined,
      )
    "
  >
    <slot />
  </TabsTrigger>
</template>
