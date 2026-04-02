<script setup lang="ts">
import {
  CircleHelp,
  Home,
  LayoutGrid,
  MessageCircle,
  Settings,
  Shapes,
  User,
} from 'lucide-vue-next'

import UiSidebar from '@/components/ui/sidebar/UiSidebar.vue'
import DashboardHeader from '@/components/ui/sidebar/DashboardHeader.vue'
import SidebarContent from '@/components/ui/sidebar/SidebarContent.vue'
import SidebarFooter from '@/components/ui/sidebar/SidebarFooter.vue'
import SidebarHeader from '@/components/ui/sidebar/SidebarHeader.vue'
import SidebarInset from '@/components/ui/sidebar/SidebarInset.vue'
import type { SidebarNavigationMenuItem } from '@/components/ui/sidebar/SidebarNavigationMenu.vue'
import SidebarNavigationMenu from '@/components/ui/sidebar/SidebarNavigationMenu.vue'
import SidebarProvider from '@/components/ui/sidebar/SidebarProvider.vue'
import SidebarRail from '@/components/ui/sidebar/SidebarRail.vue'
import UiAvatar from '@/components/ui/UiAvatar.vue'

const mainItems: SidebarNavigationMenuItem[] = [
  {
    title: 'Playground',
    to: '/dashboard',
    icon: Home,
    items: [
      { title: 'History', to: '/dashboard/history' },
      { title: 'Templates', to: '/dashboard/templates' },
      { title: 'Starred', to: '/dashboard/starred' },
    ],
  },
  {
    title: 'Profile',
    icon: User,
    to: '/dashboard/profile',
  },
  {
    title: 'Settings',
    icon: Settings,
    to: '/dashboard/settings',
  },
]

const projectItems: SidebarNavigationMenuItem[] = [
  {
    title: 'Design System',
    to: '/docs',
    icon: Shapes,
  },
  {
    title: 'UI Components',
    to: '/docs/ui/button',
    icon: LayoutGrid,
  },
]

const secondaryItems: SidebarNavigationMenuItem[] = [
  {
    title: 'Help',
    to: '/dashboard/help',
    icon: CircleHelp,
  },
  {
    title: 'Feedback',
    to: '/dashboard/feedback',
    icon: MessageCircle,
  },
]
</script>

<template>
  <SidebarProvider>
    <UiSidebar collapsible="icon">
      <SidebarHeader>
        <h1 class="text-2xl font-bold">BS</h1>
      </SidebarHeader>
      <SidebarContent>
        <SidebarNavigationMenu :items="mainItems" />
        <SidebarNavigationMenu title="Projects" :items="projectItems" class="mt-6" />
        <SidebarNavigationMenu :items="secondaryItems" class="mt-auto" />
      </SidebarContent>
      <SidebarFooter />
      <SidebarRail />
    </UiSidebar>
    <SidebarInset>
      <DashboardHeader>
        <UiAvatar fallback="HP" class="ml-auto" />
      </DashboardHeader>
      <div class="p-6">
        <RouterView />
      </div>
    </SidebarInset>
  </SidebarProvider>
</template>
