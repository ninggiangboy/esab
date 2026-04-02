<script setup lang="ts">
import { ArrowRight } from 'lucide-vue-next'

import { docNavItems } from '@/docs/nav'
import UiButton from '@/components/ui/UiButton.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Documentation</h1>
    <p class="lead text-muted-foreground">
      UI reference and live examples ported from the sample docs. Each page matches the component
      MDX structure.
    </p>
    <ul class="not-prose mt-8 list-none columns-1 gap-3 space-y-3 p-0 sm:columns-2">
      <li v-for="item in docNavItems" :key="item.slug">
        <RouterLink
          :to="`/docs/ui/${item.slug}`"
          class="flex items-center justify-between gap-4 rounded-lg border bg-card p-4 no-underline transition-colors hover:bg-accent/50"
        >
          <span>
            <span class="font-medium text-foreground">{{ item.title }}</span>
          </span>
          <UiButton variant="outline" size="icon" class="shrink-0">
            <ArrowRight class="size-4" />
          </UiButton>
        </RouterLink>
      </li>
    </ul>
  </article>
</template>
