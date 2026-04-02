<script setup lang="ts">
import { computed, useAttrs } from 'vue'
import { TooltipContent, TooltipPortal, TooltipRoot, TooltipTrigger } from 'radix-vue'

import {
  tooltipInverseContentClass,
  tooltipPanelContentClass,
} from '@/components/ui/tooltip-content-classes'
import { cn } from '@/lib/utils'

defineOptions({ inheritAttrs: false })

const props = withDefaults(
  defineProps<{
    side?: 'top' | 'right' | 'bottom' | 'left'
    align?: 'start' | 'center' | 'end'
    sideOffset?: number
    /** Inherits from app `TooltipProvider` when omitted. */
    delayDuration?: number
    /** `inverse` = dark compact; `panel` = bordered popover look. */
    variant?: 'inverse' | 'panel'
  }>(),
  { side: 'top', align: 'center', sideOffset: 4, variant: 'inverse' },
)

const attrs = useAttrs()

const contentClass = computed(() =>
  cn(
    props.variant === 'panel' ? tooltipPanelContentClass : tooltipInverseContentClass,
    attrs.class as string | undefined,
  ),
)

const rootBind = computed(() =>
  props.delayDuration != null ? { delayDuration: props.delayDuration } : {},
)
</script>

<template>
  <TooltipRoot v-bind="rootBind">
    <TooltipTrigger as-child>
      <slot name="trigger" />
    </TooltipTrigger>
    <TooltipPortal>
      <TooltipContent
        :side="props.side"
        :align="props.align"
        :side-offset="props.sideOffset"
        :class="contentClass"
      >
        <slot />
      </TooltipContent>
    </TooltipPortal>
  </TooltipRoot>
</template>
