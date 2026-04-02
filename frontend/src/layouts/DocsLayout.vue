<script setup lang="ts">
import { Blocks, BookOpen, ChevronRight } from 'lucide-vue-next'

import { docNavItems } from '@/docs/nav'

const componentNavItems = docNavItems.map((item) => ({
  title: item.title,
  to: `/docs/ui/${item.slug}`,
}))

const nav = [
  {
    title: 'Components',
    items: componentNavItems,
  },
]
</script>

<template>
  <div class="min-h-screen bg-background">
    <header class="sticky top-0 z-10 border-b bg-background/95 backdrop-blur">
      <div class="mx-auto flex h-14 max-w-6xl items-center gap-2 px-4 md:px-6">
        <RouterLink to="/docs" class="flex items-center gap-2 font-semibold text-foreground">
          <BookOpen class="size-5 text-primary" />
          Docs
        </RouterLink>
        <span class="text-muted-foreground">/</span>
        <span class="text-sm text-muted-foreground">UI</span>
        <div class="flex-1" />
        <RouterLink to="/" class="text-sm text-muted-foreground hover:text-foreground">
          Home
        </RouterLink>
        <RouterLink to="/dashboard" class="text-sm text-muted-foreground hover:text-foreground">
          Dashboard
        </RouterLink>
      </div>
    </header>

    <div class="mx-auto max-w-6xl px-4 py-8 md:px-6">
      <nav
        class="mb-6 flex flex-wrap gap-x-4 gap-y-2 md:hidden"
        aria-label="Documentation sections"
      >
        <RouterLink
          v-for="item in componentNavItems"
          :key="item.to"
          :to="item.to"
          class="text-sm text-muted-foreground hover:text-foreground"
          active-class="text-primary font-medium"
        >
          {{ item.title }}
        </RouterLink>
      </nav>

      <div class="flex gap-8">
        <aside class="hidden w-52 shrink-0 md:block">
          <p
            class="mb-3 flex items-center gap-2 text-xs font-medium uppercase tracking-wide text-muted-foreground"
          >
            <Blocks class="size-3.5" />
            Reference
          </p>
          <nav class="space-y-4">
            <div v-for="section in nav" :key="section.title">
              <p class="mb-1.5 text-sm font-medium text-foreground">{{ section.title }}</p>
              <ul class="space-y-0.5 border-l border-border pl-3">
                <li v-for="item in section.items" :key="item.to">
                  <RouterLink
                    :to="item.to"
                    class="flex items-center gap-1 py-1 text-sm text-muted-foreground transition-colors hover:text-foreground"
                    active-class="!text-primary font-medium"
                  >
                    <ChevronRight class="size-3.5 opacity-50" />
                    {{ item.title }}
                  </RouterLink>
                </li>
              </ul>
            </div>
          </nav>
        </aside>

        <main class="min-w-0 flex-1">
          <RouterView />
        </main>
      </div>
    </div>
  </div>
</template>
