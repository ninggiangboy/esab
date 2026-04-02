<script setup lang="ts">
import { useAttrs } from 'vue'
import { PopoverContent, PopoverPortal, PopoverRoot, PopoverTrigger } from 'radix-vue'

import { popoverContentClass } from '@/components/ui/popover-content-classes'
import { cn } from '@/lib/utils'

defineOptions({ inheritAttrs: false })

withDefaults(
  defineProps<{
    side?: 'top' | 'right' | 'bottom' | 'left'
    align?: 'start' | 'center' | 'end'
    sideOffset?: number
  }>(),
  { side: 'bottom', align: 'start', sideOffset: 8 },
)

const attrs = useAttrs()
</script>

<template>
  <PopoverRoot v-bind="{ ...attrs, class: undefined }">
    <PopoverTrigger as-child>
      <slot name="trigger" />
    </PopoverTrigger>
    <PopoverPortal>
      <PopoverContent
        :side="side"
        :align="align"
        :side-offset="sideOffset"
        :class="cn(popoverContentClass, attrs.class as string | undefined)"
      >
        <slot />
      </PopoverContent>
    </PopoverPortal>
  </PopoverRoot>
</template>
