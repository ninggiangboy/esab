<script setup lang="ts">
import {
  DialogContent,
  DialogDescription,
  DialogOverlay,
  DialogPortal,
  DialogRoot,
  DialogTitle,
} from 'radix-vue'

import { useSidebar } from '@/composables/useSidebar'
import { cn } from '@/lib/utils'

import { SIDEBAR_WIDTH_MOBILE } from './constants'

const props = withDefaults(
  defineProps<{
    side?: 'left' | 'right'
    variant?: 'sidebar' | 'floating' | 'inset'
    collapsible?: 'offcanvas' | 'icon' | 'none'
    class?: string
  }>(),
  {
    side: 'left',
    variant: 'sidebar',
    collapsible: 'icon',
  },
)

const { isMobile, state, openMobile } = useSidebar()
</script>

<template>
  <div
    v-if="collapsible === 'none'"
    data-slot="sidebar"
    :class="
      cn('bg-sidebar text-sidebar-foreground flex h-full w-(--sidebar-width) flex-col', props.class)
    "
  >
    <slot />
  </div>

  <template v-else-if="isMobile">
    <DialogRoot v-model:open="openMobile">
      <DialogPortal>
        <DialogOverlay
          class="data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:animate-in data-[state=open]:fade-in-0 fixed inset-0 z-50 bg-black/70 duration-300 md:hidden"
        />
        <DialogContent
          data-sidebar="sidebar"
          data-slot="sidebar"
          data-mobile="true"
          :class="
            cn(
              'bg-sidebar text-sidebar-foreground fixed z-50 flex flex-col gap-4 shadow-lg duration-300 ease-in-out md:hidden',
              'data-[state=closed]:animate-out data-[state=open]:animate-in',
              props.side === 'left'
                ? 'data-[state=closed]:slide-out-to-left data-[state=open]:slide-in-from-left inset-y-0 left-0 h-full border-r'
                : 'data-[state=closed]:slide-out-to-right data-[state=open]:slide-in-from-right inset-y-0 right-0 h-full border-l',
              'w-(--sidebar-width) p-0 outline-none',
              props.class,
            )
          "
          :style="{ '--sidebar-width': SIDEBAR_WIDTH_MOBILE }"
        >
          <DialogTitle class="sr-only">Sidebar</DialogTitle>
          <DialogDescription class="sr-only">Displays the mobile sidebar.</DialogDescription>
          <div class="flex h-full w-full flex-col">
            <slot />
          </div>
        </DialogContent>
      </DialogPortal>
    </DialogRoot>
  </template>

  <div
    v-else
    class="group peer text-sidebar-foreground hidden md:block"
    :data-state="state"
    :data-collapsible="state === 'collapsed' ? collapsible : ''"
    :data-variant="variant"
    :data-side="side"
    data-slot="sidebar"
  >
    <div
      data-slot="sidebar-gap"
      :class="
        cn(
          'relative w-(--sidebar-width) bg-transparent transition-[width] duration-200 ease-linear',
          'group-data-[collapsible=offcanvas]:w-0',
          'group-data-[side=right]:rotate-180',
          variant === 'floating' || variant === 'inset'
            ? 'group-data-[collapsible=icon]:w-[calc(var(--sidebar-width-icon)+(--spacing(4)))]'
            : 'group-data-[collapsible=icon]:w-(--sidebar-width-icon)',
        )
      "
    />
    <div
      data-slot="sidebar-container"
      :class="
        cn(
          'fixed inset-y-0 z-10 hidden h-svh w-(--sidebar-width) transition-[left,right,width] duration-200 ease-linear md:flex',
          side === 'left'
            ? 'left-0 group-data-[collapsible=offcanvas]:left-[calc(var(--sidebar-width)*-1)]'
            : 'right-0 group-data-[collapsible=offcanvas]:right-[calc(var(--sidebar-width)*-1)]',
          variant === 'floating' || variant === 'inset'
            ? 'p-2 group-data-[collapsible=icon]:w-[calc(var(--sidebar-width-icon)+(--spacing(4))+2px)]'
            : 'group-data-[collapsible=icon]:w-(--sidebar-width-icon) group-data-[side=left]:border-r group-data-[side=right]:border-l',
          props.class,
        )
      "
    >
      <div
        data-sidebar="sidebar"
        data-slot="sidebar-inner"
        class="bg-sidebar group-data-[variant=floating]:border-border flex h-full w-full flex-col group-data-[variant=floating]:rounded-lg group-data-[variant=floating]:border group-data-[variant=floating]:shadow-sm"
      >
        <slot />
      </div>
    </div>
  </div>
</template>
