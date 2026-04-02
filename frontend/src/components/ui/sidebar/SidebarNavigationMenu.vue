<script setup lang="ts">
import { CollapsibleContent, CollapsibleRoot, CollapsibleTrigger } from 'radix-vue'
import { ChevronRight } from 'lucide-vue-next'
import type { Component } from 'vue'
import { computed } from 'vue'
import { RouterLink, useRoute } from 'vue-router'

import UiPopover from '@/components/ui/UiPopover.vue'
import { useSidebar } from '@/composables/useSidebar'
import { cn } from '@/lib/utils'

import SidebarGroup from './SidebarGroup.vue'
import SidebarGroupLabel from './SidebarGroupLabel.vue'
import SidebarMenu from './SidebarMenu.vue'
import SidebarMenuButton from './SidebarMenuButton.vue'
import SidebarMenuItem from './SidebarMenuItem.vue'
import SidebarMenuSub from './SidebarMenuSub.vue'
import SidebarMenuSubButton from './SidebarMenuSubButton.vue'
import SidebarMenuSubItem from './SidebarMenuSubItem.vue'

export interface SidebarNavigationSubItem {
  title: string
  to: string
}

export interface SidebarNavigationMenuItem {
  title: string
  to: string
  icon?: Component
  items?: SidebarNavigationSubItem[]
}

const props = defineProps<{
  items: SidebarNavigationMenuItem[]
  title?: string
  class?: string
}>()

const route = useRoute()
const { state, isMobile } = useSidebar()

const isCollapsed = computed(() => state.value === 'collapsed')
</script>

<template>
  <SidebarGroup :class="cn(props.class)">
    <SidebarGroupLabel v-if="title">{{ title }}</SidebarGroupLabel>
    <SidebarMenu>
      <template v-for="item in items" :key="item.title">
        <SidebarMenuItem v-if="!item.items?.length">
          <SidebarMenuButton
            :is-active="route.path.startsWith(item.to)"
            as-child
            :tooltip="item.title"
          >
            <RouterLink :to="item.to" class="flex w-full items-center gap-2">
              <component :is="item.icon" v-if="item.icon" />
              <span>{{ item.title }}</span>
            </RouterLink>
          </SidebarMenuButton>
        </SidebarMenuItem>

        <SidebarMenuItem v-else-if="isCollapsed && !isMobile">
          <UiPopover
            side="right"
            align="start"
            :side-offset="4"
            class="min-w-[150px] !w-auto max-w-xs !rounded-lg !p-2 bg-popover/80 backdrop-blur-xl shadow-popover outline-none"
          >
            <template #trigger>
              <SidebarMenuButton :tooltip="item.title">
                <component :is="item.icon" v-if="item.icon" />
                <span>{{ item.title }}</span>
                <ChevronRight
                  class="ml-auto transition-transform duration-200 group-data-[state=open]/collapsible:rotate-90 text-sidebar-foreground"
                />
              </SidebarMenuButton>
            </template>
            <p class="mb-2 text-xs font-medium text-sidebar-foreground">{{ item.title }}</p>
            <ul class="m-0 list-none space-y-0.5 p-0">
              <SidebarMenuSubItem v-for="sub in item.items" :key="sub.title">
                <SidebarMenuSubButton as-child :is-active="route.path.startsWith(sub.to)">
                  <RouterLink :to="sub.to">
                    <span>{{ sub.title }}</span>
                  </RouterLink>
                </SidebarMenuSubButton>
              </SidebarMenuSubItem>
            </ul>
          </UiPopover>
        </SidebarMenuItem>

        <CollapsibleRoot v-else as-child :default-open="true">
          <SidebarMenuItem class="group/collapsible">
            <CollapsibleTrigger as-child>
              <SidebarMenuButton :tooltip="item.title">
                <component :is="item.icon" v-if="item.icon" />
                <span>{{ item.title }}</span>
                <ChevronRight
                  class="ml-auto transition-transform duration-200 group-data-[state=open]/collapsible:rotate-90 text-sidebar-foreground"
                />
              </SidebarMenuButton>
            </CollapsibleTrigger>
            <CollapsibleContent class="mt-0.5">
              <SidebarMenuSub>
                <SidebarMenuSubItem v-for="sub in item.items" :key="sub.title">
                  <SidebarMenuSubButton as-child :is-active="route.path.startsWith(sub.to)">
                    <RouterLink :to="sub.to">
                      <span>{{ sub.title }}</span>
                    </RouterLink>
                  </SidebarMenuSubButton>
                </SidebarMenuSubItem>
              </SidebarMenuSub>
            </CollapsibleContent>
          </SidebarMenuItem>
        </CollapsibleRoot>
      </template>
    </SidebarMenu>
  </SidebarGroup>
</template>
