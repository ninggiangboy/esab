<script setup lang="ts">
import { Slot } from 'radix-vue'
import { computed, useAttrs } from 'vue'

import { cn } from '@/lib/utils'

const props = withDefaults(
  defineProps<{
    size?: 'sm' | 'md'
    isActive?: boolean
    asChild?: boolean
  }>(),
  {
    size: 'md',
    isActive: false,
    asChild: false,
  },
)

const attrs = useAttrs()
const classes = computed(() =>
  cn(
    'transition-colors font-medium text-sidebar-foreground ring-sidebar-ring hover:bg-sidebar-accent/60 hover:text-sidebar-accent-foreground active:bg-sidebar-accent active:text-sidebar-accent-foreground [&>svg]:text-sidebar-accent-foreground flex h-8 min-w-0 -translate-x-px items-center gap-2 overflow-hidden rounded px-2 outline-hidden focus-visible:ring-2 disabled:pointer-events-none disabled:opacity-50 aria-disabled:pointer-events-none aria-disabled:opacity-50 [&>span:last-child]:truncate [&>svg]:size-4 [&>svg]:shrink-0',
    'data-[active=true]:bg-sidebar-accent! data-[active=true]:text-primary-foreground!',
    props.size === 'sm' && 'text-xs',
    props.size === 'md' && 'text-sm',
    'group-data-[collapsible=icon]:hidden',
    attrs.class as string | undefined,
  ),
)
</script>

<template>
  <Slot
    v-if="asChild"
    data-slot="sidebar-menu-sub-button"
    data-sidebar="menu-sub-button"
    :data-size="size"
    :data-active="isActive ? 'true' : 'false'"
    :class="classes"
  >
    <slot />
  </Slot>
  <a
    v-else
    data-slot="sidebar-menu-sub-button"
    data-sidebar="menu-sub-button"
    :data-size="size"
    :data-active="isActive ? 'true' : 'false'"
    :class="classes"
    href="#"
    @click.prevent
  >
    <slot />
  </a>
</template>
