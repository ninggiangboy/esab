<script setup lang="ts">
import { Slot } from 'radix-vue'
import { TooltipContent, TooltipPortal, TooltipRoot, TooltipTrigger } from 'radix-vue'
import { computed, useAttrs } from 'vue'

import { tooltipInverseContentClass } from '@/components/ui/tooltip-content-classes'
import { useSidebar } from '@/composables/useSidebar'
import { cn } from '@/lib/utils'

import { type SidebarMenuButtonVariants, sidebarMenuButtonVariants } from './sidebar-menu-variants'

const props = withDefaults(
  defineProps<{
    isActive?: boolean
    variant?: SidebarMenuButtonVariants['variant']
    size?: SidebarMenuButtonVariants['size']
    tooltip?: string
    asChild?: boolean
  }>(),
  {
    isActive: false,
    variant: 'default',
    size: 'default',
    asChild: false,
  },
)

const attrs = useAttrs()
const { isMobile, state } = useSidebar()

const showTooltip = computed(
  () => !!props.tooltip && state.value === 'collapsed' && !isMobile.value,
)

const classes = computed(() =>
  cn(
    sidebarMenuButtonVariants({ variant: props.variant, size: props.size }),
    attrs.class as string | undefined,
  ),
)
</script>

<template>
  <template v-if="showTooltip">
    <TooltipRoot :delay-duration="300">
      <TooltipTrigger as-child>
        <Slot
          v-if="asChild"
          data-slot="sidebar-menu-button"
          data-sidebar="menu-button"
          :data-size="size"
          :data-active="isActive ? 'true' : 'false'"
          :class="classes"
        >
          <slot />
        </Slot>
        <button
          v-else
          type="button"
          data-slot="sidebar-menu-button"
          data-sidebar="menu-button"
          :data-size="size"
          :data-active="isActive ? 'true' : 'false'"
          :class="classes"
        >
          <slot />
        </button>
      </TooltipTrigger>
      <TooltipPortal>
        <TooltipContent
          side="right"
          :side-offset="4"
          :class="cn(tooltipInverseContentClass, 'dark:border')"
        >
          {{ tooltip }}
        </TooltipContent>
      </TooltipPortal>
    </TooltipRoot>
  </template>
  <Slot
    v-else-if="asChild"
    data-slot="sidebar-menu-button"
    data-sidebar="menu-button"
    :data-size="size"
    :data-active="isActive ? 'true' : 'false'"
    :class="classes"
  >
    <slot />
  </Slot>
  <button
    v-else
    type="button"
    data-slot="sidebar-menu-button"
    data-sidebar="menu-button"
    :data-size="size"
    :data-active="isActive ? 'true' : 'false'"
    :class="classes"
  >
    <slot />
  </button>
</template>
