<script setup lang="ts">
import SidebarTrigger from './SidebarTrigger.vue'
</script>

<template>
  <header
    class="sticky top-0 z-10 bg-background flex h-16 shrink-0 items-center gap-2 border-b w-full"
  >
    <div class="flex items-center gap-2 px-4 w-full md:px-6">
      <SidebarTrigger class="-ml-1" />
      <slot />
    </div>
  </header>
</template>
