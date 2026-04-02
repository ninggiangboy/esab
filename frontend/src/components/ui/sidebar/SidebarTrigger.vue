<script setup lang="ts">
import { PanelLeft } from 'lucide-vue-next'

import { useSidebar } from '@/composables/useSidebar'
import { cn } from '@/lib/utils'

import UiButton from '../UiButton.vue'

const { toggleSidebar } = useSidebar()
</script>

<template>
  <UiButton
    data-sidebar="trigger"
    data-slot="sidebar-trigger"
    variant="ghost"
    size="icon"
    :class="cn('size-7', $attrs.class as string | undefined)"
    @click="toggleSidebar"
  >
    <PanelLeft class="size-5! stroke-1!" />
    <span class="sr-only">Toggle Sidebar</span>
  </UiButton>
</template>
