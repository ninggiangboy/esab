<script setup lang="ts">
import UiButton from '@/components/ui/UiButton.vue'
import UiTooltip from '@/components/ui/UiTooltip.vue'
</script>

<template>
  <div class="flex flex-wrap gap-4">
    <UiTooltip variant="panel">
      <template #trigger>
        <UiButton variant="outline">Custom tooltip</UiButton>
      </template>
      <div class="p-0.5">
        <h4 class="mb-1 text-sm font-semibold">Custom tooltip</h4>
        <p class="m-0 text-xs">Title and body with panel styling.</p>
      </div>
    </UiTooltip>
    <UiTooltip variant="panel">
      <template #trigger>
        <UiButton variant="outline">Rich content</UiButton>
      </template>
      <div class="p-0.5">
        <div class="mb-1 flex items-center gap-2">
          <div class="size-2 rounded-full bg-green-500" />
          <span class="text-sm font-medium">Status: Active</span>
        </div>
        <p class="m-0 text-xs">Rich layout inside the tooltip.</p>
      </div>
    </UiTooltip>
  </div>
</template>
