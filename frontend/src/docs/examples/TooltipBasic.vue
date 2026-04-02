<script setup lang="ts">
import UiButton from '@/components/ui/UiButton.vue'
import UiTooltip from '@/components/ui/UiTooltip.vue'
</script>

<template>
  <div class="flex flex-wrap gap-4">
    <UiTooltip variant="inverse">
      <template #trigger>
        <UiButton variant="outline">Basic tooltip</UiButton>
      </template>
      <p class="m-0">Simple tooltip text</p>
    </UiTooltip>
    <UiTooltip variant="inverse">
      <template #trigger>
        <UiButton variant="outline">Another tooltip</UiButton>
      </template>
      <p class="m-0">Another simple tooltip</p>
    </UiTooltip>
  </div>
</template>
