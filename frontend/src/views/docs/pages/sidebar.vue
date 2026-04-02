<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Sidebar</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <p class="text-muted-foreground">The collapsible sidebar and dashboard shell live in <code>layouts/DashboardLayout.vue</code> and <code>components/ui/sidebar/*</code>.</p>
    <div class="not-prose mt-4 rounded-lg border bg-card p-4">
      <RouterLink to="/dashboard" class="text-primary underline-offset-4 hover:underline">Open dashboard layout</RouterLink>
    </div>
  </article>
</template>
