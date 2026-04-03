<script setup lang="ts">
import { cn } from '@/ui/lib/utils'
import { computed } from 'vue'
import { RouterLink, useRoute } from 'vue-router'

type Item = { title: string; href: string }
type Group = { title: string; items: Item[] }

const route = useRoute()

const guideGroups: Group[] = [
  {
    title: 'Getting Started',
    items: [{ title: 'Introduction', href: '/docs/guide/introduction' }],
  },
]

const uiGroups: Group[] = [
  {
    title: 'Buttons',
    items: [{ title: 'Button', href: '/docs/ui/button' }],
  },
]

const groups = computed(() => (route.path.includes('/docs/ui') ? uiGroups : guideGroups))
</script>

<template>
  <div class="docs-sidebar-scroll px-3 pb-8 pt-2 overflow-y-auto max-h-[calc(100vh-8rem)]">
    <div v-for="group in groups" :key="group.title" class="mb-6">
      <div class="px-3 mb-2 text-xs font-medium text-muted-foreground tracking-wide uppercase">
        {{ group.title }}
      </div>
      <ul class="list-none space-y-0.5">
        <li v-for="item in group.items" :key="item.href">
          <RouterLink
            :to="item.href"
            :class="
              cn(
                'block rounded-md px-3 py-1.5 text-[13px] text-secondary-foreground no-underline hover:bg-sidebar-accent',
                route.path === item.href && 'bg-sidebar-accent font-medium text-sidebar-accent-foreground',
              )
            "
          >
            {{ item.title }}
          </RouterLink>
        </li>
      </ul>
    </div>
  </div>
</template>
